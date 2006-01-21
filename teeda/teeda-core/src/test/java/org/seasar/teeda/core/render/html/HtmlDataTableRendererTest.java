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

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class HtmlDataTableRendererTest extends RendererTest {

    private HtmlDataTableRenderer renderer_;

    private MockHtmlDataTable htmlDataTable_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlDataTableRenderer();
        htmlDataTable_ = new MockHtmlDataTable();
        htmlDataTable_.setRenderer(renderer_);
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBeginToChildrenToEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);
        renderer_.encodeChildren(context, htmlDataTable_);
        renderer_.encodeEnd(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setId("aa");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table id=\"aa\"", getResponseText());
    }

    public void testEncodeBeginToChildrenToEnd() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);
        renderer_.encodeChildren(context, htmlDataTable_);
        renderer_.encodeEnd(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table></table>", getResponseText());
    }

    public void testEncodeBegin_Facet() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setHeader(new MockUIComponentBase());
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead", getResponseText());
    }
    
    // TODO test

    public void testGetRendersChildren() throws Exception {
        HtmlDataTableRenderer renderer = new HtmlDataTableRenderer();
        assertEquals(true, renderer.getRendersChildren());
    }

    private HtmlDataTableRenderer createHtmlDataTableRenderer() {
        return (HtmlDataTableRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlDataTableRenderer();
    }

    private static class MockHtmlDataTable extends HtmlDataTable {
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
