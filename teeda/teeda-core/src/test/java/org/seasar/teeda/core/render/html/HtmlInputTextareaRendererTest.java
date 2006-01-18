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

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlInputTextareaRendererTest extends RendererTest {

    public void testEncodeEnd_WithNoValue() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeEnd(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals("<textarea id=\"_id0\" name=\"_id0\"></textarea>",
                getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
        htmlInputTextarea.setClientId("key1");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals(null, htmlInputTextarea.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        HtmlInputTextareaRenderer renderer = createHtmlInputTextareaRenderer();
        MockHtmlInputTextarea htmlInputText = new MockHtmlInputTextarea();
        htmlInputText.setRenderer(renderer);
        htmlInputText.setClientId("key1");

        MockFacesContext context = getFacesContext();
        getExternalContext().getRequestParameterMap().put("key1", "aabb");

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals("aabb", htmlInputText.getSubmittedValue());
    }

    private HtmlInputTextareaRenderer createHtmlInputTextareaRenderer() {
        return (HtmlInputTextareaRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlInputTextareaRenderer();
    }

    private static class MockHtmlInputTextarea extends HtmlInputTextarea {

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
