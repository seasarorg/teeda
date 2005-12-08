package javax.faces.convert;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestBooleanConverter extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestBooleanConverter.class);
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
	 * Constructor for TestBooleanConverter.
	 * @param arg0
	 */
	public TestBooleanConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		BooleanConverter converter = new BooleanConverter();
		
		try{
			converter.getAsObject(null, new MockUIComponent(), "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsObject(getFacesContext(), null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		Object o = converter.getAsObject(getFacesContext(), new MockUIComponent(), null);
		assertEquals(o, null);
		
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), "");
		assertNull(o);
		
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), " ");
		assertNull(o);
		
		String value = "true";
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), value);
		assertTrue(o instanceof Boolean);
		
		Boolean result = (Boolean)o;
		assertEquals(value, result.toString());
		assertEquals(true, result.booleanValue());
		
		value = "false";
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), value);

		result = (Boolean)o;
		assertEquals(value, result.toString());
		assertEquals(false, result.booleanValue());
		
		value = "other";
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), value);

		result = (Boolean)o;
		assertNotSame(value, result.toString());
		assertEquals(false, result.booleanValue());
		
	}

	public void testGetAsString() {
		
		BooleanConverter converter = new BooleanConverter();
		
		try{
			converter.getAsString(null, new MockUIComponent(), "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsString(getFacesContext(), null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		String str = converter.getAsString(getFacesContext(), new MockUIComponent(), null);
		assertEquals(str, "");
		
		str = converter.getAsString(getFacesContext(), new MockUIComponent(), "a");
		
		assertEquals("a", str);
		
		Boolean b = new Boolean(true);
		str = converter.getAsString(getFacesContext(), new MockUIComponent(), b);
		
		assertEquals(b.toString(), str);
		
		Integer i = new Integer(1);
		try{
			str = converter.getAsString(getFacesContext(), new MockUIComponent(), i);
			fail();
		}catch(ConverterException e){
			assertTrue(true);
		}
	}

}
