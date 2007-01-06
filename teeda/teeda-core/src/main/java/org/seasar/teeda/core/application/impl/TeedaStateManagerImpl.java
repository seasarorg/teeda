/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.application.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.ResponseStateManagerUtil;

/**
 * @author higa
 * @author shot
 */
public class TeedaStateManagerImpl extends TeedaStateManager implements
        Serializable {

    static final long serialVersionUID = 0L;

    private final Map serializedViews = new HashMap();

    public TeedaStateManagerImpl() {
    }

    public synchronized UIViewRoot restoreView(final FacesContext context,
            final String viewId, final String renderKitId) {
        assertRenderKitIdNotNull(renderKitId);
        final UIViewRoot viewRoot = restoreTreeStructure(context, viewId,
                renderKitId);
        if (viewRoot != null) {
            viewRoot.setViewId(viewId);
            restoreComponentState(context, viewRoot, renderKitId);
            // PortletSupport
            if (PortletUtil.isPortlet(context)) {
                removeSerializedView(viewId);
            }
        }
        return viewRoot;
    }

    public synchronized SerializedView saveSerializedView(
            final FacesContext context) throws IllegalStateException {
        final UIViewRoot viewRoot = context.getViewRoot();
        if (isSavingStateInClient(context)) {
            return createSerializedView(context);
        }
        if (!hasSerializedViewInServer(viewRoot.getViewId())) {
            saveSerializedViewToServer(viewRoot.getViewId(),
                    createSerializedView(context));
        }
        return null;
    }

    protected SerializedView createSerializedView(final FacesContext context) {
        final Object struct = getTreeStructureToSave(context);
        final Object state = getComponentStateToSave(context);
        return new SerializedView(struct, state);
    }

    public synchronized void removeSerializedView(final String viewId) {
        // PortletSupport
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && PortletUtil.isPortlet(context)) {
            if (!isSavingStateInClient(context)) {
                context.getExternalContext().getSessionMap().remove(
                        SERIALIZED_VIEW_ATTR + "-" + viewId);
                return;
            }
        }

        serializedViews.remove(viewId);
    }

    public synchronized void writeState(final FacesContext context,
            final SerializedView serializedView) throws IOException {
        if (isSavingStateInClient(context)) {
            final UIViewRoot viewRoot = context.getViewRoot();
            final ResponseStateManager responseStateManager = getResponseStateManager(
                    context, viewRoot.getRenderKitId());
            responseStateManager.writeState(context, serializedView);
        }
    }

    protected Object getComponentStateToSave(final FacesContext context) {
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return viewRoot.processSaveState(context);
    }

    protected Object getTreeStructureToSave(final FacesContext context) {
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return getTreeStructureManager().buildTreeStructure(viewRoot);
    }

    protected void restoreComponentState(final FacesContext context,
            final UIViewRoot viewRoot, final String renderKitId) {
        assertRenderKitIdNotNull(renderKitId);
        if (viewRoot.getRenderKitId() == null) {
            viewRoot.setRenderKitId(renderKitId);
        }
        if (isSavingStateInClient(context)) {
            restoreComponentStateFromClient(context, viewRoot, renderKitId);
        } else {
            restoreComponentStateFromServer(context, viewRoot);
        }
    }

    protected UIViewRoot restoreTreeStructure(final FacesContext context,
            final String viewId, final String renderKitId) {
        assertRenderKitIdNotNull(renderKitId);
        if (isSavingStateInClient(context)) {
            return restoreTreeStructureFromClient(context, viewId, renderKitId);
        }
        return restoreTreeStructureFromServer(context, viewId);
    }

    protected void restoreComponentStateFromClient(final FacesContext context,
            final UIViewRoot viewRoot, final String renderKitId) {
        final ResponseStateManager responseStateManager = getResponseStateManager(
                context, renderKitId);
        final Object state = responseStateManager
                .getComponentStateToRestore(context);
        viewRoot.processRestoreState(context, state);
    }

    protected void restoreComponentStateFromServer(final FacesContext context,
            final UIViewRoot viewRoot) {
        final SerializedView serializedView = getSerializedViewFromServer(viewRoot
                .getViewId());
        if (serializedView == null) {
            return;
        }
        final Object state = serializedView.getState();
        if (state == null) {
            return;
        }
        viewRoot.processRestoreState(context, state);
    }

    protected UIViewRoot restoreTreeStructureFromClient(
            final FacesContext context, final String viewId,
            final String renderKitId) {
        final ResponseStateManager responseStateManager = getResponseStateManager(
                context, renderKitId);
        final TreeStructure struct = (TreeStructure) responseStateManager
                .getTreeStructureToRestore(context, viewId);
        if (struct == null) {
            return null;
        }
        return (UIViewRoot) getTreeStructureManager().restoreTreeStructure(
                struct);
    }

    protected UIViewRoot restoreTreeStructureFromServer(
            final FacesContext context, final String viewId) {
        final SerializedView serializedView = getSerializedViewFromServer(viewId);
        if (serializedView == null) {
            return null;
        }
        final TreeStructure struct = (TreeStructure) serializedView
                .getStructure();
        return (UIViewRoot) getTreeStructureManager().restoreTreeStructure(
                struct);
    }

    private static void assertRenderKitIdNotNull(final String renderKitId) {
        if (renderKitId == null) {
            throw new IllegalArgumentException();
        }
    }

    protected SerializedView getSerializedViewFromServer(final String viewId) {
        // PortletSupport
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && PortletUtil.isPortlet(context)) {
            return (SerializedView) context.getExternalContext()
                    .getSessionMap().get(SERIALIZED_VIEW_ATTR + "-" + viewId);
        }

        return (SerializedView) serializedViews.get(viewId);
    }

    protected boolean hasSerializedViewInServer(final String viewId) {
        // PortletSupport
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && PortletUtil.isPortlet(context)) {
            return context.getExternalContext().getSessionMap().containsKey(
                    SERIALIZED_VIEW_ATTR + "-" + viewId);
        }

        return serializedViews.containsKey(viewId);
    }

    protected void saveSerializedViewToServer(final String viewId,
            final SerializedView serializedView) {
        // PortletSupport
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && PortletUtil.isPortlet(context)) {
            context.getExternalContext().getSessionMap().put(
                    SERIALIZED_VIEW_ATTR + "-" + viewId, serializedView);
            return;
        }

        serializedViews.put(viewId, serializedView);
    }

    private ResponseStateManager getResponseStateManager(
            final FacesContext context, final String renderKitId) {
        return ResponseStateManagerUtil.getResponseStateManager(context,
                renderKitId);
    }

}
