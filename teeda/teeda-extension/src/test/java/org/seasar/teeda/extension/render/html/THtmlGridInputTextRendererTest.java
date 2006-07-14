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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.render.html.AbstractHtmlRenderer;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.component.html.THtmlGridInputText;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class THtmlGridInputTextRendererTest extends RendererTest {

    // TODO making

    private THtmlGridInputTextRenderer renderer;

    private MockHtmlGridInputText gridInputText;

    private HtmlOutputTextRenderer outputTextRenderer = new HtmlOutputTextRenderer();

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlGridInputTextRenderer) createRenderer();
        outputTextRenderer = new HtmlOutputTextRenderer();
        outputTextRenderer.setRenderAttributes(getRenderAttributes());
        gridInputText = new MockHtmlGridInputText();
        gridInputText.setRenderer(renderer);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        gridInputText.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        final String expected = "<span onclick=\"editOn(this);\"></span>"
                + "<input type=\"text\" name=\"_id0\" value=\"\" onblur=\"editOff(this);\" class=\"teeda_editable\" style=\"display:none;\" />";
        System.out.println(getResponseText());
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        gridInputText.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        final String expected = "<span onclick=\"editOn(this);\">abc</span>"
                + "<input type=\"text\" name=\"_id0\" value=\"abc\" onblur=\"editOff(this);\" class=\"teeda_editable\" style=\"display:none;\" />";
        System.out.println(getResponseText());
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithId() throws Exception {
        // ## Arrange ##
        gridInputText.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(gridInputText);

        // ## Act ##
        encodeByRenderer(renderer, gridInputText);

        // ## Assert ##
        final String expected = "<span onclick=\"editOn(this);\"></span>"
                + "<input type=\"text\" id=\"a\" name=\"b:a\" value=\"\" onblur=\"editOff(this);\" class=\"teeda_editable\" style=\"display:none;\" />";
        System.out.println(getResponseText());
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        return super.diff("<dummy>" + expected + "</dummy>", "<dummy>" + actual
                + "</dummy>");
    }

    protected Renderer createRenderer() {
        THtmlGridInputTextRenderer renderer = new THtmlGridInputTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        return renderer;
    }

    public class MockHtmlGridInputText extends THtmlGridInputText {

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

    private boolean addChild(UIComponent parent, UIComponent child) {
        return parent.getChildren().add(child);
    }

    public static class THtmlGridInputTextRenderer extends AbstractHtmlRenderer {
        public void encodeEnd(FacesContext context, UIComponent component)
                throws IOException {
            assertNotNull(context, component);
            if (!component.isRendered()) {
                return;
            }
            encodeHtmlGridInputTextEnd(context, (THtmlGridInputText) component);
        }

        protected void encodeHtmlGridInputTextEnd(FacesContext context,
                THtmlGridInputText gridInputText) throws IOException {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement(JsfConstants.SPAN_ELEM, gridInputText);
            RendererUtil.renderAttribute(writer, JsfConstants.ONCLICK_ATTR,
                    "editOn(this);");
            final String value = ValueHolderUtil.getValueForRender(context,
                    gridInputText);
            writer.writeText(value, null);
            // TODO
            //            if (gridInputText.isEscape()) {
            //                writer.writeText(value, null);
            //            } else {
            //                writer.write(value);
            //            }
            writer.endElement(JsfConstants.SPAN_ELEM);

            writer.startElement(JsfConstants.INPUT_ELEM, gridInputText);
            RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                    JsfConstants.TEXT_VALUE);
            RendererUtil.renderIdAttributeIfNecessary(writer, gridInputText,
                    getIdForRender(context, gridInputText));
            RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                    gridInputText.getClientId(context));
            RendererUtil.renderAttribute(writer, JsfConstants.ONBLUR_ATTR,
                    "editOff(this);");
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    "teeda_editable");
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                    "display:none;");
            RendererUtil
                    .renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
            renderAttributes(gridInputText, writer);
            writer.endElement(JsfConstants.INPUT_ELEM);
        }
    }

}
