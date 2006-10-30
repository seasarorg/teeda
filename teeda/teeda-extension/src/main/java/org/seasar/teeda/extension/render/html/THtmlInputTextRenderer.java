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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlInputText;

/**
 * @author shot
 */
public class THtmlInputTextRenderer extends HtmlInputTextRenderer {

    public static final String COMPONENT_FAMILY = HtmlInputText.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlInputText.DEFAULT_RENDERER_TYPE;

    protected void colorErrorComponent(FacesContext context, UIInput input)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        final ResponseWriter writer = context.getResponseWriter();
        final THtmlInputText inputText = (THtmlInputText) input;
        String styleClass = inputText.getStyleClass();
        final String errorCss = inputText.getErrorStyleClass();
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
        RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
    }

}
