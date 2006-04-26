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
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlInputSecretRendererTest extends RendererTest {

    private HtmlInputSecretRenderer renderer_;

    private MockHtmlInputSecret htmlInputSecret_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlInputSecretRenderer();
        htmlInputSecret_ = new MockHtmlInputSecret();
        htmlInputSecret_.setRenderer(renderer_);
    }

    public void testEncode_NoValue() throws Exception {
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputSecret_);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncodeEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputSecret_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlInputSecret_);
        renderer_.encodeEnd(context, htmlInputSecret_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputSecret_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputSecret_);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithValueRedisplayTrue() throws Exception {
        // ## Arrange ##
        htmlInputSecret_.setValue("abc");
        htmlInputSecret_.setRedisplay(true);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputSecret_);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputSecret_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputSecret_);

        encodeByRenderer(renderer_, getFacesContext(), htmlInputSecret_);

        assertEquals(
                "<input type=\"password\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputSecret_.setAccesskey("a");
        htmlInputSecret_.setAlt("b");
        htmlInputSecret_.setDir("c");
        htmlInputSecret_.setDisabled(true);
        htmlInputSecret_.setLang("e");
        htmlInputSecret_.setMaxlength(5);
        htmlInputSecret_.setOnblur("g");
        htmlInputSecret_.setOnchange("h");
        htmlInputSecret_.setOnclick("i");
        htmlInputSecret_.setOndblclick("j");
        htmlInputSecret_.setOnfocus("k");
        htmlInputSecret_.setOnkeydown("l");
        htmlInputSecret_.setOnkeypress("m");
        htmlInputSecret_.setOnkeyup("n");
        htmlInputSecret_.setOnmousedown("o");
        htmlInputSecret_.setOnmousemove("p");
        htmlInputSecret_.setOnmouseout("q");
        htmlInputSecret_.setOnmouseover("r");
        htmlInputSecret_.setOnmouseup("s");
        htmlInputSecret_.setOnselect("t");
        htmlInputSecret_.setReadonly(true);
        htmlInputSecret_.setSize(2);
        htmlInputSecret_.setStyle("w");
        htmlInputSecret_.setStyleClass("u");
        htmlInputSecret_.setTabindex("x");
        htmlInputSecret_.setTitle("y");

        htmlInputSecret_.setId("Aa");
        htmlInputSecret_.setValue("Ba");
        htmlInputSecret_.setRedisplay(false);

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlInputSecret_);

        Diff diff = new Diff(
                "<input type=\"password\" id=\"Aa\" name=\"Aa\" value=\"\""
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
        htmlInputSecret_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlInputSecret_);

        // ## Assert ##
        assertEquals(null, htmlInputSecret_.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputSecret_.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer_.decode(context, htmlInputSecret_);

        // ## Assert ##
        assertEquals("12345", htmlInputSecret_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlInputSecretRenderer createHtmlInputSecretRenderer() {
        return (HtmlInputSecretRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputSecretRenderer renderer = new HtmlInputSecretRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlInputSecret extends HtmlInputSecret {
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
