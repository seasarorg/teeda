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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputDateText;

/**
 * @author shot
 *
 */
public class TInputDateTextTag extends InputTextTag {

    private String rendererType;

    private String pattern;

    private String length;

    private String threshold;

    public String getComponentType() {
        return THtmlInputDateText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        if (rendererType == null) {
            rendererType = THtmlInputDateText.DEFAULT_RENDERER_TYPE;
        }
        return rendererType;
    }

    public void setRendererType(String rendererType) {
        this.rendererType = rendererType;
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

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        String p = getPattern();
        if (isValueReference(p)) {
            p = BindingUtil.resolveBindingNoException(p);
        }
        setComponentProperty(component, ExtensionConstants.PATTERN_ATTR, p);
        String l = getLength();
        if (isValueReference(l)) {
            l = BindingUtil.resolveBindingNoException(l);
        }
        setComponentProperty(component, ExtensionConstants.LENGTH_ATTR, l);
        String t = getThreshold();
        if (isValueReference(t)) {
            t = BindingUtil.resolveBindingNoException(t);
        }
        setComponentProperty(component, ExtensionConstants.THRESHOD_ATTR, t);
    }
}
