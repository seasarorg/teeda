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
package org.seasar.teeda.core.mock;

import java.io.IOException;

import javax.faces.application.StateManager;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class MockStateManager extends StateManager {

    private static final long serialVersionUID = 1L;

    public static final boolean MOCK_SAVING_STATE_CLIENT = true;

    public static final boolean MOCK_SAVING_STATE_SERVER = false;

    private Boolean isSavingStateClient = null;

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

    public UIViewRoot restoreView(FacesContext context, String viewId,
            String renderKitId) {
        throw new UnsupportedOperationException();
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId, String renderKitId) {
        return null;
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {
    }

    public boolean isSavingStateInClient(FacesContext context) {
        if (isSavingStateClient != null) {
            return isSavingStateClient.booleanValue();
        }
        return super.isSavingStateInClient(context);
    }

    public void setSavingStateInClient(boolean savingState) {
        isSavingStateClient = new Boolean(savingState);
    }
}
