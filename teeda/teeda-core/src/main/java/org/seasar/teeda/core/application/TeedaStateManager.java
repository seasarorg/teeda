/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.application.StateManager;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public abstract class TeedaStateManager extends StateManager {

    protected static final String SERIALIZED_VIEW_ATTR = TeedaStateManager.class
            .getName()
            + ".SERIALIZED_VIEW";

    private TreeStructureManager treeStructureManager;

    public TreeStructureManager getTreeStructureManager() {
        return treeStructureManager;
    }

    public void setTreeStructureManager(
            TreeStructureManager treeStructureManager) {
        this.treeStructureManager = treeStructureManager;
    }

    public abstract void removeSerializedView(String viewId);

    public void saveViewToServer(FacesContext context, UIViewRoot viewRoot)
            throws IllegalStateException {

        SerializedView serializedView = createSerializedView(context, viewRoot);
        saveSerializedViewToServer(context, viewRoot.getViewId(),
                serializedView);
    }

    public Object getComponentStateToSave(FacesContext context,
            UIViewRoot viewRoot) {
        if (viewRoot.isTransient()) {
            return null;
        }
        return viewRoot.processSaveState(context);
    }

    public Object getTreeStructureToSave(FacesContext context,
            UIViewRoot viewRoot) {
        if (viewRoot.isTransient()) {
            return null;
        }
        return getTreeStructureManager().buildTreeStructure(viewRoot);
    }

    protected SerializedView createSerializedView(FacesContext context) {
        Object struct = getTreeStructureToSave(context);
        Object state = getComponentStateToSave(context);
        return new SerializedView(struct, state);
    }

    protected SerializedView createSerializedView(FacesContext context,
            UIViewRoot viewRoot) {
        Object struct = getTreeStructureToSave(context, viewRoot);
        Object state = getComponentStateToSave(context, viewRoot);
        return new SerializedView(struct, state);
    }

    protected abstract void saveSerializedViewToServer(FacesContext context,
            String viewId, SerializedView serializedView);

    public abstract boolean hasSerializedView(FacesContext context,
            String viewId);
}
