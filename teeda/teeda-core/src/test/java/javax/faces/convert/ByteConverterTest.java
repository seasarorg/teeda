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

import java.util.Locale;

import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;

public class ByteConverterTest extends AbstractConverterTestCase {

    public void testGetAsObject_convertSuccess() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "126";
        Object o = converter.getAsObject(context, component, value);
        assertTrue(o instanceof Byte);
        Byte b = (Byte) o;
        assertEquals(Byte.parseByte(value), b.byteValue());
    }

    public void testGetAsObject_convertFail() throws Exception {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "128";
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setLocale(Locale.getDefault());
        context.setViewRoot(viewRoot);
        try {
            converter.getAsObject(context, component, value);
            fail();
        } catch (ConverterException e) {
            success();
        }
    }

    public void testGetAsString_convertSuccess() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "1";
        String str = converter.getAsString(context, component, value);
        assertEquals(Byte.parseByte(value), Byte.parseByte(str));
    }

    public void testGetAsString_convertFail() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        Long l = new Long(123456789);
        try {
            converter.getAsString(context, component, l);
            fail();
        } catch (ConverterException e) {
            success();
        }
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Byte", ByteConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createByteConverter();
    }

    protected ByteConverter createByteConverter() {
        return new ByteConverter();
    }

}
