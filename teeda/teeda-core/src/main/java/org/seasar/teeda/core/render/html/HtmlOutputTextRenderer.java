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
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.UIComponentUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlOutputTextRenderer extends Renderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        encodeHtmlOutputText(context, (HtmlOutputText) component);
    }

    protected void encodeHtmlOutputText(FacesContext context,
            HtmlOutputText htmlOutputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (UIComponentUtil.containsAttributes(htmlOutputText,
                JsfConstants.ID_WITH_COMMON_PASSTROUGH_ATTRIBUTES)) {
            writer.startElement(JsfConstants.SPAN_ELEM, htmlOutputText);
            startSpan = true;
        }
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputText,
                htmlOutputText.getId());
        RendererUtil.renderAttributes(writer, htmlOutputText,
                JsfConstants.COMMON_PASSTROUGH_ATTRIBUTES);
        String value = ValueHolderUtil.getValueForRender(context,
                htmlOutputText);
        if (htmlOutputText.isEscape()) {
            writer.writeText(value, null);
        } else {
            writer.write(value);
        }
        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

}
