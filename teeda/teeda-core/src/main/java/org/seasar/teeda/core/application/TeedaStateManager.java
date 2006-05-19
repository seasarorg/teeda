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

    private TreeStructureManager treeStructureManager_;

    protected abstract void restoreComponentStateFromClient(
            FacesContext context, UIViewRoot viewRoot, String renderKitId);

    protected abstract void restoreComponentStateFromServer(
            FacesContext context, UIViewRoot viewRoot);

    protected abstract UIViewRoot restoreTreeStructureFromClient(
            FacesContext context, String viewId, String renderKitId);

    protected abstract UIViewRoot restoreTreeStructureFromServer(
            FacesContext context, String viewId);

    public TreeStructureManager getTreeStructureManager() {
        return treeStructureManager_;
    }

    public void setTreeStructureManager(
            TreeStructureManager treeStructureManager) {
        treeStructureManager_ = treeStructureManager;
    }

    public abstract void removeSerializedView(String viewId);
}
