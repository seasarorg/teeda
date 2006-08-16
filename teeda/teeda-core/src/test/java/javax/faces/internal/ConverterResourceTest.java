package javax.faces.internal;

import javax.faces.convert.Converter;
import javax.faces.convert.IntegerConverter;

import junit.framework.TestCase;

public class ConverterResourceTest extends TestCase {

    public void tearDown() throws Exception {
        ConverterResource.removeAll();
    }

    public void testGetConverter() throws Exception {
        IntegerConverter c = new IntegerConverter();
        ConverterResource.addConverter("#{a.b}", c);
        Converter converter = ConverterResource.getConverter("#{a.b}");
        assertNotNull(converter);
        assertTrue(converter instanceof IntegerConverter);
    }
}
