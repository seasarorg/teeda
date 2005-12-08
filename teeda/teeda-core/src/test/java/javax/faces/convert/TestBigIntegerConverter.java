package javax.faces.convert;

import java.math.BigInteger;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestBigIntegerConverter extends TeedaTestCase {

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
		
		BigInteger b = new BigInteger("2").pow(32);
		
		String value = b.toString();
		
		o = converter.getAsObject(getFacesContext(), new MockUIComponent(), value);
		assertTrue(o instanceof BigInteger);
		
		BigInteger result = (BigInteger)o;
		
		assertTrue(b.equals(result));
		assertEquals(b.longValue(), result.longValue());
		
		value = "aaa";
		try{
			o = converter.getAsObject(getFacesContext(), new MockUIComponent(), value);
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
			converter.getAsString(getFacesContext(), null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		String str = converter.getAsString(getFacesContext(), new MockUIComponent(), null);
		assertEquals(str, "");
		
		str = converter.getAsString(getFacesContext(), new MockUIComponent(), "a");
		
		assertEquals("a", str);
		
		BigInteger b = new BigInteger("123");
		str = converter.getAsString(getFacesContext(), new MockUIComponent(), b);
		assertEquals(b.toString(), str);
		
		Boolean bl = new Boolean(true);
		try{
			str = converter.getAsString(getFacesContext(), new MockUIComponent(), bl);
			fail();
		}catch(ConverterException e){
			assertTrue(true);
		}

	}

}
