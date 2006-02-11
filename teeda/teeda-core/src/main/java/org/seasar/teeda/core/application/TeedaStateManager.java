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
package org.seasar.teeda.core.application;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

import org.seasar.framework.util.ClassUtil;

/**
 * @author higa
 * 
 * TODO need to re-define.
 */
public class TeedaStateManager extends StateManager implements Serializable {

    static final long serialVersionUID = 0L;

    private static final String SERIALIZED_VIEW_ATTR = TeedaStateManager.class
            .getName()
            + ".SERIALIZED_VIEW";

    private static final String LAST_MODIFIED_ATTR = TeedaStateManager.class
            .getName()
            + ".LAST_MODIFIED";

    private transient RenderKitFactory renderKitFactory;

    // private transient ViewTemplateFactory viewTemplateFactory;

    public TeedaStateManager() {
    }

    public SerializedView saveSerializedView(FacesContext context)
            throws IllegalStateException {

        UIViewRoot viewRoot = context.getViewRoot();
        ExternalContext externalContext = context.getExternalContext();
        saveLastModifiedToServer(externalContext, viewRoot.getViewId());
        Object struct = getTreeStructureToSave(context);
        Object state = getComponentStateToSave(context);
        SerializedView serializedView = new SerializedView(struct, state);
        if (isSavingStateInClient(context)) {
            return serializedView;
        }
        saveSerializedViewToServer(externalContext, viewRoot.getViewId(),
                serializedView);
        return null;
    }

    protected void saveLastModifiedToServer(ExternalContext externalContext,
            String viewId) {
        Long lm = new Long(getLastModifiedFromFile(viewId));
        externalContext.getSessionMap().put(LAST_MODIFIED_ATTR + "-" + viewId,
                lm);
    }

    protected long getLastModifiedFromServer(ExternalContext externalContext,
            String viewId) {
        Long lm = (Long) externalContext.getSessionMap().get(
                LAST_MODIFIED_ATTR + "-" + viewId);
        if (lm != null) {
            return lm.longValue();
        }
        return 0;
    }

    protected void removeLastModifiedFromServer(
            ExternalContext externalContext, String viewId) {
        externalContext.getSessionMap().remove(
                LAST_MODIFIED_ATTR + "-" + viewId);
    }

    protected boolean isViewModified(ExternalContext externalContext,
            String viewId) {
        long lastModifiedFromServer = getLastModifiedFromServer(
                externalContext, viewId);
        long lastModifiedFromFile = getLastModifiedFromFile(viewId);
        return lastModifiedFromFile > lastModifiedFromServer;
    }

    protected long getLastModifiedFromFile(String viewId) {
        // ViewTemplateFactory vtf = getViewTemplateFactory();
        // ViewTemplate vt = vtf.getViewTemplate(viewId);
        // long lastModified = vt.getLastModified();
        // if (lastModified == 0) {
        // return lastModified;
        // }
        // ViewProcessor vp = (ViewProcessor) vt.getRootTagProcessor();
        // String[] includes = vp.getIncludes();
        // for (int i = 0; i < includes.length; ++i) {
        // vt = vtf.getViewTemplate(includes[i]);
        // long lm = vt.getLastModified();
        // if (lm > lastModified) {
        // lastModified = lm;
        // }
        // }
        // return lastModified;
        return 0;
    }

    protected SerializedView getSerializedViewFromServer(
            ExternalContext externalContext, String viewId) {

        return (SerializedView) externalContext.getSessionMap().get(
                SERIALIZED_VIEW_ATTR + "-" + viewId);
    }

    protected void saveSerializedViewToServer(ExternalContext externalContext,
            String viewId, SerializedView serializedView) {

        externalContext.getSessionMap().put(
                SERIALIZED_VIEW_ATTR + "-" + viewId, serializedView);
    }

    protected void removeSerializedViewFromSession(
            ExternalContext externalContext, String viewId) {

        externalContext.getSessionMap().remove(
                SERIALIZED_VIEW_ATTR + "-" + viewId);
    }

