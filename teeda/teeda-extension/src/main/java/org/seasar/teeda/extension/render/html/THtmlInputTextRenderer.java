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

import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;

/**
 * @author shot
 */
public class THtmlInputTextRenderer extends HtmlInputTextRenderer {

    public static final String COMPONENT_FAMILY = HtmlInputText.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputText";

    private static final String VALIDATION_FAIL_CSS = "onTeedaError";

    public static final String errorCss_BINDING = "bindingType=may";

    private String errorCss = VALIDATION_FAIL_CSS;
    
    public void setErrorCss(String errorCss) {
        this.errorCss = errorCss;
    }
    
    public String getErrorCss() {
        return errorCss;
    }
    
    protected void colorErrorComponent(FacesContext context, UIInput input) {
        HtmlInputText inputText = (HtmlInputText) input;
        String styleClass = inputText.getStyleClass();
        final String errorCss = getErrorCss();
        if(StringUtil.isEmpty(errorCss)) {
            return;
        }
        if (styleClass != null) {
            styleClass = styleClass + " " + errorCss;
        } else  {
            styleClass = errorCss;
        }
        inputText.setStyleClass(styleClass);
    }

}
