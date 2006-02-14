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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.ArrayIterator;
import javax.faces.internal.SelectItemsIterator;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.ArrayUtil;
import org.seasar.teeda.core.util.DecodeUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlSelectManyListboxRenderer extends AbstractHtmlRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlSelectManyListboxEnd(context,
                (HtmlSelectManyListbox) component);
    }

    protected void encodeHtmlSelectManyListboxEnd(FacesContext context,
            HtmlSelectManyListbox htmlSelectManyListbox) throws IOException {

        Iterator it = new SelectItemsIterator(htmlSelectManyListbox);
        if (!it.hasNext()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.SELECT_ELEM, htmlSelectManyListbox);

        RendererUtil.renderIdAttributeIfNecessary(writer,
                htmlSelectManyListbox, getIdForRender(context,
                        htmlSelectManyListbox));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlSelectManyListbox.getClientId(context));
        RendererUtil.renderAttribute(writer, JsfConstants.MULTIPLE_ATTR,
                JsfConstants.MULTIPLE_VALUE);
        renderSize(context, htmlSelectManyListbox, writer);
        if (htmlSelectManyListbox.isDisabled()) {
            RendererUtil.renderDisabledAttribute(writer);
        }
        RendererUtil.renderAttributes(writer, htmlSelectManyListbox,
                JsfConstants.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        final String[] values = ValueHolderUtil.getValuesForRender(context,
                htmlSelectManyListbox);
        renderSelectItems(context, htmlSelectManyListbox, writer, it, values);

        writer.endElement(JsfConstants.SELECT_ELEM);
    }

    protected void renderSize(FacesContext context,
            HtmlSelectManyListbox htmlSelectManyListbox, ResponseWriter writer)
            throws IOException {
        final int size = getSize((HtmlSelectManyListbox) htmlSelectManyListbox);
        RendererUtil.renderAttribute(writer, JsfConstants.SIZE_ATTR,
                new Integer(size));
    }

    private void renderSelectItems(FacesContext context,
            HtmlSelectManyListbox htmlSelectManyListbox, ResponseWriter writer,
            Iterator it, final String[] values) throws IOException {
        while (it.hasNext()) {
            final SelectItem selectItem = (SelectItem) it.next();
            if (selectItem instanceof SelectItemGroup) {
                SelectItemGroup selectItemGroup = (SelectItemGroup) selectItem;
                SelectItem[] selectItems = selectItemGroup.getSelectItems();
                Iterator selectItemsIt = new ArrayIterator(selectItems);
                writer.startElement(JsfConstants.OPTGROUP_ELEM,
                        htmlSelectManyListbox);
                RendererUtil.renderAttribute(writer, JsfConstants.LABEL_ATTR,
                        selectItemGroup.getLabel());
                renderSelectItems(context, htmlSelectManyListbox, writer,
                        selectItemsIt, values);
                writer.endElement(JsfConstants.OPTGROUP_ELEM);
            } else {
                writer.startElement(JsfConstants.OPTION_ELEM,
                        htmlSelectManyListbox);
                final Object value = selectItem.getValue();
                RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                        value);

                final boolean disabled = htmlSelectManyListbox.isDisabled()
                        || selectItem.isDisabled();
                final String labelClass = getLabelStyleClass(
                        htmlSelectManyListbox, disabled);
                if (labelClass != null) {
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.CLASS_ATTR, labelClass);
                }

                if (ArrayUtil.contains(values, value)) {
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.SELECTED_ATTR,
                            JsfConstants.SELECTED_VALUE);
                }
                if (selectItem.isDisabled()) {
                    RendererUtil.renderDisabledAttribute(writer);
                }
                writer.writeText(selectItem.getLabel(), null);
                writer.endElement(JsfConstants.OPTION_ELEM);
            }
        }
    }

    private int getSize(HtmlSelectManyListbox htmlSelectManyListbox) {
        int size = htmlSelectManyListbox.getSize();
        if (0 < size) {
            return size;
        }
        size = 0;
        for (Iterator it = new SelectItemsIterator(htmlSelectManyListbox); it
                .hasNext();) {
            SelectItem item = (SelectItem) it.next();
            if (item instanceof SelectItemGroup) {
                SelectItemGroup itemGroup = (SelectItemGroup) item;
                size += itemGroup.getSelectItems().length;
            }
            size++;
        }
        return size;
    }

    private String getLabelStyleClass(
            HtmlSelectManyListbox htmlSelectManyListbox, boolean disabled) {
        if (disabled) {
            return htmlSelectManyListbox.getDisabledClass();
        } else {
            return htmlSelectManyListbox.getEnabledClass();
        }
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlSelectManyListbox(context, (HtmlSelectManyListbox) component);
    }

    protected void decodeHtmlSelectManyListbox(FacesContext context,
            HtmlSelectManyListbox htmlSelectManyCheckbox) {
        DecodeUtil.decodeMany(context, htmlSelectManyCheckbox);
    }

}
