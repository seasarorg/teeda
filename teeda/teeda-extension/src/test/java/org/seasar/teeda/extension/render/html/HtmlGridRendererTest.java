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

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.AbstractHtmlRenderer;

/**
 * @author manhole
 */
public class HtmlGridRendererTest extends RendererTest {

    // TODO making
    private HtmlGridRenderer renderer_;

    private MockHtmlGrid htmlGrid_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = (HtmlGridRenderer) createRenderer();
        htmlGrid_ = new MockHtmlGrid();
        htmlGrid_.setRenderer(renderer_);
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer_, htmlGrid_);

        // ## Assert ##
        assertEquals("<table></table>", getResponseText());
    }

    public void testEncode_TBody() throws Exception {
        // ## Arrange ##
        HtmlTBody htmlTBody = new HtmlTBody();
        htmlGrid_.getChildren().add(htmlTBody);

        // ## Act ##
        encodeByRenderer(renderer_, htmlGrid_);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    protected Renderer createRenderer() {
        HtmlGridRenderer renderer = new HtmlGridRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class HtmlGridRenderer extends AbstractHtmlRenderer {

        public void encodeBegin(FacesContext context, UIComponent component)
                throws IOException {
            assertNotNull(context, component);
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement(JsfConstants.TABLE_ELEM, component);

            for (Iterator it = getRenderedChildrenIterator(component); it
                    .hasNext();) {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlTBody) {
                    encodeTBody(context, (HtmlTBody) child, writer);
                }
            }

            writer.endElement(JsfConstants.TABLE_ELEM);

        }

        private void encodeTBody(FacesContext context, HtmlTBody tbody,
                ResponseWriter writer) throws IOException {
            // TODO Auto-generated method stub
            writer.startElement(JsfConstants.TBODY_ELEM, tbody);

            writer.endElement(JsfConstants.TBODY_ELEM);
        }

    }

    private static class HtmlGrid extends UIComponentBase {

        public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Grid";

        public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGrid";

        private static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Grid";

        private Integer border;

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    private static class HtmlTBody extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public class MockHtmlGrid extends HtmlGrid {

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
