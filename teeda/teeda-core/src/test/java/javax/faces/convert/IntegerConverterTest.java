/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.convert;

import java.util.ArrayList;
import java.util.Locale;

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

    public void testGetAsObject_convertWithDelimeter() throws Exception {
        Converter converter = createConverter();
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "1,000,000");
        assertNotNull(o);
        Integer i = (Integer) o;
        assertEquals(1000000, i.intValue());
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Integer", IntegerConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createIntegerConverter();
    }

    protected IntegerConverter createIntegerConverter() {
        return new IntegerConverter();
    }
}
