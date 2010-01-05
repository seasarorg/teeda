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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlOutputLabelRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "javax.faces.Label";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.FOR_ATTR);
    }

    public void encodeBegin(final FacesContext context,
            final UIComponent component) throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputLabelBegin(context, (HtmlOutputLabel) component);
    }

    protected void encodeHtmlOutputLabelBegin(final FacesContext context,
            final HtmlOutputLabel htmlOutputLabel) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();

        writer.startElement(JsfConstants.LABEL_ELEM, htmlOutputLabel);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputLabel,
                getIdForRender(context, htmlOutputLabel));

        final String forAttr = htmlOutputLabel.getFor();
        if (forAttr != null) {
            final UIComponent forComponent = htmlOutputLabel
                    .findComponent(forAttr);
            if (forComponent == null) {
                throw new IllegalStateException("for Component [" + forAttr
                        + "] does not found");
            }
            final String forClientId = getIdForRender(context, forComponent);
            RendererUtil.renderAttribute(writer, JsfConstants.FOR_ATTR,
                    forClientId, null);
        }
        renderRemainAttributes(htmlOutputLabel, writer, ignoreComponent);
        final String value = ValueHolderUtil.getValueForRender(context,
                htmlOutputLabel);
        writer.writeText(value, null);
    }

    public void encodeEnd(final FacesContext context,
            final UIComponent component) throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputLabelEnd(context, (HtmlOutputLabel) component);
    }

    protected void encodeHtmlOutputLabelEnd(final FacesContext context,
            final HtmlOutputLabel htmlOutputLabel) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.LABEL_ELEM);
    }

}
