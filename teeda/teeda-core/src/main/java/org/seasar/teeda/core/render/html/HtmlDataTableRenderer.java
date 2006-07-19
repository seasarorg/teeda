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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.LoopIterator;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlDataTableRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Data";

    public static final String RENDERER_TYPE = "javax.faces.Table";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlDataTableBegin(context, (HtmlDataTable) component);
    }

    protected void encodeHtmlDataTableBegin(FacesContext context,
            HtmlDataTable htmlDataTable) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TABLE_ELEM, htmlDataTable);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlDataTable,
                getIdForRender(context, htmlDataTable));
        renderAttributes(htmlDataTable, writer);

        final List columns = new ArrayList();
        boolean isColumnHeaderExist = false;
        boolean isColumnFooterExist = false;
        {
            for (Iterator it = getRenderedChildrenIterator(htmlDataTable); it
                    .hasNext();) {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof UIColumn) {
                    UIColumn column = (UIColumn) child;
                    columns.add(column);
                    if (!isColumnHeaderExist) {
                        UIComponent columnHeader = column.getHeader();
                        if (columnHeader != null && columnHeader.isRendered()) {
                            isColumnHeaderExist = true;
                        }
                    }
                    if (!isColumnFooterExist) {
                        UIComponent columnFooter = column.getFooter();
                        if (columnFooter != null && columnFooter.isRendered()) {
                            isColumnFooterExist = true;
                        }
                    }
                }
            }
        }
        // thead
        {
            UIComponent tableHeader = toNullIfNotRendered(htmlDataTable
                    .getHeader());
            if (tableHeader != null || isColumnHeaderExist) {
                writer.startElement(JsfConstants.THEAD_ELEM, tableHeader);

                if (tableHeader != null) {
                    writer.startElement(JsfConstants.TR_ELEM, tableHeader);
                    writer.startElement(JsfConstants.TH_ELEM, tableHeader);
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.COLSPAN_ATTR, new Integer(columns
                                    .size()));
                    writer.writeAttribute(JsfConstants.SCOPE_ATTR,
                            JsfConstants.COLGROUP_VALUE, null);
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.CLASS_ATTR, htmlDataTable
                                    .getHeaderClass(),
                            JsfConstants.HEADER_CLASS_ATTR);
                    encodeComponent(context, tableHeader);
                    writer.endElement(JsfConstants.TH_ELEM);
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                if (isColumnHeaderExist) {
                    writer.startElement(JsfConstants.TR_ELEM, tableHeader);
                    for (Iterator it = columns.iterator(); it.hasNext();) {
                        writer.startElement(JsfConstants.TH_ELEM, tableHeader);
                        writer.writeAttribute(JsfConstants.COLGROUP_ATTR,
                                JsfConstants.COL_VALUE, null);
                        RendererUtil.renderAttribute(writer,
                                JsfConstants.CLASS_ATTR, htmlDataTable
                                        .getHeaderClass(),
                                JsfConstants.HEADER_CLASS_ATTR);
                        UIColumn column = (UIColumn) it.next();
                        UIComponent columnHeader = column.getHeader();
                        if (columnHeader != null) {
                            encodeComponent(context, columnHeader);
                        }
                        writer.endElement(JsfConstants.TH_ELEM);
                    }
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                writer.endElement(JsfConstants.THEAD_ELEM);
            }
        }
        // tfoot
        {
            UIComponent tableFooter = toNullIfNotRendered(htmlDataTable
                    .getFooter());
            if (tableFooter != null || isColumnFooterExist) {
                writer.startElement(JsfConstants.TFOOT_ELEM, tableFooter);

                if (tableFooter != null) {
                    writer.startElement(JsfConstants.TR_ELEM, tableFooter);
                    writer.startElement(JsfConstants.TD_ELEM, tableFooter);
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.COLSPAN_ATTR, new Integer(columns
                                    .size()));
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.CLASS_ATTR, htmlDataTable
                                    .getFooterClass(),
                            JsfConstants.FOOTER_CLASS_ATTR);
                    encodeComponent(context, tableFooter);
                    writer.endElement(JsfConstants.TD_ELEM);
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                if (isColumnFooterExist) {
                    writer.startElement(JsfConstants.TR_ELEM, tableFooter);
                    for (Iterator it = columns.iterator(); it.hasNext();) {
                        writer.startElement(JsfConstants.TD_ELEM, tableFooter);
                        RendererUtil.renderAttribute(writer,
                                JsfConstants.CLASS_ATTR, htmlDataTable
                                        .getFooterClass(),
                                JsfConstants.FOOTER_CLASS_ATTR);
                        UIColumn column = (UIColumn) it.next();
                        UIComponent columnFooter = column.getFooter();
                        if (columnFooter != null) {
                            encodeComponent(context, columnFooter);
                        }
                        writer.endElement(JsfConstants.TD_ELEM);
                    }
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                writer.endElement(JsfConstants.TFOOT_ELEM);
            }
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlDataTableChildren(context, (HtmlDataTable) component);
    }

    protected void encodeHtmlDataTableChildren(FacesContext context,
            HtmlDataTable htmlDataTable) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(JsfConstants.TBODY_ELEM, htmlDataTable);

        LoopIterator rowClasses = toStyleLoopIterator(htmlDataTable
                .getRowClasses());
        LoopIterator columnClasses = toStyleLoopIterator(htmlDataTable
                .getColumnClasses());

        int start = htmlDataTable.getFirst();
        int rows = htmlDataTable.getRows();
        boolean allRow = true;
        if (0 < rows) {
            allRow = false;
        }
        htmlDataTable.setRowIndex(start);
        for (int rowIndex = start; ((allRow || 0 < rows) && htmlDataTable
                .isRowAvailable());) {
            encodeBodyRow(context, htmlDataTable, writer, rowClasses,
                    columnClasses);
            rowIndex++;
            rows--;
            htmlDataTable.setRowIndex(rowIndex);
        }
        writer.endElement(JsfConstants.TBODY_ELEM);
    }

    private void encodeBodyRow(FacesContext context,
            HtmlDataTable htmlDataTable, ResponseWriter writer,
            Iterator rowClasses, LoopIterator columnClasses) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, htmlDataTable);
        if (rowClasses.hasNext()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    rowClasses.next(), JsfConstants.ROW_CLASSES_ATTR);
        }
        columnClasses.reset();
        for (Iterator itColumn = getRenderedChildrenIterator(htmlDataTable); itColumn
                .hasNext();) {
            UIComponent col = (UIComponent) itColumn.next();
            if (col instanceof UIColumn) {
                UIColumn column = (UIColumn) col;
                encodeBodyRowColumn(context, column, writer, columnClasses);
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeBodyRowColumn(FacesContext context, UIColumn column,
            ResponseWriter writer, Iterator columnClasses) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, column);
        if (columnClasses.hasNext()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    columnClasses.next(), JsfConstants.COLUMN_CLASSES_ATTR);
        }
        encodeDescendantComponent(context, column);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlDataTableEnd(context, (HtmlDataTable) component);
    }

    protected void encodeHtmlDataTableEnd(FacesContext context,
            HtmlDataTable htmlDataTable) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

}
