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
package org.seasar.teeda.extension.render.html;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.mock.MockViewHandler;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.extension.component.html.THtmlOutputText;

/**
 * @author shot
 * @author yone
 */
public class THtmlOutputTextRendererTest extends RendererTest {

    private THtmlOutputTextRenderer renderer;

    private MockTHtmlOutputText htmlOutputText;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlOutputTextRenderer();
        htmlOutputText = new MockTHtmlOutputText();
        htmlOutputText.setRenderer(renderer);
    }

    public void testEncode_WithValue() throws Exception {
        htmlOutputText.setValue("abc");

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("abc", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlOutputText.setRendered(false);
        htmlOutputText.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NullValue() throws Exception {
        htmlOutputText.setValue(null);

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("", getResponseText());
    }

    public void testEncode_EscapeTrue() throws Exception {
        assertTrue("default is true", htmlOutputText.isEscape());
        htmlOutputText.setValue("<a>");

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("&lt;a&gt;", getResponseText());
    }

    public void testEncode_EscapeFalse() throws Exception {
        htmlOutputText.setEscape(false);
        htmlOutputText.setValue("<a>");

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("<a>", getResponseText());
    }

    public void testEncode_WithStyle() throws Exception {
        htmlOutputText.setStyle("some style");
        htmlOutputText.setValue("a");
        htmlOutputText.setEscape(false);

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("<span style=\"some style\">a</span>", getResponseText());
    }

    public void testEncode_WithStyleClass() throws Exception {
        htmlOutputText.setStyleClass("some styleClass");
        htmlOutputText.setValue("a");

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("styleClass -> class",
                "<span class=\"some styleClass\">a</span>", getResponseText());
    }

    // TODO
    public void todo_testEncode_CommonAttributtes() throws Exception {
        htmlOutputText.getAttributes().put("onmouseout", "do something");
        htmlOutputText.getAttributes().put("title", "someTitle");
        htmlOutputText.setValue("a");

        encodeByRenderer(renderer, htmlOutputText);

        System.out.println(getResponseText());
        Diff diff = new Diff(
                "<span title=\"someTitle\" onmouseout=\"do something\">a</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        htmlOutputText.setId("someId");
        htmlOutputText.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("<span id=\"someId\">a</span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        htmlOutputText.getAttributes().put("b", "c");
        htmlOutputText.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("<span b=\"c\">a</span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        htmlOutputText.getAttributes().put("b.c", "c");
        htmlOutputText.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("a", getResponseText());
    }

    public void testEncode_NotWriteId() throws Exception {
        // ## Arrange ##
        htmlOutputText.setId(UIViewRoot.UNIQUE_ID_PREFIX + "someId");
        htmlOutputText.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("a", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // ## Arrange ##
        htmlOutputText.setId("fooId");
        htmlOutputText.setTitle("someTitle");
        htmlOutputText.setValue("a");
        htmlOutputText.setStyle("style");
        htmlOutputText.setStyleClass("styleClass");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        Diff diff = new Diff("<span" + " id=\"fooId\"" + " title=\"someTitle\""
                + " style=\"style\" class=\"styleClass\">a</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_NotRenderChild() throws Exception {
        // ## Arrange ##
        htmlOutputText.setValue("abc");

        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setRenderer(renderer);
        child.setValue("d");
        htmlOutputText.getChildren().add(child);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("abc", getResponseText());
    }

    public void testEncode_Converter() throws Exception {
        // ## Arrange ##
        Converter converter = new MockConverter() {
            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException {
                return value + "ddd";
            }
        };
        htmlOutputText.setValue("abc");
        htmlOutputText.setConverter(converter);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("abcddd", getResponseText());
    }

    public void testGetConvertedValue() throws Exception {
        Converter converter = new MockConverter() {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return value + ".testGetConvertedValue";
            }
        };
        htmlOutputText.setConverter(converter);
        Object convertedValue = renderer.getConvertedValue(getFacesContext(),
                htmlOutputText, "bbb");

        assertEquals("bbb.testGetConvertedValue", convertedValue);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testValueBinding() throws Exception {
        MockFacesContext context = getFacesContext();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBinding vb = new ValueBindingImpl(context.getApplication(),
                "#{a}", parser);
        htmlOutputText.setValueBinding("value", vb);

        context.getExternalContext().getRequestMap().put("a", "123");

        encodeByRenderer(renderer, htmlOutputText);

        assertEquals("123", getResponseText());
    }

    public void testEncode_propertiesNameAndKeyExist() throws Exception {
        htmlOutputText.setId("aaaLabel");
        htmlOutputText.setKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        htmlOutputText.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals("<span id=\"aaaLabel\">CCC</span>", getResponseText());
    }

    public void testEncode_propertiesNameAndDefaultKeyExist() throws Exception {
        htmlOutputText.setId("aaaLabel");
        htmlOutputText.setKey("no_such_key");
        htmlOutputText.setDefaultKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        htmlOutputText.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals("<span id=\"aaaLabel\">CCC</span>", getResponseText());
    }

    public void testEncode_keyNotFoundAtLocaleJapanese() throws Exception {
        htmlOutputText.setId("aaaLabel");
        htmlOutputText.setKey("eee");
        String packageName = ClassUtil.getPackageName(this.getClass());
        htmlOutputText.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals("<span id=\"aaaLabel\">EEE</span>", getResponseText());
    }

    public void testEncode_keyDuplicateWithMultipleProperties()
            throws Exception {
        HotdeployUtil.setHotdeploy(true);
        htmlOutputText.setId("aaaLabel");
        htmlOutputText.setKey("fff");
        String packageName = ClassUtil.getPackageName(this.getClass());
        htmlOutputText.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals("<span id=\"aaaLabel\">FFF</span>", getResponseText());

        handler.setLocale(Locale.ENGLISH);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals(
                "<span id=\"aaaLabel\">FFF</span><span id=\"aaaLabel\">F_DEFAULT</span>",
                getResponseText());
        HotdeployUtil.clearHotdeploy();
    }

    public void testEncode_defaultPropertiesNameExist() throws Exception {
        htmlOutputText.setId("aaaLabel");
        htmlOutputText.setDefaultKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        htmlOutputText.setDefaultPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, htmlOutputText);
        assertEquals("<span id=\"aaaLabel\">CCC</span>", getResponseText());
    }

    public void testInvisibleFalse() throws Exception {
        assertFalse("default is false", htmlOutputText.isInvisible());
        // ## Arrange ##
        htmlOutputText.setId("fooId");
        htmlOutputText.setValue("aaa");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        Diff diff = new Diff("<span" + " id=\"fooId\">aaa</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }
    
    public void testInvisibleTrue() throws Exception {
        assertFalse("default is false", htmlOutputText.isInvisible());
        // ## Arrange ##
        htmlOutputText.setId("fooId");
        htmlOutputText.setValue("aaa");
        htmlOutputText.setInvisible(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("aaa", getResponseText());
    }

    public void testOmittagFalse() throws Exception {
        assertFalse("default is false", htmlOutputText.isOmittag());
        // ## Arrange ##
        htmlOutputText.setId("fooId");
        htmlOutputText.setValue("aaa");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        Diff diff = new Diff("<span" + " id=\"fooId\">aaa</span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }
    
    public void testOmittagTrue() throws Exception {
        assertFalse("default is false", htmlOutputText.isOmittag());
        // ## Arrange ##
        htmlOutputText.setId("fooId");
        htmlOutputText.setValue("aaa");
        htmlOutputText.setOmittag(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputText);

        // ## Assert ##
        assertEquals("aaa", getResponseText());
    }

    private THtmlOutputTextRenderer createTHtmlOutputTextRenderer() {
        return (THtmlOutputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlOutputTextRenderer renderer = new THtmlOutputTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public static class MockTHtmlOutputText extends THtmlOutputText {

        private Renderer renderer;

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer != null) {
                return renderer;
            }
            return super.getRenderer(context);
        }

    }
}
