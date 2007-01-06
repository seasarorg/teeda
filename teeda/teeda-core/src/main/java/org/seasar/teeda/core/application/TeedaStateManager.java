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
package org.seasar.teeda.core.application;

import javax.faces.application.StateManager;

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

}
