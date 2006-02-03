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
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlMessageRenderer extends AbstractHtmlRenderer {

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

        Severity severity = facesMassage.getSeverity();
        final String style = getStyle(htmlMessage, severity);
        final String styleClass = getStyleClass(htmlMessage, severity);
        String title = htmlMessage.getTitle();

        String summary = getSummary(facesMassage);
        String detail = getDetail(facesMassage);
        boolean isWriteSummary = htmlMessage.isShowSummary() && summary != null;
        boolean isWriteDetail = htmlMessage.isShowDetail() && detail != null;
        if (htmlMessage.isTooltip() && isWriteSummary && isWriteDetail) {
            isWriteSummary = false;
            title = summary;
        }

        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (RendererUtil.shouldRenderIdAttribute(htmlMessage) || style != null
                || styleClass != null || title != null) {
            startSpan = true;
            writer.startElement(JsfConstants.SPAN_ELEM, htmlMessage);
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlMessage,
                    getIdForRender(context, htmlMessage));
            RendererUtil
                    .renderAttribute(writer, JsfConstants.TITLE_ATTR, title);
            RendererUtil
                    .renderAttribute(writer, JsfConstants.STYLE_ATTR, style);
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                    styleClass);
        }

        // TODO not escape HTML tags
        if (isWriteSummary) {
            writer.writeText(summary, null);
        }
        if (isWriteDetail) {
            if (isWriteSummary) {
                writer.writeText(" ", detail);
            }
            writer.writeText(detail, null);
        }

        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    private String getStyleClass(HtmlMessage htmlMessage, Severity severity) {
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

    private String getStyle(HtmlMessage htmlMessage, Severity severity) {
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

    private String getDetail(FacesMessage facesMassage) {
        String detail = facesMassage.getDetail();
        return detail;
    }

    private String getSummary(FacesMessage facesMassage) {
        String summary = facesMassage.getSummary();
        return summary;
    }

}
