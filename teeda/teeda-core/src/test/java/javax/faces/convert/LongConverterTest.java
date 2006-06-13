package javax.faces.convert;

import java.util.ArrayList;
import java.util.Locale;

import org.seasar.teeda.core.mock.MockUIComponent;


public class LongConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "12345678901234");
        assertNotNull(o);
        Long l = (Long) o;
        assertEquals(12345678901234L, l.longValue());
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
                new MockUIComponent(), new Long(12345678901234L));
        assertEquals("12345678901234", value);
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

    public void testGetAsObject_convertWithDelimeter() throws Exception {
        Converter converter = createConverter();
        String value = "12,345,678,901,234";
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertNotNull(o);
        Long l = (Long) o;
        assertEquals(12345678901234L, l.longValue());
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Long", LongConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createLongConverter();
    }

    private LongConverter createLongConverter() {
        return new LongConverter();
    }
}
