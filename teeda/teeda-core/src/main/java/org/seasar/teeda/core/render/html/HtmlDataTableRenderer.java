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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlDataTableRenderer extends AbstractHtmlRenderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
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
        RendererUtil.renderAttributes(writer, htmlDataTable,
                JsfConstants.TABLE_PASSTHROUGH_ATTRIBUTES);

        final List columns = new ArrayList();
        boolean isColumnHeaderExist = false;
        boolean isColumnFooterExist = false;
        {
            for (Iterator it = new RenderedComponentIterator(htmlDataTable
                    .getChildren()); it.hasNext();) {
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
                    writeColspanAttribute(writer, columns);
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
                    writeColspanAttribute(writer, columns);
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
        // tbody
        {
        }
    }

    protected void encodeComponent(FacesContext context, UIComponent component)
            throws IOException {
        component.encodeBegin(context);
        component.encodeChildren(context);
        component.encodeEnd(context);
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeChildren(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlDataTableChildren(context, (HtmlDataTable) component);
    }

    protected void encodeHtmlDataTableChildren(FacesContext context,
            HtmlDataTable htmlDataTable) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(JsfConstants.TBODY_ELEM, htmlDataTable);

        LoopList rowClasses = toLoopList(splitByComma(htmlDataTable
                .getRowClasses()));
        LoopList columnClasses = toLoopList(splitByComma(htmlDataTable
                .getColumnClasses()));

        int start = htmlDataTable.getFirst();
        int rows = htmlDataTable.getRows();
        boolean allRow = true;
        if (0 < rows) {
            allRow = false;
        }
        htmlDataTable.setRowIndex(start);
        rowClasses.moveFirst();
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
            LoopList rowClasses, LoopList columnClasses) throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, htmlDataTable);
        if (rowClasses.isNotEmpty()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    rowClasses.next(), JsfConstants.ROW_CLASSES_ATTR);
        }
        columnClasses.moveFirst();
        for (Iterator itColumn = new RenderedComponentIterator(htmlDataTable
                .getChildren()); itColumn.hasNext();) {
            UIComponent col = (UIComponent) itColumn.next();
            if (col instanceof UIColumn) {
                UIColumn column = (UIColumn) col;
                encodeBodyColumn(context, column, writer, columnClasses);
            }
        }
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeBodyColumn(FacesContext context, UIColumn column,
            ResponseWriter writer, LoopList columnClasses) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, column);
        if (columnClasses.isNotEmpty()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    columnClasses.next(), JsfConstants.COLUMN_CLASSES_ATTR);
        }
        for (Iterator itChild = new RenderedComponentIterator(column
                .getChildren()); itChild.hasNext();) {
            UIComponent component = (UIComponent) itChild.next();
            encodeComponentAndChildren(context, component);
        }
        writer.endElement(JsfConstants.TD_ELEM);
    }

    protected void encodeComponentAndChildren(FacesContext context,
            UIComponent component) throws IOException {
        encodeComponent(context, component);
        for (Iterator it = new RenderedComponentIterator(component
                .getChildren()); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            encodeComponentAndChildren(context, child);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
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

    private UIComponent toNullIfNotRendered(UIComponent component) {
        if (component != null && !component.isRendered()) {
            component = null;
        }
        return component;
    }

    private void writeColspanAttribute(ResponseWriter writer, final List columns)
            throws IOException {
        if (2 <= columns.size()) {
            RendererUtil.renderAttribute(writer, JsfConstants.COLSPAN_ATTR,
                    new Integer(columns.size()));
        }
    }

    protected static class RenderedComponentIterator implements Iterator {

        private Iterator iterator_;

        RenderedComponentIterator(Collection c) {
            iterator_ = c.iterator();
        }

        private UIComponent component_;

        public boolean hasNext() {
            if (component_ != null) {
                return true;
            }
            while (iterator_.hasNext()) {
                UIComponent component = (UIComponent) iterator_.next();
                if (component.isRendered()) {
                    component_ = component;
                    return true;
                }
            }
            return false;
        }

        public Object next() {
            if (component_ != null) {
                UIComponent component = component_;
                component_ = null;
                return component;
            }
            while (true) {
                UIComponent component = (UIComponent) iterator_.next();
                if (component.isRendered()) {
                    return component;
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }

    protected String[] splitByComma(String s) {
        String[] split = StringUtil.split(s, " ,");
        return split;
    }

    protected LoopList toLoopList(String[] s) {
        LoopList loopList = new LoopList();
        for (int i = 0; i < s.length; i++) {
            loopList.add(s[i]);
        }
        return loopList;
    }

    protected static class LoopList {

        List l_ = new ArrayList();

        int pos_ = -1;

        public void add(String s) {
            l_.add(s);
        }

        public String next() {
            if (l_.isEmpty()) {
                return null;
            }
            pos_++;
            if (!(pos_ < l_.size())) {
                pos_ = 0;
            }
            return (String) l_.get(pos_);
        }

        public boolean isEmpty() {
            return l_.isEmpty();
        }

        public boolean isNotEmpty() {
            return !(isEmpty());
        }

        public void moveFirst() {
            pos_ = -1;
        }
    }

}
