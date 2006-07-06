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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import org.seasar.framework.container.S2Container;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.render.html.MockHtmlOutputText;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.TForEach;

/**
 * @author manhole
 */
public class TForEachRendererTeedaTest extends TeedaTestCase {

    private S2Container container;

    public void testEncode() throws Exception {
        // ## Arrange ##
        final HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();

        TForEachRenderer renderer = new TForEachRenderer();
        TForEach forEach = new TForEach();

        final String pageName = "fooPage";
        FooPage page = new FooPage();
        container.register(page, pageName);
        forEach.setPageName(pageName);
        forEach.setItemsName("fooItems");
        // items
        {
            Foo[] items = new Foo[3];
            {
                Foo item = new Foo();
                item.setAaa("1");
                items[0] = item;
            }
            {
                Foo item = new Foo();
                item.setAaa("2");
                items[1] = item;
            }
            {
                Foo item = new Foo();
                item.setAaa("3");
                items[2] = item;
            }
            page.setFooItems(items);
        }

        {
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooPage.aaa}", parser);
            text.setValueBinding("value", vb);

            forEach.getChildren().add(text);
        }

        // ## Act ##
        encodeByRenderer(renderer, forEach);

        // ## Assert ##
        assertEquals("123", getResponseText());
    }

    protected void encodeByRenderer(Renderer renderer, UIComponent component)
            throws IOException {
        encodeByRenderer(renderer, getFacesContext(), component);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        if (renderer.getRendersChildren()) {
            renderer.encodeChildren(context, component);
        }
        renderer.encodeEnd(context, component);
    }

    public static class FooPage {

        private String aaa;

        private Foo[] fooItems;

        public Foo[] getFooItems() {
            return fooItems;
        }

        public void setFooItems(Foo[] hogeItems) {
            this.fooItems = hogeItems;
        }

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }
    }

    public static class Foo {
        private String aaa;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }
    }

}
