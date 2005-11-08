package javax.faces.convert;

import javax.faces.application.FacesMessage;

import junit.framework.TestCase;


public class TestConverterException extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestConverterException.class);
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
	 * Constructor for TestConverterException.
	 * @param arg0
	 */
	public TestConverterException(String arg0) {
		super(arg0);
	}

	/*
	 * void ConverterException のテスト中のクラス()
	 */
	public void testConverterException() {
		
		ConverterException e = new ConverterException();
		assertNull(e.getFacesMessage());
		
	}

	/*
	 * void ConverterException のテスト中のクラス(String)
	 */
	public void testConverterExceptionString() {
		
		ConverterException e = new ConverterException("a");
		assertEquals("a", e.getMessage());
	}

	/*
	 * void ConverterException のテスト中のクラス(Throwable)
	 */
	public void testConverterExceptionThrowable() {
		Throwable t = new Throwable("a");
		ConverterException e = new ConverterException(t);
		assertEquals(t, e.getCause());
		assertEquals(t.getMessage(), e.getCause().getMessage());
	}

	/*
	 * void ConverterException のテスト中のクラス(String, Throwable)
	 */
	public void testConverterExceptionStringThrowable() {
		String s = "a";
		Throwable t = new Throwable("b");
		ConverterException e = new ConverterException(s, t);
		
		assertEquals(s, e.getMessage());
		
		assertEquals(t, e.getCause());
		assertEquals(t.getMessage(), e.getCause().getMessage());
		
	}

	/*
	 * void ConverterException のテスト中のクラス(FacesMessage)
	 */
	public void testConverterExceptionFacesMessage() {
		
		FacesMessage facesMessage = new FacesMessage("summary", "detail");
		ConverterException e = new ConverterException(facesMessage);
		
		assertNotNull(e.getFacesMessage());
		assertEquals(facesMessage, e.getFacesMessage());
	}

	/*
	 * void ConverterException のテスト中のクラス(FacesMessage, Throwable)
	 */
	public void testConverterExceptionFacesMessageThrowable() {

		FacesMessage facesMessage = new FacesMessage("summary", "detail");
		Throwable t = new Throwable("t");
		ConverterException e = new ConverterException(facesMessage, t);
		assertNotNull(e.getFacesMessage());
		assertEquals(facesMessage, e.getFacesMessage());
		assertEquals(t, e.getCause());
	}

	public void testGetFacesMessage() {
		String s = "summary";
		String d = "detail";
		FacesMessage facesMessage = new FacesMessage(s, d);
		ConverterException e = new ConverterException(facesMessage);
		
		assertNotNull(e.getFacesMessage());
		assertEquals(facesMessage, e.getFacesMessage());
		assertEquals(facesMessage.getDetail(), e.getMessage());
		
		
	}

}
