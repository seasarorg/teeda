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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.HTMLEncodeUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlInputSecretRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = "javax.faces.Secret";

    private final IgnoreAttribute ignoreAttribute = new IgnoreAttribute();
    {
        ignoreAttribute.addAttributeName(JsfConstants.ID_ATTR);
        ignoreAttribute.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreAttribute.addAttributeName(JsfConstants.REDISPLAY_ATTR);
        ignoreAttribute.addAttributeName(JsfConstants.STYLE_CLASS_ATTR);
    }

    public void encodeEnd(final FacesContext context,
            final UIComponent component) throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlInputSecretEnd(context, (HtmlInputSecret) component);
    }

    protected void encodeHtmlInputSecretEnd(final FacesContext context,
            final HtmlInputSecret htmlInputSecret) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputSecret);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.PASSWORD_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputSecret,
                getIdForRender(context, htmlInputSecret));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputSecret.getClientId(context));

        String value = ValueHolderUtil.getValueForRender(context,
                htmlInputSecret);
        if (!htmlInputSecret.isRedisplay()) {
            value = "";
        }
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                HTMLEncodeUtil.encode(value, true, true));
        renderStyleClass(context, htmlInputSecret, writer);
        renderRemainAttributes(htmlInputSecret, writer, ignoreAttribute);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected void renderStyleClass(final FacesContext context,
            final HtmlInputSecret htmlInputSecret, final ResponseWriter writer)
            throws IOException {
        final String styleClass = htmlInputSecret.getStyleClass();
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
    }

    public void decode(final FacesContext context, final UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlInputSecret(context, (HtmlInputSecret) component);
    }

    protected void decodeHtmlInputSecret(final FacesContext context,
            final HtmlInputSecret htmlInputSecret) {
        getDecoder().decode(context, htmlInputSecret);
    }

    public void addIgnoreAttributeName(final String name) {
        ignoreAttribute.addAttributeName(name);
    }

}
