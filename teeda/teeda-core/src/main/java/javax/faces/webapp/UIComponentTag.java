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
import javax.faces.internal.AssertionUtil;
import javax.faces.internal.PageContextUtil;
import javax.faces.internal.WebAppConstants;
import javax.faces.internal.WebAppUtils;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author shot
 */
public abstract class UIComponentTag implements Tag {

    //TODO need to re-think for tag problem with jsp
    private UIComponent component_ = null;

    private FacesContext context_ = null;

    private boolean created_ = false;

    private List createdComponents_ = null;

    private List createdFacets_ = null;

    protected PageContext pageContext_ = null;

    private Tag parent_ = null;

    private String binding_ = null;

    private String id_ = null;

    private String rendered_ = null;

    public void setBinding(String binding) throws JspException {
        if (!isValueReference(binding)) {
            throw new IllegalArgumentException();
        }
        binding_ = binding;
    }

    public void setId(String id) {
        if (null != id && id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            throw new IllegalArgumentException();
        }
        id_ = id;
    }

    public void setRendered(String rendered) {
        rendered_ = rendered;
    }

    public abstract String getComponentType();

    public UIComponent getComponentInstance() {
        return component_;
    }

    public boolean getCreated() {
        return created_;
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
        pageContext_ = pageContext;
    }

    public Tag getParent() {
        return parent_;
    }

    public void setParent(Tag parent) {
        parent_ = parent;
    }

    public int doStartTag() throws JspException {
        context_ = PageContextUtil
                .getCurrentFacesContextAttribute(pageContext_);
        if (context_ == null) {
            context_ = FacesContext.getCurrentInstance();
            if (context_ == null) {
                throw new JspException("Cannot find FacesContext");
            }
            PageContextUtil.setCurrentFacesContextAttribute(pageContext_,
                    context_);
        }
        setupResponseWriter();
        UIComponentTag parentTag = getParentUIComponentTag(pageContext_);
        Map requestMap = context_.getExternalContext().getRequestMap();
        Map componentIds = null;
        if (parentTag == null) {
            componentIds = new HashMap();
            requestMap.put(WebAppConstants.GLOBAL_ID_VIEW, componentIds);
        } else {
            componentIds = (Map) requestMap.get(WebAppConstants.GLOBAL_ID_VIEW);
        }
        component_ = findComponent(context_);

        boolean isAlreadyTagInstanced = false;
        String clientId = null;
        if (id_ != null) {
            clientId = component_.getClientId(context_);
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
                parentTag.addChild(component_);
            } else {
                parentTag.addFacet(getFacetName());
            }
        }

