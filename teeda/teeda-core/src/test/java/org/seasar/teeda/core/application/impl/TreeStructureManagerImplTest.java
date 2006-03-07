package org.seasar.teeda.core.application.impl;

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.mock.MockUIComponent;

public class TreeStructureManagerImplTest extends TestCase {

    public void testBuildTreeStructure_simple() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent component = new MockUIComponent();
        component.setId("id");

        // # Act #
        TreeStructure struct = manager.buildTreeStructure(component);

        // # Assert #
        assertEquals(component.getClass().getName(), struct
                .getComponentClassName());
        assertEquals("id", struct.getComponentId());
    }

    public void testBuildTreeStructure_hasChildrenAndFacets() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        MockUIComponent child1 = new MockUIComponent();
        child1.setId("child1");
        parent.getChildren().add(child1);
        MockUIComponent child2 = new MockUIComponent();
        child2.setId("child2");
        parent.getFacets().put("hoge", child2);

        // # Act #
        TreeStructure struct = manager.buildTreeStructure(parent);

        // # Assert #
        assertEquals(parent.getClass().getName(), struct
                .getComponentClassName());
        assertEquals("parent", struct.getComponentId());
        TreeStructure[] children = struct.getChildren();
        assertEquals(1, children.length);
        TreeStructure childStruct = children[0];
        assertEquals("child1", childStruct.getComponentId());
        Object[] facets = struct.getFacets();
        assertEquals(1, facets.length);
        Object[] childFacets = (Object[]) facets[0];
        assertEquals("hoge", childFacets[0]);
        assertEquals("child2", ((TreeStructure) childFacets[1])
                .getComponentId());
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

    public void testBuildChildrenTreeStructure_null() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");

        // # Act #
        TreeStructure[] structs = manager.buildChildrenTreeStructure(parent);

        // # Assert #
        assertNull(structs);
    }

    public void testBuildFacetsTreeStructure_simple() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        MockUIComponent child = new MockUIComponent();
        child.setId("child");
        parent.getFacets().put("hoge", child);

        // # Act #
        Object[] array = manager.buildFacetsTreeStructure(parent);

        // # Assert #
        assertNotNull(array);
        assertTrue(array.length == 1);
        Object[] objs = (Object[]) array[0];
        assertEquals("hoge", objs[0].toString());
        TreeStructure struct = (TreeStructure) objs[1];
        assertEquals(child.getClass().getName(), struct.getComponentClassName());
        assertEquals("child", struct.getComponentId());
    }

    public void testBuildFacetsTreeStructure_null() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");

        // # Act #
        Object[] array = manager.buildFacetsTreeStructure(parent);

        // # Assert #
        assertNull(array);
    }

    public void testRestoreTreeStructure_simple() throws Exception {
        // # Arrange #
        TreeStructureManagerImpl manager = new TreeStructureManagerImpl();
        TreeStructure struct = new TreeStructure(MockUIComponent.class
                .getName(), "id");

        // # Act #
        UIComponent component = manager.restoreTreeStructure(struct);

        // # Assert #
        assertNotNull(component);
        assertTrue(component instanceof MockUIComponent);
        assertEquals("id", component.getId());
    }

}
