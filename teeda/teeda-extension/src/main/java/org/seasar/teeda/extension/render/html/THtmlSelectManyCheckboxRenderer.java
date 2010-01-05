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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlSelectManyCheckboxRenderer;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlSelectManyCheckbox;

/**
 * @author koichik
 */
public class THtmlSelectManyCheckboxRenderer extends
        HtmlSelectManyCheckboxRenderer {

    public static final String COMPONENT_FAMILY = THtmlSelectManyCheckbox.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlSelectManyCheckbox.DEFAULT_RENDERER_TYPE;

    {
        addIgnoreAttributeName(ExtensionConstants.COL_ATTR);
    }

    protected void renderSelectItems(final FacesContext context,
            final UIComponent component, final ResponseWriter writer,
            final Iterator it, final String[] selectedValues,
            final boolean pageDirectionLayout, final boolean noneLayout)
            throws IOException {
        if (noneLayout) {
            super.renderSelectItems(context, component, writer, it,
                    selectedValues, pageDirectionLayout, noneLayout);
        }

        final THtmlSelectManyCheckbox htmlSelectManyCheckbox = (THtmlSelectManyCheckbox) component;
        final Integer col = htmlSelectManyCheckbox.getCol();
        final int colCount = (col != null) ? col.intValue() : 0;
        int i = 1;
        while (it.hasNext()) {
            final SelectItem selectItem = (SelectItem) it.next();
            if (pageDirectionLayout) {
                writer.startElement(JsfConstants.TR_ELEM,
                        htmlSelectManyCheckbox);
            } else if (colCount > 0 && i > colCount) {
                writer.endElement(JsfConstants.TR_ELEM);
                writer.startElement(JsfConstants.TR_ELEM,
                        htmlSelectManyCheckbox);
                i = 1;
            }
            writer.startElement(JsfConstants.TD_ELEM, htmlSelectManyCheckbox);
            if (selectItem instanceof SelectItemGroup) {
                renderSelectItemGroup(context, htmlSelectManyCheckbox, writer,
                        selectedValues, selectItem, pageDirectionLayout,
                        noneLayout);
            } else {
                renderSelectItem(context, htmlSelectManyCheckbox, writer,
                        selectedValues, selectItem);
            }
            writer.endElement(JsfConstants.TD_ELEM);
            if (pageDirectionLayout) {
                writer.endElement(JsfConstants.TR_ELEM);
            }
            ++i;
        }
    }

}
