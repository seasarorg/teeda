/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class BigIntegerConverterTest extends TeedaTestCase {

    public void testGetAsObject() {

        BigIntegerConverter converter = new BigIntegerConverter();

        try {
            converter.getAsObject(null, new MockUIComponent(), "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        try {
            converter.getAsObject(getFacesContext(), null, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), null);
        assertEquals(o, null);

        o = converter.getAsObject(getFacesContext(), new MockUIComponent(), "");
        assertNull(o);

        o = converter
                .getAsObject(getFacesContext(), new MockUIComponent(), " ");
        assertNull(o);

        BigInteger b = new BigInteger("2").pow(32);

        String value = b.toString();

        o = converter.getAsObject(getFacesContext(), new MockUIComponent(),
                value);
        assertTrue(o instanceof BigInteger);

        BigInteger result = (BigInteger) o;

        assertTrue(b.equals(result));
        assertEquals(b.longValue(), result.longValue());

        value = "aaa";
        try {
            o = converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    value);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testGetAsString() {

        BigIntegerConverter converter = new BigIntegerConverter();

        try {
            converter.getAsString(null, new MockUIComponent(), "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        try {
            converter.getAsString(getFacesContext(), null, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        String str = converter.getAsString(getFacesContext(),
                new MockUIComponent(), null);
        assertEquals(str, "");

        str = converter.getAsString(getFacesContext(), new MockUIComponent(),
                "a");

        assertEquals("a", str);

        BigInteger b = new BigInteger("123");
        str = converter
                .getAsString(getFacesContext(), new MockUIComponent(), b);
        assertEquals(b.toString(), str);

        Boolean bl = new Boolean(true);
        try {
            str = converter.getAsString(getFacesContext(),
                    new MockUIComponent(), bl);
            fail();
        } catch (ConverterException e) {
            assertTrue(true);
        }

    }

}
