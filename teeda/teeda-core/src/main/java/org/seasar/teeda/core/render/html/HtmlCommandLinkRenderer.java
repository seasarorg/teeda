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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.exception.TagNotFoundRuntimeException;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.HtmlFormRendererUtil;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 * @author shot
 */
public class HtmlCommandLinkRenderer extends AbstractHtmlRenderer {

    private static final String HIDDEN_FIELD_NAME_SUFFIX = "__link_clicked__";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlCommandLinkBegin(context, (HtmlCommandLink) component);
    }

    protected void encodeHtmlCommandLinkBegin(FacesContext context,
            HtmlCommandLink commandLink) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.ANCHOR_ELEM, commandLink);
        RendererUtil.renderIdAttributeIfNecessary(writer, commandLink,
                getIdForRender(context, commandLink));

        if (JavaScriptPermissionUtil.isJavaScriptPermitted(context)) {
            encodeHtmlCommandLinkWithJavaScript(context, writer, commandLink);
        } else {
            encodeHtmlCommandLinkWithoutJavaScript(context, writer, commandLink);
        }
    }

    protected void encodeHtmlCommandLinkWithJavaScript(FacesContext context,
            ResponseWriter writer, HtmlCommandLink commandLink)
            throws IOException {
        UIForm parentForm = findParentForm(commandLink);
        final String formId = getIdForRender(context, parentForm);

        RendererUtil.renderAttribute(writer, JsfConstants.HREF_ATTR, "#");

        StringBuffer sb = new StringBuffer();
        sb.append("var f = document.forms['");
        sb.append(formId);
        sb.append("'];");

        sb.append(" f['");
        final String hiddenFieldName = getHiddenFieldName(formId);
        sb.append(hiddenFieldName);
        sb.append("'].value = '");
        sb.append(commandLink.getClientId(context));
        sb.append("';");

        HtmlFormRenderer.setHiddenParameter(parentForm, hiddenFieldName, null);

        for (Iterator it = commandLink.getChildren().iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                UIParameter p = ((UIParameter) child);
                String name = p.getName();
                Object value = p.getValue();
                sb.append(" f['" + name + "'].value = '");
                sb.append(value);
                sb.append("';");
                HtmlFormRenderer.setHiddenParameter(parentForm, name, null);
            }
        }

        sb.append(" if (f.onsubmit) { f.onsubmit(); }");
        sb.append(" f.submit();");
        sb.append(" return false;");

        RendererUtil.renderAttribute(writer, JsfConstants.ONCLICK_ATTR, sb
                .toString());

        RendererUtil.renderAttributes(writer, commandLink,
                JsfConstants.ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_ONCLICK);

        Object value = commandLink.getValue();
        if (value != null) {
            writer.writeText(value.toString(), JsfConstants.VALUE_ATTR);
        }
    }

    protected void encodeHtmlCommandLinkWithoutJavaScript(FacesContext context,
            ResponseWriter writer, HtmlCommandLink commandLink)
            throws IOException {
        ViewHandler viewHandler = FacesContextUtil.getViewHandler(context);
        String viewId = context.getViewRoot().getViewId();
        String path = viewHandler.getActionURL(context, viewId);
        StringBuffer hrefBuf = new StringBuffer(100);
        hrefBuf.append(path);
        if (path.indexOf("?") == -1) {
            hrefBuf.append("?");
        } else {
            hrefBuf.append("&");
        }
        UIForm parentForm = findParentForm(commandLink);

        final String formSubmitKey = HtmlFormRendererUtil.getFormSubmitKey(
                context, parentForm);
        hrefBuf.append(formSubmitKey);
        hrefBuf.append("=");
        hrefBuf.append(formSubmitKey);
        hrefBuf.append("&");

        final String formId = getIdForRender(context, parentForm);
        final String hiddenFieldName = getHiddenFieldName(formId);
        hrefBuf.append(hiddenFieldName);
        hrefBuf.append("=");
        hrefBuf.append(commandLink.getClientId(context));
        if (commandLink.getChildCount() > 0) {
            addChildParametersToHref(commandLink, hrefBuf, writer
                    .getCharacterEncoding());
        }

        String href = ExternalContextUtil.encodeActionURL(context, hrefBuf
                .toString());
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, href, null);
        Object value = commandLink.getValue();
        if (value != null) {
            writer.writeText(value.toString(), JsfConstants.VALUE_ATTR);
        }
    }

    private void addChildParametersToHref(UIComponent linkComponent,
            StringBuffer hrefBuf, String charEncoding) throws IOException {
        for (Iterator it = linkComponent.getChildren().iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                UIParameter param = (UIParameter) child;
                String name = param.getName();
                Object value = param.getValue();
                addParameterToHref(name, value, hrefBuf, charEncoding);
            }
        }
    }

    private static void addParameterToHref(String name, Object value,
            StringBuffer hrefBuf, String charEncoding)
            throws UnsupportedEncodingException {
        if (name == null) {
            throw new IllegalArgumentException(
                    "Unnamed parameter value not allowed within command link.");
        }
        hrefBuf.append("&");
        hrefBuf.append(URLEncoder.encode(name, charEncoding));
        hrefBuf.append("=");
        if (value != null) {
            hrefBuf.append(URLEncoder.encode(value.toString(), charEncoding));
        }
    }

    private String getHiddenFieldName(final String formId) {
        return formId + NamingContainer.SEPARATOR_CHAR
                + HIDDEN_FIELD_NAME_SUFFIX;
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputLinkEnd(context, (HtmlCommandLink) component);
    }

    protected void encodeHtmlOutputLinkEnd(FacesContext context,
            HtmlCommandLink commandLink) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.ANCHOR_ELEM);
    }

    private UIForm findParentForm(UIComponent component) {
        UIComponent parent = component.getParent();
        while (parent != null && !(parent instanceof UIForm)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            throw new TagNotFoundRuntimeException("form");
        }
        return (UIForm) parent;
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlCommandLink(context, (HtmlCommandLink) component);
    }

    protected void decodeHtmlCommandLink(FacesContext context,
            HtmlCommandLink commandLink) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String clientId = commandLink.getClientId(context);

        UIForm parentForm = findParentForm(commandLink);
        String formId = getIdForRender(context, parentForm);
        String hiddenFieldName = getHiddenFieldName(formId);
        String entry = (String) paramMap.get(hiddenFieldName);
        if (clientId.equals(entry)) {
            commandLink.queueEvent(new ActionEvent(commandLink));
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

}
