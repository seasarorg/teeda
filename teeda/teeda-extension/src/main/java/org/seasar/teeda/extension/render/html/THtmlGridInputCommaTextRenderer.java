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

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;
import org.seasar.teeda.extension.component.html.THtmlInputText;

/**
 * @author manhole
 */
public class THtmlGridInputCommaTextRenderer extends
        THtmlInputCommaTextRenderer {

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlGridInputCommaText";

    private THtmlGridInputTextRenderer gridInputTextRenderer = new THtmlGridInputTextRenderer();

    protected void encodeInputExtendTextEnd(FacesContext context,
            THtmlInputText htmlInputText) throws IOException {
        assertHtmlInputCommaText(htmlInputText);
        THtmlInputCommaText htmlInputCommaText = (THtmlInputCommaText) htmlInputText;
        gridInputTextRenderer.renderStartDiv(context, htmlInputCommaText);
        gridInputTextRenderer.renderSpan(context, htmlInputCommaText, getValue(
                context, htmlInputCommaText));
        super.encodeInputExtendTextEnd(context, htmlInputText);
        gridInputTextRenderer.renderEndDiv(context, htmlInputCommaText);
    }

    protected String createOnblurAttribute(
            THtmlInputCommaText htmlInputCommaText, String fraction,
            String groupingSeparator, String fractionSeparator) {
        return super.createOnblurAttribute(htmlInputCommaText, fraction,
                groupingSeparator, fractionSeparator)
                + THtmlGridInputTextRenderer.EDIT_OFF;
    }

    protected String createStyleAttribute(THtmlInputCommaText htmlInputCommaText) {
        return super.createStyleAttribute(htmlInputCommaText)
                + THtmlGridInputTextRenderer.DISPLAY_NONE;
    }

    protected void colorErrorComponent(FacesContext context, UIInput input)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        THtmlInputText htmlInputText = (THtmlInputText) input;
        final ResponseWriter writer = context.getResponseWriter();
        final String clientId = htmlInputText.getClientId(context);
        String styleClass = createStyleClassAttribute(htmlInputText);
        if (FacesMessageUtil.hasMessagesByClientId(context, clientId)
                || containsClientId(context, clientId)) {
            final String errorCss = htmlInputText.getErrorStyleClass();
            if (StringUtil.isEmpty(errorCss)) {
                return;
            }
            if (styleClass != null && styleClass.indexOf(errorCss) >= 0) {
                return;
            }
            if (styleClass != null) {
                styleClass = styleClass + " " + errorCss;
            } else {
                styleClass = errorCss;
            }
        }
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
    }

    protected String createStyleClassAttribute(THtmlInputText htmlInputText) {
        final String styleClass = htmlInputText.getStyleClass();
        if (StringUtil.isNotBlank(styleClass)) {
            return styleClass + " "
                    + THtmlGridInputTextRenderer.GRID_CELL_EDIT_CLASS_NAME;
        }
        return THtmlGridInputTextRenderer.GRID_CELL_EDIT_CLASS_NAME;
    }

    protected String getScriptKey() {
        return THtmlInputCommaText.class.getName();
    }

}
