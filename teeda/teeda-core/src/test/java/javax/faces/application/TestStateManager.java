package javax.faces.application;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestStateManager extends TeedaTestCase {

    public TestStateManager(String name) {
        super(name);
    }

    public void testIsSavingStateClient() {
        
        StateManager stateManager = new StateManagerImpl();
        try{
            stateManager.isSavingStateClient(null);
            fail();
        }catch(NullPointerException e){
            assertTrue(true);
        }

        getServletContext().setInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME, StateManager.STATE_SAVING_METHOD_CLIENT);
        MockFacesContext facesContext = getFacesContext();
        assertTrue(stateManager.isSavingStateClient(facesContext));
    }

    private static class StateManagerImpl extends StateManager{

        public SerializedView saveSerializedView(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        public void writeState(FacesContext context, SerializedView state) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected Object getTreeStructureToSave(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        protected Object getComponentStateToSave(FacesContext context) {
            throw new UnsupportedOperationException();
        }

        public UIViewRoot restoteView(FacesContext context, String viewId) {
            throw new UnsupportedOperationException();
        }

        protected UIViewRoot restoreTreeStructure(FacesContext context, String viewId) {
            throw new UnsupportedOperationException();
        }

        protected void restoreComponentState(FacesContext context, UIViewRoot viewRoot) {
            throw new UnsupportedOperationException();
        }
        
    }

}
