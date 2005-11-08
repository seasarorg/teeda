package javax.faces.convert;

import java.math.BigDecimal;

import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestBigDecimalConverter extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestBigDecimalConverter.class);
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
	 * Constructor for TestBigDecimalConverter.
	 * @param arg0
	 */
	public TestBigDecimalConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		BigDecimalConverter converter = new BigDecimalConverter();
		
		try{
			converter.getAsObject(null, new MockUIComponent(), "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsObject(new MockFacesContext(), null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		Object o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), null);
		assertEquals(o, null);
		
		o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), "");
		assertNull(o);
		
		o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), " ");
		assertNull(o);
		
		String value = "123000000000";
		o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), value);
		assertTrue(o instanceof BigDecimal);
		BigDecimal b = (BigDecimal)o;
		
		assertEquals(Long.valueOf(value).longValue(), b.longValue());
		
		
		value = "aaa";
		try{
			o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), value);
			fail();
		}catch(Exception e){
			assertTrue(true);
		}
	}

	public void testGetAsString() {
		
		BigDecimalConverter converter = new BigDecimalConverter();
		
		try{
			converter.getAsString(null, new MockUIComponent(), "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsString(new MockFacesContext(), null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		String str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), null);
		assertEquals(str, "");
		
		str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), "a");
		
		assertEquals("a", str);
		
		BigDecimal b = new BigDecimal("123.456");
		str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), b);
		assertEquals(b.toString(), str);
		
		
		Integer i = new Integer(1);
		try{
			str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), i);
			fail();
		}catch(ConverterException e){
			assertTrue(true);
		}

	}

}
