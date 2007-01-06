/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlOutputFormat;

/**
 * @author manhole
 */
public class HtmlOutputFormatRendererTest extends RendererTest {

    private HtmlOutputFormatRenderer renderer;

    private MockHtmlOutputFormat htmlOutputFormat;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlOutputFormatRenderer();
        htmlOutputFormat = new MockHtmlOutputFormat();
        htmlOutputFormat.setRenderer(renderer);
    }

    public void testEncode_WithValue() throws Exception {
        htmlOutputFormat.setValue("abc");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("abc", getResponseText());
    }

    public void testEncode_NullValue() throws Exception {
        htmlOutputFormat.setValue(null);

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlOutputFormat.setId("a");
        htmlOutputFormat.setValue("b");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("<span id=\"a\">b</span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        htmlOutputFormat.setValue("b");
        htmlOutputFormat.getAttributes().put("aa", "AA");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("<span aa=\"AA\">b</span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        htmlOutputFormat.setValue("b");
        htmlOutputFormat.getAttributes().put("a.a", "AA");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("b", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        htmlOutputFormat.setRendered(false);
        htmlOutputFormat.setId("a");
        htmlOutputFormat.setValue("b");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("", getResponseText());
    }

    public void testEncode_WithParams() throws Exception {
        // ## Arrange ##
        htmlOutputFormat.setValue("1{0}2{1}3");

        UIParameter param1 = new UIParameter();
        param1.setValue("a");
        UIParameter param2 = new UIParameter();
        param2.setValue("b");
        htmlOutputFormat.getChildren().add(param1);
        htmlOutputFormat.getChildren().add(param2);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputFormat);

        // ## Assert ##
        assertEquals("1a2b3", getResponseText());
    }

    public void testEncode_WithJapaneseParam() throws Exception {
        // ## Arrange ##
        htmlOutputFormat.setValue("1{0}2");

        UIParameter param2 = new UIParameter();
        param2.setValue(new Character((char) 12354));
        htmlOutputFormat.getChildren().add(param2);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputFormat);

        // ## Assert ##
        assertEquals("1" + new Character((char) 12354) + "2", getResponseText());
    }

    public void testEncode_EscapeTrue() throws Exception {
        htmlOutputFormat.setEscape(true);
        htmlOutputFormat.setValue("<a>");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("&lt;a&gt;", getResponseText());
    }

    public void testEncode_EscapeFalse() throws Exception {
        htmlOutputFormat.setEscape(false);
        htmlOutputFormat.setValue("<a>");

        encodeByRenderer(renderer, htmlOutputFormat);

        assertEquals("<a>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlOutputFormat.setId("a");
        htmlOutputFormat.setValue("b");
        htmlOutputFormat.setStyle("c");
        htmlOutputFormat.setStyleClass("d");
        htmlOutputFormat.setTitle("e");

        encodeByRenderer(renderer, htmlOutputFormat);

        Diff diff = new Diff("<span" + " id=\"a\"" + " style=\"c\""
                + " class=\"d\"" + " title=\"e\"" + ">b</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetLocaleFromUIViewRoot() throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        context.setViewRoot(new UIViewRoot());
        context.getViewRoot().setLocale(Locale.CANADA);

        // ## Act & Assert ##
        assertEquals(Locale.CANADA, renderer.getLocale(context));

        context.getViewRoot().setLocale(Locale.CHINESE);
        assertEquals(Locale.CHINESE, renderer.getLocale(context));
    }

    public void testGetLocaleFromDefault() throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        context.setViewRoot(new UIViewRoot());
        context.getViewRoot().setLocale(null);
        final Locale defaultLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.GERMANY);

            // ## Act & Assert ##
            assertEquals(Locale.GERMANY, renderer.getLocale(context));

            context.getViewRoot().setLocale(Locale.CHINESE);
            assertEquals(Locale.CHINESE, renderer.getLocale(context));
        } finally {
            Locale.setDefault(defaultLocale);
        }
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    protected MockFacesContext getFacesContext() {
        MockFacesContext context = super.getFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setLocale(Locale.getDefault());
        context.setViewRoot(viewRoot);
        return context;
    }

    private HtmlOutputFormatRenderer createHtmlOutputFormatRenderer() {
        return (HtmlOutputFormatRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlOutputFormatRenderer renderer = new HtmlOutputFormatRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
