/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputFormat;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlOutputFormatRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "javax.faces.Format";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.ESCAPE_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputFormatEnd(context, (HtmlOutputFormat) component);
    }

    protected void encodeHtmlOutputFormatEnd(FacesContext context,
            HtmlOutputFormat htmlOutputFormat) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (containsAttributeForRender(htmlOutputFormat, ignoreComponent)) {
            writer.startElement(JsfConstants.SPAN_ELEM, htmlOutputFormat);
            startSpan = true;
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputFormat,
                    getIdForRender(context, htmlOutputFormat));
            renderRemainAttributes(htmlOutputFormat, writer, ignoreComponent);
        }
        String value = getFormattedValue(context, htmlOutputFormat);
        if (htmlOutputFormat.isEscape()) {
            writer.writeText(value, null);
        } else {
            writer.write(value);
        }
        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    protected String getFormattedValue(FacesContext context,
            HtmlOutputFormat htmlOutputFormat) {
        List args = new ArrayList();
        for (Iterator it = htmlOutputFormat.getChildren().iterator(); it
                .hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                UIParameter parameter = (UIParameter) child;
                args.add(parameter.getValue());
            }
        }
        String pattern = ValueHolderUtil.getValueForRender(context,
                htmlOutputFormat);
        MessageFormat format = new MessageFormat(pattern, getLocale(context));
        String value = format.format((Object[]) args.toArray(new Object[args
                .size()]));
        return value;
    }

    protected Locale getLocale(FacesContext context) {
        Locale locale = context.getViewRoot().getLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

}
