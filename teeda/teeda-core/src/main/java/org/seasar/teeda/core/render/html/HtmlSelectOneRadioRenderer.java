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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DecodeUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 * @author shot
 */
public class HtmlSelectOneRadioRenderer extends HtmlSelectManyCheckboxRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";

    public static final String RENDERER_TYPE = "javax.faces.Radio";

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
        RendererUtil
                .renderAttributes(
                        writer,
                        htmlSelectManyCheckbox,
                        JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE);
        if (isChecked(selectedValues, value.toString())) {
            renderCheckedAttribute(writer);
        }
        if (disabled) {
            RendererUtil.renderDisabledAttribute(writer);
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
        DecodeUtil.decode(context, component);
    }

}
