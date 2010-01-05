/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.internal.SelectItemsIterator;
import javax.faces.internal.UIComponentUtil;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.framework.util.ArrayIterator;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlSelectManyCheckboxRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";

    public static final String RENDERER_TYPE = "javax.faces.Checkbox";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.LAYOUT_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.DISABLED_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.DISABLED_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.ENABLED_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.BORDER_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.STYLE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.STYLE_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.NAME_ATTR);
        ignoreComponent.addAttributeName("selectedValues");
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlSelectManyCheckboxEnd(context, component);
    }

    protected static final String[] TABLE_ATTRIBUTES = new String[] {
            JsfConstants.BORDER_ATTR, JsfConstants.STYLE_ATTR,
            JsfConstants.STYLE_CLASS_ATTR };

    protected void encodeHtmlSelectManyCheckboxEnd(FacesContext context,
            UIComponent htmlSelectManyCheckbox) throws IOException {

        final Iterator it = new SelectItemsIterator(htmlSelectManyCheckbox);
        if (!it.hasNext()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        boolean noneLayout = isNoneLayout(htmlSelectManyCheckbox);
        if (!noneLayout) {
            writer
                    .startElement(JsfConstants.TABLE_ELEM,
                            htmlSelectManyCheckbox);

            RendererUtil.renderIdAttributeIfNecessary(writer,
                    htmlSelectManyCheckbox, getIdForRender(context,
                            htmlSelectManyCheckbox));
            RendererUtil.renderAttributes(writer, htmlSelectManyCheckbox,
                    TABLE_ATTRIBUTES);
        }
        String[] selectedValues = getValuesForRender(context,
                htmlSelectManyCheckbox);

        final boolean pageDirectionLayout = isPageDirectionLayout(htmlSelectManyCheckbox);
        if (!noneLayout) {
            if (!pageDirectionLayout) {
                writer.startElement(JsfConstants.TR_ELEM,
                        htmlSelectManyCheckbox);
            }
        }
        renderSelectItems(context, htmlSelectManyCheckbox, writer, it,
                selectedValues, pageDirectionLayout, noneLayout);
        if (!noneLayout) {
            if (!pageDirectionLayout) {
                writer.endElement(JsfConstants.TR_ELEM);
            }
            writer.endElement(JsfConstants.TABLE_ELEM);
        }
    }

    protected String[] getValuesForRender(FacesContext context,
            UIComponent htmlSelectManyCheckbox) {
        return ValueHolderUtil.getValuesForRender(context,
                htmlSelectManyCheckbox);
    }

    protected void renderSelectItems(FacesContext context,
            UIComponent htmlSelectManyCheckbox, ResponseWriter writer,
            Iterator it, String[] selectedValues,
            final boolean pageDirectionLayout, final boolean noneLayout)
            throws IOException {

        while (it.hasNext()) {
            final SelectItem selectItem = (SelectItem) it.next();

            if (!noneLayout) {
                if (pageDirectionLayout) {
                    writer.startElement(JsfConstants.TR_ELEM,
                            htmlSelectManyCheckbox);
                }
                writer.startElement(JsfConstants.TD_ELEM,
                        htmlSelectManyCheckbox);
            }
            if (selectItem instanceof SelectItemGroup) {
                renderSelectItemGroup(context, htmlSelectManyCheckbox, writer,
                        selectedValues, selectItem, pageDirectionLayout,
                        noneLayout);
            } else {
                renderSelectItem(context, htmlSelectManyCheckbox, writer,
                        selectedValues, selectItem);
            }
            if (!noneLayout) {
                writer.endElement(JsfConstants.TD_ELEM);
                if (pageDirectionLayout) {
                    writer.endElement(JsfConstants.TR_ELEM);
                }
            }
        }
    }

    protected void renderSelectItemGroup(FacesContext context,
            UIComponent htmlSelectManyCheckbox, ResponseWriter writer,
            String[] selectedValues, final SelectItem selectItem,
            final boolean pageDirectionLayout, final boolean noneLayout)
            throws IOException {
        SelectItemGroup selectItemGroup = (SelectItemGroup) selectItem;
        SelectItem[] selectItems = selectItemGroup.getSelectItems();
        Iterator selectItemsIt = new ArrayIterator(selectItems);
        if (!noneLayout) {
            writer
                    .startElement(JsfConstants.TABLE_ELEM,
                            htmlSelectManyCheckbox);
            if (!pageDirectionLayout) {
                writer.startElement(JsfConstants.TR_ELEM,
                        htmlSelectManyCheckbox);
            }
        }
        renderSelectItems(context, htmlSelectManyCheckbox, writer,
                selectItemsIt, selectedValues, pageDirectionLayout, noneLayout);
        if (!noneLayout) {
            if (!pageDirectionLayout) {
                writer.endElement(JsfConstants.TR_ELEM);
            }
            writer.endElement(JsfConstants.TABLE_ELEM);
        }
    }

    protected void renderSelectItem(FacesContext context,
            UIComponent htmlSelectManyCheckbox, ResponseWriter writer,
            String[] selectedValues, final SelectItem selectItem)
            throws IOException {

        writer.startElement(JsfConstants.LABEL_ELEM, htmlSelectManyCheckbox);
        final boolean disabled = UIComponentUtil
                .isDisabled(htmlSelectManyCheckbox) ||
                selectItem.isDisabled();
        final String labelClass = getLabelStyleClass(htmlSelectManyCheckbox,
                disabled);
        if (labelClass != null) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    labelClass);
        }

        renderInputElement(context, htmlSelectManyCheckbox, writer,
                selectedValues, selectItem, disabled);

        final String label = selectItem.getLabel();
        if (!StringUtil.isEmpty(label)) {
            writer.writeText(label, null);
        }
        writer.endElement(JsfConstants.LABEL_ELEM);
    }

    protected void renderInputElement(FacesContext context,
            UIComponent htmlSelectManyCheckbox, ResponseWriter writer,
            String[] selectedValues, final SelectItem selectItem,
            final boolean disabled) throws IOException {

        writer.startElement(JsfConstants.INPUT_ELEM, htmlSelectManyCheckbox);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.CHECKBOX_VALUE);
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlSelectManyCheckbox.getClientId(context));
        final Object value = selectItem.getValue();
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        renderRemainAttributes(htmlSelectManyCheckbox, writer, ignoreComponent);
        if (isChecked(selectedValues, value.toString())) {
            renderCheckedAttribute(writer);
        }
        if (disabled) {
            renderDisabledAttribute(writer);
        }
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected boolean isPageDirectionLayout(UIComponent htmlSelectManyCheckbox) {
        return JsfConstants.PAGE_DIRECTION_VALUE.equals(UIComponentUtil
                .getStringAttribute(htmlSelectManyCheckbox,
                        JsfConstants.LAYOUT_ATTR));
    }

    protected boolean isNoneLayout(UIComponent htmlSelectManyCheckbox) {
        return JsfConstants.NONE_VALUE.equals(UIComponentUtil
                .getStringAttribute(htmlSelectManyCheckbox,
                        JsfConstants.LAYOUT_ATTR));
    }

    protected boolean isChecked(String[] selectedValues, final String value) {
        return ArrayUtil.contains(selectedValues, value);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        getDecoder().decodeMany(context, component);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        return RendererUtil.getConvertedUIOutputValues(context,
                (UIOutput) component, submittedValue);
    }

    public void addIgnoreAttributeName(final String name) {
        ignoreComponent.addAttributeName(name);
    }

}
