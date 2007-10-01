/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
 * @author shot
 */
public abstract class InputTagBase extends UIComponentTagBase {

    private String immediate;

    private String required;

    private String validator;

    private String valueChangeListener;

    private String label;

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.MAXLENGTH_ATTR,
                getMaxlength());
        setComponentProperty(component, JsfConstants.IMMEDIATE_ATTR,
                getImmediate());
        setComponentProperty(component, JsfConstants.REQUIRED_ATTR,
                getRequired());
        setValidatorProperty(component, getValidator());
        setValueChangeListenerProperty(component, getValueChangeListener());
        setComponentProperty(component, JsfConstants.READONLY_ATTR,
                getReadonly());
        setComponentProperty(component, JsfConstants.LABEL_ATTR, getLabel());
    }

    public void release() {
        super.release();
        immediate = null;
        required = null;
        validator = null;
        valueChangeListener = null;
        label = null;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public void setValueChangeListener(String valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImmediate() {
        return immediate;
    }

    public String getRequired() {
        return required;
    }

    public String getValidator() {
        return validator;
    }

    public String getValueChangeListener() {
        return valueChangeListener;
    }

    public String getLabel() {
        return label;
    }

}
