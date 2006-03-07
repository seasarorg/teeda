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
public class DoubleConverterTest extends TeedaTestCase {

    public void testGetAsObject() throws Exception {
        DoubleConverter converter = new DoubleConverter();
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
                new MockUIComponent(), "2.834");
        assertNotNull(target);
        assertTrue(target instanceof Double);
        Double d = (Double) target;
        assertTrue(d.doubleValue() == 2.834);
    }

    public void testGetAsObject_throwConverterException() throws Exception {
        DoubleConverter converter = new DoubleConverter();
        try {
            converter.getAsObject(getFacesContext(), new MockUIComponent(),
                    "hoge");
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

    public void testGetAsString() throws Exception {
        DoubleConverter converter = new DoubleConverter();
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
                new MockUIComponent(), new Double(1.234));
        assertEquals("1.234", s);

        s = converter.getAsString(getFacesContext(), new MockUIComponent(),
                "aaa");
        assertEquals("aaa", s);
    }

    public void testGetAsString_throwConverterException() throws Exception {
        DoubleConverter converter = new DoubleConverter();
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(),
                    new ArrayList());
            fail();
        } catch (ConverterException expected) {
            success();
        }
    }

}
