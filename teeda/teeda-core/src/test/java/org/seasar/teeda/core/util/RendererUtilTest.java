/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;

import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 * @author shot
 */
public class RendererUtilTest extends TeedaTestCase {

    public void testRenderAttribute() throws Exception {
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(new StringWriter());
        HtmlInputText htmlInputText = new HtmlInputText();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputText);
        RendererUtil.renderAttribute(writer, "readonly", "true");
        RendererUtil.renderAttribute(writer, "disabled", "true");
        writer.endElement(JsfConstants.INPUT_ELEM);
        assertEquals("<input readonly=\"readonly\" disabled=\"disabled\" />",
                writer.toString());
    }

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

    public void testRenderChild1() throws Exception {
        FacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false, false, false };
        MockUIComponent child = new MockUIComponent() {

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

            public boolean getRendersChildren() {
                return true;
            }

        };
        RendererUtil.renderChild(context, child);
        assertTrue(calls[0]);
        assertTrue(calls[1]);
        assertTrue(calls[2]);
    }

    public void testRenderChild2() throws Exception {
        FacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false, false, false };
        MockUIComponent child = new MockUIComponent() {

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

            public boolean getRendersChildren() {
                return false;
            }

        };
        RendererUtil.renderChild(context, child);
        assertTrue(calls[0]);
        assertFalse(calls[1]);
        assertTrue(calls[2]);
    }

    public void testRenderChildlen1() throws Exception {
        FacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false, false, false };
        MockUIComponent child = new MockUIComponent() {

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

            public boolean getRendersChildren() {
                return true;
            }

        };
        MockUIComponent parent = new MockUIComponent();
        parent.getChildren().add(child);
        RendererUtil.renderChildren(context, parent);
        assertTrue(calls[0]);
        assertTrue(calls[1]);
        assertTrue(calls[2]);
    }

}
