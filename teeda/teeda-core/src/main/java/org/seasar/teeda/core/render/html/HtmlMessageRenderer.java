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

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlMessageRenderer extends AbstractHtmlMessagesRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlMessageEnd(context, (HtmlMessage) component);
    }

    protected void encodeHtmlMessageEnd(FacesContext context,
            HtmlMessage htmlMessage) throws IOException {
        String forAttr = htmlMessage.getFor();
        if (forAttr == null) {
            throw new FacesException("for");
        }
        UIComponent forComponent = htmlMessage.findComponent(forAttr);
        if (forComponent == null) {
            throw new FacesException("forComponent");
        }

        final String clientId = forComponent.getClientId(context);

        Iterator it = context.getMessages(clientId);
        if (!it.hasNext()) {
            return;
        }
        FacesMessage facesMassage = (FacesMessage) it.next();
        String idForRender = null;
        if (RendererUtil.shouldRenderIdAttribute(htmlMessage)) {
            idForRender = getIdForRender(context, htmlMessage);
        }
        renderOneMessage(context, htmlMessage, facesMassage, idForRender);
    }

    protected boolean isTooltip(UIComponent component) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        return htmlMessage.isTooltip();
    }

    protected boolean isShowDetail(UIComponent component) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        return htmlMessage.isShowDetail();
    }

    protected boolean isShowSummary(UIComponent component) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        return htmlMessage.isShowSummary();
    }

    protected String getTitle(UIComponent component) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        return htmlMessage.getTitle();
    }

    protected String getStyleClass(UIComponent component, Severity severity) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        String styleClass = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            styleClass = htmlMessage.getInfoClass();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            styleClass = htmlMessage.getWarnClass();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            styleClass = htmlMessage.getErrorClass();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            styleClass = htmlMessage.getFatalClass();
        }
        if (styleClass == null) {
            styleClass = htmlMessage.getStyleClass();
        }
        return styleClass;
    }

    protected String getStyle(UIComponent component, Severity severity) {
        HtmlMessage htmlMessage = (HtmlMessage) component;
        String style = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            style = htmlMessage.getInfoStyle();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            style = htmlMessage.getWarnStyle();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            style = htmlMessage.getErrorStyle();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            style = htmlMessage.getFatalStyle();
        }
        if (style == null) {
            style = htmlMessage.getStyle();
        }
        return style;
    }

}
