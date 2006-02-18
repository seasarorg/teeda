package org.seasar.teeda.core.application.impl;

import junit.framework.TestCase;

import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.mock.MockUIComponent;

public class TreeStructureManagerImplTest extends TestCase {

    //TODO testing
    public void testBuildTreeStructure_simple() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent component = new MockUIComponent();
        component.setId("id");

        // # Act #
        TreeStructure struct = manager.buildTreeStructure(component);
        
        // # Assert #
        assertEquals(component.getClass().getName(), struct.getComponentClassName());
        assertEquals("id", struct.getComponentId());
    }
    
    public void testBuildChildrenTreeStructure_simple() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        MockUIComponent child = new MockUIComponent();
        child.setId("child");
        parent.getChildren().add(child);

        // # Act #
        TreeStructure[] structs = manager.buildChildrenTreeStructure(parent);

        // # Assert #
        assertNotNull(structs);
        assertTrue(structs.length == 1);
        TreeStructure struct = structs[0];
        assertEquals(child.getClass().getName(), struct.getComponentClassName());
        assertEquals("child", struct.getComponentId());
    }
}
