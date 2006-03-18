package javax.faces.convert;

import java.util.ArrayList;

import org.seasar.teeda.core.mock.MockUIComponent;

public class IntegerConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "123");
        assertNotNull(o);
        Integer i = (Integer) o;
        assertEquals(123, i.intValue());
    }

    public void testGetAsObject_convertFail() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    "aaa");
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetAsString_convertSuccess() throws Exception {
        Converter converter = createConverter();
        String value = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new Integer(123));
        assertEquals("123", value);
    }

    public void testGetAsString_convertFail() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(),
                    new ArrayList());
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Integer", IntegerConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createIntegerConverter();
    }

    private IntegerConverter createIntegerConverter() {
        return new IntegerConverter();
    }
}
