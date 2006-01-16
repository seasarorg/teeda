package javax.faces.convert;

import javax.faces.application.FacesMessage;

import junit.framework.TestCase;

public class ConverterExceptionTest extends TestCase {

    public void testConverterException() {
        ConverterException e = new ConverterException();
        assertNull(e.getFacesMessage());
    }

    public void testConverterExceptionString() {
        ConverterException e = new ConverterException("a");
        assertEquals("a", e.getMessage());
    }

    public void testConverterExceptionThrowable() {
        Throwable t = new Throwable("a");
        ConverterException e = new ConverterException(t);
        assertEquals(t, e.getCause());
        assertEquals(t.getMessage(), e.getCause().getMessage());
    }

    public void testConverterExceptionStringThrowable() {
        String s = "a";
        Throwable t = new Throwable("b");
        ConverterException e = new ConverterException(s, t);

        assertEquals(s, e.getMessage());

        assertEquals(t, e.getCause());
        assertEquals(t.getMessage(), e.getCause().getMessage());
    }

    public void testConverterExceptionFacesMessage() {
        FacesMessage facesMessage = new FacesMessage("summary", "detail");
        ConverterException e = new ConverterException(facesMessage);

        assertNotNull(e.getFacesMessage());
        assertEquals(facesMessage, e.getFacesMessage());
    }

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
