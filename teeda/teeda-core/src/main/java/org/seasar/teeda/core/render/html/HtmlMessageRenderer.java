/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;

import org.seasar.framework.log.Logger;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlMessageRenderer extends AbstractHtmlMessagesRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Message";

    public static final String RENDERER_TYPE = "javax.faces.Message";

    private static final Logger logger = Logger
            .getLogger(HtmlMessageRenderer.class);

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
            logger.debug("[HtmlMessage] forComponent [" + forAttr
                    + "] is null. [id=" + htmlMessage.getId() + "]");
            return;
        }
        final String clientId = forComponent.getClientId(context);
        Iterator it = context.getMessages(clientId);
        if (!it.hasNext()) {
            return;
        }

        FacesMessage facesMassage = (FacesMessage) it.next();
        String idForRender = getIdForRenderOrNull(context, htmlMessage);
        renderOneMessage(context, htmlMessage, facesMassage, idForRender,
                htmlMessage.getAttributes());
    }

    private String getIdForRenderOrNull(FacesContext context,
            HtmlMessage htmlMessage) {
        if (RendererUtil.shouldRenderIdAttribute(htmlMessage)) {
            return getIdForRender(context, htmlMessage);
        }
        return null;
    }

}
