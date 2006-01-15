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
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlOutputLabelRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        renderHtmlOutputLabelBegin(context, (HtmlOutputLabel) component);
    }

    protected void renderHtmlOutputLabelBegin(FacesContext context,
            HtmlOutputLabel htmlOutputLabel) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.LABEL_ATTR, htmlOutputLabel);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputLabel,
                htmlOutputLabel.getId());
        String forAttr = htmlOutputLabel.getFor();
        if (forAttr != null) {
            RendererUtil.renderAttribute(writer, JsfConstants.FOR_ATTR,
                    forAttr, null);
        }

        RendererUtil.renderAttributes(writer, htmlOutputLabel,
                JsfConstants.LABEL_PASSTHROUGH_ATTRIBUTES);
        String value = ValueHolderUtil.getValueForRender(context,
                htmlOutputLabel);
        writer.writeText(value, null);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        renderHtmlOutputLabelEnd(context, (HtmlOutputLabel) component);
    }

    protected void renderHtmlOutputLabelEnd(FacesContext context,
            HtmlOutputLabel htmlOutputLabel) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.LABEL_ATTR);
    }

}
