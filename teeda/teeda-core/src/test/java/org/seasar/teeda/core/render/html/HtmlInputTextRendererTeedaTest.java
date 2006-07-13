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
import org.seasar.teeda.core.mock.MockUIViewRoot;

/**
 * @author manhole
 */
public class HtmlInputTextRendererTeedaTest extends AbstractRendererTeedaTest {

    private S2Container container;

    private HtmlInputTextRenderer renderer;

    private MockHtmlInputText htmlInputText;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputTextRenderer();
        htmlInputText = new MockHtmlInputText();
        htmlInputText.setRenderer(renderer);
    }

    public void testEncode() throws Exception {
        // ## Arrange ##
        htmlInputText.setId("a");
        {
            Foo foo = new Foo();
            foo.setAaa("b");
            container.register(foo, "foo");
        }
        {
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{foo.aaa}", parser);
            htmlInputText.setValueBinding("value", vb);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeComponent(context, htmlInputText);

        // ## Assert ##
        assertEquals("<input type=\"text\" id=\"a\" name=\"a\" value=\"b\" />",
                getResponseText());
    }

    public void testDecode() throws Exception {
        // ## Arrange ##
        MockUIViewRoot mockUIViewRoot = new MockUIViewRoot();
        mockUIViewRoot.getChildren().add(htmlInputText);
        htmlInputText.setId("a");

        Foo foo = new Foo();
        container.register(foo, "foo");

        FacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("a", "12345");

        {
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{foo.aaa}", parser);
            htmlInputText.setValueBinding("value", vb);
        }

        // ## Act ##
        // ## Assert ##
        htmlInputText.decode(context);
        assertEquals("12345", htmlInputText.getSubmittedValue());
        htmlInputText.processValidators(context);
        htmlInputText.processUpdates(context);
        assertEquals(null, htmlInputText.getSubmittedValue());
        assertEquals("12345", foo.getAaa());
    }

    private HtmlInputTextRenderer createHtmlInputTextRenderer() {
        return (HtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputTextRenderer renderer = new HtmlInputTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        return renderer;
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
