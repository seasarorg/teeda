/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import javax.faces.application.StateManager;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.NotImplementedException;

/**
 * @author shot
 */
public class MockStateManager extends StateManager {

    public SerializedView saveSerializedView(FacesContext context) {
        return null;
    }

    public void writeState(FacesContext context, SerializedView state)
            throws IOException {
    }

    protected Object getTreeStructureToSave(FacesContext context) {
        return null;
    }

    protected Object getComponentStateToSave(FacesContext context) {
        return null;
    }

    public UIViewRoot restoteView(FacesContext context, String viewId) {
        return null;
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId) {
        return null;
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot) {
    }

    public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
        // TODO Auto-generated method stub
        throw new NotImplementedException();
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context, String viewId, String renderKitId) {
        // TODO Auto-generated method stub
        return null;
    }

    protected void restoreComponentState(FacesContext context, UIViewRoot viewRoot, String renderKitId) {
        // TODO Auto-generated method stub
    }

}
