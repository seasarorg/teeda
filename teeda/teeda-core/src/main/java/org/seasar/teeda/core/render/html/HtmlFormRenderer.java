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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlFormRenderer extends AbstractHtmlRenderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlFormBegin(context, (HtmlForm) component);
    }

    protected void encodeHtmlFormBegin(FacesContext context, HtmlForm htmlForm)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.FORM_ELEM, htmlForm);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlForm,
                getIdForRender(context, htmlForm));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, htmlForm
                .getClientId(context));
        RendererUtil.renderAttribute(writer, JsfConstants.METHOD_ATTR,
                JsfConstants.POST_VALUE);
        RendererUtil.renderAttributes(writer, htmlForm,
                JsfConstants.FORM_PASSTHROUGH_ATTRIBUTES);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlFormEnd(context, (HtmlForm) component);
    }

    protected void encodeHtmlFormEnd(FacesContext context, HtmlForm htmlForm)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        encodeFormSubmitMarker(context, htmlForm, writer);
        writer.endElement(JsfConstants.FORM_ELEM);
    }

    private void encodeFormSubmitMarker(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, htmlForm);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        String clientId = htmlForm.getClientId(context);
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, clientId);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, clientId);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlForm(context, (HtmlForm) component);
    }

    protected void decodeHtmlForm(FacesContext context, HtmlForm htmlForm) {
        Map reqParam = context.getExternalContext().getRequestParameterMap();
        String clientId = htmlForm.getClientId(context);
        if (reqParam.containsKey(clientId)) {
            htmlForm.setSubmitted(true);
        } else {
            htmlForm.setSubmitted(false);
        }
    }

}
