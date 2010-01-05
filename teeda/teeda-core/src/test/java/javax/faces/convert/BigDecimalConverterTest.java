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

import java.math.BigDecimal;
import java.util.Locale;

import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class BigDecimalConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        String value = "123000000000";
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof BigDecimal);
        BigDecimal b = (BigDecimal) o;
        assertEquals(Long.valueOf(value).longValue(), b.longValue());
    }

    public void testGetAsObject_convertFail() throws Exception {
        Converter converter = createConverter();
        String value = "aaa";
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    value);
            fail();
        } catch (ConverterException e) {
            success();
        }
    }

    public void testGetAsString_convertSuccess() throws Exception {
        Converter converter = createConverter();
        BigDecimal b = new BigDecimal("123.456");
        String str = converter.getAsString(getFacesContext(),
                new MockUIComponent(), b);
        assertEquals(b.toString(), str);
    }

    public void testGetAsString_convertFail() throws Exception {
        Converter converter = createConverter();
        Integer i = new Integer(1);
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(), i);
            fail();
        } catch (ConverterException e) {
            assertTrue(true);
        }
    }

    public void testGetAsObject_convertwithDelimeter() throws Exception {
        Converter converter = createConverter();
        String value = "123,000,000,000";
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof BigDecimal);
        BigDecimal b = (BigDecimal) o;
        assertEquals(Long.valueOf("123000000000").longValue(), b.longValue());
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.BigDecimal", BigDecimalConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createBigDecimalConverter();
    }

    private BigDecimalConverter createBigDecimalConverter() {
        return new BigDecimalConverter();
    }
}
