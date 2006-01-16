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

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;

/**
 * @author manhole
 */
public class HtmlOutputTextRendererTest extends RendererTest {

    public void testEncodeEnd() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setValue("abc");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("abc", getResponseText());
    }

    public void testEncodeEnd_NullValue() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setValue(null);
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("", getResponseText());
    }

    public void testEncodeEnd_EscapeTrue() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        assertTrue("default is true", htmlOutputText.isEscape());
        htmlOutputText.setValue("<a>");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("&lt;a&gt;", getResponseText());
    }

    public void testEncodeEnd_EscapeFalse() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setEscape(false);
        htmlOutputText.setValue("<a>");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("<a>", getResponseText());
    }

    public void testEncodeEnd_WithStyle() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setStyle("some style");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("<span style=\"some style\">a</span>", getResponseText());
    }

    public void testEncodeEnd_WithStyleClass() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setStyleClass("some styleClass");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("styleClass -> class",
                "<span class=\"some styleClass\">a</span>", getResponseText());
    }

    public void testEncodeEnd_WithTitle() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setTitle("some title");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        assertEquals("<span title=\"some title\">a</span>", getResponseText());
    }

    public void testEncodeEnd_CommonAttributtes() throws Exception {
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.getAttributes().put("onmouseout", "do something");
        htmlOutputText.getAttributes().put("title", "someTitle");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        Diff diff = new Diff(
                "<span title=\"someTitle\" onmouseout=\"do something\">a</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncodeEnd_Id() throws Exception {
        // ## Arrange ##
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setId("someId");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        // ## Assert ##
        assertEquals("<span id=\"someId\">a</span>", getResponseText());
    }

    public void testEncodeEnd_NotWriteId() throws Exception {
        // ## Arrange ##
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setId(UIViewRoot.UNIQUE_ID_PREFIX + "someId");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        // ## Assert ##
        assertEquals("<span>a</span>", getResponseText());
    }

    public void testEncodeEnd_WithAllAttributes() throws Exception {
        // ## Arrange ##
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        htmlOutputText.setId("fooId");
        htmlOutputText.setTitle("someTitle");
        htmlOutputText.getAttributes().put("onmouseout", "do something");
        htmlOutputText.setValue("a");
        HtmlOutputTextRenderer renderer = createHtmlOutputTextRenderer();

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlOutputText);

        // ## Assert ##
        Diff diff = new Diff("<span" + " id=\"fooId\"" + " title=\"someTitle\""
                + " onmouseout=\"do something\">a</span>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    private HtmlOutputTextRenderer createHtmlOutputTextRenderer() {
        return (HtmlOutputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputTextRenderer();
    }

}
