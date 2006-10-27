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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ConverterResource;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlOutputText;

/**
 * @author shot
 */
public class THtmlOutputTextRenderer extends HtmlOutputTextRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.OutputText";

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        decodeTHtmlOutputText(context, (THtmlOutputText) component);
    }

    protected void decodeTHtmlOutputText(final FacesContext context,
            final THtmlOutputText text) {
        Object value = null;
        for (Iterator itr = text.getChildren().iterator(); itr.hasNext();) {
            UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                final HtmlInputHidden hidden = (HtmlInputHidden) c;
                final String hiddenId = c.getId();
                if (hiddenId.equals(text.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    hidden.decode(context);
                    value = hidden.getSubmittedValue();
                    final ValueBinding vb = text.getValueBinding("value");
                    if (vb != null) {
                        final String expression = vb.getExpressionString();
                        final Converter converter = ConverterResource
                                .getConverter(expression);
                        hidden.setConverter(converter);
                    }
                    break;
                }
            }
        }
        text.setValue(value);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputTextBegin(context, (THtmlOutputText) component);
    }

    protected void encodeHtmlOutputTextBegin(FacesContext context,
            HtmlOutputText htmlOutputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (containsAttributeForRender(htmlOutputText)) {
            writer.startElement(JsfConstants.SPAN_ELEM, htmlOutputText);
            startSpan = true;
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputText,
                    getIdForRender(context, htmlOutputText));
            renderAttributes(htmlOutputText, writer);
        }
        final String value = ValueHolderUtil.getValueForRender(context,
                htmlOutputText);
        if (htmlOutputText.isEscape()) {
            writer.writeText(value, null);
        } else {
            writer.write(value);
        }
        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
        for (Iterator itr = htmlOutputText.getChildren().iterator(); itr
                .hasNext();) {
            UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                final String hiddenId = c.getId();
                if (hiddenId.equals(htmlOutputText.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    final HtmlInputHidden hidden = (HtmlInputHidden) c;
                    hidden.setSubmittedValue(value);
                    break;
                }
            }
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
    }

    public boolean getRendersChildren() {
        return true;
    }

}
