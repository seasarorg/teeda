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
package org.seasar.teeda.extension.convert;

import javax.faces.convert.AbstractConverterTestCase;
import javax.faces.convert.Converter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class TextareaSeparatorCharacterConverterTest extends
        AbstractConverterTestCase {

    public void testGetAsString_convertSuccess1() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc" + "\r\n" + "def";
        Object o = converter.getAsString(context, component, value);
        assertEquals("abc<br/>def", o.toString());
    }

    public void testGetAsString_convertSuccess2() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc" + "\r" + "def";
        Object o = converter.getAsString(context, component, value);
        assertEquals("abc<br/>def", o.toString());
    }

    public void testGetAsString_convertSuccess3() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc" + "\n" + "def";
        Object o = converter.getAsString(context, component, value);
        assertEquals("abc<br/>def", o.toString());
    }

    public void testGetAsString_convertSuccess4() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc" + "\r\r\n\n\n" + "def";
        Object o = converter.getAsString(context, component, value);
        assertEquals("abc<br/><br/><br/><br/>def", o.toString());
    }

    public void testGetAsString_convertSuccess5() {
        Converter converter = createConverter();
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        String value = "abc<br/>" + "\n" + "def";
        Object o = converter.getAsString(context, component, value);
        assertEquals("abc&lt;br/&gt;<br/>def", o.toString());
    }

    public void testConvertTargetNotPointed() throws Exception {
        TextareaSeparatorCharacterConverter converter = (TextareaSeparatorCharacterConverter) createConverter();
        converter.setTarget("aaa");
        MockUIComponent component = new MockUIComponent();
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        String value = "abc" + "\r\n\r\n" + "def";
        Object o = converter.getAsString(context, component, value);
        // targetが異なる為、コンバート処理は行われない
        assertNull(o);

    }

    protected Converter createConverter() {
        return new TextareaSeparatorCharacterConverter();
    }

    public void testConstants() throws Exception {
        assertTrue(true);
    }

}
