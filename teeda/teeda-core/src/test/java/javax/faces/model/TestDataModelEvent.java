package javax.faces.model;

import javax.faces.mock.MockDataModel;

import junit.framework.TestCase;


public class TestDataModelEvent extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestDataModelEvent.class);
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
	 * Constructor for TestDataModelEvent.
	 * @param arg0
	 */
	public TestDataModelEvent(String arg0) {
		super(arg0);
	}

	public void testGetDataModel() throws Exception{
		DataModel model = new MockDataModel("a");
		DataModelEvent event = new DataModelEvent(model, 1, "b");
		
		assertEquals(model, event.getDataModel());
		assertEquals("a", event.getDataModel().toString());
		
	}
	
	public void testGetRowData() throws Exception{
		DataModel model = new MockDataModel("b");
		DataModelEvent event = new DataModelEvent(model, 1, "c");
		
		assertEquals("c", event.getRowData());
		
	}
	
	public void testGetRowIndex() throws Exception{
		DataModel model = new MockDataModel("c");
		DataModelEvent event = new DataModelEvent(model, 1, "d");
		
		assertEquals(1, event.getRowIndex());
	}
	
}
