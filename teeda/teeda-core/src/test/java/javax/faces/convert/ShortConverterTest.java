/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Locale;

import org.seasar.teeda.core.mock.MockUIComponent;

public class ShortConverterTest extends AbstractConverterTestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Short", ShortConverter.CONVERTER_ID);
    }

    public void testGetValueAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "2");
        assertNotNull(o);
        Short s = (Short) o;
        assertTrue(s.intValue() == 2);
    }

    public void testGetValueAsObject_convertFail() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    "32768");
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetValueAsString_convertSuccess() throws Exception {
        Converter converter = createConverter();
        String str = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new Short((short) 2));
        assertEquals("2", str);
    }

    public void testGetValueAsString_convertFail() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(),
                    new Integer(32768));
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetValueAsObject_convertWithDelimeter() throws Exception {
        Converter converter = createConverter();
        String value = "32,767";
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertNotNull(o);
        Short s = (Short) o;
        assertTrue(s.intValue() == 32767);
    }

    protected Converter createConverter() {
        return createShortConverter();
    }

    protected ShortConverter createShortConverter() {
        return new ShortConverter();
    }

}
