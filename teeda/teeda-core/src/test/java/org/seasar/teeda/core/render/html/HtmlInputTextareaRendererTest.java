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
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlInputTextareaRendererTest extends RendererTest {

    private HtmlInputTextareaRenderer renderer_;

    private MockHtmlInputTextarea htmlInputTextarea_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlInputTextareaRenderer();
        htmlInputTextarea_ = new MockHtmlInputTextarea();
        htmlInputTextarea_.setRenderer(renderer_);
    }

    public void testEncode_WithNoValue() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputTextarea_);

        // ## Assert ##
        assertEquals("<textarea id=\"_id0\" name=\"_id0\"></textarea>",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputTextarea_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputTextarea_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputTextarea_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlInputTextarea_);

        // ## Assert ##
        assertEquals("<textarea id=\"_id0\" name=\"_id0\">abc</textarea>",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputTextarea_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputTextarea_);

        encodeByRenderer(renderer_, getFacesContext(), htmlInputTextarea_);

        assertEquals("<textarea id=\"a\" name=\"b:a\"></textarea>",
                getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputTextarea_.setClientId("key1");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlInputTextarea_);

        // ## Assert ##
        assertEquals(null, htmlInputTextarea_.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputTextarea_.setClientId("key1");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key1",
                "aabb");

        // ## Act ##
        renderer_.decode(context, htmlInputTextarea_);

        // ## Assert ##
        assertEquals("aabb", htmlInputTextarea_.getSubmittedValue());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        htmlInputTextarea_.setAccesskey("a");
        htmlInputTextarea_.setCols(10);
        htmlInputTextarea_.setDir("c");
        htmlInputTextarea_.setDisabled(true);
        htmlInputTextarea_.setLang("e");
        htmlInputTextarea_.setOnblur("g");
        htmlInputTextarea_.setOnchange("h");
        htmlInputTextarea_.setOnclick("i");
        htmlInputTextarea_.setOndblclick("j");
        htmlInputTextarea_.setOnfocus("k");
        htmlInputTextarea_.setOnkeydown("l");
        htmlInputTextarea_.setOnkeypress("m");
        htmlInputTextarea_.setOnkeyup("n");
        htmlInputTextarea_.setOnmousedown("o");
        htmlInputTextarea_.setOnmousemove("p");
        htmlInputTextarea_.setOnmouseout("q");
        htmlInputTextarea_.setOnmouseover("r");
        htmlInputTextarea_.setOnmouseup("s");
        htmlInputTextarea_.setOnselect("t");
        htmlInputTextarea_.setReadonly(true);
        htmlInputTextarea_.setRows(20);
        htmlInputTextarea_.setStyle("w");
        htmlInputTextarea_.setStyleClass("u");
        htmlInputTextarea_.setTabindex("x");
        htmlInputTextarea_.setTitle("y");

        htmlInputTextarea_.setId("A");
        htmlInputTextarea_.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer_.encodeBegin(context, htmlInputTextarea_);
        renderer_.encodeEnd(context, htmlInputTextarea_);

        Diff diff = new Diff("<textarea" + " id=\"A\" name=\"A\""
                + " accesskey=\"a\"" + " cols=\"10\"" + " dir=\"c\""
                + " disabled=\"true\"" + " lang=\"e\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"true\"" + " rows=\"20\"" + " style=\"w\""
                + " class=\"u\"" + " tabindex=\"x\"" + " title=\"y\""
                + ">B</textarea>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlInputTextareaRenderer createHtmlInputTextareaRenderer() {
        return (HtmlInputTextareaRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlInputTextareaRenderer();
    }

    private static class MockHtmlInputTextarea extends HtmlInputTextarea {

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
