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
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlInputHiddenRendererTest extends RendererTest {

    public void testEncodeEnd_WithNoValue() throws Exception {
        // ## Arrange ##
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputHidden);

        // ## Assert ##
        assertEquals(
                "<input type=\"hidden\" id=\"_id0\" name=\"_id0\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithValue() throws Exception {
        // ## Arrange ##
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
        htmlInputHidden.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputHidden);

        // ## Assert ##
        assertEquals(
                "<input type=\"hidden\" id=\"_id0\" name=\"_id0\" value=\"abc\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithId() throws Exception {
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
        htmlInputHidden.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputHidden);

        renderer.encodeEnd(getFacesContext(), htmlInputHidden);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"b:a\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithAllAttributes() throws Exception {
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);

        htmlInputHidden.setId("A");
        htmlInputHidden.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer.encodeBegin(context, htmlInputHidden);
        renderer.encodeEnd(context, htmlInputHidden);

        Diff diff = new Diff(
                "<input type=\"hidden\" id=\"A\" name=\"A\" value=\"B\"" + "/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
        htmlInputHidden.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals(null, htmlInputHidden.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        HtmlInputHiddenRenderer renderer = createHtmlInputHiddenRenderer();
        MockHtmlInputHidden htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
        htmlInputHidden.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put("key:aa", "12345");

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals("12345", htmlInputHidden.getSubmittedValue());
    }
    
    public void testGetRendersChildren() throws Exception {
        HtmlInputHiddenRenderer renderer = new HtmlInputHiddenRenderer();
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlInputHiddenRenderer createHtmlInputHiddenRenderer() {
        return (HtmlInputHiddenRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlInputHiddenRenderer();
    }

    private static class MockHtmlInputHidden extends HtmlInputHidden {

        private Renderer renderer_;

        private String clientId_;

        public void setRenderer(Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId_ != null) {
                return clientId_;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            clientId_ = clientId;
        }
    }

}
