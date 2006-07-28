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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Locale;

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.mock.MockHtmlInputCommaText;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class THtmlGridInputCommaTextRendererTest extends RendererTest {

    private THtmlGridInputCommaTextRenderer renderer;

    private MockHtmlInputCommaText gridInputText;

    private HtmlOutputTextRenderer outputTextRenderer = new HtmlOutputTextRenderer();

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlGridInputCommaTextRenderer) createRenderer();
        outputTextRenderer = new HtmlOutputTextRenderer();
        outputTextRenderer.setRenderAttributes(getRenderAttributes());
        gridInputText = new MockHtmlInputCommaText();
        gridInputText.setRenderer(renderer);

        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        getFacesContext().setViewRoot(root);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        gridInputText.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        final String expected = "<div onclick=\"editOn(this);\">"
                + "<span></span>"
                + "<input type=\"text\" name=\"_id0\" value=\"\""
                + " onfocus=\"removeComma(this, ',');\""
                + " onblur=\"convertByKey(this);addComma(this, '0', ',', '.');editOff(this);\""
                + " onkeydown=\"return keycheckForNumber(event);\""
                + " onkeypress=\"return keycheckForNumber(event);\""
                + " onkeyup=\"convertByKey(this);\""
                + " style=\"ime-mode:disabled;display:none;\""
                + " class=\"gridCellEdit\" />" + "</div>";
        final String responseText = getResponseText();
        final String actual = removeScriptElement(responseText);
        System.out.println(actual);
        Diff diff = diff(expected, actual);
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        gridInputText.setAccesskey("a");
        gridInputText.setAlt("b");
        gridInputText.setDir("c");
        gridInputText.setDisabled(true);
        gridInputText.setFraction("2");
        gridInputText.setLang("e");
        gridInputText.setMaxlength(5);
        gridInputText.setOnblur("g");
        gridInputText.setOnchange("h");
        gridInputText.setOnclick("i");
        gridInputText.setOndblclick("j");
        gridInputText.setOnfocus("k");
        gridInputText.setOnkeydown("l");
        gridInputText.setOnkeypress("m");
        gridInputText.setOnkeyup("n");
        gridInputText.setOnmousedown("o");
        gridInputText.setOnmousemove("p");
        gridInputText.setOnmouseout("q");
        gridInputText.setOnmouseover("r");
        gridInputText.setOnmouseup("s");
        gridInputText.setOnselect("t");
        gridInputText.setReadonly(true);
        gridInputText.setSize(3);
        gridInputText.setStyle("w");
        gridInputText.setStyleClass("u");
        gridInputText.setTabindex("x");
        gridInputText.setTitle("y");

        gridInputText.getAttributes().put("id", "AA");
        gridInputText.getAttributes().put("value", "1234567");

        MockFacesContext context = getFacesContext();
        context.getViewRoot().setLocale(Locale.JAPAN);

        encodeByRenderer(renderer, gridInputText);

        final String responseText = getResponseText();
        final String actual = removeScriptElement(responseText);
        System.out.println(actual);
        String expected = "<div onclick=\"editOn(this);\">"
                + "<span>1,234,567</span>"
                + "<input type=\"text\" id=\"AA\" name=\"AA\" value=\"1,234,567\""
                + " accesskey=\"a\""
                + " alt=\"b\""
                + " dir=\"c\""
                + " disabled=\"disabled\""
                + " label=\"AA\""
                + " lang=\"e\""
                + " class=\"u gridCellEdit\""
                + " style=\"w;ime-mode:disabled;display:none;\""
                + " onfocus=\"k;removeComma(this, ',');\""
                + " onblur=\"g;convertByKey(this);addComma(this, '2', ',', '.');editOff(this);\""
                + " onkeydown=\"l;return keycheckForNumber(event);\""
                + " onkeypress=\"m;return keycheckForNumber(event);\""
                + " onkeyup=\"n;convertByKey(this);\"" + " onchange=\"h\""
                + " readonly=\"true\"" + " ondblclick=\"j\"" + " size=\"3\""
                + " onmouseover=\"r\" " + "tabindex=\"x\"" + " maxlength=\"5\""
                + " onclick=\"i\"" + " onmouseout=\"q\"" + " onmousedown=\"o\""
                + " onselect=\"t\"" + " onmouseup=\"s\"" + " onmousemove=\"p\""
                + " title=\"y\"" + " />" + "</div>";
        Diff diff = diff(expected, actual);

        assertEquals(diff.toString(), true, diff.identical());
    }

    private String removeScriptElement(final String responseText) {
        final int start = responseText.indexOf("<script");
        final int end = responseText.indexOf("</script>")
                + "</script>".length();
        String a = responseText.substring(0, start).trim()
                + responseText.substring(end).trim();
        return a;
    }

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        return super.diff("<dummy>" + expected + "</dummy>", "<dummy>" + actual
                + "</dummy>");
    }

    protected Renderer createRenderer() {
        THtmlGridInputCommaTextRenderer renderer = new THtmlGridInputCommaTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        return renderer;
    }

}
