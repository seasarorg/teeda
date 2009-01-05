/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package javax.faces.application;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class StateManagerTest extends TeedaTestCase {

    public StateManagerTest(String name) {
        super(name);
    }

    public void testContants() throws Exception {
        assertEquals("client", StateManager.STATE_SAVING_METHOD_CLIENT);
        assertEquals("javax.faces.STATE_SAVING_METHOD",
                StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        assertEquals("server", StateManager.STATE_SAVING_METHOD_SERVER);
    }

    public void testIsSavingStateClient() {

        StateManager stateManager = new StateManagerImpl();
        try {
            stateManager.isSavingStateInClient(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_CLIENT);
        MockFacesContext facesContext = getFacesContext();
        assertTrue(stateManager.isSavingStateInClient(facesContext));
    }

    private static class StateManagerImpl extends StateManager {

        public SerializedView saveSerializedView(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        public void writeState(FacesContext context, SerializedView state)
                throws IOException {
            throw new UnsupportedOperationException();
        }

        protected Object getTreeStructureToSave(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        protected Object getComponentStateToSave(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        public UIViewRoot restoreView(FacesContext context, String viewId,
                String renderKitId) {
            throw new UnsupportedOperationException();
        }

        protected UIViewRoot restoreTreeStructure(FacesContext context,
                String viewId, String renderKitId) {
            throw new UnsupportedOperationException();
        }

        protected void restoreComponentState(FacesContext context,
                UIViewRoot viewRoot, String renderKitId) {
            throw new UnsupportedOperationException();
        }

    }

}
