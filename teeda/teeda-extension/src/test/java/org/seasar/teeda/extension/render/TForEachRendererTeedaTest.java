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
package org.seasar.teeda.extension.render;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.AbstractRendererTeedaTest;
import javax.faces.render.Renderer;

import org.seasar.framework.container.S2Container;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockHtmlInputText;
import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;

/**
 * @author manhole
 */
public class TForEachRendererTeedaTest extends AbstractRendererTeedaTest {

    private S2Container container;

    private TForEachRenderer renderer;

    private MockTForEach forEach;

    private HtmlOutputTextRenderer outputTextRenderer;

    private HtmlInputTextRenderer inputTextRenderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTForEachRenderer();
        outputTextRenderer = new HtmlOutputTextRenderer();
        inputTextRenderer = new HtmlInputTextRenderer();
        forEach = new MockTForEach();
        forEach.setRenderer(renderer);
    }

    public void testEncode_Foo1() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        final String pageName = "fooPage";
        FooPage page = new FooPage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("barItems");

        // items
        {
            Bar[] items = new Bar[3];
            items[0] = new Bar("11");
            items[1] = new Bar("22");
            items[2] = new Bar("33");
            page.setBarItems(items);
        }

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(outputTextRenderer);
            text.setValueBinding("value",
                    createValueBinding("#{fooPage.bar.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals("112233", getResponseText());
    }

    public void testEncode_FooMap1() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        final String pageName = "fooPage";
        FooMapPage page = new FooMapPage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("barItems");

        // items
        {
            Map[] items = new Map[3];
            items[0] = new HashMap();
            items[1] = new HashMap();
            items[2] = new HashMap();
            items[0].put("aaa", "1");
            items[1].put("aaa", "2");
            items[2].put("aaa", "3");
            page.setBarItems(items);
        }

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(outputTextRenderer);
            text.setValueBinding("value",
                    createValueBinding("#{fooPage.bar.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals("123", getResponseText());
    }

    public void testEncode_Foo2() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        {
            final String pageName = "fooPage";
            FooPage page = new FooPage();
            container.register(page, pageName);
            forEach.setPageName(pageName);
            forEach.setItemsName("barItems");
            forEach.setId("a");

            // items
            Bar[] items = new Bar[3];
            items[0] = new Bar("111");
            items[1] = new Bar("222");
            items[2] = new Bar("333");
            page.setBarItems(items);
        }

        {
            MockHtmlInputText text = new MockHtmlInputText();
            text.setId("z");
            text.setRenderer(inputTextRenderer);
            text.setValueBinding("value",
                    createValueBinding("#{fooPage.bar.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" id=\"z\" name=\"a:0:z\" value=\"111\" />"
                        + "<input type=\"text\" id=\"z\" name=\"a:1:z\" value=\"222\" />"
                        + "<input type=\"text\" id=\"z\" name=\"a:2:z\" value=\"333\" />",
                getResponseText());
    }

    public void testEncode_Hoge1() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        final String pageName = "hogePage";
        final HogePage page = new HogePage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("fugaItems");

        // items
        {
            Fuga[] items = new Fuga[3];
            items[0] = new Fuga("a", "1");
            items[1] = new Fuga("b", "2");
            items[2] = new Fuga("c", "3");
            page.setFugaItems(items);
        }

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(outputTextRenderer);
            text
                    .setValueBinding("value",
                            createValueBinding("#{hogePage.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals("abc", getResponseText());
    }

    public void testEncode_HogeMap1() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        final String pageName = "hogePage";
        final HogeMapPage page = new HogeMapPage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("fugaItems");

        // items
        {
            Map[] items = new Map[3];
            items[0] = new HashMap();
            items[1] = new HashMap();
            items[2] = new HashMap();
            items[0].put("aaa", "a");
            items[0].put("bbb", "1");
            items[1].put("aaa", "b");
            items[1].put("bbb", "2");
            items[2].put("aaa", "c");
            items[2].put("bbb", "3");
            page.setFugaItems(items);
        }

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(outputTextRenderer);
            text
                    .setValueBinding("value",
                            createValueBinding("#{hogePage.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals("abc", getResponseText());
    }

    public void testEncode_StringArray() throws Exception {
        // ## Arrange ##
        final FacesContext context = getFacesContext();

        final String pageName = "fooPage";
        final StringArrayPage page = new StringArrayPage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("aaaItems");

        // items
        page.setAaaItems(new String[] { "3", "1", "2", "4" });

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(outputTextRenderer);
            text.setValueBinding("value", createValueBinding("#{fooPage.aaa}"));
            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeComponent(context, forEach);

        // ## Assert ##
        assertEquals("3124", getResponseText());
    }

    private ValueBinding createValueBinding(final String el) {
        final FacesContext context = getFacesContext();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBinding vb = new ValueBindingImpl(context.getApplication(), el,
                parser);
        return vb;
    }

    public void testDecode_Foo1() throws Exception {
        // ## Arrange ##
        final MockUIViewRoot root = new MockUIViewRoot();
        root.getChildren().add(forEach);

        final FacesContext context = getFacesContext();

        final FooPage page = new FooPage();
        {
            final String pageName = "fooPage";
            container.register(page, pageName);
            forEach.setPageName(pageName);
            forEach.setItemsName("barItems");
            forEach.setId("a");
        }

        {
            MockHtmlInputText text = new MockHtmlInputText();
            text.setId("z");
            text.setRenderer(inputTextRenderer);
            text.setValueBinding("value",
                    createValueBinding("#{fooPage.bar.aaa}"));
            forEach.getChildren().add(text);
        }

        // input parameters
        {
            final Map req = context.getExternalContext()
                    .getRequestParameterMap();
            req.put("a:0:z", "A");
            req.put("a:1:z", "B");
            req.put("a:2:z", "C");
        }

        // ## Act ##
        forEach.decode(context);
        forEach.processValidators(context);
        forEach.processUpdates(context);

        // ## Assert ##
        final Bar[] items = page.getBarItems();
        assertEquals(3, items.length);
        assertEquals("A", items[0].getAaa());
        assertEquals("B", items[1].getAaa());
        assertEquals("C", items[2].getAaa());
    }

    public void testDecode_Foo2() throws Exception {
        // ## Arrange ##
        final MockUIViewRoot root = new MockUIViewRoot();
        final UIForm namingContainer = new UIForm();
        namingContainer.setId("form");
        root.getChildren().add(namingContainer);
        namingContainer.getChildren().add(forEach);

        final FacesContext context = getFacesContext();

        final FooPage page = new FooPage();
        {
            final String pageName = "fooPage";
            getVariableResolver().putValue(pageName, page);
            forEach.setPageName(pageName);
            forEach.setItemsName("barItems");
            forEach.setId("forEach");
        }

        {
            MockHtmlInputText text = new MockHtmlInputText();
            text.setId("zz");
            text.setRenderer(inputTextRenderer);
            text.setValueBinding("value",
                    createValueBinding("#{fooPage.bar.aaa}"));
            forEach.getChildren().add(text);
        }

        // input parameters
        {
            final Map req = context.getExternalContext()
                    .getRequestParameterMap();
            req.put("form:forEach:0:zz", "A1");
            req.put("form:forEach:1:zz", "B1");
            req.put("form:forEach:2:zz", "C1");
            req.put("form:forEach:3:zz", "D1");
        }

        // ## Act ##
        forEach.decode(context);
        forEach.processValidators(context);
        forEach.processUpdates(context);

        // ## Assert ##
        final Bar[] items = page.getBarItems();
        assertEquals(4, items.length);
        assertEquals("A1", items[0].getAaa());
        assertEquals("B1", items[1].getAaa());
        assertEquals("C1", items[2].getAaa());
        assertEquals("D1", items[3].getAaa());
    }

    public void testDecode_Hoge1() throws Exception {
        // ## Arrange ##
        final MockUIViewRoot root = new MockUIViewRoot();
        root.getChildren().add(forEach);

        final FacesContext context = getFacesContext();

        final HogePage page = new HogePage();
        {
            final String pageName = "hogePage";
            container.register(page, pageName);
            forEach.setPageName(pageName);
            forEach.setItemsName("fugaItems");
            forEach.setId("a");
        }

        {
            MockHtmlInputText text = new MockHtmlInputText();
            text.setId("y");
            text.setRenderer(inputTextRenderer);
            text
                    .setValueBinding("value",
                            createValueBinding("#{hogePage.aaa}"));
            forEach.getChildren().add(text);
        }
        {
            MockHtmlInputText text = new MockHtmlInputText();
            text.setId("z");
            text.setRenderer(inputTextRenderer);
            text
                    .setValueBinding("value",
                            createValueBinding("#{hogePage.bbb}"));
            forEach.getChildren().add(text);
        }

        // input parameters
        {
            final Map req = context.getExternalContext()
                    .getRequestParameterMap();
            req.put("a:0:y", "A");
            req.put("a:1:y", "B");
            req.put("a:2:y", "C");
            req.put("a:0:z", "1");
            req.put("a:1:z", "2");
            req.put("a:2:z", "3");
        }

        // ## Act ##
        forEach.decode(context);
        forEach.processValidators(context);
        forEach.processUpdates(context);

        // ## Assert ##
        final Fuga[] items = page.getFugaItems();
        assertEquals(3, items.length);
        assertEquals("A", items[0].getAaa());
        assertEquals("B", items[1].getAaa());
        assertEquals("C", items[2].getAaa());
        assertEquals("1", items[0].getBbb());
        assertEquals("2", items[1].getBbb());
        assertEquals("3", items[2].getBbb());
    }

    private TForEachRenderer createTForEachRenderer() {
        return (TForEachRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        TForEachRenderer renderer = new TForEachRenderer();
        //renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    /*
     * Dtoが持つフィールドをPageは持たない
     */
    public static class FooPage {

        private Bar bar;

        private Bar[] barItems;

        public Bar[] getBarItems() {
            return barItems;
        }

        public void setBarItems(Bar[] hogeItems) {
            this.barItems = hogeItems;
        }

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }

    }

    public static class FooMapPage {

        private Map bar;

        private Map[] barItems;

        public Map[] getBarItems() {
            return barItems;
        }

        public void setBarItems(Map[] hogeItems) {
            this.barItems = hogeItems;
        }

        public Map getBar() {
            return bar;
        }

        public void setBar(Map bar) {
            this.bar = bar;
        }

    }

    public static class Bar {

        public Bar() {
        }

        public Bar(String aaa) {
            setAaa(aaa);
        }

        private String aaa;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String toString() {
            return "Bar{aaa=" + aaa + "}";
        }
    }

    /*
     * Dtoが持つフィールドをPageも持つ。
     */
    public static class HogePage {

        private Fuga[] fugaItems;

        public Fuga[] getFugaItems() {
            return fugaItems;
        }

        public void setFugaItems(Fuga[] hogeItems) {
            this.fugaItems = hogeItems;
        }

        private String aaa;

        private String bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

    public static class HogeMapPage {

        private Map[] fugaItems;

        public Map[] getFugaItems() {
            return fugaItems;
        }

        public void setFugaItems(Map[] hogeItems) {
            this.fugaItems = hogeItems;
        }

        private String aaa;

        private String bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

    public static class Fuga {

        public Fuga() {
        }

        public Fuga(String aaa, String bbb) {
            setAaa(aaa);
            setBbb(bbb);
        }

        private String aaa;

        private String bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }

        public String toString() {
            return "Fuga{aaa=" + aaa + ",bbb=" + bbb + "}";
        }

    }

    public static class StringArrayPage {
        private String aaa;

        private String[] aaaItems;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String[] getAaaItems() {
            return aaaItems;
        }

        public void setAaaItems(String[] aaaItems) {
            this.aaaItems = aaaItems;
        }
    }

}