    protected Object getTreeStructureToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return buildTreeStructure(viewRoot);
    }

    protected Object getComponentStateToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return viewRoot.processSaveState(context);
    }

    protected TreeStructure buildTreeStructure(UIComponent component) {
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        struct.setChildren(buildChildrenTreeStructure(component));
        struct.setFacets(buildFacetsTreeStructure(component));
        return struct;
    }

    protected TreeStructure[] buildChildrenTreeStructure(UIComponent component) {
        if (component.getChildCount() > 0) {
            List children = component.getChildren();
            List structs = new ArrayList();
            for (int i = 0; i < children.size(); ++i) {
                UIComponent child = (UIComponent) children.get(i);
                if (!child.isTransient()) {
                    TreeStructure childStruct = buildTreeStructure(child);
                    structs.add(childStruct);
                }
            }
            return (TreeStructure[]) structs.toArray(new TreeStructure[structs
                    .size()]);
        }
        return null;
    }

    protected Object[] buildFacetsTreeStructure(UIComponent component) {
        Map facets = component.getFacets();
        if (!facets.isEmpty()) {
            List structs = new ArrayList();
            for (Iterator it = facets.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                UIComponent child = (UIComponent) entry.getValue();
                if (!child.isTransient()) {
                    String facetName = (String) entry.getKey();
                    TreeStructure struct = buildTreeStructure(child);
                    structs.add(new Object[] { facetName, struct });
                }
            }
            return structs.toArray();
        }
        return null;
    }

    public void writeState(FacesContext context, SerializedView serializedView)
            throws IOException {

        UIViewRoot viewRoot = context.getViewRoot();
        RenderKit renderKit = getRenderKit(context, viewRoot.getRenderKitId());
        renderKit.getResponseStateManager().writeState(context, serializedView);
    }

    protected RenderKit getRenderKit(FacesContext context, String renderKitId) {
        return getRenderKitFactory().getRenderKit(context, renderKitId);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId,
            String renderKitId) {
        ExternalContext extContext = context.getExternalContext();
        if (!isSavingStateInClient(context)
                && isViewModified(extContext, viewId)) {
            removeSerializedViewFromSession(extContext, viewId);
            removeLastModifiedFromServer(extContext, viewId);
            return null;
        }
        UIViewRoot viewRoot = restoreTreeStructure(context, viewId, renderKitId);
        if (viewRoot != null) {
            viewRoot.setViewId(viewId);
            restoreComponentState(context, viewRoot, renderKitId);
        }
        return viewRoot;
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId, String renderKitId) {

        if (isSavingStateInClient(context)) {
            return restoreTreeStructureFromClient(context, viewId, renderKitId);
        }
        return restoreTreeStructureFromServer(context, viewId);
    }

    protected UIViewRoot restoreTreeStructureFromClient(FacesContext context,
            String viewId, String renderKitId) {

        RenderKit renderKit = getRenderKit(context, renderKitId);
        ResponseStateManager responseStateManager = renderKit
                .getResponseStateManager();
        TreeStructure struct = (TreeStructure) responseStateManager
                .getTreeStructureToRestore(context, viewId);
        if (struct == null) {
            return null;
        }
        return (UIViewRoot) restoreTreeStructure(struct);
    }

    protected UIViewRoot restoreTreeStructureFromServer(FacesContext context,
            String viewId) {

        SerializedView serializedView = getSerializedViewFromServer(context
                .getExternalContext(), viewId);
        if (serializedView == null) {
            return null;
        }
        TreeStructure struct = (TreeStructure) serializedView.getStructure();
        return (UIViewRoot) restoreTreeStructure(struct);
    }

    protected UIComponent restoreTreeStructure(TreeStructure struct) {
        String className = struct.getComponentClassName();
        String id = struct.getComponentId();
        UIComponent component = (UIComponent) ClassUtil.newInstance(className);
        component.setId(id);
        restoreChildrenTreeStructure(component, struct.getChildren());
        restoreFacetsTreeStructure(component, struct.getFacets());
        return component;
    }

    protected void restoreChildrenTreeStructure(UIComponent component,
            TreeStructure[] structs) {
        if (structs != null) {
            for (int i = 0; i < structs.length; ++i) {
                UIComponent child = restoreTreeStructure(structs[i]);
                component.getChildren().add(child);
            }
        }
    }

    protected void restoreFacetsTreeStructure(UIComponent component,
            Object[] facets) {
        if (facets != null) {
            for (int i = 0, len = facets.length; i < len; i++) {
                Object[] array = (Object[]) facets[i];
                String facetName = (String) array[0];
                TreeStructure struct = (TreeStructure) array[1];
                UIComponent child = restoreTreeStructure(struct);
                component.getFacets().put(facetName, child);
            }
        }
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {

        if (viewRoot.getRenderKitId() == null) {
            viewRoot.setRenderKitId(renderKitId);
        }
        if (isSavingStateInClient(context)) {
            restoreComponentStateFromClient(context, viewRoot, renderKitId);
        } else {
            restoreComponentStateFromServer(context, viewRoot);
        }
    }

    protected void restoreComponentStateFromClient(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {

        RenderKit renderKit = getRenderKit(context, renderKitId);
        ResponseStateManager responseStateManager = renderKit
                .getResponseStateManager();
        Object state = responseStateManager.getComponentStateToRestore(context);
        viewRoot.processRestoreState(context, state);
    }

    protected void restoreComponentStateFromServer(FacesContext context,
            UIViewRoot viewRoot) {

        SerializedView serializedView = getSerializedViewFromServer(context
                .getExternalContext(), viewRoot.getViewId());
        if (serializedView == null) {
            return;
        }
        Object state = serializedView.getState();
        if (state == null) {
            return;
        }
        viewRoot.processRestoreState(context, state);
    }

    protected RenderKitFactory getRenderKitFactory() {
        if (renderKitFactory == null) {
            renderKitFactory = (RenderKitFactory) FactoryFinder
                    .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        }
        return renderKitFactory;
    }

    // protected ViewTemplateFactory getViewTemplateFactory() {
    // if (viewTemplateFactory == null) {
    // S2Container container = SingletonS2ContainerFactory.getContainer();
    // viewTemplateFactory = (ViewTemplateFactory) container
    // .getComponent(ViewTemplateFactory.class);
    // }
    // return viewTemplateFactory;
    // }

}