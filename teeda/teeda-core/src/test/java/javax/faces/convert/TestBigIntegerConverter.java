package javax.faces.convert;

import java.math.BigInteger;

import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestBigIntegerConverter extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestBigIntegerConverter.class);
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
	 * Constructor for TestBigIntegerConverter.
	 * @param arg0
	 */
	public TestBigIntegerConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		BigIntegerConverter converter = new BigIntegerConverter();
		
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
		
		BigInteger b = new BigInteger("2").pow(32);
		
		String value = b.toString();
		
		o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), value);
		assertTrue(o instanceof BigInteger);
		
		BigInteger result = (BigInteger)o;
		
		assertTrue(b.equals(result));
		assertEquals(b.longValue(), result.longValue());
		
		value = "aaa";
		try{
			o = converter.getAsObject(new MockFacesContext(), new MockUIComponent(), value);
			fail();
		}catch(Exception e){
			assertTrue(true);
		}
	}

	public void testGetAsString() {
		
		BigIntegerConverter converter = new BigIntegerConverter();
		
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
		
		BigInteger b = new BigInteger("123");
		str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), b);
		assertEquals(b.toString(), str);
		
		Boolean bl = new Boolean(true);
		try{
			str = converter.getAsString(new MockFacesContext(), new MockUIComponent(), bl);
			fail();
		}catch(ConverterException e){
			assertTrue(true);
		}

	}

}
