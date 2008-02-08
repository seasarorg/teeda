/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.internal.WindowIdUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.render.html.support.UrlBuilder;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.HtmlFormRendererUtil;
import org.seasar.teeda.core.util.JavaScriptUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 * @author shot
 * @author yone
 */
public class HtmlFormRenderer extends AbstractRenderer {

    //TODO need to avoid hidden parameter that is not used by commandlink.
    public static final String COMPONENT_FAMILY = "javax.faces.Form";

    public static final String RENDERER_TYPE = "javax.faces.Form";

    private static final String HIDDEN_PARAMETER_KEY = HtmlFormRenderer.class
            .getName()
            + ".HIDDEN_PARAMETER_KEY";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.NAME_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.METHOD_ATTR);
    }

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
        renderRemainAttributes(htmlForm, writer, ignoreComponent);

        // action attribute
        final ViewHandler viewHandler = FacesContextUtil
                .getViewHandler(context);
        final String viewId = context.getViewRoot().getViewId();
        final String url = viewHandler.getActionURL(context, viewId);
        if (url != null) {
            UrlBuilder urlBuilder = new UrlBuilder();
            urlBuilder.setBase(url);
            final String encoding = writer.getCharacterEncoding();
            if (WindowIdUtil.isNewWindowTarget(htmlForm.getTarget())) {
                urlBuilder.add(URLEncoder.encode(WindowIdUtil.NEWWINDOW,
                        encoding), URLEncoder.encode(JsfConstants.TRUE,
                        encoding));
            }
            writer.writeURIAttribute(JsfConstants.ACTION_ATTR, context
                    .getExternalContext().encodeActionURL(urlBuilder.build()),
                    null);
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
        final Map hiddenParameters = getCommandLinkHiddenParameters(htmlForm);
        boolean hasCommandLink = false;
        for (final Iterator it = hiddenParameters.entrySet().iterator(); it
                .hasNext();) {
            hasCommandLink = true;
            final Map.Entry entry = (Entry) it.next();
            final String name = (String) entry.getKey();
            final Object value = entry.getValue();
            RendererUtil.renderHidden(htmlForm, writer, name, value);
        }
        if (hasCommandLink) {
            final String target = htmlForm.getTarget();
            renderClearHiddenCommandFormParamsFunction(writer, getIdForRender(
                    context, htmlForm), hiddenParameters.entrySet(), target);
        }
        htmlForm.getAttributes().remove(HIDDEN_PARAMETER_KEY);
    }

    public static void setCommandLinkHiddenParameter(UIForm form, String key,
            Object value) {
        Map map = getCommandLinkHiddenParameters(form);
        map.put(key, value);
    }

    public static Map getCommandLinkHiddenParameters(UIForm form) {
        Map attributes = form.getAttributes();
        Map map = (Map) attributes.get(HIDDEN_PARAMETER_KEY);
        if (map == null) {
            map = new LinkedHashMap();
            attributes.put(HIDDEN_PARAMETER_KEY, map);
        }
        return map;
    }

    public static void clearCommandLinkHiddenParameters(UIForm form, String key) {
        Map map = getCommandLinkHiddenParameters(form);
        map.remove(key);
    }

    private void renderFormSubmitMarker(FacesContext context,
            HtmlForm htmlForm, ResponseWriter writer) throws IOException {
        final String clientId = htmlForm.getClientId(context);
        final String key = getFormSubmitKey(context, htmlForm);
        RendererUtil.renderHidden(htmlForm, writer, key, clientId);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlForm(context, (HtmlForm) component);
    }

    protected void decodeHtmlForm(FacesContext context, HtmlForm htmlForm) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String key = getFormSubmitKey(context, htmlForm);
        if (paramMap.containsKey(key)) {
            htmlForm.setSubmitted(true);
        } else {
            htmlForm.setSubmitted(false);
        }
    }

    private String getFormSubmitKey(FacesContext context, HtmlForm htmlForm) {
        return HtmlFormRendererUtil.getFormSubmitKey(context, htmlForm);
    }

    protected void renderClearHiddenCommandFormParamsFunction(
            ResponseWriter writer, String formName, Set hiddenFormParams,
            String formTarget) throws IOException {
        String functionName = JavaScriptUtil
                .getClearHiddenCommandFormParamsFunctionName(formName);
        StringBuffer sb = new StringBuffer(512);
        sb.append("function ").append(functionName).append("(){");
        if (hiddenFormParams != null) {
            sb.append("var f = document.forms['").append(formName)
                    .append("'];");
            for (Iterator it = hiddenFormParams.iterator(); it.hasNext();) {
                final Map.Entry entry = (Entry) it.next();
                final String name = (String) entry.getKey();
                sb.append(" f.elements['").append(name).append(
                        "'].value='null';");
            }
        }
        sb.append(" f.target=");
        if (formTarget == null || formTarget.length() == 0) {
            sb.append("'';");
        } else {
            sb.append("'").append(formTarget).append("';");
        }
        sb.append("} ").append(functionName).append("();");
        renderJavaScriptElement(writer, new String(sb));
    }

    public void addIgnoreAttributeName(final String name) {
        ignoreComponent.addAttributeName(name);
    }

}
