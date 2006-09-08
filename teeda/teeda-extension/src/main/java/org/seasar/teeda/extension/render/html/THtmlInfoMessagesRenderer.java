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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.AbstractHtmlMessagesRenderer;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author shot
 */
public class THtmlInfoMessagesRenderer extends AbstractHtmlMessagesRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Messages";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.InfoMessages";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeInfoMessagesEnd(context, (HtmlMessages) component);
    }

    protected void encodeInfoMessagesEnd(FacesContext context,
            HtmlMessages messages) throws IOException {
        if (messages.isGlobalOnly()) {
            throw new IllegalStateException(
                    "InfoMessages should not be globalOnly.");
        }
        ResponseWriter writer = context.getResponseWriter();
        Iterator it = context.getMessages();
        if (!it.hasNext()) {
            return;
        }
        final boolean tableLayout = isTableLayout(messages);
        if (tableLayout) {
            writer.startElement(JsfConstants.TABLE_ELEM, messages);
        } else {
            writer.startElement(JsfConstants.UL_ELEM, messages);
        }
        RendererUtil.renderIdAttributeIfNecessary(writer, messages,
                getIdForRender(context, messages));
        while (it.hasNext()) {
            FacesMessage facesMessage = (FacesMessage) it.next();
            if (!facesMessage.getSeverity().equals(FacesMessage.SEVERITY_INFO)) {
                continue;
            }
            if (tableLayout) {
                writer.startElement(JsfConstants.TR_ELEM, messages);
                writer.startElement(JsfConstants.TD_ELEM, messages);
            } else {
                writer.startElement(JsfConstants.LI_ELEM, messages);
            }
            renderOneMessage(context, messages, facesMessage, null, messages
                    .getAttributes());
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

    private boolean isTableLayout(HtmlMessages htmlMessages) {
        return JsfConstants.TABLE_VALUE.equals(htmlMessages.getLayout());
    }

}
