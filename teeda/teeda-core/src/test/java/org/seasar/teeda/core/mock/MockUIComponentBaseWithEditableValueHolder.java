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
package org.seasar.teeda.core.mock;

import javax.faces.component.EditableValueHolder;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * @author manhole
 */
public class MockUIComponentBaseWithEditableValueHolder extends
        MockUIComponentBase implements EditableValueHolder {

    private Object submittedValue_;

    private boolean localValueSet_ = false;

    private boolean valid_ = false;

    private boolean required_ = false;

    private boolean immediate_ = false;

    private Object value_ = null;

    public String getFamily() {
        return "javax.faces.mock";
    }

    public Object getSubmittedValue() {
        return submittedValue_;
    }

    public void setSubmittedValue(Object submittedValue) {
        submittedValue_ = submittedValue;
    }

    public boolean isLocalValueSet() {
        return localValueSet_;
    }

    public void setLocalValueSet(boolean localValueSet) {
        localValueSet_ = localValueSet;
    }

    public boolean isValid() {
        return valid_;
    }

    public void setValid(boolean valid) {
        valid_ = valid;
    }

    public boolean isRequired() {
        return required_;
    }

    public void setRequired(boolean required) {
        required_ = required;
    }

    public boolean isImmediate() {
        return immediate_;
    }

    public void setImmediate(boolean immediate) {
        immediate_ = immediate;
    }

    public MethodBinding getValidator() {
        return null;
    }

    public void setValidator(MethodBinding validatorBinding) {
    }

    public MethodBinding getValueChangeListener() {
        return null;
    }

    public void setValueChangeListener(MethodBinding valueChangeMethod) {
    }

    public void addValidator(Validator validator) {
    }

    public Validator[] getValidators() {
        return null;
    }

    public void removeValidator(Validator validator) {
    }

    public void addValueChangeListener(ValueChangeListener listener) {
    }

    public ValueChangeListener[] getValueChangeListeners() {
        return null;
    }

    public void removeValueChangeListener(ValueChangeListener listener) {
    }

    public Object getLocalValue() {
        return null;
    }

    public Object getValue() {
        return value_;
    }

    public void setValue(Object value) {
        value_ = value;
    }

    public Converter getConverter() {
        return null;
    }

    public void setConverter(Converter converter) {
    }

}
