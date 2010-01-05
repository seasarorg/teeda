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
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public abstract class AbstractHtmlMessagesRenderer extends AbstractRenderer {

    protected void renderOneMessage(FacesContext context,
            UIComponent component, FacesMessage facesMassage,
            final String idForRender, Map attributes) throws IOException {

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
                || title != null || containsAttributeForRender(attributes)) {
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
            renderAttributes(attributes, writer);
        }

        // TODO don't escape HTML tags
        if (isWriteSummary) {
            writer.write(summary);
        }
        if (isWriteDetail) {
            if (isWriteSummary) {
                writer.write(" ");
            }
            writer.write(detail);
        }

        if (startSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    protected boolean isTooltip(UIComponent component) {
        return UIComponentUtil.getPrimitiveBooleanAttribute(component,
                JsfConstants.TOOLTIP_ATTR);
    }

    protected boolean isShowDetail(UIComponent component) {
        return UIComponentUtil.getPrimitiveBooleanAttribute(component,
                JsfConstants.SHOW_DETAIL_ATTR);
    }

    protected boolean isShowSummary(UIComponent component) {
        return UIComponentUtil.getPrimitiveBooleanAttribute(component,
                JsfConstants.SHOW_SUMMARY_ATTR);
    }

    protected String getTitle(UIComponent component) {
        return UIComponentUtil.getStringAttribute(component,
                JsfConstants.TITLE_ATTR);
    }

    protected String getStyleClass(UIComponent component, Severity severity) {
        String styleClass = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            styleClass = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.INFO_CLASS_ATTR);
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            styleClass = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.WARN_CLASS_ATTR);
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            styleClass = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.ERROR_CLASS_ATTR);
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            styleClass = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.FATAL_CLASS_ATTR);
        }
        if (styleClass == null) {
            styleClass = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.STYLE_CLASS_ATTR);
        }
        return styleClass;
    }

    protected String getStyle(UIComponent component, Severity severity) {
        String style = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            style = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.INFO_STYLE_ATTR);
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            style = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.WARN_STYLE_ATTR);
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            style = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.ERROR_STYLE_ATTR);
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            style = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.FATAL_STYLE_ATTR);
        }
        if (style == null) {
            style = UIComponentUtil.getStringAttribute(component,
                    JsfConstants.STYLE_ATTR);
        }
        return style;
    }

}
