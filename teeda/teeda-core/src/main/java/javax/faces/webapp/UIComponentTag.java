/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.InternalConstants;
import javax.faces.internal.PageContextUtil;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.internal.WebAppUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public abstract class UIComponentTag implements Tag {

    //TODO need to re-think for tag problem with jsp
    private UIComponent component = null;

    private FacesContext context = null;

    private boolean created = false;

    private List createdComponents = null;

    private List createdFacets = null;

    protected PageContext pageContext = null;

    private Tag parent = null;

    private String binding = null;

    private String id = null;

    private String rendered = null;

    public void setBinding(String binding) throws JspException {
        if (!isValueReference(binding)) {
            throw new IllegalArgumentException();
        }
        this.binding = binding;
    }

    public void setId(String id) {
        if (null != id && id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public abstract String getComponentType();

    public UIComponent getComponentInstance() {
        return component;
    }

    public boolean getCreated() {
        return created;
    }

    public static UIComponentTag getParentUIComponentTag(PageContext context) {
        List list = PageContextUtil.getComponentTagStackAttribute(context);
        if (list != null) {
            return ((UIComponentTag) list.get(list.size() - 1));
        } else {
            return null;
        }
    }

    public abstract String getRendererType();

    public static boolean isValueReference(String value) {
        AssertionUtil.assertNotNull("value", value);
        if ((value.indexOf("#{") != -1)
                && (value.indexOf("#{") < value.indexOf('}'))) {
            return true;
        }
        return false;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public Tag getParent() {
        return parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public int doStartTag() throws JspException {
        setupFacesContext();
        setupResponseWriter();
        UIComponentTag parentTag = getParentUIComponentTag(pageContext);
        Map requestMap = context.getExternalContext().getRequestMap();
        Map componentIds = null;
        if (parentTag == null) {
            componentIds = new HashMap();
            requestMap.put(InternalConstants.GLOBAL_ID_VIEW, componentIds);
        } else {
            componentIds = (Map) requestMap
                    .get(InternalConstants.GLOBAL_ID_VIEW);
        }
        component = findComponent(context);

        boolean isAlreadyTagInstanced = false;
        String clientId = null;
        if (id != null) {
            clientId = component.getClientId(context);
            isAlreadyTagInstanced = (componentIds.get(clientId) == this);

            if (!isAlreadyTagInstanced && clientId != null) {
                if (!componentIds.containsKey(clientId)) {
                    componentIds.put(clientId, this);
                } else {
                    throw new JspException(new IllegalStateException(
                            "Duplicate component id: '"
                                    + clientId
                                    + "', first used in tag: '"
                                    + componentIds.get(clientId).getClass()
                                            .getName() + "'"));
                }
            }
        }

        if (!isAlreadyTagInstanced && parentTag != null) {
            if (getFacetName() == null) {
                parentTag.addChild(component);
            } else {
                parentTag.addFacet(getFacetName());
            }
        }

        try {
            if (!isSuppressed() && !component.getRendersChildren()) {
                encodeBegin();
                context.getResponseWriter().flush();
            }
        } catch (IOException e) {
            component = null;
            context = null;
            throw new JspException(e);
        }
        pushUIComponentTag();
        return getDoStartValue();
    }

    public void setupFacesContext() throws JspException {
        if (context != null) {
            return;
        }
        context = PageContextUtil.getCurrentFacesContextAttribute(pageContext);
        if (context == null) {
            context = FacesContext.getCurrentInstance();
            if (context == null) {
                throw new JspException("Cannot find FacesContext");
            }
            PageContextUtil.setCurrentFacesContextAttribute(pageContext,
                    context);
        }
    }

    public int doEndTag() throws JspException {
        popUIComponentTag();
        removeOldChildren();
        removeOldFacets();
        try {
            if (!isSuppressed()) {
                if (component.getRendersChildren()) {
                    encodeBegin();
                    encodeChildren();
                }
                encodeEnd();
            }
        } catch (IOException e) {
            throw new JspException(e);
        } finally {
            component = null;
            context = null;
        }
        created = false;
        return getDoEndValue();
    }

    public void release() {
        parent = null;
        binding = null;
        id = null;
        created = false;
        rendered = null;
    }

    protected void encodeBegin() throws IOException {
        component.encodeBegin(context);
    }

    protected void encodeChildren() throws IOException {
        component.encodeChildren(context);
    }

    protected void encodeEnd() throws IOException {
        component.encodeEnd(context);
    }

    public UIComponent findComponent(FacesContext context) throws JspException {
        if (component != null) {
            return component;
        }
        UIComponentTag parentTag = getParentUIComponentTag(pageContext);
        UIComponent parentComponent = null;
        if (parentTag != null) {
            parentComponent = parentTag.getComponentInstance();

            String newId = createNewId();

            String facetName = getFacetName();
            if (facetName != null) {
                component = (UIComponent) parentComponent.getFacets().get(
                        facetName);
                if (component == null) {
                    component = createFacet(context, parentComponent,
                            facetName, newId);
                }
            } else {
                component = getChild(parentComponent, newId);
                if (component == null) {
                    component = createChild(context, parentComponent, newId);
                }
            }
            return component;
        } else {
            //special case(almost for UIViewRoot).
            component = findComponentSpecially(context);
            return component;
        }
    }

    private UIComponent findComponentSpecially(FacesContext context) {
        UIViewRoot parentComponent = PageContextUtil
                .getCurrentViewRootAttribute(pageContext);
        if (parentComponent == null) {
            parentComponent = context.getViewRoot();
            PageContextUtil.setCurrentViewRootAttribute(pageContext,
                    parentComponent);
            if (parentComponent.getAttributes().get(
                    InternalConstants.CURRENT_VIEW_ROOT) == null) {
                setProperties(parentComponent);
                if (id != null) {
                    parentComponent.setId(id);
                }
                parentComponent.getAttributes().put(
                        InternalConstants.CURRENT_VIEW_ROOT,
                        InternalConstants.CURRENT_VIEW_ROOT);
            } else if (binding == null) {
                setProperties(parentComponent);
            }
        }
        return parentComponent;
    }

    protected int getDoEndValue() throws JspException {
        return EVAL_PAGE;
    }

    protected int getDoStartValue() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    protected FacesContext getFacesContext() {
        return context;
    }

    protected String getFacetName() {
        Tag parent = getParent();
        if (parent instanceof FacetTag) {
            return ((FacetTag) parent).getName();
        } else {
            return null;
        }
    }

    protected String getId() {
        return id;
    }

    protected boolean isSuppressed() {
        if (getFacetName() != null) {
            return true;
        }
        if (!component.isRendered()) {
            return true;
        }
        for (UIComponent c = component.getParent(); c != null; c = c
                .getParent()) {
            if (!c.isRendered() || c.getRendersChildren()) {
                return true;
            }
        }
        return false;
    }

    protected void setProperties(UIComponent component) {
        if (rendered != null) {
            if (isValueReference(rendered)) {
                component.setValueBinding("rendered", ValueBindingUtil
                        .createValueBinding(context, rendered));
            } else {
                component.setRendered(Boolean.valueOf(rendered).booleanValue());
            }
        }
        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }
    }

    protected void setupResponseWriter() {
        ResponseWriter writer = context.getResponseWriter();
        if (writer == null) {
            writer = WebAppUtil.buildResponseWriter(context, pageContext);
            context.setResponseWriter(writer);
        }
    }

    private void addChild(UIComponent child) {
        if (createdComponents == null) {
            createdComponents = new ArrayList();
        }
        createdComponents.add(child.getId());
    }

    private void addFacet(String name) {
        if (createdFacets == null) {
            createdFacets = new ArrayList();
        }
        createdFacets.add(name);
    }

    private UIComponent createComponent(FacesContext context, String newId) {
        UIComponent component = WebAppUtil.createComponent(context, binding,
                getComponentType());
        component.setId(newId);
        setProperties(component);
        return component;
    }

    private UIComponent createChild(FacesContext context, UIComponent parent,
            String componentId) {
        UIComponent component = createComponent(context, componentId);
        parent.getChildren().add(component);
        created = true;
        return component;
    }

    private UIComponent createFacet(FacesContext context, UIComponent parent,
            String name, String newId) {
        UIComponent component = createComponent(context, newId);
        parent.getFacets().put(name, component);
        created = true;
        return component;
    }

    private UIComponent getChild(UIComponent component, String componentId) {
        for (Iterator children = component.getChildren().iterator(); children
                .hasNext();) {
            UIComponent child = (UIComponent) children.next();
            if (componentId.equals(child.getId())) {
                return child;
            }
        }
        return null;
    }

    public void popUIComponentTag() {
        List list = PageContextUtil.getComponentTagStackAttribute(pageContext);
        if (list != null) {
            list.remove(list.size() - 1);
            if (list.size() < 1) {
                PageContextUtil.removeComponentStackAttribute(pageContext);
            }
        }
    }

    public void pushUIComponentTag() {
        List list = PageContextUtil.getComponentTagStackAttribute(pageContext);
        if (list == null) {
            list = new ArrayList();
            PageContextUtil.setComponentStackAttribute(pageContext, list);
        }
        list.add(this);
    }

    private void removeOldChildren() {
        List oldList = WebAppUtil.getCreatedComponentIds(component);
        if (oldList == null) {
            saveChildrenComponentAttribute();
            return;
        }
        for (Iterator olds = oldList.iterator(); olds.hasNext();) {
            String old = (String) olds.next();
            if (createdComponents == null || !createdComponents.contains(old)) {
                UIComponent child = component.findComponent(old);
                if (child != null) {
                    component.getChildren().remove(child);
                }
            }
        }
        saveChildrenComponentAttribute();
    }

    private void removeOldFacets() {
        List oldList = WebAppUtil.getCreatedFacetNames(component);
        if (oldList == null) {
            saveFacetsComponentAttribute();
            return;
        }
        for (Iterator olds = oldList.iterator(); olds.hasNext();) {
            String old = (String) olds.next();
            if (createdFacets == null || !createdFacets.contains(old)) {
                component.getFacets().remove(old);
            }
        }
        saveFacetsComponentAttribute();
    }

    private void saveChildrenComponentAttribute() {
        if (createdComponents != null) {
            WebAppUtil.setCreatedComponentIds(component, createdComponents);
        } else {
            WebAppUtil.removeCreatedComponentIds(component);
        }
        createdComponents = null;
    }

    private void saveFacetsComponentAttribute() {
        if (createdFacets != null) {
            WebAppUtil.setCreatedFacetNames(component, createdFacets);
        } else {
            WebAppUtil.removeCreatedFacetNames(component);
        }
        createdFacets = null;
    }

    private String createNewId() {
        if (id == null) {
            FacesContext context = PageContextUtil
                    .getCurrentFacesContextAttribute(pageContext);
            UIViewRoot viewRoot = context.getViewRoot();
            if (viewRoot == null) {
                viewRoot = PageContextUtil
                        .getCurrentViewRootAttribute(pageContext);
            }
            return viewRoot.createUniqueId();
        } else {
            return id;
        }
    }

}
