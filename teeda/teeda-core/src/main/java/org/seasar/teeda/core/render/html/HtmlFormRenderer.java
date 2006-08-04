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

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.HtmlFormRendererUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 * @author shot
 */
public class HtmlFormRenderer extends AbstractRenderer {

    //TODO need to avoid hidden parameter that is not used by commandlink.
    public static final String COMPONENT_FAMILY = "javax.faces.Form";

    public static final String RENDERER_TYPE = "javax.faces.Form";

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
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.FORM_ELEM, htmlForm);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlForm,
                getIdForRender(context, htmlForm));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, htmlForm
                .getClientId(context));
        RendererUtil.renderAttribute(writer, JsfConstants.METHOD_ATTR,
                JsfConstants.POST_VALUE);
        renderAttributes(htmlForm, writer);

        // action attribute
        final ViewHandler viewHandler = FacesContextUtil
                .getViewHandler(context);
        final String viewId = context.getViewRoot().getViewId();
        final String url = viewHandler.getActionURL(context, viewId);
        if (url != null) {
            writer.writeURIAttribute(JsfConstants.ACTION_ATTR, context
                    .getExternalContext().encodeActionURL(url), null);
        }
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
        context.getApplication().getViewHandler().writeState(context);
        ResponseWriter writer = context.getResponseWriter();
        renderFormSubmitMarker(context, htmlForm, writer);
        renderForCommandLink(context, htmlForm, writer);
        writer.endElement(JsfConstants.FORM_ELEM);
    }

    protected void renderForCommandLink(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        final Map hiddenParameters = getHiddenParameters(htmlForm);
        final StringBuffer sb = new StringBuffer(100);
        for (final Iterator it = hiddenParameters.entrySet().iterator(); it
                .hasNext();) {
            final Map.Entry entry = (Entry) it.next();
            final String name = (String) entry.getKey();
            final Object value = entry.getValue();
            renderHidden(htmlForm, writer, name, value);
            if (sb.length() == 0) {
                sb.append("var f = document.forms['"
                        + getIdForRender(context, htmlForm) + "'];");
            }
            sb.append(" f['" + name + "'].value = '" + String.valueOf(value)
                    + "';");
        }
        final String body = new String(sb);
        renderJavaScriptForCommandLink(writer, body);
        htmlForm.getAttributes().remove(HIDDEN_PARAMETER_KEY);
    }

    protected void renderJavaScriptForCommandLink(ResponseWriter writer,
            String scirptBody) throws IOException {
        if (StringUtil.isEmpty(scirptBody)) {
            return;
        }
        StringBuffer buf = new StringBuffer(512);
        buf.append("function addEventForCommandLink(obj, eventName, fn){");
        buf
                .append("var prev = obj[eventName]; obj[eventName] = prev ? function() { fn() ; prev() } : fn;}");
        buf.append("addEventForCommandLink( window, \'onload\', function() {");
        buf.append(scirptBody);
        buf.append("});");
        renderJavaScriptElement(writer, buf.toString());
    }

    public static void setHiddenParameter(UIForm form, String key, Object value) {
        Map map = getHiddenParameters(form);
        map.put(key, value);
    }

    public static Map getHiddenParameters(UIForm form) {
        Map attributes = form.getAttributes();
        Map map = (Map) attributes.get(HIDDEN_PARAMETER_KEY);
        if (map == null) {
            map = new LinkedHashMap();
            attributes.put(HIDDEN_PARAMETER_KEY, map);
        }
        return map;
    }

    public static void clearHiddenParameters(UIForm form, String key) {
        Map map = getHiddenParameters(form);
        map.remove(key);
    }

    private void renderFormSubmitMarker(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        final String clientId = htmlForm.getClientId(context);
        final String key = getFormSubmitKey(context, htmlForm);
        renderHidden(htmlForm, writer, key, clientId);
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

    private void renderHidden(HtmlForm htmlForm, ResponseWriter writer,
            String name, Object value) throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, htmlForm);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, name);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    private String getFormSubmitKey(FacesContext context, HtmlForm htmlForm) {
        return HtmlFormRendererUtil.getFormSubmitKey(context, htmlForm);
    }

}
