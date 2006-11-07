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

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlSelectBooleanCheckbox;

/**
 * @author manhole
 */
public class HtmlSelectBooleanCheckboxRendererTest extends RendererTest {

    private HtmlSelectBooleanCheckboxRenderer renderer;

    private MockHtmlSelectBooleanCheckbox htmlSelectBooleanCheckbox;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectBooleanCheckboxRenderer();
        htmlSelectBooleanCheckbox = new MockHtmlSelectBooleanCheckbox();
        htmlSelectBooleanCheckbox.setRenderer(renderer);

        // MockHtmlSelectBooleanCheckboxのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.getAttributes().put("a", "b");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" a=\"b\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.getAttributes().put("a.b", "b");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" />",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setValue("abc");
        htmlSelectBooleanCheckbox.setDisabled(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" disabled=\"disabled\" />",
                getResponseText());
    }

    public void testEncode_Checked() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setValue("true");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(
                "<input type=\"checkbox\" name=\"_id0\" value=\"true\" checked=\"checked\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectBooleanCheckbox.setAccesskey("a");
        htmlSelectBooleanCheckbox.setDir("c");
        htmlSelectBooleanCheckbox.setDisabled(true);
        htmlSelectBooleanCheckbox.setLang("e");
        htmlSelectBooleanCheckbox.setOnblur("g");
        htmlSelectBooleanCheckbox.setOnchange("h");
        htmlSelectBooleanCheckbox.setOnclick("i");
        htmlSelectBooleanCheckbox.setOndblclick("j");
        htmlSelectBooleanCheckbox.setOnfocus("k");
        htmlSelectBooleanCheckbox.setOnkeydown("l");
        htmlSelectBooleanCheckbox.setOnkeypress("m");
        htmlSelectBooleanCheckbox.setOnkeyup("n");
        htmlSelectBooleanCheckbox.setOnmousedown("o");
        htmlSelectBooleanCheckbox.setOnmousemove("p");
        htmlSelectBooleanCheckbox.setOnmouseout("q");
        htmlSelectBooleanCheckbox.setOnmouseover("r");
        htmlSelectBooleanCheckbox.setOnmouseup("s");
        htmlSelectBooleanCheckbox.setOnselect("t");
        htmlSelectBooleanCheckbox.setReadonly(true);
        htmlSelectBooleanCheckbox.setStyle("w");
        htmlSelectBooleanCheckbox.setStyleClass("u");
        htmlSelectBooleanCheckbox.setTabindex("x");
        htmlSelectBooleanCheckbox.setTitle("y");

        htmlSelectBooleanCheckbox.setId("A");
        htmlSelectBooleanCheckbox.setValue("true");

        encodeByRenderer(renderer, htmlSelectBooleanCheckbox);

        Diff diff = new Diff("<input type=\"checkbox\" id=\"A\" name=\"A\""
                + " value=\"true\"" + " checked=\"checked\""
                + " accesskey=\"a\"" + " dir=\"c\"" + " disabled=\"disabled\""
                + " lang=\"e\"" + " onblur=\"g\"" + " onchange=\"h\""
                + " onclick=\"i\"" + " ondblclick=\"j\"" + " onfocus=\"k\""
                + " onkeydown=\"l\"" + " onkeypress=\"m\"" + " onkeyup=\"n\""
                + " onmousedown=\"o\"" + " onmousemove=\"p\""
                + " onmouseout=\"q\"" + " onmouseover=\"r\""
                + " onmouseup=\"s\"" + " onselect=\"t\"" + " readonly=\"true\""
                + " style=\"w\"" + " class=\"u\"" + " tabindex=\"x\""
                + " title=\"y\"" + "/>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox.getSetSubmittedValueCalls());
        assertEquals("false", htmlSelectBooleanCheckbox.getSubmittedValue());
    }

    public void testDecode_RequestParameterInvalidValue() throws Exception {
        // ## Arrange ##
        htmlSelectBooleanCheckbox.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key", "aaa");

        // ## Act ##
        renderer.decode(context, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox.getSetSubmittedValueCalls());
        assertEquals("false", htmlSelectBooleanCheckbox.getSubmittedValue());
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
        htmlSelectBooleanCheckbox.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap()
                .put("keyA", input);

        // ## Act ##
        renderer.decode(context, htmlSelectBooleanCheckbox);

        // ## Assert ##
        assertEquals(1, htmlSelectBooleanCheckbox.getSetSubmittedValueCalls());
        assertEquals("true", htmlSelectBooleanCheckbox.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlSelectBooleanCheckboxRenderer createHtmlSelectBooleanCheckboxRenderer() {
        return (HtmlSelectBooleanCheckboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectBooleanCheckboxRenderer renderer = new HtmlSelectBooleanCheckboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
