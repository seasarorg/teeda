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
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlSelectManyListboxRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";

    public static final String RENDERER_TYPE = "javax.faces.Listbox";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.SIZE_ATTR);
        ignoreComponent.addAttributeName("selectedValues");
        ignoreComponent
                .addAttributeName(JsfConstants.DISABLED_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.ENABLED_CLASS_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlSelectListboxEnd(context, component);
    }

    protected void encodeHtmlSelectListboxEnd(FacesContext context,
            UIComponent component) throws IOException {

        Iterator it = new SelectItemsIterator(component);
        if (!it.hasNext()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.SELECT_ELEM, component);

        RendererUtil.renderIdAttributeIfNecessary(writer, component,
                getIdForRender(context, component));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, component
                .getClientId(context));
        renderMultiple(context, component, writer);
        renderSize(context, component, writer);
        renderRemainAttributes(component, writer, ignoreComponent);
        final String[] selectedValues = getValuesForRender(context, component);
        renderSelectItems(context, component, writer, it, selectedValues);

        writer.endElement(JsfConstants.SELECT_ELEM);
    }

    protected String[] getValuesForRender(FacesContext context,
            UIComponent component) {
        return ValueHolderUtil.getValuesForRender(context, component);
    }

    protected void renderSize(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        final int size = getSize(component);
        RendererUtil.renderAttribute(writer, JsfConstants.SIZE_ATTR,
                new Integer(size));
    }

    private int getSize(UIComponent component) {
        int size = UIComponentUtil.getPrimitiveIntAttribute(component,
                JsfConstants.SIZE_ATTR);
        if (0 < size) {
            return size;
        }
        size = 0;
        for (Iterator it = new SelectItemsIterator(component); it.hasNext();) {
            SelectItem item = (SelectItem) it.next();
            if (item instanceof SelectItemGroup) {
                SelectItemGroup itemGroup = (SelectItemGroup) item;
                size += itemGroup.getSelectItems().length;
            }
            size++;
        }
        return size;
    }

    protected void renderMultiple(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        RendererUtil.renderAttribute(writer, JsfConstants.MULTIPLE_ATTR,
                JsfConstants.MULTIPLE_VALUE);
    }

    protected void renderSelectItems(FacesContext context,
            UIComponent component, ResponseWriter writer, Iterator it,
            String[] selectedValues) throws IOException {

        while (it.hasNext()) {
            final SelectItem selectItem = (SelectItem) it.next();

            if (selectItem instanceof SelectItemGroup) {
                SelectItemGroup selectItemGroup = (SelectItemGroup) selectItem;
                SelectItem[] selectItems = selectItemGroup.getSelectItems();
                Iterator selectItemsIt = new ArrayIterator(selectItems);
                writer.startElement(JsfConstants.OPTGROUP_ELEM, component);
                RendererUtil.renderAttribute(writer, JsfConstants.LABEL_ATTR,
                        selectItemGroup.getLabel());
                // TODO case: optgroup is disabled
                renderSelectItems(context, component, writer, selectItemsIt,
                        selectedValues);
                writer.endElement(JsfConstants.OPTGROUP_ELEM);
            } else {
                writer.startElement(JsfConstants.OPTION_ELEM, component);
                final Object value = selectItem.getValue();
                RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                        value);

                final boolean disabled = UIComponentUtil.isDisabled(component)
                        || selectItem.isDisabled();
                final String labelClass = getLabelStyleClass(component,
                        disabled);
                if (labelClass != null) {
                    RendererUtil.renderAttribute(writer,
                            JsfConstants.CLASS_ATTR, labelClass);
                }
                if (value != null
                        && isSelected(selectedValues, value.toString())) {
                    renderSelectedAttribute(writer);
                }
                if (selectItem.isDisabled()) {
                    renderDisabledAttribute(writer);
                }
                writer.writeText(selectItem.getLabel(), null);
                writer.endElement(JsfConstants.OPTION_ELEM);
            }
        }
    }

    private boolean isSelected(final String[] selectedValues, final String value) {
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
