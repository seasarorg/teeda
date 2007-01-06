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

    private HtmlInputHiddenRenderer renderer;

    private MockHtmlInputHidden htmlInputHidden;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputHiddenRenderer();
        htmlInputHidden = new MockHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
    }

    public void testEncode_WithNoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        assertEquals("<input type=\"hidden\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        assertEquals("<input type=\"hidden\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputHidden.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputHidden);

        encodeByRenderer(renderer, htmlInputHidden);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        htmlInputHidden.setId("a");
        htmlInputHidden.getAttributes().put("unknown", "foo");

        encodeByRenderer(renderer, htmlInputHidden);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"a\" value=\"\" unknown=\"foo\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputHidden.setId("A");
        htmlInputHidden.setValue("B");

        encodeByRenderer(renderer, htmlInputHidden);

        Diff diff = new Diff(
                "<input type=\"hidden\" id=\"A\" name=\"A\" value=\"B\""
                        + " />", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals(null, htmlInputHidden.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key:aa",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals("12345", htmlInputHidden.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlInputHiddenRenderer createHtmlInputHiddenRenderer() {
        return (HtmlInputHiddenRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputHiddenRenderer renderer = new HtmlInputHiddenRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
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
