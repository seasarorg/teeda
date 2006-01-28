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

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class CharacterConverterTest extends TeedaTestCase {

    public void testGetAsObject() {

        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();

        CharacterConverter converter = new CharacterConverter();

        try {
            converter.getAsObject(null, component, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        try {
            converter.getAsObject(context, null, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        Object o = converter.getAsObject(context, component, null);
        assertNull(o);

        o = converter.getAsObject(context, component, "");
        assertNull(o);

        o = converter.getAsObject(context, component, " ");
        assertNull(o);

        String value = "abc";
        o = converter.getAsObject(context, component, value);

        assertTrue(o instanceof Character);
        Character c = (Character) o;
        assertEquals(value.charAt(0), c.charValue());

    }

    public void testGetAsString() {

        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();

        CharacterConverter converter = new CharacterConverter();

        try {
            converter.getAsString(null, component, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        try {
            converter.getAsString(context, null, "");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        String str = converter.getAsString(context, component, null);
        assertEquals("", str);

        String value = "abc";
        str = converter.getAsString(context, component, value);
        assertEquals(value, str);

        Character ch = new Character('d');
        str = converter.getAsString(context, component, ch);
        assertEquals(ch, new Character(str.charAt(0)));

        Integer i = new Integer(1);
        try {
            str = converter.getAsString(context, component, i);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

    }

}
