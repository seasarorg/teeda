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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlFormRenderer extends AbstractHtmlRenderer {

    private static final String HIDDEN_PARAMETER_KEY = HtmlFormRenderer.class
            .getName()
            + ".HIDDEN_PARAMETER_KEY";

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
        context.getApplication().getViewHandler().writeState(context);
        encodeHtmlFormEnd(context, (HtmlForm) component);
    }

    protected void encodeHtmlFormEnd(FacesContext context, HtmlForm htmlForm)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        renderFormSubmitMarker(context, htmlForm, writer);
        renderForCommandLink(context, htmlForm, writer);
        writer.endElement(JsfConstants.FORM_ELEM);
    }

    protected void renderForCommandLink(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        for (Iterator it = getHiddenParameters(htmlForm).entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Entry) it.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            renderHidden(htmlForm, writer, name, value);
        }
        htmlForm.getAttributes().remove(HIDDEN_PARAMETER_KEY);
    }

    private void renderHidden(HtmlForm htmlForm, ResponseWriter writer,
            String name, Object value) throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, htmlForm);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, name);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    public static void setHiddenParameter(UIForm form, String name, Object value) {
        Map map = getHiddenParameters(form);
        map.put(name, value);
    }

    private static Map getHiddenParameters(UIForm form) {
        Map attributes = form.getAttributes();
        Map map = (Map) attributes.get(HIDDEN_PARAMETER_KEY);
        if (map == null) {
            map = new LinkedHashMap();
            attributes.put(HIDDEN_PARAMETER_KEY, map);
        }
        return map;
    }

    private void renderFormSubmitMarker(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        String clientId = htmlForm.getClientId(context);
        String key = getFormSubmitKey(context, htmlForm);
        String value = clientId;
        renderHidden(htmlForm, writer, key, value);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlForm(context, (HtmlForm) component);
    }

    protected void decodeHtmlForm(FacesContext context, HtmlForm htmlForm) {
        Map reqParam = context.getExternalContext().getRequestParameterMap();
        String key = getFormSubmitKey(context, htmlForm);
        if (reqParam.containsKey(key)) {
            htmlForm.setSubmitted(true);
        } else {
            htmlForm.setSubmitted(false);
        }
    }

    protected String getFormSubmitKey(FacesContext context, HtmlForm htmlForm) {
        UIViewRoot viewRoot = context.getViewRoot();
        String viewId = viewRoot.getViewId();
        return htmlForm.getClientId(context) + viewId;
    }

}
