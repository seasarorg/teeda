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
package org.seasar.teeda.extension.render.html;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.extension.component.html.THtmlInputText;

/**
 * @author shot
 */
public class THtmlInputTextRendererTest extends RendererTest {

    private THtmlInputTextRenderer renderer;

    private MockTHtmlInputText htmlInputText;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlInputTextRenderer();
        htmlInputText = (MockTHtmlInputText) createUIInput();
        htmlInputText.setRenderer(renderer);
    }

    protected UIInput createUIInput() {
        return new MockTHtmlInputText();
    }

    public void testEncode_NoValue() throws Exception {

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals("<input type=\"text\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputText.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputText.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals("<input type=\"text\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputText.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputText);

        encodeByRenderer(renderer, htmlInputText);

        assertEquals(
                "<input type=\"text\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        htmlInputText.setId("a");
        htmlInputText.getAttributes().put("bbb", "ccc");

        encodeByRenderer(renderer, htmlInputText);

        assertEquals(
                "<input type=\"text\" id=\"a\" name=\"a\" value=\"\" bbb=\"ccc\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        htmlInputText.setId("a");
        htmlInputText.getAttributes().put("b.b", "ccc");

        encodeByRenderer(renderer, htmlInputText);

        assertEquals("<input type=\"text\" id=\"a\" name=\"a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputText.setAccesskey("a");
        htmlInputText.setAlt("b");
        htmlInputText.setDir("c");
        htmlInputText.setDisabled(true);
        htmlInputText.setLang("e");
        htmlInputText.setMaxlength(5);
        htmlInputText.setOnblur("g");
        htmlInputText.setOnchange("h");
        htmlInputText.setOnclick("i");
        htmlInputText.setOndblclick("j");
        htmlInputText.setOnfocus("k");
        htmlInputText.setOnkeydown("l");
        htmlInputText.setOnkeypress("m");
        htmlInputText.setOnkeyup("n");
        htmlInputText.setOnmousedown("o");
        htmlInputText.setOnmousemove("p");
        htmlInputText.setOnmouseout("q");
        htmlInputText.setOnmouseover("r");
        htmlInputText.setOnmouseup("s");
        htmlInputText.setOnselect("t");
        htmlInputText.setReadonly(true);
        htmlInputText.setSize(2);
        htmlInputText.setStyle("w");
        htmlInputText.setStyleClass("u");
        htmlInputText.setTabindex("x");
        htmlInputText.setTitle("y");

        htmlInputText.setId("A");
        htmlInputText.setValue("B");

        encodeByRenderer(renderer, htmlInputText);

        Diff diff = new Diff(
                "<input type=\"text\" id=\"A\" name=\"A\" value=\"B\""
                        + " accesskey=\"a\"" + " alt=\"b\"" + " dir=\"c\""
                        + " disabled=\"disabled\"" + " lang=\"e\""
                        + " maxlength=\"5\"" + " onblur=\"g\""
                        + " onchange=\"h\"" + " onclick=\"i\""
                        + " ondblclick=\"j\"" + " onfocus=\"k\""
                        + " onkeydown=\"l\"" + " onkeypress=\"m\""
                        + " onkeyup=\"n\"" + " onmousedown=\"o\""
                        + " onmousemove=\"p\"" + " onmouseout=\"q\""
                        + " onmouseover=\"r\"" + " onmouseup=\"s\""
                        + " onselect=\"t\"" + " readonly=\"true\""
                        + " size=\"2\"" + " style=\"w\"" + " class=\"u\""
                        + " tabindex=\"x\"" + " title=\"y\"" + "/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputText.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals(null, htmlInputText.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputText.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals("12345", htmlInputText.getSubmittedValue());
    }

    public void testEncode_Converter() throws Exception {
        // ## Arrange ##
        Converter converter = new MockConverter() {
            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException {
                return value + "ddd";
            }
        };
        htmlInputText.setValue("abc");
        htmlInputText.setConverter(converter);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals("<input type=\"text\" name=\"_id0\" value=\"abcddd\" />",
                getResponseText());
    }

    public void testGetConvertedValue() throws Exception {
        Converter converter = new MockConverter() {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return value + ".testGetConvertedValue";
            }
        };
        htmlInputText.setConverter(converter);
        Object convertedValue = renderer.getConvertedValue(getFacesContext(),
                htmlInputText, "aaa");

        assertEquals("aaa.testGetConvertedValue", convertedValue);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlInputTextRenderer createHtmlInputTextRenderer() {
        return (HtmlInputTextRenderer) createRenderer();
    }

    public void testErrorStyleClass() throws Exception {
        htmlInputText.setClientId("hoge");
        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("hoge", new FacesMessage("sssss"));
        htmlInputText.setErrorStyleClass("foo");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"hoge\" value=\"\" class=\"foo\" />",
                getResponseText());

    }

    private THtmlInputTextRenderer createTHtmlInputTextRenderer() {
        return (THtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputTextRenderer renderer = new THtmlInputTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public static class MockTHtmlInputText extends THtmlInputText {

        private Renderer renderer;

        private String clientId;

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer != null) {
                return renderer;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId != null) {
                return clientId;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

    }
}
