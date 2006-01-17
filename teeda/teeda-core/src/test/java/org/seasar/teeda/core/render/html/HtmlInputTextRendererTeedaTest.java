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
import javax.faces.render.Renderer;
import javax.faces.render.RendererTeedaTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
// TODO
public class HtmlInputTextRendererTeedaTest extends RendererTeedaTest {

    public void testEncodeEnd_WithNoValue() throws Exception {
        // ## Arrange ##
        HtmlInputText htmlInputText = new HtmlInputText();
        HtmlInputTextRenderer renderer = createHtmlInputTextRenderer();
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" id=\"_id0\" name=\"_id0\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithValue() throws Exception {
        // ## Arrange ##
        HtmlInputText htmlInputText = new HtmlInputText();
        htmlInputText.setValue("abc");
        HtmlInputTextRenderer renderer = createHtmlInputTextRenderer();
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" id=\"_id0\" name=\"_id0\" value=\"abc\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithId() throws Exception {
        HtmlInputText htmlInputText = new HtmlInputText();
        htmlInputText.setId("a");
        HtmlInputTextRenderer renderer = createHtmlInputTextRenderer();

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputText);

        renderer.encodeEnd(getFacesContext(), htmlInputText);

        assertEquals("<input type=\"text\" id=\"a\" name=\"b:a\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        HtmlInputText htmlInputText = new HtmlInputText();
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

        HtmlInputTextRenderer renderer = createHtmlInputTextRenderer();

        renderer.encodeBegin(getFacesContext(), htmlInputText);
        renderer.encodeEnd(getFacesContext(), htmlInputText);

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
                        + " tabindex=\"x\"" + " title=\"y\"/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    private HtmlInputTextRenderer createHtmlInputTextRenderer() {
        return (HtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlInputTextRenderer();
    }

}
