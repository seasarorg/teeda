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
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.render.html.AbstractHtmlRenderer;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.render.html.MockHtmlOutputText;
import org.seasar.teeda.core.unit.TestUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlGridRendererTest extends RendererTest {

    // TODO making

    private HtmlGridRenderer renderer;

    private MockHtmlGrid htmlGrid;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (HtmlGridRenderer) createRenderer();
        htmlGrid = new MockHtmlGrid();
        htmlGrid.setRenderer(renderer);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlGrid.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer, context, htmlGrid);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        assertEquals("<table></table>", getResponseText());
    }

    public void testEncode_HeaderNoValue() throws Exception {
        // ## Arrange ##
        HtmlGridHeader thead = new HtmlGridHeader();
        htmlGrid.getChildren().add(thead);

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String header = "<table><thead></thead></table>";
        assertEquals("<table><tr><td>" + header + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_Header() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();

        HtmlGridHeader thead = new HtmlGridHeader();
        htmlGrid.getChildren().add(thead);

        HtmlGridTr tr = new HtmlGridTr();
        addChild(thead, tr);
        {
            HtmlGridTh th = new HtmlGridTh();
            addChild(tr, th);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("th1");
            addChild(th, text);
        }
        {
            HtmlGridTh th = new HtmlGridTh();
            addChild(tr, th);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("th2");
            addChild(th, text);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String header = "<table><thead><tr>" + "<th>th1</th>"
                + "<th>th2</th>" + "</tr></thead></table>";
        assertEquals("<table><tr><td>" + header + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_BodyNoValue() throws Exception {
        // ## Arrange ##
        HtmlGridBody tbody = new HtmlGridBody();
        htmlGrid.getChildren().add(tbody);

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String body = "<table><tbody></tbody></table>";
        assertEquals("<table><tr><td>" + body + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_Body() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();

        HtmlGridBody tbody = new HtmlGridBody();
        addChild(htmlGrid, tbody);

        HtmlGridTr tr = new HtmlGridTr();
        addChild(tbody, tr);
        {
            HtmlGridTd td = new HtmlGridTd();
            addChild(tr, td);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("td1");
            td.getChildren().add(text);
            addChild(td, text);
        }
        {
            HtmlGridTd td = new HtmlGridTd();
            addChild(tr, td);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("td2");
            addChild(td, text);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String body = "<table><tbody>" + "<tr>" + "<td>td1</td>"
                + "<td>td2</td>" + "</tr>" + "</tbody></table>";
        assertEquals("<table><tr><td>" + body + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_Colgroup_Header_Body() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlGrid.setId("someGrid");

        // colgroup
        {
            HtmlGridColumnGroup columnGroup = new HtmlGridColumnGroup();
            addChild(htmlGrid, columnGroup);

            HtmlGridColumn col = new HtmlGridColumn();
            col.setSpan(new Integer(1));
            addChild(columnGroup, col);
        }

        // thead
        {
            HtmlGridHeader thead = new HtmlGridHeader();
            htmlGrid.getChildren().add(thead);

            HtmlGridTr tr = new HtmlGridTr();
            addChild(thead, tr);
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th1");
                addChild(th, text);
            }
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th2");
                addChild(th, text);
            }
        }

        // tbody
        {
            HtmlGridBody tbody = new HtmlGridBody();
            addChild(htmlGrid, tbody);

            HtmlGridTr tr = new HtmlGridTr();
            addChild(tbody, tr);
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("td1");
                td.getChildren().add(text);
                addChild(td, text);
            }
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("td2");
                addChild(td, text);
            }
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String leftHeader = "<table><thead>" + "<tr>" + "<th>th1</th>"
                + "</tr>" + "</thead></table>";
        final String rightHeader = "<table><thead>" + "<tr>" + "<th>th2</th>"
                + "</tr>" + "</thead></table>";
        final String leftBody = "<table><tbody>" + "<tr>" + "<td>td1</td>"
                + "</tr>" + "</tbody></table>";
        final String rightBody = "<table><tbody>" + "<tr>" + "<td>td2</td>"
                + "</tr>" + "</tbody></table>";
        System.out.println(getResponseText());
        assertEquals("<table id=\"someGrid\"><tr><td>" + leftHeader
                + "</td><td>" + rightHeader + "</td></tr><tr><td>" + leftBody
                + "</td><td>" + rightBody + "</td></tr></table>",
                getResponseText());
    }

    public void ignore_test1() throws Exception {
        // ## Arrange ##
        {
            HtmlGridColumn col = new HtmlGridColumn();
            col.setSpan(new Integer(2));
            HtmlGridColumnGroup columnGroup = new HtmlGridColumnGroup();
            columnGroup.getChildren().add(col);
            htmlGrid.getChildren().add(columnGroup);
        }

        // thead
        {
            HtmlGridHeader header = new HtmlGridHeader();
            htmlGrid.getChildren().add(header);
        }

        // tbody
        {
            HtmlGridBody body = new HtmlGridBody();
            htmlGrid.getChildren().add(body);
        }

        // ## Act ##

        // ## Assert ##
        final String readText = TestUtil
                .readText(getClass(), "1.html", "UTF-8");
        System.out.println(readText);
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
            if (!component.isRendered()) {
                return;
            }
            encodeHtmlGridBegin(context, (HtmlGrid) component);
        }

        protected void encodeHtmlGridBegin(final FacesContext context,
                final HtmlGrid htmlGrid) throws IOException {
            final ResponseWriter writer = context.getResponseWriter();
            writer.startElement(JsfConstants.TABLE_ELEM, htmlGrid);
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlGrid,
                    getIdForRender(context, htmlGrid));
            int leftFixCols = 0;
            for (Iterator it = getRenderedChildrenIterator(htmlGrid); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridColumnGroup) {
                    HtmlGridColumnGroup columnGroup = (HtmlGridColumnGroup) child;
                    for (Iterator itr = getRenderedChildrenIterator(columnGroup); itr
                            .hasNext();) {
                        final UIComponent element = (UIComponent) itr.next();
                        if (element instanceof HtmlGridColumn) {
                            final HtmlGridColumn col = (HtmlGridColumn) element;
                            final Integer span = col.getSpan();
                            if (span != null) {
                                leftFixCols = span.intValue();
                            }
                        }
                    }
                }
            }
            for (Iterator it = getRenderedChildrenIterator(htmlGrid); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridHeader) {
                    encodeGridHeader(context, (HtmlGridHeader) child, writer,
                            leftFixCols);
                    break;
                }
            }
            for (Iterator it = getRenderedChildrenIterator(htmlGrid); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridBody) {
                    encodeGridBody(context, (HtmlGridBody) child, writer,
                            leftFixCols);
                    break;
                }
            }
            writer.endElement(JsfConstants.TABLE_ELEM);
        }

        private void encodeGridHeader(final FacesContext context,
                final HtmlGridHeader header, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, header);
            writer.startElement(JsfConstants.TD_ELEM, header);

            // encodeLeftHeader
            if (0 < leftFixCols) {
                writer.startElement(JsfConstants.TABLE_ELEM, header);
                writer.startElement(JsfConstants.THEAD_ELEM, header);
                for (Iterator it = getRenderedChildrenIterator(header); it
                        .hasNext();) {
                    final UIComponent child = (UIComponent) it.next();
                    if (child instanceof HtmlGridTr) {
                        encodeGridLeftHeaderTr(context, (HtmlGridTr) child,
                                writer, leftFixCols);
                    }
                }

                writer.endElement(JsfConstants.THEAD_ELEM);
                writer.endElement(JsfConstants.TABLE_ELEM);
                writer.endElement(JsfConstants.TD_ELEM);

                writer.startElement(JsfConstants.TD_ELEM, header);
            }

            // encodeRightHeader
            writer.startElement(JsfConstants.TABLE_ELEM, header);
            writer.startElement(JsfConstants.THEAD_ELEM, header);
            for (Iterator it = getRenderedChildrenIterator(header); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTr) {
                    encodeGridRightHeaderTr(context, (HtmlGridTr) child,
                            writer, leftFixCols);
                }
            }
            writer.endElement(JsfConstants.THEAD_ELEM);
            writer.endElement(JsfConstants.TABLE_ELEM);
            writer.endElement(JsfConstants.TD_ELEM);
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridLeftHeaderTr(final FacesContext context,
                final HtmlGridTr tr, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, tr);
            int counter = 0;
            for (final Iterator it = getRenderedChildrenIterator(tr); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTh) {
                    counter++;
                    encodeGridHeaderTh(context, (HtmlGridTh) child, writer);
                } else if (child instanceof HtmlGridTd) {
                    counter++;
                    encodeGridHeaderTd(context, (HtmlGridTd) child, writer);
                }
                if (leftFixCols <= counter) {
                    break;
                }
            }
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridRightHeaderTr(final FacesContext context,
                final HtmlGridTr tr, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, tr);
            int counter = 0;
            for (final Iterator it = getRenderedChildrenIterator(tr); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTh) {
                    counter++;
                    if (counter <= leftFixCols) {
                        continue;
                    }
                    encodeGridHeaderTh(context, (HtmlGridTh) child, writer);
                } else if (child instanceof HtmlGridTd) {
                    counter++;
                    if (counter <= leftFixCols) {
                        continue;
                    }
                    encodeGridHeaderTd(context, (HtmlGridTd) child, writer);
                }
            }
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridHeaderTd(FacesContext context, HtmlGridTd td,
                ResponseWriter writer) throws IOException {
            writer.startElement(JsfConstants.TD_ELEM, td);
            encodeDescendantComponent(context, td);
            writer.endElement(JsfConstants.TD_ELEM);
        }

        private void encodeGridHeaderTh(FacesContext context, HtmlGridTh th,
                ResponseWriter writer) throws IOException {
            writer.startElement(JsfConstants.TH_ELEM, th);
            encodeDescendantComponent(context, th);
            writer.endElement(JsfConstants.TH_ELEM);
        }

        private void encodeGridBody(final FacesContext context,
                final HtmlGridBody body, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, body);
            writer.startElement(JsfConstants.TD_ELEM, body);

            // encodeLeftBody
            if (0 < leftFixCols) {
                writer.startElement(JsfConstants.TABLE_ELEM, body);
                writer.startElement(JsfConstants.TBODY_ELEM, body);
                for (Iterator it = getRenderedChildrenIterator(body); it
                        .hasNext();) {
                    final UIComponent child = (UIComponent) it.next();
                    if (child instanceof HtmlGridTr) {
                        encodeGridLeftBodyTr(context, (HtmlGridTr) child,
                                writer, leftFixCols);
                    }
                }

                writer.endElement(JsfConstants.TBODY_ELEM);
                writer.endElement(JsfConstants.TABLE_ELEM);
                writer.endElement(JsfConstants.TD_ELEM);

                writer.startElement(JsfConstants.TD_ELEM, body);
            }

            // encodeRightBody
            writer.startElement(JsfConstants.TABLE_ELEM, body);
            writer.startElement(JsfConstants.TBODY_ELEM, body);
            for (Iterator it = getRenderedChildrenIterator(body); it.hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTr) {
                    encodeGridRightBodyTr(context, (HtmlGridTr) child, writer,
                            leftFixCols);
                }
            }
            writer.endElement(JsfConstants.TBODY_ELEM);
            writer.endElement(JsfConstants.TABLE_ELEM);

            writer.endElement(JsfConstants.TD_ELEM);
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridLeftBodyTr(final FacesContext context,
                final HtmlGridTr tr, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, tr);
            int counter = 0;
            for (final Iterator it = getRenderedChildrenIterator(tr); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTd) {
                    counter++;
                    encodeGridBodyTd(context, (HtmlGridTd) child, writer);
                }
                if (leftFixCols <= counter) {
                    break;
                }
            }
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridRightBodyTr(final FacesContext context,
                final HtmlGridTr tr, final ResponseWriter writer,
                final int leftFixCols) throws IOException {
            writer.startElement(JsfConstants.TR_ELEM, tr);
            int counter = 0;
            for (final Iterator it = getRenderedChildrenIterator(tr); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTd) {
                    counter++;
                    if (counter <= leftFixCols) {
                        continue;
                    }
                    encodeGridBodyTd(context, (HtmlGridTd) child, writer);
                }
            }
            writer.endElement(JsfConstants.TR_ELEM);
        }

        private void encodeGridBodyTd(FacesContext context, HtmlGridTd td,
                ResponseWriter writer) throws IOException {
            writer.startElement(JsfConstants.TD_ELEM, td);
            encodeDescendantComponent(context, td);
            writer.endElement(JsfConstants.TD_ELEM);
        }

    }

    static class HtmlGridColumnGroup extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class HtmlGridColumn extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }

        private Integer span;

        public Integer getSpan() {
            return span;
        }

        public void setSpan(Integer span) {
            this.span = span;
        }
    }

    static class HtmlGrid extends UIComponentBase {

        public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Grid";

        public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGrid";

        private static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Grid";

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    static class HtmlGridHeader extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class HtmlGridBody extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class HtmlGridTh extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class HtmlGridTd extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class HtmlGridTr extends UIComponentBase {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public class MockHtmlGrid extends HtmlGrid {

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

}
