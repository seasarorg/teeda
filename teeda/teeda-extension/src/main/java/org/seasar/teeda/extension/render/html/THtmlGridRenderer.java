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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlGrid;
import org.seasar.teeda.extension.component.html.THtmlGridBody;
import org.seasar.teeda.extension.component.html.THtmlGridColumn;
import org.seasar.teeda.extension.component.html.THtmlGridColumnGroup;
import org.seasar.teeda.extension.component.html.THtmlGridHeader;
import org.seasar.teeda.extension.component.html.THtmlGridTd;
import org.seasar.teeda.extension.component.html.THtmlGridTh;
import org.seasar.teeda.extension.component.html.THtmlGridTr;
import org.seasar.teeda.extension.render.TForEachRenderer;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author manhole
 */
public class THtmlGridRenderer extends TForEachRenderer {

    public static final String COMPONENT_FAMILY = THtmlGrid.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlGrid.DEFAULT_RENDERER_TYPE;

    private static final String GRID_TABLE_CLASS_NAME = "gridTable";

    private static final String GRID_HEADER_CLASS_NAME = "gridHeader";

    private static final String GRID_CELL_CLASS_NAME = "gridCell";

    private static final String LEFT_FIXED_CLASS_NAME = "teeda_leftFixed";

    private static final String GRID_ATTRIBUTE = THtmlGrid.class.getName()
            + ".GRID_ATTRIBUTE";

