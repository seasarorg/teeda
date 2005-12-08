package javax.faces.component;

import org.seasar.teeda.core.mock.MockUIComponent;

import junit.framework.TestCase;

public class TestComponentChildrenListWrapper_ extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddComponent(){
		MockUIComponent component = new MockUIComponent();
        component.setId("a");
		ComponentChildrenListWrapper_ wrapper = new ComponentChildrenListWrapper_(component);
        MockUIComponent child = new MockUIComponent();
        wrapper.add(child);
        assertEquals("a", child.getParent().getId());
	}
    
    public void testRemoveComponent(){
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        ComponentChildrenListWrapper_ wrapper = new ComponentChildrenListWrapper_(component);
        MockUIComponent child = new MockUIComponent();
        child.setId("b");
        wrapper.add(child);
        assertEquals(1, wrapper.size());
        UIComponent c = (UIComponent)wrapper.remove(wrapper.size() - 1);
        assertEquals("b", c.getId());
    }
}