        try {
            if (!isSuppressed() && !component_.getRendersChildren()) {
                encodeBegin();
                context_.getResponseWriter().flush();
            }
        } catch (IOException e) {
            component_ = null;
            context_ = null;
            throw new JspException(e);
        }
        pushUIComponentTag();
        return getDoStartValue();
    }

    public int doEndTag() throws JspException {
        popUIComponentTag();
        removeOldChildren();
        removeOldFacets();
        try {
            if (!isSuppressed()) {
                if (component_.getRendersChildren()) {
                    encodeBegin();
                    encodeChildren();
                }
                encodeEnd();
            }
        } catch (IOException e) {
            throw new JspException(e);
        } finally {
            component_ = null;
            context_ = null;
        }
        created_ = false;
        return getDoEndValue();
    }

    public void release() {
        parent_ = null;
        binding_ = null;
        id_ = null;
        created_ = false;
        rendered_ = null;
    }

    protected void encodeBegin() throws IOException {
        component_.encodeBegin(context_);
    }

    protected void encodeChildren() throws IOException {
        component_.encodeChildren(context_);
    }

    protected void encodeEnd() throws IOException {
        component_.encodeEnd(context_);
    }

    protected UIComponent findComponent(FacesContext context)
            throws JspException {
        if (component_ != null) {
            return component_;
        }
        UIComponentTag parentTag = getParentUIComponentTag(pageContext_);
        UIComponent parentComponent = null;
        if (parentTag != null) {
            parentComponent = parentTag.getComponentInstance();

            String newId = createNewId();

            String facetName = getFacetName();
            if (facetName != null) {
                component_ = (UIComponent) parentComponent.getFacets().get(
                        facetName);
                if (component_ == null) {
                    component_ = createFacet(context, parentComponent,
                            facetName, newId);
                }
            } else {
                component_ = getChild(parentComponent, newId);
                if (component_ == null) {
                    component_ = createChild(context, parentComponent, newId);
                }
            }
            return component_;
        } else {
            //special case(almost for UIViewRoot).
            component_ = findComponentSpecially(context);
            return component_;
        }
    }

    private UIComponent findComponentSpecially(FacesContext context) {
        UIComponent parentComponent = PageContextUtil
                .getCurrentViewRootAttribute(pageContext_);
        if (parentComponent == null) {
            parentComponent = context.getViewRoot();
            PageContextUtil.setCurrentViewRootAttribute(pageContext_,
                    parentComponent);
            if (parentComponent.getAttributes().get(
                    WebAppConstants.CURRENT_VIEW_ROOT) == null) {
                setProperties(parentComponent);
                if (id_ != null) {
                    parentComponent.setId(id_);
                }
                parentComponent.getAttributes().put(
                        WebAppConstants.CURRENT_VIEW_ROOT,
                        WebAppConstants.CURRENT_VIEW_ROOT);
            } else if (binding_ == null) {
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
        return context_;
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
        return id_;
    }

    protected boolean isSuppressed() {
        if (getFacetName() != null) {
            return true;
        }
        if (!component_.isRendered()) {
            return true;
        }
        for (UIComponent c = component_.getParent(); c != null; c = c
                .getParent()) {
            if (!c.isRendered() || c.getRendersChildren()) {
                return true;
            }
        }
        return false;
    }

    protected void setProperties(UIComponent component) {
        if (rendered_ != null) {
            if (isValueReference(rendered_)) {
                component.setValueBinding("rendered", WebAppUtils
                        .createValueBindingByApplication(component, context_,
                                rendered_));
            } else {
                component
                        .setRendered(Boolean.valueOf(rendered_).booleanValue());
            }
        }
        if (getRendererType() != null) {
            component.setRendererType(getRendererType());
        }
    }

    protected void setupResponseWriter() {
        ResponseWriter writer = context_.getResponseWriter();
        if (writer == null) {
            writer = WebAppUtils.buildResponseWriter(context_, pageContext_);
            context_.setResponseWriter(writer);
        }
    }

    private void addChild(UIComponent child) {
        if (createdComponents_ == null) {
            createdComponents_ = new ArrayList();
        }
        createdComponents_.add(child.getId());
    }

    private void addFacet(String name) {
        if (createdFacets_ == null) {
            createdFacets_ = new ArrayList();
        }
        createdFacets_.add(name);
    }

    private UIComponent createComponent(FacesContext context, String newId) {
        UIComponent component = WebAppUtils.createComponent(context_, binding_,
                getComponentType());
        component.setId(newId);
        setProperties(component);
        return component;
    }

    private UIComponent createChild(FacesContext context, UIComponent parent,
            String componentId) {
        UIComponent component = createComponent(context, componentId);
        UIComponentTag parentTag = getParentUIComponentTag(pageContext_);
        parent.getChildren().add(parentTag.getIndex(), component);
        created_ = true;
        return component;
    }

    private UIComponent createFacet(FacesContext context, UIComponent parent,
            String name, String newId) {
        UIComponent component = createComponent(context, newId);
        parent.getFacets().put(name, component);
        created_ = true;
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

    private int getIndex() {
        return (createdComponents_ != null) ? createdComponents_.size() : 0;
    }

    private void popUIComponentTag() {
        List list = PageContextUtil.getComponentTagStackAttribute(pageContext_);
        if (list != null) {
            list.remove(list.size() - 1);
            if (list.size() < 1) {
                PageContextUtil.removeComponentStackAttribute(pageContext_);
            }
        }
    }

    private void pushUIComponentTag() {
        List list = PageContextUtil.getComponentTagStackAttribute(pageContext_);
        if (list == null) {
            list = new ArrayList();
            PageContextUtil.setComponentStackAttribute(pageContext_, list);
        }
        list.add(this);
    }

    private void removeOldChildren() {
        List oldList = WebAppUtils.getCreatedComponentIds(component_);
        if (oldList == null) {
            saveChildrenComponentAttribute();
            return;
        }
        for (Iterator olds = oldList.iterator(); olds.hasNext();) {
            String old = (String) olds.next();
            if (createdComponents_ == null || !createdComponents_.contains(old)) {
                UIComponent child = component_.findComponent(old);
                if (child != null) {
                    component_.getChildren().remove(child);
                }
            }
        }
        saveChildrenComponentAttribute();
    }

    private void removeOldFacets() {
        List oldList = WebAppUtils.getCreatedFacetNames(component_);
        if (oldList == null) {
            saveFacesComponentAttribute();
            return;
        }
        for (Iterator olds = oldList.iterator(); olds.hasNext();) {
            String old = (String) olds.next();
            if (createdFacets_ == null || !createdFacets_.contains(old)) {
                component_.getFacets().remove(old);
            }
        }
        saveFacesComponentAttribute();
    }

    private void saveChildrenComponentAttribute() {
        if (createdComponents_ != null) {
            WebAppUtils.setCreatedComponentIds(component_, createdComponents_);
        } else {
            WebAppUtils.removeCreatedComponentIds(component_);
        }
        createdComponents_ = null;
    }

    private void saveFacesComponentAttribute() {
        if (createdFacets_ != null) {
            WebAppUtils.setCreatedFacetNames(component_, createdComponents_);
        } else {
            WebAppUtils.removeCreatedFacetNames(component_);
        }
        createdFacets_ = null;
    }

    private String createNewId() {
        if (id_ == null) {
            FacesContext context = PageContextUtil
                    .getCurrentFacesContextAttribute(pageContext_);
            return context.getViewRoot().createUniqueId();
        } else {
            return id_;
        }
    }

}
