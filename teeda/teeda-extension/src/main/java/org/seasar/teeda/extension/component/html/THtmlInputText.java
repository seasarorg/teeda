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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class THtmlInputText extends HtmlInputText {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputText";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputText";

    private static final String VALIDATION_FAIL_CSS = "onTeedaError";

    public static final String errorCss_BINDING = "bindingType=may";

    private String errorStyleClass = VALIDATION_FAIL_CSS;

    public String getErrorStyleClass() {
        return errorStyleClass;
    }

    public void setErrorStyleClass(String errorStyleClass) {
        this.errorStyleClass = errorStyleClass;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        errorStyleClass = (String) values[1];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = errorStyleClass;
        return values;
    }

}
