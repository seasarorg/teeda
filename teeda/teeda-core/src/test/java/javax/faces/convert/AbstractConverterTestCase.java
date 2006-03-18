package javax.faces.convert;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public abstract class AbstractConverterTestCase extends TeedaTestCase {

    public void testGetAsObject_facesContextIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(null, new MockUIComponent(), "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsObject_componentIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(new MockFacesContextImpl(), null, "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsObject_valueIsNull() throws Exception {
        Converter converter = createConverter();
        Object value = converter.getAsObject(new MockFacesContextImpl(),
                new MockUIComponent(), null);
        assertNull(value);
    }

    public void testGetAsObject_valueLengthIsBlank() throws Exception {
        Converter converter = createConverter();
        Object value = converter.getAsObject(new MockFacesContextImpl(),
                new MockUIComponent(), "");
        assertNull(value);
    }

    public void testGetAsString_facesContextIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(null, new MockUIComponent(), "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsString_componentIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(new MockFacesContextImpl(), null, "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsString_valueIsNull() throws Exception {
        Converter converter = createConverter();
        String value = converter.getAsString(new MockFacesContextImpl(),
                new MockUIComponent(), null);
        assertEquals("", value);
    }

    public void testGetAsString_valueIsString() throws Exception {
        Converter converter = createConverter();
        String value = converter.getAsString(new MockFacesContextImpl(),
                new MockUIComponent(), "a");
        assertEquals("a", value);
    }

    public abstract void testConstants() throws Exception;

    protected abstract Converter createConverter();
}
