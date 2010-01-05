/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.extension.component.html.THtmlInputTextarea;

/**
 * @author shot
 */
public class THtmlInputTextareaRendererTest extends RendererTest {

    private THtmlInputTextareaRenderer renderer;

    private MockTHtmlInputTextarea htmlInputTextarea;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlInputTextareaRenderer();
        htmlInputTextarea = new MockTHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
    }

    public void testEncode_WithNoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlInputTextarea);

        // ## Assert ##
        assertEquals("<textarea name=\"_id0\"></textarea>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputTextarea);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputTextarea);

        // ## Assert ##
        assertEquals("<textarea name=\"_id0\">abc</textarea>",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputTextarea.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputTextarea);

        encodeByRenderer(renderer, htmlInputTextarea);

        assertEquals("<textarea id=\"a\" name=\"b:a\"></textarea>",
                getResponseText());
    }

    /**
     * name属性は無視されること。
     * (実装がマズイとnameが2回出力されてしまう)
     */
    public void testEncode_WithName() throws Exception {
        htmlInputTextarea.setId("a");
        htmlInputTextarea.getAttributes().put("name", "hoge");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputTextarea);

        encodeByRenderer(renderer, htmlInputTextarea);

        assertEquals("<textarea id=\"a\" name=\"b:a\"></textarea>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        htmlInputTextarea.setId("a");
        htmlInputTextarea.getAttributes().put("aa", "bb");

        encodeByRenderer(renderer, htmlInputTextarea);

        assertEquals("<textarea id=\"a\" name=\"a\" aa=\"bb\"></textarea>",
                getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setClientId("key1");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals(null, htmlInputTextarea.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setClientId("key1");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key1",
                "aabb");

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals("aabb", htmlInputTextarea.getSubmittedValue());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        htmlInputTextarea.setAccesskey("a");
        htmlInputTextarea.setCols(10);
        htmlInputTextarea.setDir("c");
        htmlInputTextarea.setDisabled(true);
        htmlInputTextarea.setLang("e");
        htmlInputTextarea.setOnblur("g");
        htmlInputTextarea.setOnchange("h");
        htmlInputTextarea.setOnclick("i");
        htmlInputTextarea.setOndblclick("j");
        htmlInputTextarea.setOnfocus("k");
        htmlInputTextarea.setOnkeydown("l");
        htmlInputTextarea.setOnkeypress("m");
        htmlInputTextarea.setOnkeyup("n");
        htmlInputTextarea.setOnmousedown("o");
        htmlInputTextarea.setOnmousemove("p");
        htmlInputTextarea.setOnmouseout("q");
        htmlInputTextarea.setOnmouseover("r");
        htmlInputTextarea.setOnmouseup("s");
        htmlInputTextarea.setOnselect("t");
        htmlInputTextarea.setReadonly(true);
        htmlInputTextarea.setRows(20);
        htmlInputTextarea.setStyle("w");
        htmlInputTextarea.setStyleClass("u");
        htmlInputTextarea.setTabindex("x");
        htmlInputTextarea.setTitle("y");
        htmlInputTextarea.setWrap("z");

        htmlInputTextarea.setId("A");
        htmlInputTextarea.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer.encodeBegin(context, htmlInputTextarea);
        renderer.encodeEnd(context, htmlInputTextarea);

        Diff diff = new Diff("<textarea" + " id=\"A\" name=\"A\""
                + " accesskey=\"a\"" + " cols=\"10\"" + " dir=\"c\""
                + " disabled=\"disabled\"" + " lang=\"e\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"readonly\"" + " rows=\"20\"" + " style=\"w\""
                + " class=\"u\"" + " tabindex=\"x\"" + " title=\"y\""
                + " wrap=\"z\"" + ">B</textarea>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testErrorStyleClass() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setClientId("aaId");
        htmlInputTextarea.setStyleClass("cc");

        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("aaId", new FacesMessage("sssss"));
        htmlInputTextarea.setErrorStyleClass("fooErrorClass");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputTextarea);

        System.out.println(getResponseText());
        // ## Assert ##
        assertEquals(
                "<textarea name=\"aaId\" class=\"cc fooErrorClass\"></textarea>",
                getResponseText());
    }

    private THtmlInputTextareaRenderer createTHtmlInputTextareaRenderer() {
        return (THtmlInputTextareaRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputTextareaRenderer renderer = new THtmlInputTextareaRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockTHtmlInputTextarea extends THtmlInputTextarea {

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
