package javax.faces.convert;

import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestCharacterConverter extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestCharacterConverter.class);
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
	 * Constructor for TestCharacterConverter.
	 * @param arg0
	 */
	public TestCharacterConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		MockUIComponent component = new MockUIComponent();
		MockFacesContext context = new MockFacesContext();
		
		CharacterConverter converter = new CharacterConverter();
		
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

		String value = "abc";
		o = converter.getAsObject(context, component, value);
		
		assertTrue(o instanceof Character);
		Character c = (Character)o;
		assertEquals(value.charAt(0), c.charValue());
		
	}

	public void testGetAsString() {

		MockUIComponent component = new MockUIComponent();
		MockFacesContext context = new MockFacesContext();
		
		CharacterConverter converter = new CharacterConverter();
		
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

		String value = "abc";
		str = converter.getAsString(context, component, value);
		assertEquals(value, str);
		
		Character ch = new Character('d');
		str = converter.getAsString(context, component, ch);
		assertEquals(ch, new Character(str.charAt(0)));
		
		Integer i = new Integer(1);
		try{
			str = converter.getAsString(context, component, i);
			fail();
		}catch(Exception e){
			assertTrue(true);
		}
		
		
		
	}

}
