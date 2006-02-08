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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.ArrayIterator;
import javax.faces.internal.SelectItemGroupsIterator;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlSelectManyCheckboxRenderer extends AbstractHtmlRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlSelectManyCheckboxEnd(context,
                (HtmlSelectManyCheckbox) component);
    }

    protected void encodeHtmlSelectManyCheckboxEnd(FacesContext context,
            HtmlSelectManyCheckbox htmlSelectManyCheckbox) throws IOException {
        Iterator it = new SelectItemGroupsIterator(htmlSelectManyCheckbox);
        if (!it.hasNext()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TABLE_ELEM, htmlSelectManyCheckbox);
        RendererUtil.renderIdAttributeIfNecessary(writer,
                htmlSelectManyCheckbox, getIdForRender(context,
                        htmlSelectManyCheckbox));
        RendererUtil.renderAttributes(writer, htmlSelectManyCheckbox,
                JsfConstants.SELECT_TABLE_PASSTHROUGH_ATTRIBUTES);

        Object[] selectedValues = getSelectedValues(htmlSelectManyCheckbox);

        final boolean pageDirectionLayout = isPageDirectionLayout(htmlSelectManyCheckbox);
        if (!pageDirectionLayout) {
            writer.startElement(JsfConstants.TR_ELEM, htmlSelectManyCheckbox);
        }
        renderSelectItems(context, htmlSelectManyCheckbox, writer, it,
                selectedValues, pageDirectionLayout);
        if (!pageDirectionLayout) {
            writer.endElement(JsfConstants.TR_ELEM);
        }
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    protected void renderSelectItems(FacesContext context,
            HtmlSelectManyCheckbox htmlSelectManyCheckbox,
            ResponseWriter writer, Iterator it, Object[] selectedValues,
            final boolean pageDirectionLayout) throws IOException {

        while (it.hasNext()) {
            final SelectItem selectItem = (SelectItem) it.next();

            if (pageDirectionLayout) {
                writer.startElement(JsfConstants.TR_ELEM,
                        htmlSelectManyCheckbox);
            }
            writer.startElement(JsfConstants.TD_ELEM, htmlSelectManyCheckbox);
            if (selectItem instanceof SelectItemGroup) {
                SelectItemGroup selectItemGroup = (SelectItemGroup) selectItem;
                SelectItem[] selectItems = selectItemGroup.getSelectItems();
                Iterator selectItemsIt = new ArrayIterator(selectItems);
                writer.startElement(JsfConstants.TABLE_ELEM,
                        htmlSelectManyCheckbox);
                if (!pageDirectionLayout) {
                    writer.startElement(JsfConstants.TR_ELEM,
                            htmlSelectManyCheckbox);
                }
                renderSelectItems(context, htmlSelectManyCheckbox, writer,
                        selectItemsIt, selectedValues, pageDirectionLayout);
                if (!pageDirectionLayout) {
                    writer.endElement(JsfConstants.TR_ELEM);
                }
                writer.endElement(JsfConstants.TABLE_ELEM);
            } else {
                writer.startElement(JsfConstants.LABEL_ELEM,
                        htmlSelectManyCheckbox);
                final boolean disabled = htmlSelectManyCheckbox.isDisabled()
                        || selectItem.isDisabled();
                final String labelClass = getLabelStyleClass(
                        htmlSelectManyCheckbox, disabled);
                if (labelClass != null) {
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.CLASS_ATTR, labelClass);
                }

                writer.startElement(JsfConstants.INPUT_ELEM,
                        htmlSelectManyCheckbox);
                RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                        JsfConstants.CHECKBOX_VALUE);
                RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                        htmlSelectManyCheckbox.getClientId(context));
                final Object value = selectItem.getValue();
                RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                        value);
                RendererUtil
                        .renderAttributes(
                                writer,
                                htmlSelectManyCheckbox,
                                JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE);

                if (isChecked(selectedValues, value)) {
                    RendererUtil.renderCheckedAttribute(writer);
                }
                if (disabled) {
                    RendererUtil.renderDisabledAttribute(writer);
                }
                writer.endElement(JsfConstants.INPUT_ELEM);

                final String label = selectItem.getLabel();
                if (!StringUtil.isEmpty(label)) {
                    writer.writeText(label, null);
                }
                writer.endElement(JsfConstants.LABEL_ELEM);
            }
            writer.endElement(JsfConstants.TD_ELEM);
            if (pageDirectionLayout) {
                writer.endElement(JsfConstants.TR_ELEM);
            }
        }
    }

    private String getLabelStyleClass(
            HtmlSelectManyCheckbox htmlSelectManyCheckbox, boolean disabled) {
        if (disabled) {
            return htmlSelectManyCheckbox.getDisabledClass();
        } else {
            return htmlSelectManyCheckbox.getEnabledClass();
        }
    }

    private boolean isPageDirectionLayout(
            HtmlSelectManyCheckbox htmlSelectManyCheckbox) {
        return JsfConstants.PAGE_DIRECTION_ATTR.equals(htmlSelectManyCheckbox
                .getLayout());
    }

    private boolean isChecked(Object[] selectedValues, final Object value) {
        for (int i = 0; i < selectedValues.length; i++) {
            Object object = selectedValues[i];
            if (value.equals(object)) {
                return true;
            }
        }
        return false;
    }

    private Object[] getSelectedValues(
            HtmlSelectManyCheckbox htmlSelectManyCheckbox) {
        Object[] v = htmlSelectManyCheckbox.getSelectedValues();
        if (v == null) {
            return new Object[0];
        }
        return v;
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlSelectManyCheckbox(context,
                (HtmlSelectManyCheckbox) component);
    }

    protected void decodeHtmlSelectManyCheckbox(FacesContext context,
            HtmlSelectManyCheckbox htmlSelectManyCheckbox) {
        Map reqParam = context.getExternalContext()
                .getRequestParameterValuesMap();
        String clientId = htmlSelectManyCheckbox.getClientId(context);
        String[] value = null;
        if (reqParam.containsKey(clientId)) {
            value = (String[]) reqParam.get(clientId);
        }
        if (value != null) {
            htmlSelectManyCheckbox.setSubmittedValue(value);
        } else {
            htmlSelectManyCheckbox.setSubmittedValue(new String[0]);
        }
    }

}
