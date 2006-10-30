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
package org.seasar.teeda.extension.render.html;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.render.html.HtmlInputTextRendererTest;
import org.seasar.teeda.extension.component.html.THtmlInputText;

/**
 * @author shot
 */
public class THtmlInputTextRendererTest extends HtmlInputTextRendererTest {

    private THtmlInputTextRenderer renderer;

    private MockTHtmlInputText htmlInputText;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlInputTextRenderer();
        htmlInputText = new MockTHtmlInputText();
        htmlInputText.setRenderer(renderer);
    }

    public void testErrorStyleClass() throws Exception {
        htmlInputText.setClientId("hoge");
        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("hoge", new FacesMessage("sssss"));
        htmlInputText.setErrorStyleClass("foo");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"hoge\" value=\"\" class=\"foo\" />",
                getResponseText());

    }

    private THtmlInputTextRenderer createTHtmlInputTextRenderer() {
        return (THtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputTextRenderer renderer = new THtmlInputTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        return renderer;
    }

    public static class MockTHtmlInputText extends THtmlInputText {

        private Renderer renderer;

        private String clientId;

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer != null) {
                return renderer;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId != null) {
                return clientId;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

    }
}
