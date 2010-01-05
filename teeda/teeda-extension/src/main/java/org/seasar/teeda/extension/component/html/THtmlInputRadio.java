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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author koichik
 */
public class THtmlInputRadio extends HtmlSelectOneRadio {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputRadio";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputRadio";

    public void validate(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        final Object submittedValue = getSubmittedValue();
        final Object convertedValue = getConvertedValue(context, submittedValue);
        if (!isValid()) {
            return;
        }
        validateValue(context, convertedValue);
        if (!isValid()) {
            return;
        }
        Object previous = getValue();
        setValue(convertedValue);
        setSubmittedValue(null);
        if (compareValues(previous, convertedValue)) {
            queueEvent(new ValueChangeEvent(this, previous, convertedValue));
        }
    }

}
