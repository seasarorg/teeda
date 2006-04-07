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
package org.seasar.teeda.core.util;

import java.lang.reflect.Array;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.convert.IntegerConverter;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class RendererUtilTest extends TestCase {

    public void testShouldRenderIdAttribute_True() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId(UIViewRoot.UNIQUE_ID_PREFIX + "aaa");

        // ## Act & Assert ##
        assertEquals(false, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testShouldRenderIdAttribute_False() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId("aaa");

        // ## Act & Assert ##
        assertEquals(true, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testShouldRenderIdAttribute_False_IdIsNull() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId(null);

        // ## Act & Assert ##
        assertEquals(false, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testIsDefaultValue() throws Exception {
        assertEquals(true, RendererUtil.isDefaultAttributeValue(null));

        assertEquals(true, RendererUtil.isDefaultAttributeValue(Boolean.FALSE));
        assertEquals(true, RendererUtil.isDefaultAttributeValue(new Integer(
                Integer.MIN_VALUE)));

        assertEquals(false, RendererUtil.isDefaultAttributeValue(Boolean.TRUE));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(0)));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(-1)));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(1)));
        assertEquals(false, RendererUtil.isDefaultAttributeValue(new Integer(
                Integer.MAX_VALUE)));

        assertEquals(false, RendererUtil.isDefaultAttributeValue(""));
    }

    public void testContainsAttributes_false() throws Exception {
        UIComponent component = new MockUIComponentBase();
        assertEquals(false, RendererUtil.containsAttributesForRender(component,
                new String[] { "foo" }));
    }

    public void testContainsAttributes_true() throws Exception {
        UIComponent component = new MockUIComponentBase();
        component.getAttributes().put("foo", "something");
        assertEquals(true, RendererUtil.containsAttributesForRender(component,
                new String[] { "foo" }));
    }

    public void testContainsAttributes_falseWhenDefaultValue() throws Exception {
        UIComponent component = new MockUIComponentBase();
        component.getAttributes().put("rendered", Boolean.FALSE);
        assertEquals(false, RendererUtil.containsAttributesForRender(component,
                new String[] { "rendered" }));
    }

    public void testGetConvertedUIOutputValues_submittedValueIsNull()
            throws Exception {
        assertNull(RendererUtil.getConvertedUIOutputValues(
                new MockFacesContextImpl(), new UIOutput(), null));
    }

    public void testGetConvertedUIOutputValues_converterIsNull()
            throws Exception {
        UIOutput out = new UIOutput();
        out.setConverter(null);
        Object o = RendererUtil.getConvertedUIOutputValues(
                new MockFacesContextImpl(), out, "aaa");
        assertEquals("aaa", o);
    }

    public void testGetConvertedUIOutputValues_arrayLengthIsZero()
            throws Exception {
        UIOutput out = new UIOutput();
        out.setConverter(new MockConverter());
        MockValueBinding vb = new MockValueBinding();
        vb.setType(int[].class);
        out.setValueBinding("value", vb);
        Object submittedValue = Array.newInstance(int.class, 0);
        Object o = RendererUtil.getConvertedUIOutputValues(
                new MockFacesContextImpl(), out, submittedValue);
        int[] result = (int[]) o;
        assertEquals(0, result.length);
    }

    public void testGetConvertedUIOutputValues_arrayReturn() throws Exception {
        UIOutput out = new UIOutput();
        out.setConverter(new IntegerConverter());
        MockValueBinding vb = new MockValueBinding();
        vb.setType(int[].class);
        out.setValueBinding("value", vb);
        Object submittedValue = Array.newInstance(String.class, 3);
        Array.set(submittedValue, 0, "1");
        Array.set(submittedValue, 1, "2");
        Array.set(submittedValue, 2, "3");
        Object o = RendererUtil.getConvertedUIOutputValues(
                new MockFacesContextImpl(), out, submittedValue);
        int[] result = (int[]) o;
        assertEquals(3, result.length);
        ArrayAssert.assertEquals(new int[] { 1, 2, 3 }, result);
    }

    // TODO more tests

}
