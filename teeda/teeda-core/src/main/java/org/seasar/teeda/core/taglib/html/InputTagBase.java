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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public abstract class InputTagBase extends UIComponentTagBase {

    private String immediate_;

    private String required_;

    private String validator_;

    private String valueChangeListener_;

    private String readonly_;

    private String label_;

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.IMMEDIATE_ATTR, immediate_);
        setComponentProperty(component, JsfConstants.REQUIRED_ATTR, required_);
        setValidatorProperty(component, validator_);
        setValueChangeListenerProperty(component, valueChangeListener_);
        setComponentProperty(component, JsfConstants.READONLY_ATTR, readonly_);
        setComponentProperty(component, JsfConstants.LABEL_ATTR, label_);
    }

    public void release() {
        super.release();
        immediate_ = null;
        required_ = null;
        validator_ = null;
        valueChangeListener_ = null;
        readonly_ = null;
        label_ = null;
    }

    public void setImmediate(String immediate) {
        immediate_ = immediate;
    }

    public void setReadonly(String readonly) {
        readonly_ = readonly;
    }

    public void setRequired(String required) {
        required_ = required;
    }

    public void setValidator(String validator) {
        validator_ = validator;
    }

    public void setValueChangeListener(String valueChangeListener) {
        valueChangeListener_ = valueChangeListener;
    }

    public void setLabel(String label) {
        label_ = label;
    }

    String getImmediate() {
        return immediate_;
    }

    String getReadonly() {
        return readonly_;
    }

    String getRequired() {
        return required_;
    }

    String getValidator() {
        return validator_;
    }

    String getValueChangeListener() {
        return valueChangeListener_;
    }

    public String getLabel() {
        return label_;
    }

}
