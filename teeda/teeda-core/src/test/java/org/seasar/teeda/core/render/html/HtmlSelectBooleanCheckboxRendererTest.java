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

import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlSelectBooleanCheckboxRendererTest extends RendererTest {

    private HtmlSelectBooleanCheckboxRenderer renderer_;

    private MockHtmlSelectBooleanCheckbox htmlSelectBooleanCheckbox_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectBooleanCheckboxRenderer();
        htmlSelectBooleanCheckbox_ = new MockHtmlSelectBooleanCheckbox();
        htmlSelectBooleanCheckbox_.setRenderer(renderer_);
    }

    public void testEncode_NoValue() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setRendered(false);
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" />",
                getResponseText());
    }

    public void testEncode_WithValueChecked() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setValue("true");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" checked=\"true\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectBooleanCheckbox_.setAccesskey("a");
        htmlSelectBooleanCheckbox_.setDir("c");
        htmlSelectBooleanCheckbox_.setDisabled(true);
        htmlSelectBooleanCheckbox_.setLang("e");
        htmlSelectBooleanCheckbox_.setOnblur("g");
        htmlSelectBooleanCheckbox_.setOnchange("h");
        htmlSelectBooleanCheckbox_.setOnclick("i");
        htmlSelectBooleanCheckbox_.setOndblclick("j");
        htmlSelectBooleanCheckbox_.setOnfocus("k");
        htmlSelectBooleanCheckbox_.setOnkeydown("l");
        htmlSelectBooleanCheckbox_.setOnkeypress("m");
        htmlSelectBooleanCheckbox_.setOnkeyup("n");
        htmlSelectBooleanCheckbox_.setOnmousedown("o");
        htmlSelectBooleanCheckbox_.setOnmousemove("p");
        htmlSelectBooleanCheckbox_.setOnmouseout("q");
        htmlSelectBooleanCheckbox_.setOnmouseover("r");
        htmlSelectBooleanCheckbox_.setOnmouseup("s");
        htmlSelectBooleanCheckbox_.setOnselect("t");
        htmlSelectBooleanCheckbox_.setReadonly(true);
        htmlSelectBooleanCheckbox_.setStyle("w");
        htmlSelectBooleanCheckbox_.setStyleClass("u");
        htmlSelectBooleanCheckbox_.setTabindex("x");
        htmlSelectBooleanCheckbox_.setTitle("y");

        htmlSelectBooleanCheckbox_.setId("A");
        htmlSelectBooleanCheckbox_.setValue("true");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectBooleanCheckbox_);

        Diff diff = new Diff("<input type=\"checkbox\" id=\"A\" name=\"A\""
                + " value=\"true\"" + " checked=\"true\"" + " accesskey=\"a\""
                + " dir=\"c\"" + " disabled=\"true\"" + " lang=\"e\""
                + " onblur=\"g\"" + " onchange=\"h\"" + " onclick=\"i\""
                + " ondblclick=\"j\"" + " onfocus=\"k\"" + " onkeydown=\"l\""
                + " onkeypress=\"m\"" + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"true\"" + " style=\"w\"" + " class=\"u\""
                + " tabindex=\"x\"" + " title=\"y\"" + "/>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox_.getSetSubmittedValueCalls());
        assertEquals("false", htmlSelectBooleanCheckbox_.getSubmittedValue());
    }

    public void testDecode_RequestParameterInvalidValue() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key", "aaa");

        // ## Act ##
        renderer_.decode(context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox_.getSetSubmittedValueCalls());
        assertEquals("false", htmlSelectBooleanCheckbox_.getSubmittedValue());
    }

    public void testDecode_RequestParameterTrue() throws Exception {
        decodeSuccessTest("tRue");
    }

    public void testDecode_RequestParameterOn() throws Exception {
        decodeSuccessTest("oN");
    }

    public void testDecode_RequestParameterYes() throws Exception {
        decodeSuccessTest("yes");
    }

    private void decodeSuccessTest(String input) throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("keyA", input);

        // ## Act ##
        renderer_.decode(context, htmlSelectBooleanCheckbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox_.getSetSubmittedValueCalls());
        assertEquals("true", htmlSelectBooleanCheckbox_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlSelectBooleanCheckboxRenderer createHtmlSelectBooleanCheckboxRenderer() {
        return (HtmlSelectBooleanCheckboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlSelectBooleanCheckboxRenderer();
    }

    private static class MockHtmlSelectBooleanCheckbox extends
            HtmlSelectBooleanCheckbox {
        private Renderer renderer_;

        private String clientId_;

        private int setSubmittedValueCalls_;

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

        public void setSubmittedValue(Object submittedValue) {
            setSubmittedValueCalls_++;
            super.setSubmittedValue(submittedValue);
        }

        public int getSetSubmittedValueCalls() {
            return setSubmittedValueCalls_;
        }
    }

}
