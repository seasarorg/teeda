/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 */
public class THtmlInputSecret extends HtmlInputSecret {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputSecret";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputSecret";

    private static final String VALIDATION_FAIL_CSS = "onTeedaError";

    public static final String errorCss_BINDING = "bindingType=may";

    private String errorStyleClass = VALIDATION_FAIL_CSS;

    public String getErrorStyleClass() {
        return errorStyleClass;
    }

    public void setErrorStyleClass(final String errorStyleClass) {
        this.errorStyleClass = errorStyleClass;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        errorStyleClass = (String) values[1];
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = errorStyleClass;
        return values;
    }

}
