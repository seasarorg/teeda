/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlStyle;

/**
 * @author shot
 */
public class THtmlStyleRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = THtmlStyle.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlStyle.DEFAULT_RENDERER_TYPE;

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
        encodeTHtmlStyleBegin(context, (THtmlStyle) component);
    }

    protected void encodeTHtmlStyleBegin(FacesContext context, THtmlStyle style)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.STYLE_ELEM, style);
        final String type = style.getType();
        if (!StringUtil.isEmpty(type)) {
            RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR, type,
                    null);
        }
        final String dir = style.getDir();
        if (!StringUtil.isEmpty(dir)) {
            RendererUtil.renderAttribute(writer, JsfConstants.DIR_ATTR, dir,
                    null);
        }
        final String lang = style.getLang();
        if (!StringUtil.isEmpty(lang)) {
            RendererUtil.renderAttribute(writer, JsfConstants.LANG_ATTR, lang,
                    null);
        }
        final String media = style.getMedia();
        if (!StringUtil.isEmpty(media)) {
            RendererUtil.renderAttribute(writer, ExtensionConstants.MEDIA_ATTR,
                    media, null);
        }
        final String title = style.getTitle();
        if (!StringUtil.isEmpty(title)) {
            RendererUtil.renderAttribute(writer, JsfConstants.TITLE_ATTR,
                    title, null);
        }
        renderRemainAttributes(style, writer, IGNORE_COMPONENT);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeTHtmlStyleEnd(context, (THtmlStyle) component);
    }

    protected void encodeTHtmlStyleEnd(FacesContext context, THtmlStyle style)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.STYLE_ELEM);
    }

    protected static IgnoreAttribute buildIgnoreComponent() {
        IgnoreAttribute ignore = new IgnoreAttribute();
        ignore.addAttributeName(JsfConstants.TYPE_ATTR);
        ignore.addAttributeName(JsfConstants.DIR_ATTR);
        ignore.addAttributeName(JsfConstants.LANG_ATTR);
        ignore.addAttributeName(ExtensionConstants.MEDIA_ATTR);
        ignore.addAttributeName(JsfConstants.TITLE_ATTR);
        return ignore;
    }
}