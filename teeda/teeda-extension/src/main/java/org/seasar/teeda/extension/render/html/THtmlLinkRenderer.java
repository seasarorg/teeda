/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlLink;
import org.seasar.teeda.extension.util.PathUtil;

/**
 * @author shot
 */
public class THtmlLinkRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = THtmlLink.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlLink.DEFAULT_RENDERER_TYPE;

    private static final IgnoreAttribute IGNORE_COMPONENT;

    static {
        IGNORE_COMPONENT = buildIgnoreComponent();
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeTHtmlLinkBegin(context, (THtmlLink) component);
    }

    protected void encodeTHtmlLinkBegin(FacesContext context, THtmlLink link)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.LINK_ELEM, link);
        final String rel = link.getRel();
        if (!StringUtil.isEmpty(rel)) {
            RendererUtil.renderAttribute(writer, JsfConstants.REL_ATTR, rel,
                    null);
        }
        final String href = link.getHref();
        if (!StringUtil.isEmpty(href)) {
            RendererUtil
                    .renderAttribute(writer, JsfConstants.HREF_ATTR,
                            PathUtil.toAbsolutePath(context, href, link
                                    .getBaseViewId()), null);
        }
        final String src = link.getSrc();
        if (!StringUtil.isEmpty(src)) {
            RendererUtil.renderAttribute(writer, JsfConstants.SRC_ATTR, src,
                    null);
        }
        renderRemainAttributes(link, writer, IGNORE_COMPONENT);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeTHtmlLinkEnd(context, (THtmlLink) component);
    }

    protected void encodeTHtmlLinkEnd(FacesContext context, THtmlLink link)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.LINK_ELEM);
    }

    protected static IgnoreAttribute buildIgnoreComponent() {
        IgnoreAttribute ignore = new IgnoreAttribute();
        ignore.addAttributeName(JsfConstants.REL_ATTR);
        ignore.addAttributeName(JsfConstants.HREF_ATTR);
        ignore.addAttributeName(JsfConstants.SRC_ATTR);
        return ignore;
    }
}