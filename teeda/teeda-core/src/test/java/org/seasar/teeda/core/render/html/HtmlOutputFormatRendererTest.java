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

import java.util.Locale;

import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputFormat;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.util.TeedaTestUtil;

/**
 * @author manhole
 */
public class HtmlOutputFormatRendererTest extends RendererTest {

    protected void setUpAfterContainerInit() throws Throwable {
        super.setUpAfterContainerInit();
        TeedaTestUtil.setupMockUIViewRoot(getFacesContext());
    }

    public void testEncodeEnd() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setValue("abc");
        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        assertEquals("abc", getResponseText());
    }

    public void testEncodeEnd_NullValue() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setValue(null);
        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        assertEquals("", getResponseText());
    }

    public void testEncodeBeginAndEnd() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setId("a");
        htmlOutputFormat.setValue("b");
        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputFormat);
        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        assertEquals("<span id=\"a\">b</span>", getResponseText());
    }

    public void testEncodeEnd_WithParams() throws Exception {
        // ## Arrange ##
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setValue("1{0}2{1}3");

        UIParameter param1 = new UIParameter();
        param1.setValue("a");
        UIParameter param2 = new UIParameter();
        param2.setValue("b");
        htmlOutputFormat.getChildren().add(param1);
        htmlOutputFormat.getChildren().add(param2);

        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        // ## Assert ##
        assertEquals("1a2b3", getResponseText());
    }

    public void testEncodeEnd_WithJapaneseParam() throws Exception {
        // ## Arrange ##
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setValue("1{0}2");

        UIParameter param2 = new UIParameter();
        param2.setValue(new Character((char) 12354));
        htmlOutputFormat.getChildren().add(param2);

        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();
        // ## Act ##
        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        // ## Assert ##
        assertEquals("1" + new Character((char) 12354) + "2", getResponseText());
    }

    public void testEncodeEnd_EscapeTrue() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setEscape(true);
        htmlOutputFormat.setValue("<a>");
        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        assertEquals("&lt;a&gt;", getResponseText());
    }

    public void testEncodeEnd_EscapeFalse() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setEscape(false);
        htmlOutputFormat.setValue("<a>");
        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        assertEquals("<a>", getResponseText());
    }

    public void testEncodeBeginToEnd_WithAllAttributes() throws Exception {
        HtmlOutputFormat htmlOutputFormat = new HtmlOutputFormat();
        htmlOutputFormat.setId("a");
        htmlOutputFormat.setValue("b");
        htmlOutputFormat.setStyle("c");
        htmlOutputFormat.setStyleClass("d");
        htmlOutputFormat.setTitle("e");

        HtmlOutputFormatRenderer renderer = createHtmlOutputFormatRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputFormat);
        renderer.encodeEnd(getFacesContext(), htmlOutputFormat);

        Diff diff = new Diff("<span" + " id=\"a\"" + " style=\"c\""
                + " class=\"d\"" + " title=\"e\"" + ">b</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetLocaleFromUIViewRoot() throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        context.getViewRoot().setLocale(Locale.CANADA);

        HtmlOutputFormatRenderer renderer = new HtmlOutputFormatRenderer();

        // ## Act & Assert ##
        assertEquals(Locale.CANADA, renderer.getLocale(context));

        context.getViewRoot().setLocale(Locale.CHINESE);
        assertEquals(Locale.CHINESE, renderer.getLocale(context));
    }

    private HtmlOutputFormatRenderer createHtmlOutputFormatRenderer() {
        return (HtmlOutputFormatRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputFormatRenderer();
    }

}
