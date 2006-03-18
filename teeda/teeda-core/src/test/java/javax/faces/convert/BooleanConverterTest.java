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

import org.seasar.teeda.core.mock.MockUIComponent;

public class BooleanConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_success() throws Exception {
        Converter converter = createConverter();
        String value = "true";
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof Boolean);
        Boolean result = (Boolean) o;
        assertEquals(value, result.toString());
        assertEquals(true, result.booleanValue());

        value = "false";
        o = converter.getAsObject(getFacesContext(), new MockUIComponent(),
                value);

        result = (Boolean) o;
        assertEquals(value, result.toString());
        assertEquals(false, result.booleanValue());

        value = "other";
        o = converter.getAsObject(getFacesContext(), new MockUIComponent(),
                value);

        result = (Boolean) o;
        assertNotSame(value, result.toString());
        assertEquals(false, result.booleanValue());
    }

    public void testGetAsString_convertSuccess() throws Exception {
        Converter converter = createConverter();
        Boolean b = new Boolean(true);
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

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Boolean", BooleanConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createBooleanConverter();
    }

    private BooleanConverter createBooleanConverter() {
        return new BooleanConverter();
    }
}
