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
import java.net.URLEncoder;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlOutputLinkRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        renderHtmlOutputLinkBegin(context, (HtmlOutputLink) component);
    }

    protected void renderHtmlOutputLinkBegin(FacesContext context,
            HtmlOutputLink htmlOutputLink) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.ANCHOR_ELEM, htmlOutputLink);

        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase(htmlOutputLink.getValue().toString());

        final String encoding = writer.getCharacterEncoding();
        for (Iterator it = htmlOutputLink.getChildren().iterator(); it
                .hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                UIParameter parameter = (UIParameter) child;
                urlBuilder.add(
                        URLEncoder.encode(parameter.getName(), encoding),
                        URLEncoder.encode(parameter.getValue().toString(),
                                encoding));
            }
        }
        String href = context.getExternalContext().encodeResourceURL(
                urlBuilder.build());
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputLink,
                htmlOutputLink.getId());
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, href, null);
        RendererUtil
                .renderAttributes(
                        writer,
                        htmlOutputLink,
                        JsfConstants.ANCHOR_PASSTHROUGH_AND_ONBLUR_AND_ONFOCUS_ATTRIBUTES);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        renderHtmlOutputLinkEnd(context, (HtmlOutputLink) component);
    }

    protected void renderHtmlOutputLinkEnd(FacesContext context,
            HtmlOutputLink link) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.writeText("", null);
        writer.endElement(JsfConstants.ANCHOR_ELEM);
    }

}
