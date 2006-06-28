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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.AbstractHtmlRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.HtmlGrid;
import org.seasar.teeda.extension.component.html.HtmlGridBody;
import org.seasar.teeda.extension.component.html.HtmlGridColumn;
import org.seasar.teeda.extension.component.html.HtmlGridColumnGroup;
import org.seasar.teeda.extension.component.html.HtmlGridHeader;
import org.seasar.teeda.extension.component.html.HtmlGridTd;
import org.seasar.teeda.extension.component.html.HtmlGridTh;
import org.seasar.teeda.extension.component.html.HtmlGridTr;

/**
 * @author manhole
 */
public class HtmlGridRenderer extends AbstractHtmlRenderer {

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
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGridChildren(context, (HtmlGrid) component);
    }

    protected void encodeHtmlGridChildren(FacesContext context,
            HtmlGrid htmlGrid) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        HtmlGridRenderer.GridAttribute attribute = getGridAttribute(htmlGrid);
        for (Iterator it = getRenderedChildrenIterator(htmlGrid); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridHeader) {
                encodeGridHeader(context, htmlGrid, (HtmlGridHeader) child,
                        writer, attribute);
                break;
            }
        }
        for (Iterator it = getRenderedChildrenIterator(htmlGrid); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridBody) {
                encodeGridBody(context, htmlGrid, (HtmlGridBody) child, writer,
                        attribute);
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
        encodeHtmlGridEnd(context, (HtmlGrid) component);
    }

    protected void encodeHtmlGridEnd(FacesContext context, HtmlGrid htmlGrid)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    private void encodeGridHeader(final FacesContext context,
            HtmlGrid htmlGrid, final HtmlGridHeader header,
            final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, header);
        writer.startElement(JsfConstants.TD_ELEM, header);

        // encodeLeftHeader
        if (attribute.hasLeftFixCols()) {
            writer.startElement(JsfConstants.TABLE_ELEM, header);
            writer.startElement(JsfConstants.THEAD_ELEM, header);
            for (Iterator it = getRenderedChildrenIterator(header); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTr) {
                    encodeGridLeftHeaderTr(context, (HtmlGridTr) child, writer,
                            attribute);
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
        writer.startElement(JsfConstants.THEAD_ELEM, header);
        for (Iterator it = getRenderedChildrenIterator(header); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridTr) {
                encodeGridRightHeaderTr(context, (HtmlGridTr) child, writer,
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
            final HtmlGridTr tr, final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int counter = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridTh) {
                counter++;
                encodeGridHeaderTh(context, (HtmlGridTh) child, writer);
            } else if (child instanceof HtmlGridTd) {
                counter++;
                encodeGridHeaderTd(context, (HtmlGridTd) child, writer);
            }
            if (attribute.getLeftFixCols() <= counter) {
                break;
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridRightHeaderTr(final FacesContext context,
            final HtmlGridTr tr, final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int counter = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridTh) {
                counter++;
                if (counter <= attribute.getLeftFixCols()) {
                    continue;
                }
                encodeGridHeaderTh(context, (HtmlGridTh) child, writer);
            } else if (child instanceof HtmlGridTd) {
                counter++;
                if (counter <= attribute.getLeftFixCols()) {
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
        writer.startElement(JsfConstants.NOBR_ELEM, td);
        encodeDescendantComponent(context, td);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    private void encodeGridHeaderTh(FacesContext context, HtmlGridTh th,
            ResponseWriter writer) throws IOException {
        writer.startElement(JsfConstants.TH_ELEM, th);
        writer.startElement(JsfConstants.NOBR_ELEM, th);
        encodeDescendantComponent(context, th);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.TH_ELEM);
    }

    private void encodeGridBody(final FacesContext context, HtmlGrid htmlGrid,
            final HtmlGridBody body, final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, body);
        writer.startElement(JsfConstants.TD_ELEM, body);
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "vertical-align:top;");

        // encodeLeftBody
        if (attribute.hasLeftFixCols()) {
            writer.startElement(JsfConstants.DIV_ELEM, body);
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR,
                    getIdForRender(context, htmlGrid) + "LeftBody");
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                    "overflow:hidden; height:" + attribute.leftBodyHeight
                            + "px;");

            writer.startElement(JsfConstants.TABLE_ELEM, body);
            writer.startElement(JsfConstants.TBODY_ELEM, body);

            // TODO
            final Collection items = getBodyItems(context, htmlGrid);
            for (final Iterator itItem = items.iterator(); itItem.hasNext();) {
                Object rowBean = (Object) itItem.next();
                enterRow(context, htmlGrid, rowBean);

                for (Iterator it = getRenderedChildrenIterator(body); it
                        .hasNext();) {
                    final UIComponent child = (UIComponent) it.next();
                    if (child instanceof HtmlGridTr) {
                        encodeGridLeftBodyTr(context, (HtmlGridTr) child,
                                writer, attribute);
                    }
                }

                leaveRow(context, htmlGrid);
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
                "overflow:scroll; width:" + attribute.rightBodyWidth
                        + "px; height:" + attribute.rightBodyHeight + "px;");
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
        writer.startElement(JsfConstants.TBODY_ELEM, body);

        // TODO
        final Collection items = getBodyItems(context, htmlGrid);
        for (final Iterator itItem = items.iterator(); itItem.hasNext();) {
            Object rowBean = (Object) itItem.next();
            enterRow(context, htmlGrid, rowBean);

            for (Iterator it = getRenderedChildrenIterator(body); it.hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridTr) {
                    encodeGridRightBodyTr(context, (HtmlGridTr) child, writer,
                            attribute);
                }
            }

            leaveRow(context, htmlGrid);
        }

        writer.endElement(JsfConstants.TBODY_ELEM);
        writer.endElement(JsfConstants.TABLE_ELEM);
        writer.endElement(JsfConstants.DIV_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridLeftBodyTr(final FacesContext context,
            final HtmlGridTr tr, final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int counter = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridTd) {
                counter++;
                encodeGridBodyTd(context, (HtmlGridTd) child, writer);
            }
            if (attribute.getLeftFixCols() <= counter) {
                break;
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeGridRightBodyTr(final FacesContext context,
            final HtmlGridTr tr, final ResponseWriter writer,
            final HtmlGridRenderer.GridAttribute attribute) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, tr);
        int counter = 0;
        for (final Iterator it = getRenderedChildrenIterator(tr); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof HtmlGridTd) {
                counter++;
                if (counter <= attribute.getLeftFixCols()) {
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
        writer.startElement(JsfConstants.NOBR_ELEM, td);
        encodeDescendantComponent(context, td);
        writer.endElement(JsfConstants.NOBR_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    static final String GRID_ATTRIBUTE = HtmlGrid.class.getName()
            + ".GRID_ATTRIBUTE";

    private HtmlGridRenderer.GridAttribute getGridAttribute(HtmlGrid htmlGrid) {
        HtmlGridRenderer.GridAttribute attribute = (HtmlGridRenderer.GridAttribute) htmlGrid
                .getAttributes().get(GRID_ATTRIBUTE);
        if (attribute == null) {
            attribute = new GridAttribute();
            {
                final String width = htmlGrid.getWidth();
                if (width != null) {
                    attribute.setTableWidth(toDigit(width));
                }
                final String height = htmlGrid.getHeight();
                if (height != null) {
                    attribute.setTableHeight(toDigit(height));
                }
            }

            for (final Iterator it = getRenderedChildrenIterator(htmlGrid); it
                    .hasNext();) {
                final UIComponent child = (UIComponent) it.next();
                if (child instanceof HtmlGridColumnGroup) {
                    HtmlGridColumnGroup columnGroup = (HtmlGridColumnGroup) child;
                    for (Iterator itr = getRenderedChildrenIterator(columnGroup); itr
                            .hasNext();) {
                        final UIComponent element = (UIComponent) itr.next();
                        if (element instanceof HtmlGridColumn) {
                            final HtmlGridColumn col = (HtmlGridColumn) element;
                            final String span = col.getSpan();
                            if (span != null) {
                                attribute
                                        .setLeftFixCols(Integer.parseInt(span));
                            }
                            final String width = col.getWidth();
                            if (width != null) {
                                attribute.setLeftWidth(toDigit(width)
                                        + attribute.getLeftWidth());
                            }
                        }
                    }
                }
            }
            for (final Iterator itGrid = getRenderedChildrenIterator(htmlGrid); itGrid
                    .hasNext();) {
                final UIComponent gridChild = (UIComponent) itGrid.next();
                if (gridChild instanceof HtmlGridHeader) {
                    for (final Iterator itHeader = getRenderedChildrenIterator(gridChild); itHeader
                            .hasNext();) {
                        final UIComponent headerChild = (UIComponent) itHeader
                                .next();
                        if (headerChild instanceof HtmlGridTr) {
                            final HtmlGridTr tr = (HtmlGridTr) headerChild;
                            final String height = tr.getHeight();
                            if (height != null) {
                                attribute.setHeaderHeight(toDigit(height)
                                        + attribute.getHeaderHeight());
                            }
                        }
                    }
                }
            }

            attribute.rightHeaderWidth = attribute.tableWidth
                    - attribute.leftWidth - attribute.scrollBarWidth;
            attribute.leftBodyHeight = attribute.tableHeight
                    - attribute.headerHeight - attribute.scrollBarWidth;
            attribute.rightBodyHeight = attribute.tableHeight
                    - attribute.headerHeight;
            attribute.rightBodyWidth = attribute.tableWidth
                    - attribute.leftWidth;

            htmlGrid.getAttributes().put(GRID_ATTRIBUTE, attribute);
        }
        return attribute;
    }

    private int toDigit(final String l) {
        final String s;
        if (l.toLowerCase().endsWith("px")) {
            s = l.substring(0, l.length() - 2);
        } else {
            s = l;
        }
        return Integer.parseInt(s);
    }

    public boolean getRendersChildren() {
        return true;
    }

    private static class GridAttribute implements Serializable {

        private static final long serialVersionUID = 1L;

        // Windows XP default...
        private int scrollBarWidth = 16;

        private int tableWidth;

        private int tableHeight;

        private int headerHeight;

        private int rightHeaderWidth;

        private int leftBodyHeight;

        private int rightBodyHeight;

        private int rightBodyWidth;

        private int leftFixCols;

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
            return leftBodyHeight;
        }

        public void setLeftBodyHeight(int leftBodyHeight) {
            this.leftBodyHeight = leftBodyHeight;
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
            return rightBodyHeight;
        }

        public void setRightBodyHeight(int rightBodyHeight) {
            this.rightBodyHeight = rightBodyHeight;
        }

        public int getRightBodyWidth() {
            return rightBodyWidth;
        }

        public void setRightBodyWidth(int rightBodyWidth) {
            this.rightBodyWidth = rightBodyWidth;
        }

        public int getRightHeaderWidth() {
            return rightHeaderWidth;
        }

        public void setRightHeaderWidth(int rightHeaderWidth) {
            this.rightHeaderWidth = rightHeaderWidth;
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

    Collection getBodyItems(final FacesContext context, final HtmlGrid htmlGrid) {
        //        final String itemsName = getNaturalId(htmlGrid) + "Items";
        //        final Object value = context.getApplication().getVariableResolver()
        //                .resolveVariable(context, itemsName);
        final Object value = htmlGrid.getValue();
        if (value == null) {
            return Collections.EMPTY_LIST;
        }
        if (!(value instanceof Collection)) {
            throw new ClassCastException(value.getClass().getName());
        }
        return (Collection) value;
    }

    private String getNaturalId(final HtmlGrid htmlGrid) {
        final String id = htmlGrid.getId();
        final int pos = id.lastIndexOf("Grid");
        final String naturalId = id.substring(0, pos);
        return naturalId;
    }

    void enterRow(final FacesContext context, final HtmlGrid htmlGrid,
            Object rowBean) {
        final String itemName = getNaturalId(htmlGrid);
        context.getExternalContext().getRequestMap().put(itemName, rowBean);
    }

    void leaveRow(FacesContext context, HtmlGrid htmlGrid) {
        final String itemName = getNaturalId(htmlGrid);
        context.getExternalContext().getRequestMap().remove(itemName);
    }

}