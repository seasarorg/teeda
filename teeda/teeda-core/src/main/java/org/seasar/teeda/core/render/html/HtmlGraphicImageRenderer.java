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
package org.seasar.teeda.core.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlGraphicImageRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Graphic";

    public static final String RENDERER_TYPE = "javax.faces.Image";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.URL_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGraphicImageEnd(context, (UIGraphic) component);
    }

    protected void encodeHtmlGraphicImageEnd(FacesContext context,
            UIGraphic graphic) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.IMG_ELEM, graphic);
        RendererUtil.renderIdAttributeIfNecessary(writer, graphic,
                getIdForRender(context, graphic));
        final String url = getUrl(context, graphic);
        writer.writeURIAttribute(JsfConstants.SRC_ATTR, url, null);
        renderRemainAttributes(graphic, writer, ignoreComponent);
        writer.endElement(JsfConstants.IMG_ELEM);
    }

    protected String getUrl(FacesContext context, UIGraphic htmlGraphicImage) {
        String url = htmlGraphicImage.getUrl();
        if (url == null) {
            url = "";
        }
        url = FacesContextUtil.getViewHandler(context).getResourceURL(context,
                url);
        url = context.getExternalContext().encodeResourceURL(url);
        return url;
    }

    protected void addIgnoreAttributeName(final String attr) {
        ignoreComponent.addAttributeName(attr);
    }

}
