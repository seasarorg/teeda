/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.SelectItemsIterator;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.framework.util.ArrayIterator;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlSelectOneRadioRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlSelectOneRadio;

/**
 * @author shot
 */
public class THtmlSelectOneRadioRenderer extends HtmlSelectOneRadioRenderer {

    public static final String COMPONENT_FAMILY = THtmlSelectOneRadio.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlSelectOneRadio.DEFAULT_RENDERER_TYPE;

    {
        addIgnoreAttributeName(ExtensionConstants.COL_ATTR);
        addIgnoreAttributeName(ExtensionConstants.PAGE_NAME_ATTR);
        addIgnoreAttributeName(ExtensionConstants.LABEL_NAME_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        if (isPageDirectionLayout(component)) {
            super.encodeEnd(context, component);
            return;
        }
        encodeTHtmlSelectOneRadio(context, component);
    }

    protected void encodeTHtmlSelectOneRadio(FacesContext context,
            UIComponent component) throws IOException {
        final Iterator it = new SelectItemsIterator(component);
        if (!it.hasNext()) {
            return;
        }
        final ResponseWriter writer = context.getResponseWriter();
        boolean noneLayout = isNoneLayout(component);
        if (!noneLayout) {
            writer.startElement(JsfConstants.TABLE_ELEM, component);
            final String id = getIdForRender(context, component);
            RendererUtil.renderIdAttributeIfNecessary(writer, component, id);
            RendererUtil.renderAttributes(writer, component, TABLE_ATTRIBUTES);
            writer.startElement(JsfConstants.TR_ELEM, component);
        }
        final THtmlSelectOneRadio radio = (THtmlSelectOneRadio) component;
        final Integer col = radio.getCol();
        final int colCount = (col != null) ? col.intValue() : 0;
        String[] selectedValues = getValuesForRender(context, radio);
        int i = 1;
        while (it.hasNext()) {
            if (!noneLayout && (col != null && i > colCount)) {
                writer.endElement(JsfConstants.TR_ELEM);
                writer.startElement(JsfConstants.TR_ELEM, component);
                i = 1;
            }
            final SelectItem selectItem = (SelectItem) it.next();
            if (!noneLayout) {
                writer.startElement(JsfConstants.TD_ELEM, radio);
            }
            if (selectItem instanceof SelectItemGroup) {
                SelectItemGroup selectItemGroup = (SelectItemGroup) selectItem;
                SelectItem[] selectItems = selectItemGroup.getSelectItems();
                Iterator selectItemsIt = new ArrayIterator(selectItems);
                if (!noneLayout) {
                    writer.startElement(JsfConstants.TABLE_ELEM, radio);
                }
                renderSelectItems(context, radio, writer, selectItemsIt,
                        selectedValues, false, noneLayout);
                if (!noneLayout) {
                    writer.endElement(JsfConstants.TABLE_ELEM);
                }
            } else {
                renderSelectItem(context, radio, writer, selectedValues,
                        selectItem);
            }
            if (!noneLayout) {
                writer.endElement(JsfConstants.TD_ELEM);
            }
            i++;
        }
        if (!noneLayout) {
            writer.endElement(JsfConstants.TR_ELEM);
            writer.endElement(JsfConstants.TABLE_ELEM);
        }
    }

}
