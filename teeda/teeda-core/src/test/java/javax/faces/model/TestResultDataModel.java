package javax.faces.model;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.faces.mock.MockDataModelListener;
import javax.faces.mock.MockResult;
import javax.servlet.jsp.jstl.sql.Result;

import junit.framework.TestCase;

public class TestResultDataModel extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestResultDataModel.class);
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
	 * Constructor for TestResultDataModel.
	 * 
	 * @param arg0
	 */
	public TestResultDataModel(String arg0) {
		super(arg0);
	}

	public void testIsRowAvailable() throws Exception {
		
		Object[] obj = new Object[]{"a", "b"};
		MockResult result = new MockResult(obj);
		ResultDataModel model = new ResultDataModel(result); 
		
		assertTrue(model.isRowAvailable());
		
		model.setRowIndex(1);
		assertTrue(model.isRowAvailable());
		
		model.setRowIndex(obj.length + 1);
		assertFalse(model.isRowAvailable());
		
		ResultDataModel model2 = new ResultDataModel();
		assertFalse(model2.isRowAvailable());
	}
	
	public void testGetRowCount() throws Exception{
		Object[] obj = new Object[]{"a"};
		MockResult result = new MockResult(obj);
		ResultDataModel model = new ResultDataModel(result);
		
		assertEquals(1, model.getRowCount());
		
		ResultDataModel model2 = new ResultDataModel();
		assertEquals(-1, model2.getRowCount());		
	}
	
	public void testGetRowData() throws Exception{
		Object[] obj = new Object[]{"a", "b"};
		MockResult result = new MockResult(obj);
		ResultDataModel model = new ResultDataModel(result);

		Object o = model.getRowData();
		assertTrue(o instanceof SortedMap);
		assertEquals("a", ((SortedMap)o).get("a"));
		
		model.setRowIndex(1);
		o = model.getRowData();
		assertTrue(o instanceof SortedMap);
		assertEquals("b", ((SortedMap)o).get("b"));

		ResultDataModel model2 = new ResultDataModel();
		assertNull(model2.getRowData());
		
		Object[] target = new Object[]{"A"};
		MockResult result3 = new MockResult(target);
		ResultDataModel model3 = new ResultDataModel(result);
		
		model3.setRowIndex(target.length + 1);
		try{
			model3.getRowData();
			fail();
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}
	
	public void testGetRowIndex() throws Exception{
		Object[] obj = new Object[]{"a", "b"};
		MockResult result = new MockResult(obj);
		ResultDataModel model = new ResultDataModel(result);

		assertEquals(0, model.getRowIndex());
		
		model.setRowIndex(1);
		assertEquals(1, model.getRowIndex());
		
		ResultDataModel model2 = new ResultDataModel();
		assertEquals(-1, model2.getRowIndex());
	}
	
	public void testSetRowIndex() throws Exception{
		Object[] args = new Object[]{"1","2"};
		MockResult result = new MockResult(args);
		ResultDataModel model = new ResultDataModel(result);
		MockDataModelListener listener = new MockDataModelListener();
		model.addDataModelListener(listener);
		
		int index = 1;
		model.setRowIndex(index);
		
		DataModelEvent event = listener.getDataModelEvent();

		Object o = event.getRowData();
		assertTrue(o instanceof SortedMap);
		SortedMap map = (SortedMap)o;

		String target = (String)args[index];
		assertEquals(target, map.get(target));
		
		try{
			model.setRowIndex(-2);
			fail();
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		ResultDataModel model2 = new ResultDataModel();
		MockDataModelListener listener2 = new MockDataModelListener();
		model2.addDataModelListener(listener2);
		
		model2.setRowIndex(1);
		
		assertNull(listener2.getDataModelEvent());
		
		Object[] args3 = new Object[]{"b", "c", "d"};
		MockResult result3 = new MockResult(args3);
		ResultDataModel model3 = new ResultDataModel(result3);
		MockDataModelListener listener3 = new MockDataModelListener();
		model3.addDataModelListener(listener3);
		
		model3.setRowIndex(args3.length + 1);
		
		DataModelEvent event3 = listener3.getDataModelEvent();
		assertNull(event3.getRowData());
		assertEquals(args3.length + 1, event3.getRowIndex());		
		
	}
	
	public void testGetWrappedData() throws Exception{
		Object[] args = new Object[]{"a"};
		MockResult result = new MockResult(args);
		ResultDataModel model = new ResultDataModel(result);
		
		Object o = model.getWrappedData();
		assertNotNull(o);
		assertTrue(o instanceof Result);
		assertEquals(result, o);
	}
	
	public void testSetWrappedData() throws Exception{
		ResultDataModel model = new ResultDataModel();

		assertNull(model.getWrappedData());
		
		Object[] args = new Object[]{new Integer(1), new Integer(2)};
		MockResult result = new MockResult(args);
		model.setWrappedData(result);
		
		Object o = model.getWrappedData();
		assertNotNull(o);
		assertTrue(o instanceof Result);
		assertEquals(result, o);
		
	}
	
}
