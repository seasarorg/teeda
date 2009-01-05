/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

/**
 * @author shot
 */
public class DoubleConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        Object target = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "2.834");
        assertNotNull(target);
        assertTrue(target instanceof Double);
        Double d = (Double) target;
        assertTrue(d.doubleValue() == 2.834);
    }

    public void testGetAsObject_convertFail() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    "hoge");
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetAsString_convertSuccess() throws Exception {
        Converter converter = createConverter();
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new Double(1.234));
        assertEquals("1.234", s);
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
        String value = "1,002.834";
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object target = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertNotNull(target);
        assertTrue(target instanceof Double);
        Double d = (Double) target;
        assertTrue(d.doubleValue() == 1002.834);
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.DoubleTime", DoubleConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createDoubleConverter();
    }

    protected DoubleConverter createDoubleConverter() {
        return new DoubleConverter();
    }

}
