/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.math.BigInteger;
import java.util.Locale;

import org.seasar.teeda.core.mock.MockUIComponent;

public class BigIntegerConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        Converter converter = createConverter();
        BigInteger b = new BigInteger("2").pow(32);
        String value = b.toString();
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof BigInteger);
        BigInteger result = (BigInteger) o;
        assertTrue(b.equals(result));
        assertEquals(b.longValue(), result.longValue());
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
        BigInteger b = new BigInteger("123");
        String str = converter.getAsString(getFacesContext(),
                new MockUIComponent(), b);
        assertEquals(b.toString(), str);
    }

    public void testGetAsString_convertFail() throws Exception {
        Converter converter = createConverter();
        Boolean bl = new Boolean(true);
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(), bl);
            fail();
        } catch (ConverterException e) {
            assertTrue(true);
        }

    }

    public void testGetAsObject_convertWithDelimeter() throws Exception {
        Converter converter = createConverter();
        String value = "4,294,967,296";
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof BigInteger);
        BigInteger result = (BigInteger) o;
        assertEquals(Long.valueOf(value.replaceAll(",", "")).longValue(),
                result.longValue());
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.BigInteger", BigIntegerConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createBigIntegerConverter();
    }

    protected BigIntegerConverter createBigIntegerConverter() {
        return new BigIntegerConverter();
    }

}
