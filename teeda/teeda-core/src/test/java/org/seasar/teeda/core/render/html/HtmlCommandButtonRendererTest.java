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

    private HtmlCommandButtonRenderer renderer_;

    private MockHtmlCommandButton htmlCommandButton_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlCommandButtonRenderer();
        htmlCommandButton_ = new MockHtmlCommandButton();
        htmlCommandButton_.setRenderer(renderer_);
    }

    public void testEncode_NoValue() throws Exception {
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandButton_);

        // ## Assert ##
        assertEquals("<input type=\"submit\" id=\"_id0\" name=\"_id0\" />",
                getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlCommandButton_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandButton_);

        // ## Assert ##
        assertEquals(
                "<input type=\"submit\" id=\"_id0\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlCommandButton_.setRendered(false);
        htmlCommandButton_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandButton_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlCommandButton_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlCommandButton_);
        MockFacesContext context = getFacesContext();

        encodeByRenderer(renderer_, context, htmlCommandButton_);

        assertEquals("<input type=\"submit\" id=\"a\" name=\"b:a\" />",
                getResponseText());
    }

    public void testEncode_Reset() throws Exception {
        htmlCommandButton_.setId("a");
        htmlCommandButton_.setType("reset");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlCommandButton_);

        assertEquals("<input type=\"reset\" id=\"a\" name=\"a\" />",
                getResponseText());
    }

    public void testEncode_Image() throws Exception {
        // ## Arrange ##
        htmlCommandButton_.setId("a");
        htmlCommandButton_.setImage("bb");
        htmlCommandButton_.setValue("c"); // should be ignored

        // ## Act ##
        encodeByRenderer(renderer_, getFacesContext(), htmlCommandButton_);

        // ## Assert ##
        assertEquals("<input type=\"image\" id=\"a\" name=\"a\" src=\"bb\" />",
                getResponseText());
    }

    public void testEncode_ImageIfTypeIsReset() throws Exception {
        // ## Arrange ##
        htmlCommandButton_.setId("a");
        htmlCommandButton_.setType("reset"); // should be ignored
        htmlCommandButton_.setImage("bb");

        // ## Act ##
        encodeByRenderer(renderer_, getFacesContext(), htmlCommandButton_);

        // ## Assert ##
        assertEquals("<input type=\"image\" id=\"a\" name=\"a\" src=\"bb\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlCommandButton_.setAccesskey("a");
        htmlCommandButton_.setAlt("b");
        htmlCommandButton_.setDir("c");
        htmlCommandButton_.setDisabled(true);
        // htmlCommandButton_.setImage("e");
        htmlCommandButton_.setLang("f");
        htmlCommandButton_.setOnblur("g");
        htmlCommandButton_.setOnchange("h");
        htmlCommandButton_.setOnclick("i");
        htmlCommandButton_.setOndblclick("j");
        htmlCommandButton_.setOnfocus("k");
        htmlCommandButton_.setOnkeydown("l");
        htmlCommandButton_.setOnkeypress("m");
        htmlCommandButton_.setOnkeyup("n");
        htmlCommandButton_.setOnmousedown("o");
        htmlCommandButton_.setOnmousemove("p");
        htmlCommandButton_.setOnmouseout("q");
        htmlCommandButton_.setOnmouseover("r");
        htmlCommandButton_.setOnmouseup("s");
        htmlCommandButton_.setOnselect("t");
        htmlCommandButton_.setReadonly(true);
        htmlCommandButton_.setStyle("w");
        htmlCommandButton_.setStyleClass("u");
        htmlCommandButton_.setTabindex("x");
        htmlCommandButton_.setTitle("y");
        htmlCommandButton_.setType("reset");

        htmlCommandButton_.setId("A");
        htmlCommandButton_.setValue("B");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlCommandButton_);

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
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
                super.queueEvent(event);
            }
        };
        htmlCommandButton.setRenderer(renderer_);
        htmlCommandButton.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlCommandButton);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_NoneReset() throws Exception {
        // ## Arrange ##
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
                super.queueEvent(event);
            }
        };
        htmlCommandButton.setRenderer(renderer_);
        htmlCommandButton.setType("reset");
        htmlCommandButton.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer_.decode(context, htmlCommandButton);

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
        final FacesEvent[] args = { null };
        MockHtmlCommandButton htmlCommandButton = new MockHtmlCommandButton() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandButton.setRenderer(renderer_);
        htmlCommandButton.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put(requestKey,
                "12345");

        // ## Act ##
        renderer_.decode(context, htmlCommandButton);

        // ## Assert ##
        assertNotNull(requestKey, args[0]);
        ObjectAssert.assertInstanceOf(requestKey, ActionEvent.class, args[0]);
        assertSame(requestKey, htmlCommandButton, args[0].getSource());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
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
