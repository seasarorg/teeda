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
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ObjectAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlCommandButtonRendererTest extends RendererTest {

    public void testEncode_WithNoValue() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlCommandButton);

        // ## Assert ##
        assertEquals("<input type=\"submit\" id=\"_id0\" name=\"_id0\"/>",
                getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlCommandButton);

        // ## Assert ##
        assertEquals(
                "<input type=\"submit\" id=\"_id0\" name=\"_id0\" value=\"abc\"/>",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlCommandButton);

        renderer.encodeEnd(getFacesContext(), htmlCommandButton);

        assertEquals("<input type=\"submit\" id=\"a\" name=\"b:a\"/>",
                getResponseText());
    }

    public void testEncode_Reset() throws Exception {
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setId("a");
        htmlCommandButton.setType("reset");

        renderer.encodeEnd(getFacesContext(), htmlCommandButton);

        assertEquals("<input type=\"reset\" id=\"a\" name=\"a\"/>",
                getResponseText());
    }

    public void testEncode_Image() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setId("a");
        htmlCommandButton.setImage("bb");
        htmlCommandButton.setValue("c"); // should be ignored

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlCommandButton);

        // ## Assert ##
        assertEquals("<input type=\"image\" id=\"a\" name=\"a\" src=\"bb\"/>",
                getResponseText());
    }

    public void testEncode_ImageIfTypeIsReset() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setId("a");
        htmlCommandButton.setType("reset"); // should be ignored
        htmlCommandButton.setImage("bb");

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlCommandButton);

        // ## Assert ##
        assertEquals("<input type=\"image\" id=\"a\" name=\"a\" src=\"bb\"/>",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton();
        htmlCommandButton.setRenderer(renderer);

        htmlCommandButton.setAccesskey("a");
        htmlCommandButton.setAlt("b");
        htmlCommandButton.setDir("c");
        htmlCommandButton.setDisabled(true);
        // htmlCommandButton.setImage("e");
        htmlCommandButton.setLang("f");
        htmlCommandButton.setOnblur("g");
        htmlCommandButton.setOnchange("h");
        htmlCommandButton.setOnclick("i");
        htmlCommandButton.setOndblclick("j");
        htmlCommandButton.setOnfocus("k");
        htmlCommandButton.setOnkeydown("l");
        htmlCommandButton.setOnkeypress("m");
        htmlCommandButton.setOnkeyup("n");
        htmlCommandButton.setOnmousedown("o");
        htmlCommandButton.setOnmousemove("p");
        htmlCommandButton.setOnmouseout("q");
        htmlCommandButton.setOnmouseover("r");
        htmlCommandButton.setOnmouseup("s");
        htmlCommandButton.setOnselect("t");
        htmlCommandButton.setReadonly(true);
        htmlCommandButton.setStyle("w");
        htmlCommandButton.setStyleClass("u");
        htmlCommandButton.setTabindex("x");
        htmlCommandButton.setTitle("y");
        htmlCommandButton.setType("reset");

        htmlCommandButton.setId("A");
        htmlCommandButton.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer.encodeBegin(context, htmlCommandButton);
        renderer.encodeEnd(context, htmlCommandButton);

        Diff diff = new Diff(
                "<input type=\"reset\" id=\"A\" name=\"A\" value=\"B\""
                        + " accesskey=\"a\"" + " alt=\"b\"" + " dir=\"c\""
                        + " disabled=\"true\"" + " lang=\"f\""
                        + " onblur=\"g\"" + " onchange=\"h\""
                        + " onclick=\"i\"" + " ondblclick=\"j\""
                        + " onfocus=\"k\"" + " onkeydown=\"l\""
                        + " onkeypress=\"m\"" + " onkeyup=\"n\""
                        + " onmousedown=\"o\"" + " onmousemove=\"p\""
                        + " onmouseout=\"q\"" + " onmouseover=\"r\""
                        + " onmouseup=\"s\"" + " onselect=\"t\""
                        + " readonly=\"true\"" + " style=\"w\""
                        + " class=\"u\"" + " tabindex=\"x\"" + " title=\"y\""
                        + "/>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
                super.queueEvent(event);
            }
        };
        htmlCommandButton.setRenderer(renderer);
        htmlCommandButton.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlCommandButton);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_NoneReset() throws Exception {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
                super.queueEvent(event);
            }
        };
        htmlCommandButton.setRenderer(renderer);
        htmlCommandButton.setType("reset");
        htmlCommandButton.setClientId("key");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put("key", "12345");

        // ## Act ##
        renderer.decode(context, htmlCommandButton);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_SubmitSuccess() throws Exception {
        submitSuccessTest("key:aa");
    }

    public void testDecode_SubmitSuccessX() throws Exception {
        submitSuccessTest("key:aa.x");
    }

    public void testDecode_SubmitSuccessY() throws Exception {
        submitSuccessTest("key:aa.y");
    }

    private void submitSuccessTest(final String requestKey) {
        // ## Arrange ##
        HtmlCommandButtonRenderer renderer = createHtmlCommandButtonRenderer();
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandButton.setRenderer(renderer);
        htmlCommandButton.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put(requestKey, "12345");

        // ## Act ##
        renderer.decode(context, htmlCommandButton);

        // ## Assert ##
        assertNotNull(requestKey, args[0]);
        ObjectAssert.assertInstanceOf(requestKey, ActionEvent.class, args[0]);
        assertSame(requestKey, htmlCommandButton, args[0].getSource());
    }

    public void testGetRendersChildren() throws Exception {
        HtmlCommandButtonRenderer renderer = new HtmlCommandButtonRenderer();
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlCommandButtonRenderer createHtmlCommandButtonRenderer() {
        return (HtmlCommandButtonRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlCommandButtonRenderer();
    }

    private static class MockHtmlCommandButton extends HtmlCommandButton {
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
