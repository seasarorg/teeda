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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlMessagesRenderer extends AbstractHtmlMessagesRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlMessagesEnd(context, (HtmlMessages) component);
    }

    protected void encodeHtmlMessagesEnd(FacesContext context,
            HtmlMessages htmlMessages) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        Iterator it;
        if (htmlMessages.isGlobalOnly()) {
            it = context.getMessages(null);
        } else {
            it = context.getMessages();
        }
        if (!it.hasNext()) {
            return;
        }
        final boolean tableLayout;
        if (JsfConstants.TABLE_VALUE.equals(htmlMessages.getLayout())) {
            tableLayout = true;
        } else {
            tableLayout = false;
        }

        if (tableLayout) {
            writer.startElement(JsfConstants.TABLE_ELEM, htmlMessages);
        } else {
            writer.startElement(JsfConstants.UL_ELEM, htmlMessages);
        }
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlMessages,
                getIdForRender(context, htmlMessages));
        while (it.hasNext()) {
            if (tableLayout) {
                writer.startElement(JsfConstants.TR_ELEM, htmlMessages);
                writer.startElement(JsfConstants.TD_ELEM, htmlMessages);
            } else {
                writer.startElement(JsfConstants.LI_ELEM, htmlMessages);
            }
            FacesMessage facesMassage = (FacesMessage) it.next();
            renderOneMessage(context, htmlMessages, facesMassage, null);
            if (tableLayout) {
                writer.endElement(JsfConstants.TD_ELEM);
                writer.endElement(JsfConstants.TR_ELEM);
            } else {
                writer.endElement(JsfConstants.LI_ELEM);
            }
        }
        if (tableLayout) {
            writer.endElement(JsfConstants.TABLE_ELEM);
        } else {
            writer.endElement(JsfConstants.UL_ELEM);
        }
    }

    protected boolean isTooltip(UIComponent component) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        return htmlMessages.isTooltip();
    }

    protected boolean isShowDetail(UIComponent component) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        return htmlMessages.isShowDetail();
    }

    protected boolean isShowSummary(UIComponent component) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        return htmlMessages.isShowSummary();
    }

    protected String getTitle(UIComponent component) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        return htmlMessages.getTitle();
    }

    protected String getStyleClass(UIComponent component, Severity severity) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        String styleClass = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            styleClass = htmlMessages.getInfoClass();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            styleClass = htmlMessages.getWarnClass();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            styleClass = htmlMessages.getErrorClass();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            styleClass = htmlMessages.getFatalClass();
        }
        if (styleClass == null) {
            styleClass = htmlMessages.getStyleClass();
        }
        return styleClass;
    }

    protected String getStyle(UIComponent component, Severity severity) {
        HtmlMessages htmlMessages = (HtmlMessages) component;
        String style = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            style = htmlMessages.getInfoStyle();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            style = htmlMessages.getWarnStyle();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            style = htmlMessages.getErrorStyle();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            style = htmlMessages.getFatalStyle();
        }
        if (style == null) {
            style = htmlMessages.getStyle();
        }
        return style;
    }

}
