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
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlPanelGroupRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Panel";

    public static final String RENDERER_TYPE = "javax.faces.Group";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGroupBegin(context, (HtmlPanelGroup) component);
    }

    protected void encodeHtmlPanelGroupBegin(FacesContext context,
            HtmlPanelGroup htmlPanelGroup) throws IOException {
        if (isWriteSpan(htmlPanelGroup)) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement(JsfConstants.SPAN_ELEM, htmlPanelGroup);
            RendererUtil.renderIdAttributeIfNecessary(writer, htmlPanelGroup,
                    getIdForRender(context, htmlPanelGroup));
            renderRemainAttributes(htmlPanelGroup, writer, ignoreComponent);
        }
    }

    private boolean isWriteSpan(HtmlPanelGroup htmlPanelGroup) {
        return containsAttributeForRender(htmlPanelGroup, ignoreComponent);
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGroupChildren(context, (HtmlPanelGroup) component);
    }

    protected void encodeHtmlPanelGroupChildren(FacesContext context,
            HtmlPanelGroup htmlPanelGroup) throws IOException {
        encodeDescendantComponent(context, htmlPanelGroup);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGroupEnd(context, (HtmlPanelGroup) component);
    }

    protected void encodeHtmlPanelGroupEnd(FacesContext context,
            HtmlPanelGroup htmlPanelGroup) throws IOException {
        if (isWriteSpan(htmlPanelGroup)) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

}
