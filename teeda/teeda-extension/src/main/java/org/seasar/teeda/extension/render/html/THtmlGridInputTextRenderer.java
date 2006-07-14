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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.AbstractHtmlRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.component.html.THtmlGridInputText;

/**
 * @author manhole
 */
public class THtmlGridInputTextRenderer extends AbstractHtmlRenderer {

    public static final String COMPONENT_FAMILY = THtmlGridInputText.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlGridInputText.DEFAULT_RENDERER_TYPE;

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGridInputTextEnd(context, (THtmlGridInputText) component);
    }

    protected void encodeHtmlGridInputTextEnd(FacesContext context,
            THtmlGridInputText gridInputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.SPAN_ELEM, gridInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.ONCLICK_ATTR,
                "editOn(this);");
        final String value = ValueHolderUtil.getValueForRender(context,
                gridInputText);
        writer.writeText(value, null);
        // TODO
        //            if (gridInputText.isEscape()) {
        //                writer.writeText(value, null);
        //            } else {
        //                writer.write(value);
        //            }
        writer.endElement(JsfConstants.SPAN_ELEM);

        writer.startElement(JsfConstants.INPUT_ELEM, gridInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, gridInputText,
                getIdForRender(context, gridInputText));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                gridInputText.getClientId(context));
        RendererUtil.renderAttribute(writer, JsfConstants.ONBLUR_ATTR,
                "editOff(this);");
        RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                "teeda_editable");
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
                "display:none;");
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        renderAttributes(gridInputText, writer);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }
}