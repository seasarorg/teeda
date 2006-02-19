package org.seasar.teeda.core.application.impl;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIViewRoot;
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

}
