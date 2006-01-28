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
package org.seasar.teeda.core.render.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlInputTextRendererTest extends RendererTest {

    private HtmlInputTextRenderer renderer_;

    private MockHtmlInputText htmlInputText_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlInputTextRenderer();
        htmlInputText_ = new MockHtmlInputText();
        htmlInputText_.setRenderer(renderer_);
    }

    public void testEncode_NoValue() throws Exception {
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputText_);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" id=\"_id0\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputText_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputText_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputText_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputText_);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" id=\"_id0\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputText_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputText_);

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlInputText_);

        assertEquals(
                "<input type=\"text\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputText_.setAccesskey("a");
        htmlInputText_.setAlt("b");
        htmlInputText_.setDir("c");
        htmlInputText_.setDisabled(true);
        htmlInputText_.setLang("e");
        htmlInputText_.setMaxlength(5);
        htmlInputText_.setOnblur("g");
        htmlInputText_.setOnchange("h");
        htmlInputText_.setOnclick("i");
        htmlInputText_.setOndblclick("j");
        htmlInputText_.setOnfocus("k");
        htmlInputText_.setOnkeydown("l");
        htmlInputText_.setOnkeypress("m");
        htmlInputText_.setOnkeyup("n");
        htmlInputText_.setOnmousedown("o");
        htmlInputText_.setOnmousemove("p");
        htmlInputText_.setOnmouseout("q");
        htmlInputText_.setOnmouseover("r");
        htmlInputText_.setOnmouseup("s");
        htmlInputText_.setOnselect("t");
        htmlInputText_.setReadonly(true);
        htmlInputText_.setSize(2);
        htmlInputText_.setStyle("w");
        htmlInputText_.setStyleClass("u");
        htmlInputText_.setTabindex("x");
        htmlInputText_.setTitle("y");

        htmlInputText_.setId("A");
        htmlInputText_.setValue("B");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlInputText_);

        Diff diff = new Diff(
                "<input type=\"text\" id=\"A\" name=\"A\" value=\"B\""
                        + " accesskey=\"a\"" + " alt=\"b\"" + " dir=\"c\""
                        + " disabled=\"true\"" + " lang=\"e\""
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
        htmlInputText_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlInputText_);

        // ## Assert ##
        assertEquals(null, htmlInputText_.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputText_.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer_.decode(context, htmlInputText_);

        // ## Assert ##
        assertEquals("12345", htmlInputText_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlInputTextRenderer createHtmlInputTextRenderer() {
        return (HtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlInputTextRenderer();
    }

    private static class MockHtmlInputText extends HtmlInputText {
        private Renderer renderer_;

        private String clientId_;

        public void setRenderer(Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId_ != null) {
                return clientId_;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            clientId_ = clientId;
        }
    }

}
