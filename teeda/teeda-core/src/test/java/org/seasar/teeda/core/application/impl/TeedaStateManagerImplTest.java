package org.seasar.teeda.core.application.impl;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.application.TreeStructureManager;
import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockResponseStateManager;
import org.seasar.teeda.core.mock.MockResponseStateManagerImpl;
import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.mock.NullResponseStateManager;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TeedaStateManagerImplTest extends TeedaTestCase {

    // TODO testing restoreComponentState and writeState, getSerializedView
    public void testRestoreView() throws Exception {
    }

    public void testRestoreComponentStateFromServer() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        NotifyViewRoot component = new NotifyViewRoot();
        component.setViewId("id");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, "id");
        manager.saveSerializedViewToServer(getExternalContext(), "id", view);

        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), component);
        
        // # Assert #
        assertEquals(1, component.getNotifyCount());
    }
    
    public void testRestoreComponentStateFromServer_stateIsNull() throws Exception {
        // # Arrange #
        NotifyViewRoot component = new NotifyViewRoot();
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        component.setId("id");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, null);
        manager.saveSerializedViewToServer(getExternalContext(), "id", view);

        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), component);
        
        // # Assert #
        assertEquals(0, component.getNotifyCount());
    }
    
    public void testRestoreComponenentStateFromServer_serializedViewIsNull() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        NotifyViewRoot root = new NotifyViewRoot();
        
        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), root);
        
        // # Assert #
        assertEquals(0, root.getNotifyCount());
    }
    
    public void testRestoreTreeStructure_renderKitIdNull() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act #
        try {
            manager.restoreTreeStructure(getFacesContext(), "id", null);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    public void testRestoreTreeStructureFromClient_restoredStructureIsNull()
            throws Exception {
        // # Arrange #
        MockRenderKit renderKit = getRenderKit();
        renderKit.setResponseStateManager(new NullResponseStateManager());
        setRenderKit(renderKit);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act & Assert #
        assertNull(manager.restoreTreeStructureFromClient(getFacesContext(),
                "id", RenderKitFactory.HTML_BASIC_RENDER_KIT));
    }

    public void testRestoreTreeStructureFromClient_restoreSucceess()
            throws Exception {
        // # Arrange #
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("hoge");
        TreeStructure struct = new TreeStructure(root.getClass().getName(),
                root.getId());
        MockResponseStateManager responseStateManager = new MockResponseStateManagerImpl();
        responseStateManager.setTreeStructureToRestore(struct);
        getRenderKit().setResponseStateManager(responseStateManager);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);

        // # Act #
        UIViewRoot returnRoot = manager.restoreTreeStructureFromClient(
                getFacesContext(), "hoge",
                RenderKitFactory.HTML_BASIC_RENDER_KIT);

        // # Assert #
        assertNotNull(returnRoot);
        assertEquals("hoge", returnRoot.getId());
        assertTrue(returnRoot instanceof MockUIViewRoot);
    }

    public void testRestoreTreeStructureFromServer_serializedViewIsNull()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act & Assert #
        assertNull(manager.restoreTreeStructureFromServer(getFacesContext(),
                "id"));
    }

    public void testRestoreTreeStructureFromServer_restoreSuccess()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        UIViewRoot component = new UIViewRoot();
        component.setId("root");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, "state");
        manager.saveSerializedViewToServer(getExternalContext(), "id", view);
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);

        // # Act #
        UIViewRoot root = manager.restoreTreeStructureFromServer(
                getFacesContext(), "id");

        // # Arrange #
        assertNotNull(root);
        assertEquals("root", root.getId());
    }

    private static class NotifyViewRoot extends UIViewRoot {

        private int count = 0;
        public NotifyViewRoot() {
        }
        
        public void processRestoreState(FacesContext context, Object state) {
            count++;
        }
        
        public void reset() {
            count = 0;
        }
        
        public int getNotifyCount() {
            return count;
        }
    }
}
