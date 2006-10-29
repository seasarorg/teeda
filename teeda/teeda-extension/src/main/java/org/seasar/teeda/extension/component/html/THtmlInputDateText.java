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

import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class THtmlInputDateText extends THtmlInputText {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputDateText";

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputDateText";

    private String pattern;

    private String length;

    private String threshold;

    public THtmlInputDateText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] value = (Object[]) state;
        super.restoreState(context, value[0]);
        pattern = (String) value[1];
        length = (String) value[2];
        threshold = (String) value[3];
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[4];
        state[0] = super.saveState(context);
        state[1] = pattern;
        state[2] = length;
        state[3] = threshold;
        return state;
    }

}
