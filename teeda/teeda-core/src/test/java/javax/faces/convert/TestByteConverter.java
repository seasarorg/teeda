package javax.faces.convert;

import java.util.Locale;

import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestByteConverter extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestByteConverter.class);
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
	 * Constructor for TestByteConverter.
	 * @param arg0
	 */
	public TestByteConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		MockUIComponent component = new MockUIComponent();
		MockFacesContext context = getFacesContext();
		
		ByteConverter converter = new ByteConverter();
		
		try{
			converter.getAsObject(null, component, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsObject(context, null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		Object o = converter.getAsObject(context, component, null);
		assertNull(o);
		
		o = converter.getAsObject(context, component, "");
		assertNull(o);
		
		o = converter.getAsObject(context, component, " ");
		assertNull(o);
		
		String value = "126";
		o = converter.getAsObject(context, component, value);
		assertTrue(o instanceof Byte);
		
		Byte b = (Byte)o;
		assertEquals(Byte.parseByte(value), b.byteValue());
		
		value = "128";
		UIViewRoot viewRoot = new UIViewRoot();
		viewRoot.setLocale(Locale.getDefault());
		context.setViewRoot(viewRoot);
		try{
			o = converter.getAsObject(context, component, value);
			fail();
		}catch(Exception e){
			//TODO somehow need catching ConverterException 
			//since using FacesMessageUtils, 
			//need properties file if catching ConverterException
			assertTrue(true);
		}
	}

	public void testGetAsString() {
		
		MockUIComponent component = new MockUIComponent();
		MockFacesContext context = getFacesContext();
		
		ByteConverter converter = new ByteConverter();
		
		try{
			converter.getAsString(null, component, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsString(context, null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		String str = converter.getAsString(context, component, null);
		assertEquals("", str);
		
		String value = "a";
		str = converter.getAsString(context, component, value);
		assertEquals(value, str);
		
		value = "1";
		str = converter.getAsString(context, component, value);
		assertEquals(Byte.parseByte(value), Byte.parseByte(str));
		
		Long l = new Long(123456789);
		try{
			str = converter.getAsString(context, component, l);
			fail();
		}catch(Exception e){
			assertTrue(true);
		}
	}

}
