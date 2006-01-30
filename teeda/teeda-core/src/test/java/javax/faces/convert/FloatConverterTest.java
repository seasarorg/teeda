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

import java.util.ArrayList;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class FloatConverterTest extends TeedaTestCase {

    public void testGetAsObject() throws Exception {
        FloatConverter converter = new FloatConverter();
        try {
            converter.getAsObject(null, new MockUIComponent(), "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        try {
            converter.getAsObject(getFacesContext(), null, "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        assertNull(converter.getAsObject(getFacesContext(),
                new MockUIComponent(), null));
        assertNull(converter.getAsObject(getFacesContext(),
                new MockUIComponent(), ""));
        Object target = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), "1.2F");
        assertNotNull(target);
        assertTrue(target instanceof Float);
        Float f = (Float) target;
        assertTrue(f.floatValue() == 1.2F);
    }

    public void testGetAsObject_throwConverterException() throws Exception {
        FloatConverter converter = new FloatConverter();
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    "hoge");
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetAsString() throws Exception {
        FloatConverter converter = new FloatConverter();
        try {
            converter.getAsString(null, new MockUIComponent(), "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        try {
            converter.getAsString(getFacesContext(), null, "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new Float(2.3F));
        assertEquals("2.3", s);

        s = converter.getAsString(getFacesContext(), new MockUIComponent(),
                "bbb");
        assertEquals("bbb", s);
    }

    public void testGetAsString_throwConverterException() throws Exception {
        FloatConverter converter = new FloatConverter();
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(),
                    new ArrayList());
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }
}
