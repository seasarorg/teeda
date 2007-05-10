/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;

public class CharacterConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_valueLengthIsBlank() throws Exception {
        Converter converter = createConverter();
        try {
            converter.getAsObject(new MockFacesContextImpl(),
                    new MockUIComponent(), "");
            fail();
        } catch (ConverterException e) {
            success();
        }
    }

    public void testGetAsObject_convertSuccess() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc";
        Object o = converter.getAsObject(context, component, value);

        assertTrue(o instanceof Character);
        Character c = (Character) o;
        assertEquals(value.charAt(0), c.charValue());
    }

    public void testGetAsString_convertSuccess() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc";
        String str = converter.getAsString(context, component, value);
        assertEquals(value, str);

        Character ch = new Character('d');
        str = converter.getAsString(context, component, ch);
        assertEquals(ch, new Character(str.charAt(0)));
    }

    public void testGetAsString_convertFail() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        Integer i = new Integer(1);
        try {
            converter.getAsString(context, component, i);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Character", CharacterConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createCharacterConverter();
    }

    private CharacterConverter createCharacterConverter() {
        return new CharacterConverter();
    }
}
