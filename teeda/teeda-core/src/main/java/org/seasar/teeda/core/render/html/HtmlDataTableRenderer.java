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
        UIComponent header = htmlDataTable.getHeader();
        if (header != null) {
            writer.startElement(JsfConstants.THEAD_ELEM, header);
            writer.startElement(JsfConstants.TR_ELEM, header);
            writer.startElement(JsfConstants.TH_ELEM, header);
            List childColumns = new ArrayList();
            for (Iterator it = htmlDataTable.getChildren().iterator(); it
                    .hasNext();) {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof UIColumn) {
                    childColumns.add(child);
                }
            }
            if (!childColumns.isEmpty()) {
                RendererUtil.renderAttribute(writer, JsfConstants.COLSPAN_ATTR,
                        new Integer(childColumns.size()));
            }

            renderComponent(context, header);

            writer.endElement(JsfConstants.TH_ELEM);
            writer.endElement(JsfConstants.TR_ELEM);

            if (!childColumns.isEmpty()) {
                writer.startElement(JsfConstants.TR_ELEM, header);
                for (Iterator it = childColumns.iterator(); it.hasNext();) {
                    writer.startElement(JsfConstants.TH_ELEM, header);
                    UIColumn column = (UIColumn) it.next();
                    renderComponent(context, column.getHeader());
                    writer.endElement(JsfConstants.TH_ELEM);
                }
                writer.endElement(JsfConstants.TR_ELEM);
            }

            writer.endElement(JsfConstants.THEAD_ELEM);
        }
    }

    private void renderComponent(FacesContext context, UIComponent header)
            throws IOException {
        header.encodeBegin(context);
        header.encodeChildren(context);
        header.encodeEnd(context);
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

}
