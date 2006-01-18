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

    public void testEncodeEnd_WithNoValue() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals("<textarea id=\"_id0\" name=\"_id0\"></textarea>",
                getResponseText());
    }

    public void testEncodeEnd_WithValue() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
        htmlInputTextarea.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals("<textarea id=\"_id0\" name=\"_id0\">abc</textarea>",
                getResponseText());
    }

    public void testEncodeEnd_WithId() throws Exception {
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
        htmlInputTextarea.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputTextarea);

        renderer.encodeEnd(getFacesContext(), htmlInputTextarea);

        assertEquals("<textarea id=\"a\" name=\"b:a\"></textarea>",
                getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
        htmlInputTextarea.setClientId("key1");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals(null, htmlInputTextarea.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputText = new MockHtmlInputTextarea();
        htmlInputText.setRenderer(renderer);
        htmlInputText.setClientId("key1");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put("key1", "aabb");

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals("aabb", htmlInputText.getSubmittedValue());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputText = new MockHtmlInputTextarea();
        htmlInputText.setRenderer(renderer);

        htmlInputText.setAccesskey("a");
        htmlInputText.setCols(10);
        htmlInputText.setDir("c");
        htmlInputText.setDisabled(true);
        htmlInputText.setLang("e");
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
        htmlInputText.setRows(20);
        htmlInputText.setStyle("w");
        htmlInputText.setStyleClass("u");
        htmlInputText.setTabindex("x");
        htmlInputText.setTitle("y");

        htmlInputText.setId("A");
        htmlInputText.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer.encodeBegin(context, htmlInputText);
        renderer.encodeEnd(context, htmlInputText);

        Diff diff = new Diff("<textarea" + " id=\"A\" name=\"A\""
                + " accesskey=\"a\"" + " cols=\"10\"" + " dir=\"c\""
                + " disabled=\"true\"" + " lang=\"e\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"true\"" + " rows=\"20\"" + " style=\"w\""
                + " class=\"u\"" + " tabindex=\"x\""
                + " title=\"y\">B</textarea>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
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
