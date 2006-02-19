package org.seasar.teeda.core.application.impl;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.application.TreeStructureManager;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TeedaStateManagerImplTest extends TeedaTestCase {

    //TODO test save and restore view
    public void testRestoreView() throws Exception {
    }
    
    public void testRestoreTreeStructureFromServer_serializedViewIsNull() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        
        // # Act & Assert #
        assertNull(manager.restoreTreeStructureFromServer(getFacesContext(), "id"));
    }
    
    public void testRestoreTreeStructureFromServer_restoreSuccess() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        UIViewRoot component = new UIViewRoot();
        component.setId("root");
        TreeStructure struct = new TreeStructure(component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, "state");
        manager.saveSerializedViewToServer(getExternalContext(), "id", view);
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);
        
        // # Act #
        UIViewRoot root = manager.restoreTreeStructureFromServer(getFacesContext(), "id");
        
        // # Arrange #
        assertNotNull(root);
        assertEquals("root", root.getId());
    }

    
    
}
