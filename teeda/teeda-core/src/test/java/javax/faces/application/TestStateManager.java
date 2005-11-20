package javax.faces.application;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.mock.MockExternalContext;
import javax.faces.mock.MockFacesContext;
import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.seasar.framework.aop.interceptors.MockInterceptor;


public class TestStateManager extends TestCase {

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

        MockInterceptor mi = new MockInterceptor();
        mi.setReturnValue("getInitParameter", StateManager.STATE_SAVING_METHOD_CLIENT);
        ServletContext servletContext = (ServletContext)mi.createProxy(ServletContext.class);
        MockExternalContext externalContext = new MockExternalContext(servletContext);
        MockFacesContext context = new MockFacesContext(externalContext);
        assertTrue(stateManager.isSavingStateClient(context));
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
