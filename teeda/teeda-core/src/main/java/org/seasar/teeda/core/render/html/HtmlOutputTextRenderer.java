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

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;
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
public class HtmlOutputTextRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "javax.faces.Text";

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
        //TODO if there is no impact to other code, it's better to merge them
        if (component instanceof HtmlOutputText) {
            encodeHtmlOutputTextEnd(context, (HtmlOutputText) component);
        } else if (component instanceof UIOutput) {
            encodeUIOutputEnd(context, (UIOutput) component);
        }
    }

    protected void encodeHtmlOutputTextEnd(FacesContext context,
            HtmlOutputText htmlOutputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (containsAttributeForRender(htmlOutputText, ignoreComponent)) {
            writer.startElement(JsfConstants.SPAN_ELEM, htmlOutputText);
            startSpan = true;
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputText,
                    getIdForRender(context, htmlOutputText));
            renderRemainAttributes(htmlOutputText, writer, ignoreComponent);
        }
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

    protected void encodeUIOutputEnd(FacesContext context, UIOutput uiOutput)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (containsAttributeForRender(uiOutput, ignoreComponent)) {
            writer.startElement(JsfConstants.SPAN_ELEM, uiOutput);
            startSpan = true;
            RendererUtil.renderIdAttributeIfNecessary(writer, uiOutput,
                    getIdForRender(context, uiOutput));
            renderRemainAttributes(uiOutput, writer, ignoreComponent);
        }
        String value = ValueHolderUtil.getValueForRender(context, uiOutput);
        Boolean b = (Boolean) uiOutput.getAttributes().get(
                JsfConstants.ESCAPE_ATTR);
        if (b != null && b.booleanValue()) {
            writer.writeText(value, null);
        } else {
            writer.write(value);
        }
        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

}
