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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public abstract class AbstractHtmlMessagesRenderer extends AbstractHtmlRenderer {

    protected abstract boolean isTooltip(UIComponent component);

    protected abstract boolean isShowDetail(UIComponent component);

    protected abstract boolean isShowSummary(UIComponent component);

    protected abstract String getTitle(UIComponent component);

    protected abstract String getStyleClass(UIComponent component,
            Severity severity);

    protected abstract String getStyle(UIComponent component, Severity severity);

    protected void renderOneMessage(FacesContext context,
            UIComponent component, FacesMessage facesMassage,
            final String idForRender) throws IOException {

        final String style = getStyle(component, facesMassage.getSeverity());
        final String styleClass = getStyleClass(component, facesMassage
                .getSeverity());
        String title = getTitle(component);

        String summary = facesMassage.getSummary();
        String detail = facesMassage.getDetail();
        boolean isWriteSummary = isShowSummary(component) && summary != null;
        boolean isWriteDetail = isShowDetail(component) && detail != null;
        if (isTooltip(component) && isWriteSummary && isWriteDetail) {
            isWriteSummary = false;
            title = summary;
        }

        ResponseWriter writer = context.getResponseWriter();
        boolean startSpan = false;
        if (idForRender != null || style != null || styleClass != null
                || title != null) {
            startSpan = true;
            writer.startElement(JsfConstants.SPAN_ELEM, component);
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR,
                    idForRender);
            RendererUtil
                    .renderAttribute(writer, JsfConstants.TITLE_ATTR, title);
            RendererUtil
                    .renderAttribute(writer, JsfConstants.STYLE_ATTR, style);
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                    styleClass);
        }

        // TODO don't escape HTML tags
        if (isWriteSummary) {
            writer.writeText(summary, null);
        }
        if (isWriteDetail) {
            if (isWriteSummary) {
                writer.writeText(" ", detail);
            }
            writer.writeText(detail, null);
        }

        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

}
