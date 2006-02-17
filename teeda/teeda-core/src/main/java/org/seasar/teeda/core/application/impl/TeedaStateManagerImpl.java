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
package org.seasar.teeda.core.application.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.util.ResponseStateManagerUtil;
import org.seasar.teeda.core.util.StateManagerUtil;

/**
 * @author higa
 * @author shot
 */
public class TeedaStateManagerImpl extends TeedaStateManager implements
        Serializable {

    static final long serialVersionUID = 0L;

    // TODO testing
    public TeedaStateManagerImpl() {
    }

    public UIViewRoot restoreView(FacesContext context, String viewId,
            String renderKitId) {
        assertRenderKitIdNotNull(renderKitId);
        UIViewRoot viewRoot = restoreTreeStructure(context, viewId, renderKitId);
        if (viewRoot != null) {
            viewRoot.setViewId(viewId);
            restoreComponentState(context, viewRoot, renderKitId);
            if (!isSavingStateInClient(context)) {
                ExternalContext extContext = context.getExternalContext();
                removeSerializedViewFromServer(extContext, viewId);
            }
        }
        return viewRoot;
    }

    public SerializedView saveSerializedView(FacesContext context)
            throws IllegalStateException {
        UIViewRoot viewRoot = context.getViewRoot();
        StateManagerUtil.assertComponentNoDuplicateId(viewRoot);
        Object struct = getTreeStructureToSave(context);
        Object state = getComponentStateToSave(context);
        SerializedView serializedView = new SerializedView(struct, state);
        if (isSavingStateInClient(context)) {
            return serializedView;
        }
        ExternalContext externalContext = context.getExternalContext();
        saveSerializedViewToServer(externalContext, viewRoot.getViewId(),
                serializedView);
        return null;
    }

    public void writeState(FacesContext context, SerializedView serializedView)
            throws IOException {
        if (isSavingStateInClient(context)) {
            UIViewRoot viewRoot = context.getViewRoot();
            ResponseStateManager responseStateManager = ResponseStateManagerUtil
                    .getResponseStateManager(context, viewRoot.getRenderKitId());
            responseStateManager.writeState(context, serializedView);
        }
    }

    protected Object getComponentStateToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return viewRoot.processSaveState(context);
    }

    protected Object getTreeStructureToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return getTreeStructureManager().buildTreeStructure(viewRoot);
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {
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

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId, String renderKitId) {
        assertRenderKitIdNotNull(renderKitId);
        if (isSavingStateInClient(context)) {
            return restoreTreeStructureFromClient(context, viewId, renderKitId);
        }
        return restoreTreeStructureFromServer(context, viewId);
    }

    protected void restoreComponentStateFromClient(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {
        ResponseStateManager responseStateManager = ResponseStateManagerUtil
                .getResponseStateManager(context, renderKitId);
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

    protected UIViewRoot restoreTreeStructureFromClient(FacesContext context,
            String viewId, String renderKitId) {
        ResponseStateManager responseStateManager = ResponseStateManagerUtil
                .getResponseStateManager(context, renderKitId);
        TreeStructure struct = (TreeStructure) responseStateManager
                .getTreeStructureToRestore(context, viewId);
        if (struct == null) {
            return null;
        }
        return (UIViewRoot) getTreeStructureManager().restoreTreeStructure(
                struct);
    }

    protected UIViewRoot restoreTreeStructureFromServer(FacesContext context,
            String viewId) {
        SerializedView serializedView = getSerializedViewFromServer(context
                .getExternalContext(), viewId);
        if (serializedView == null) {
            return null;
        }
        TreeStructure struct = (TreeStructure) serializedView.getStructure();
        return (UIViewRoot) getTreeStructureManager().restoreTreeStructure(
                struct);
    }

    private static void assertRenderKitIdNotNull(String renderKitId) {
        if (renderKitId == null) {
            throw new IllegalArgumentException();
        }
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

    protected void removeSerializedViewFromServer(
            ExternalContext externalContext, String viewId) {
        externalContext.getSessionMap().remove(
                SERIALIZED_VIEW_ATTR + "-" + viewId);
    }

}