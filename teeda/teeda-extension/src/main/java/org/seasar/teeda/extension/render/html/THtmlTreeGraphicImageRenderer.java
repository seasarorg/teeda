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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlGraphicImageRenderer;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author shot
 */
public class THtmlTreeGraphicImageRenderer extends HtmlGraphicImageRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Graphic";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.tree.Image";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        //        ignoreComponent.addIgnoreComponentName(JsfConstants.VALUE_ATTR);
        //        ignoreComponent.addIgnoreComponentName(JsfConstants.URL_ATTR);
    }

    protected String getIdForRender(FacesContext context, UIComponent component) {
        return component.getClientId(context);
    }

    protected void encodeHtmlGraphicImageEnd(FacesContext context,
            HtmlGraphicImage htmlGraphicImage) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.IMG_ELEM, htmlGraphicImage);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlGraphicImage,
                getIdForRender(context, htmlGraphicImage));
        final String url = getUrl(context, htmlGraphicImage);
        writer.writeURIAttribute(JsfConstants.SRC_ATTR, url, null);
        renderRemainAttributes(htmlGraphicImage, writer, ignoreComponent);
        writer.endElement(JsfConstants.IMG_ELEM);
    }

}
