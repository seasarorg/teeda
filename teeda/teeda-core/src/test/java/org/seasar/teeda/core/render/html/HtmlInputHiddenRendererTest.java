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

    private HtmlInputHiddenRenderer renderer_;

    private MockHtmlInputHidden htmlInputHidden_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlInputHiddenRenderer();
        htmlInputHidden_ = new MockHtmlInputHidden();
        htmlInputHidden_.setRenderer(renderer_);
    }

    public void testEncodeEnd_WithNoValue() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeEnd(context, htmlInputHidden_);

        // ## Assert ##
        assertEquals(
                "<input type=\"hidden\" id=\"_id0\" name=\"_id0\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputHidden_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlInputHidden_);
        renderer_.encodeEnd(context, htmlInputHidden_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeEnd_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputHidden_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeEnd(context, htmlInputHidden_);

        // ## Assert ##
        assertEquals(
                "<input type=\"hidden\" id=\"_id0\" name=\"_id0\" value=\"abc\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithId() throws Exception {
        htmlInputHidden_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputHidden_);

        renderer_.encodeEnd(getFacesContext(), htmlInputHidden_);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"b:a\" value=\"\"/>",
                getResponseText());
    }

    public void testEncodeEnd_WithAllAttributes() throws Exception {
        htmlInputHidden_.setId("A");
        htmlInputHidden_.setValue("B");

        MockFacesContext context = getFacesContext();
        renderer_.encodeBegin(context, htmlInputHidden_);
        renderer_.encodeEnd(context, htmlInputHidden_);

        Diff diff = new Diff(
                "<input type=\"hidden\" id=\"A\" name=\"A\" value=\"B\"" + "/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputHidden_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlInputHidden_);

        // ## Assert ##
        assertEquals(null, htmlInputHidden_.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputHidden_.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put("key:aa", "12345");

        // ## Act ##
        renderer_.decode(context, htmlInputHidden_);

        // ## Assert ##
        assertEquals("12345", htmlInputHidden_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
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
