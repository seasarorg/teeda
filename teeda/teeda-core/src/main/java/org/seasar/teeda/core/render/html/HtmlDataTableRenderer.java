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

        final List columns = new ArrayList();
        boolean isColumnHeaderExist = false;
        boolean isColumnFooterExist = false;
        {
            for (Iterator it = htmlDataTable.getChildren().iterator(); it
                    .hasNext();) {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof UIColumn) {
                    UIColumn column = (UIColumn) child;
                    if (!column.isRendered()) {
                        continue;
                    }
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
                    encodeComponent(context, tableHeader);
                    writer.endElement(JsfConstants.TH_ELEM);
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                if (isColumnHeaderExist) {
                    writer.startElement(JsfConstants.TR_ELEM, tableHeader);
                    for (Iterator it = columns.iterator(); it.hasNext();) {
                        writer.startElement(JsfConstants.TH_ELEM, tableHeader);
                        UIColumn column = (UIColumn) it.next();
                        UIComponent columnHeader = column.getHeader();
                        if (columnHeader != null) {
                            writer.writeAttribute(JsfConstants.COLGROUP_ATTR,
                                    JsfConstants.COL_VALUE, null);
                            encodeComponent(context, columnHeader);
                        }
                        writer.writeText("", null);
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
                    encodeComponent(context, tableFooter);
                    writer.endElement(JsfConstants.TD_ELEM);
                    writer.endElement(JsfConstants.TR_ELEM);
                }

                if (isColumnFooterExist) {
                    writer.startElement(JsfConstants.TR_ELEM, tableFooter);
                    for (Iterator it = columns.iterator(); it.hasNext();) {
                        writer.startElement(JsfConstants.TD_ELEM, tableFooter);
                        UIColumn column = (UIColumn) it.next();
                        UIComponent columnFooter = column.getFooter();
                        if (columnFooter != null) {
                            encodeComponent(context, columnFooter);
                        }
                        writer.writeText("", null);
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

        writer.writeText("", null);
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

}
