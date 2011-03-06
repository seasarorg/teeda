/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.mock;

import java.io.IOException;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.application.TreeStructure;

/**
 * @author shot
 * 
 */
public class MockResponseStateManagerImpl extends MockResponseStateManager {

    private TreeStructure struct_;

    private Object state_;

    public void writeState(FacesContext context, SerializedView state)
            throws IOException {
        //TODO need to notify something for test?
    }

    public Object getTreeStructureToRestore(FacesContext context, String viewId) {
        return struct_;
    }

    public Object getComponentStateToRestore(FacesContext context) {
        return state_;
    }

    public void setTreeStructureToRestore(TreeStructure struct) {
        struct_ = struct;
    }

    public void setComponentStateToRestore(Object state) {
        state_ = state;
    }

}
