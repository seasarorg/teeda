package javax.faces.component;

import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestComponentFacetMapWrapper_ extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestComponentFacetMapWrapper_.class);
    }

    public void testPutChildFacet() {
        MockUIComponent parent = new MockUIComponent();
        parent.setId("a");
        ComponentFacetMapWrapper_ wrapper = new ComponentFacetMapWrapper_(parent);
        MockUIComponent child = new MockUIComponent();
        child.setId("b");
        wrapper.put("c", child);
        UIComponent c = (UIComponent)wrapper.get("c");
        assertTrue(c.equals(child));
        assertEquals("a", c.getParent().getId());
    }

}
