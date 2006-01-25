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
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlGraphicImageRenderer extends AbstractHtmlRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlGraphicImageEnd(context, (HtmlGraphicImage) component);
    }

    protected void encodeHtmlGraphicImageEnd(FacesContext context,
            HtmlGraphicImage htmlGraphicImage) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.IMG_ELEM, htmlGraphicImage);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlGraphicImage,
                getIdForRender(context, htmlGraphicImage));

        String url = htmlGraphicImage.getUrl();
        if (url == null) {
            url = "";
        }
        RendererUtil.renderAttribute(writer, JsfConstants.SRC_ATTR, url);
        RendererUtil.renderAttributes(writer, htmlGraphicImage,
                JsfConstants.IMG_PASSTHROUGH_ATTRIBUTES);
        writer.endElement(JsfConstants.IMG_ELEM);
    }

}