    private static final String ALREADY_WRITE = THtmlGrid.class.getName()
            + ".ALREADY_WRITE";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGridBegin(context, (THtmlGrid) component);
    }

    protected void encodeHtmlGridBegin(final FacesContext context,
            final THtmlGrid htmlGrid) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writeJavascript(context, htmlGrid, writer);
        writer.startElement(JsfConstants.TABLE_ELEM, htmlGrid);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlGrid,
                getIdForRender(context, htmlGrid));
        RendererUtil.renderAttribute(writer, JsfConstants.BORDER_ATTR, "0");
        RendererUtil
                .renderAttribute(writer, JsfConstants.CELLSPACING_ATTR, "0");
        RendererUtil
                .renderAttribute(writer, JsfConstants.CELLPADDING_ATTR, "0");
    }

    private void writeJavascript(FacesContext context, THtmlGrid htmlGrid,
            ResponseWriter writer) throws IOException {
        final UIViewRoot viewRoot = context.getViewRoot();
        final Map attributes = viewRoot.getAttributes();
        if (attributes.containsKey(ALREADY_WRITE)) {
            return;
        }
        JavaScriptContext scriptContext = new JavaScriptContext();
        scriptContext.loadScript(THtmlGrid.class.getName());
        writer.write(scriptContext.getResult());
        attributes.put(ALREADY_WRITE, Boolean.TRUE);
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGridChildren(context, (THtmlGrid) component);
    }

    protected void encodeHtmlGridChildren(FacesContext context,
            THtmlGrid htmlGrid) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        THtmlGridRenderer.GridAttribute attribute = getGridAttribute(htmlGrid);
        for (final Iterator it = getRenderedChildrenIterator(htmlGrid); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridHeader) {
                encodeGridHeader(context, htmlGrid, (THtmlGridHeader) child,
                        writer, attribute);
                break;
            }
        }
        for (final Iterator it = getRenderedChildrenIterator(htmlGrid); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridBody) {
                encodeGridBody(context, htmlGrid, (THtmlGridBody) child,
                        writer, attribute);
                break;
            }
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGridEnd(context, (THtmlGrid) component);
    }

    protected void encodeHtmlGridEnd(FacesContext context, THtmlGrid htmlGrid)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    private void encodeGridHeader(final FacesContext context,
            THtmlGrid htmlGrid, final THtmlGridHeader header,
            final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, header);
        writer.startElement(JsfConstants.TD_ELEM, header);

        // encodeLeftHeader
        if (attribute.hasLeftFixCols()) {
            writer.startElement(JsfConstants.TABLE_ELEM, header);
            renderInnerTableAttributes(writer);

            writer.startElement(JsfConstants.THEAD_ELEM, header);
            for (final Iterator it = getRenderedChildrenIterator(header); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof THtmlGridTr) {
                    encodeGridLeftHeaderTr(context, (THtmlGridTr) child,
                            writer, attribute);
                }
            }

            writer.endElement(JsfConstants.THEAD_ELEM);
            writer.endElement(JsfConstants.TABLE_ELEM);
            writer.endElement(JsfConstants.TD_ELEM);

            writer.startElement(JsfConstants.TD_ELEM, header);
        }

        // encodeRightHeader
        writer.startElement(JsfConstants.DIV_ELEM, header);
        RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR,
                getIdForRender(context, htmlGrid) + "RightHeader");
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "overflow:hidden; width:" + attribute.getRightHeaderWidth()
                        + "px;");

        writer.startElement(JsfConstants.TABLE_ELEM, header);
        renderInnerTableAttributes(writer);
        writer.startElement(JsfConstants.THEAD_ELEM, header);
        for (final Iterator it = getRenderedChildrenIterator(header); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridTr) {
                encodeGridRightHeaderTr(context, (THtmlGridTr) child, writer,
                        attribute);
            }
        }
        writer.endElement(JsfConstants.THEAD_ELEM);
        writer.endElement(JsfConstants.TABLE_ELEM);
        writer.endElement(JsfConstants.DIV_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridLeftHeaderTr(final FacesContext context,
            final THtmlGridTr tr, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int columnNo = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridTh) {
                columnNo++;
                encodeGridHeaderTh(context, (THtmlGridTh) child, writer,
                        attribute, columnNo);
            } else if (child instanceof THtmlGridTd) {
                columnNo++;
                encodeGridHeaderTd(context, (THtmlGridTd) child, writer,
                        attribute, columnNo);
            }
            if (attribute.getLeftFixCols() <= columnNo) {
                break;
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridRightHeaderTr(final FacesContext context,
            final THtmlGridTr tr, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int columnNo = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridTh) {
                columnNo++;
                if (columnNo <= attribute.getLeftFixCols()) {
                    continue;
                }
                encodeGridHeaderTh(context, (THtmlGridTh) child, writer,
                        attribute, columnNo);
            } else if (child instanceof THtmlGridTd) {
                columnNo++;
                if (columnNo <= attribute.getLeftFixCols()) {
                    continue;
                }
                encodeGridHeaderTd(context, (THtmlGridTd) child, writer,
                        attribute, columnNo);
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridHeaderTd(final FacesContext context,
            final THtmlGridTd td, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute, final int columnNo)
            throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, td);
        writer.startElement(JsfConstants.NOBR_ELEM, td);
        encodeDescendantComponent(context, td);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    private void encodeGridHeaderTh(final FacesContext context,
            final THtmlGridTh th, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute, final int columnNo)
            throws IOException {
        writer.startElement(JsfConstants.TH_ELEM, th);
        writer.startElement(JsfConstants.DIV_ELEM, th);
        RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                GRID_HEADER_CLASS_NAME);
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "overflow:hidden; width:" + attribute.getColumnWidth(columnNo)
                        + "px;");
        writer.startElement(JsfConstants.NOBR_ELEM, th);
        encodeDescendantComponent(context, th);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.DIV_ELEM);
        writer.endElement(JsfConstants.TH_ELEM);
    }

    private void encodeGridBody(final FacesContext context, THtmlGrid htmlGrid,
            final THtmlGridBody body, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, body);
        writer.startElement(JsfConstants.TD_ELEM, body);
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "vertical-align:top;");

        final Object page = htmlGrid.getPage(context);
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        final Object[] items = htmlGrid.getItems(context);
        final String itemName = htmlGrid.getItemName();
        final String indexName = htmlGrid.getIndexName();

        // encodeLeftBody
        if (attribute.hasLeftFixCols()) {
            writer.startElement(JsfConstants.DIV_ELEM, body);
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR,
                    getIdForRender(context, htmlGrid) + "LeftBody");
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                    "overflow:hidden; height:" + attribute.getLeftBodyHeight()
                            + "px;");

            writer.startElement(JsfConstants.TABLE_ELEM, body);
            renderInnerTableAttributes(writer);
            writer.startElement(JsfConstants.TBODY_ELEM, body);

            for (int i = 0; i < items.length; ++i) {
                htmlGrid.enterRow(context, i);
                processItem(beanDesc, page, items[i], itemName, i, indexName);
                for (Iterator it = getRenderedChildrenIterator(body); it
                        .hasNext();) {
                    final UIComponent child = (UIComponent) it.next();
                    if (child instanceof THtmlGridTr) {
                        encodeGridLeftBodyTr(context, (THtmlGridTr) child,
                                writer, attribute);
                    }
                }
                htmlGrid.leaveRow(context);
            }

            writer.endElement(JsfConstants.TBODY_ELEM);
            writer.endElement(JsfConstants.TABLE_ELEM);
            writer.endElement(JsfConstants.DIV_ELEM);
            writer.endElement(JsfConstants.TD_ELEM);

            writer.startElement(JsfConstants.TD_ELEM, body);
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                    "vertical-align:top;");
        }

        // encodeRightBody
        writer.startElement(JsfConstants.DIV_ELEM, body);
        RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR,
                getIdForRender(context, htmlGrid) + "RightBody");
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "overflow:scroll; width:" + attribute.getRightBodyWidth()
                        + "px; height:" + attribute.getRightBodyHeight()
                        + "px;");
        RendererUtil
                .renderAttribute(
                        writer,
                        JsfConstants.ONSCROLL_ATTR,
                        "document.all."
                                + htmlGrid.getId()
                                + "RightHeader.scrollLeft=this.scrollLeft; document.all."
                                + htmlGrid.getId()
                                + "LeftBody.scrollTop=this.scrollTop;");

        writer.startElement(JsfConstants.TABLE_ELEM, body);
        renderInnerTableAttributes(writer);
        writer.startElement(JsfConstants.TBODY_ELEM, body);

        for (int i = 0; i < items.length; ++i) {
            htmlGrid.enterRow(context, i);
            processItem(beanDesc, page, items[i], itemName, i, indexName);
            for (final Iterator it = getRenderedChildrenIterator(body); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof THtmlGridTr) {
                    encodeGridRightBodyTr(context, (THtmlGridTr) child, writer,
                            attribute);
                }
            }
            htmlGrid.leaveRow(context);
        }

        writer.endElement(JsfConstants.TBODY_ELEM);
        writer.endElement(JsfConstants.TABLE_ELEM);
        writer.endElement(JsfConstants.DIV_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridLeftBodyTr(final FacesContext context,
            final THtmlGridTr tr, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        renderStyleClass(tr, writer);
        int columnNo = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridTd) {
                columnNo++;
                encodeGridBodyTd(context, (THtmlGridTd) child, writer,
                        attribute, columnNo);
            }
            if (attribute.getLeftFixCols() <= columnNo) {
                break;
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridRightBodyTr(final FacesContext context,
            final THtmlGridTr tr, final ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        renderStyleClass(tr, writer);
        int columnNo = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof THtmlGridTd) {
                columnNo++;
                if (columnNo <= attribute.getLeftFixCols()) {
                    continue;
                }
                encodeGridBodyTd(context, (THtmlGridTd) child, writer,
                        attribute, columnNo);
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridBodyTd(FacesContext context, THtmlGridTd td,
            ResponseWriter writer,
            final THtmlGridRenderer.GridAttribute attribute, final int columnNo)
            throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, td);
        writer.startElement(JsfConstants.DIV_ELEM, td);
        RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                GRID_CELL_CLASS_NAME);
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "overflow:hidden; width:" + attribute.getColumnWidth(columnNo)
                        + "px;");
        writer.startElement(JsfConstants.NOBR_ELEM, td);
        encodeDescendantComponent(context, td);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.DIV_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    private THtmlGridRenderer.GridAttribute getGridAttribute(THtmlGrid htmlGrid) {
        THtmlGridRenderer.GridAttribute attribute = (THtmlGridRenderer.GridAttribute) htmlGrid
                .getAttributes().get(GRID_ATTRIBUTE);
        if (attribute == null) {
            attribute = new GridAttribute();
            setupTableSize(htmlGrid, attribute);
            setupWidth(htmlGrid, attribute);
            setupHeight(htmlGrid, attribute);
            htmlGrid.getAttributes().put(GRID_ATTRIBUTE, attribute);
        }
        return attribute;
    }

    private void setupTableSize(THtmlGrid htmlGrid,
            THtmlGridRenderer.GridAttribute attribute) {
        final String width = htmlGrid.getWidth();
        if (width != null) {
            attribute.setTableWidth(toDigit(width));
        }
        final String height = htmlGrid.getHeight();
        if (height != null) {
            attribute.setTableHeight(toDigit(height));
        }
    }

    private void setupWidth(THtmlGrid htmlGrid,
            THtmlGridRenderer.GridAttribute attribute) {
        int columnNo = 0;
        for (final Iterator itGrid = getRenderedChildrenIterator(htmlGrid); itGrid
                .hasNext();) {
            final UIComponent child = (UIComponent) itGrid.next();
            if (child instanceof THtmlGridColumnGroup) {
                THtmlGridColumnGroup columnGroup = (THtmlGridColumnGroup) child;
                for (final Iterator itColGroup = getRenderedChildrenIterator(columnGroup); itColGroup
                        .hasNext();) {
                    final UIComponent element = (UIComponent) itColGroup.next();
                    if (element instanceof THtmlGridColumn) {
                        final THtmlGridColumn col = (THtmlGridColumn) element;
                        // TODO check
                        final int span = Integer.parseInt(col.getSpan());
                        final int width = toDigit(col.getWidth());
                        final String styleClass = col.getStyleClass();
                        if (StringUtil.contains(styleClass,
                                LEFT_FIXED_CLASS_NAME)) {
                            attribute.setLeftFixCols(span);
                            // TODO check
                            attribute.setLeftWidth(width * span
                                    + attribute.getLeftWidth());
                        }
                        for (int i = 0; i < span; i++) {
                            columnNo++;
                            attribute.setColumnWidth(columnNo, width);
                        }
                    }
                }
            }
        }
    }

    private void setupHeight(THtmlGrid htmlGrid,
            THtmlGridRenderer.GridAttribute attribute) {
        for (final Iterator itGrid = getRenderedChildrenIterator(htmlGrid); itGrid
                .hasNext();) {
            final UIComponent gridChild = (UIComponent) itGrid.next();
            if (gridChild instanceof THtmlGridHeader) {
                for (final Iterator itHeader = getRenderedChildrenIterator(gridChild); itHeader
                        .hasNext();) {
                    final UIComponent headerChild = (UIComponent) itHeader
                            .next();
                    if (headerChild instanceof THtmlGridTr) {
                        final THtmlGridTr tr = (THtmlGridTr) headerChild;
                        final String height = tr.getHeight();
                        if (height != null) {
                            attribute.setHeaderHeight(toDigit(height)
                                    + attribute.getHeaderHeight());
                        }
                    }
                }
            }
        }
    }

    private int toDigit(String s) {
        if (s.toLowerCase().endsWith("px")) {
            s = s.substring(0, s.length() - 2);
        }
        return Integer.parseInt(s);
    }

    public boolean getRendersChildren() {
        return true;
    }

    private void renderInnerTableAttributes(final ResponseWriter writer)
            throws IOException {
        RendererUtil
                .renderAttribute(writer, JsfConstants.CELLSPACING_ATTR, "1");
        RendererUtil
                .renderAttribute(writer, JsfConstants.CELLPADDING_ATTR, "0");
        RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                GRID_TABLE_CLASS_NAME);
    }

    private void renderStyleClass(final THtmlGridTr tr,
            final ResponseWriter writer) throws IOException {
        final String styleClass = tr.getStyleClass();
        if (styleClass != null) {
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                    styleClass);
        }
    }

    private static class GridAttribute implements Serializable {

        private static final long serialVersionUID = 1L;

        // TODO Windows XP default...
        private int scrollBarWidth = 16;

        private int tableWidth;

        private int tableHeight;

        private int headerHeight;

        private int leftFixCols;

        private Map columnWidths = new HashMap();

        public int getColumnWidth(int no) {
            return ((Integer) columnWidths.get(new Integer(no))).intValue();
        }

        public void setColumnWidth(int no, int width) {
            columnWidths.put(new Integer(no), new Integer(width));
        }

        public boolean hasLeftFixCols() {
            return 0 < leftFixCols;
        }

        private int leftWidth;

        public int getHeaderHeight() {
            return headerHeight;
        }

        public void setHeaderHeight(int headerHeight) {
            this.headerHeight = headerHeight;
        }

        public int getLeftBodyHeight() {
            return tableHeight - headerHeight - scrollBarWidth;
        }

        public int getLeftFixCols() {
            return leftFixCols;
        }

        public void setLeftFixCols(int leftFixCols) {
            this.leftFixCols = leftFixCols;
        }

        public int getLeftWidth() {
            return leftWidth;
        }

        public void setLeftWidth(int leftWidth) {
            this.leftWidth = leftWidth;
        }

        public int getRightBodyHeight() {
            return tableHeight - headerHeight;
        }

        public int getRightBodyWidth() {
            return tableWidth - leftWidth;
        }

        public int getRightHeaderWidth() {
            return tableWidth - leftWidth - scrollBarWidth;
        }

        public int getScrollBarWidth() {
            return scrollBarWidth;
        }

        public void setScrollBarWidth(int scrollBarWidth) {
            this.scrollBarWidth = scrollBarWidth;
        }

        public int getTableHeight() {
            return tableHeight;
        }

        public void setTableHeight(int tableHeight) {
            this.tableHeight = tableHeight;
        }

        public int getTableWidth() {
            return tableWidth;
        }

        public void setTableWidth(int tableWidth) {
            this.tableWidth = tableWidth;
        }

    }

}
