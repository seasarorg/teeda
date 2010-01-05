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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputTextareaRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputTextarea;

/**
 * @author shot
 */
public class THtmlInputTextareaRenderer extends HtmlInputTextareaRenderer {

    public static final String COMPONENT_FAMILY = THtmlInputTextarea.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlInputTextarea.DEFAULT_RENDERER_TYPE;

    {
        addIgnoreAttributeName(ExtensionConstants.ERROR_STYLE_CLASS);
    }

    protected void renderStyleClass(FacesContext context,
            HtmlInputTextarea htmlInputTextarea, ResponseWriter writer)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        THtmlInputTextarea textarea = (THtmlInputTextarea) htmlInputTextarea;
        final String clientId = textarea.getClientId(context);
        String styleClass = textarea.getStyleClass();
        if (FacesMessageUtil.hasMessagesByClientId(context, clientId)
                || containsClientId(context, clientId)) {
            final String errorCss = textarea.getErrorStyleClass();
            if (StringUtil.isEmpty(errorCss)) {
                return;
            }
            if (styleClass != null && styleClass.indexOf(errorCss) >= 0) {
                return;
            }
            if (styleClass != null) {
                styleClass = styleClass + " " + errorCss;
            } else {
                styleClass = errorCss;
            }
        }
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
    }

    protected boolean containsClientId(FacesContext context, String clientId) {
        final ExternalContext externalContext = context.getExternalContext();
        final Map requestMap = externalContext.getRequestMap();
        Set clientIds = (Set) requestMap
                .get(ExtensionConstants.TEEDA_EXTENSION_MESSAGE_CLIENTIDS);
        return (clientIds != null && clientIds.contains(clientId));
    }

}
