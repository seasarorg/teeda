package javax.faces.component;

import org.seasar.teeda.core.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestUIColumn extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIColumn.class);
	}

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

	/**
	 * Constructor for TestUIColumn.
	 * @param arg0
	 */
	public TestUIColumn(String arg0) {
		super(arg0);
	}

	public void testGetFamily() {
		UIColumn column = new UIColumn();
		assertEquals(column.getFamily(), UIColumn.COMPONENT_FAMILY);
	}

	public void testGetFooter() {
		MockUIComponent component = new MockUIComponent();
		UIColumn column = new UIColumn();
		column.setFooter(component);
		UIComponent c = column.getFooter();
		assertEquals(component, c);
	}

	public void testSetFooter() {
		UIColumn column = new UIColumn();
		try{
			column.setFooter(null);
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		MockUIComponent component = new MockUIComponent();
		column.setFooter(component);
		UIComponent c = column.getFooter();
		assertEquals(component, c);		
	}

	public void testGetHeader() {
		MockUIComponent component = new MockUIComponent();
		UIColumn column = new UIColumn();
		column.setHeader(component);
		UIComponent c = column.getHeader();
		assertEquals(component, c);
	}

	public void testSetHeader() {
		UIColumn column = new UIColumn();
		try{
			column.setHeader(null);
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		MockUIComponent component = new MockUIComponent();
		column.setHeader(component);
		UIComponent c = column.getHeader();
		assertEquals(component, c);		
	}

}
