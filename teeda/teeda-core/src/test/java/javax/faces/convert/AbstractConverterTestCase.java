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

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public abstract class AbstractConverterTestCase extends TeedaTestCase {

    public void testGetAsObject_facesContextIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(null, new MockUIComponent(), "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsObject_componentIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(new MockFacesContextImpl(), null, "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsObject_valueIsNull() throws Exception {
        Converter converter = createConverter();
        Object value = converter.getAsObject(new MockFacesContextImpl(),
                new MockUIComponent(), null);
        assertNull(value);
    }

    public void testGetAsObject_valueLengthIsBlank() throws Exception {
        Converter converter = createConverter();
        Object value = converter.getAsObject(new MockFacesContextImpl(),
                new MockUIComponent(), "");
        assertNull(value);
    }

    public void testGetAsString_facesContextIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(null, new MockUIComponent(), "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsString_componentIsNull() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsString(new MockFacesContextImpl(), null, "1");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetAsString_valueIsNull() throws Exception {
        Converter converter = createConverter();
        String value = converter.getAsString(new MockFacesContextImpl(),
                new MockUIComponent(), null);
        assertEquals("", value);
    }

    public void testGetAsString_valueIsString() throws Exception {
        Converter converter = createConverter();
        String value = converter.getAsString(new MockFacesContextImpl(),
                new MockUIComponent(), "a");
        assertEquals("a", value);
    }

    public abstract void testConstants() throws Exception;

    protected abstract Converter createConverter();
}
