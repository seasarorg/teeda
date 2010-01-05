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

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.model.SelectItem;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 * @author shot
 */
public class HtmlSelectOneRadioRenderer extends HtmlSelectManyCheckboxRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";

    public static final String RENDERER_TYPE = "javax.faces.Radio";

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
    }

    protected void renderInputElement(FacesContext context,
            UIComponent htmlSelectManyCheckbox, ResponseWriter writer,
            String[] selectedValues, final SelectItem selectItem,
            final boolean disabled) throws IOException {

        writer.startElement(JsfConstants.INPUT_ELEM, htmlSelectManyCheckbox);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.RADIO_VALUE);
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

    protected String[] getValuesForRender(FacesContext context,
            UIComponent component) {
        String value = ValueHolderUtil.getValueForRender(context, component);
        // value is not null
        return new String[] { value };
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        getDecoder().decode(context, component);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        return RendererUtil.getConvertedUIOutputValue(context,
                (UIOutput) component, submittedValue);
    }

    public void addIgnoreAttributeName(final String name) {
        ignoreComponent.addAttributeName(name);
    }

}
