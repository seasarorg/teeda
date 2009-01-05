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
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.config.faces.element.ValidatorElement;

/**
 * @author shot
 */
public class ValidatorElementImpl implements ValidatorElement {

    private String validatorId_;

    private String validatorClass_;

    private List attributeElements_ = new ArrayList();

    private List propertyElements_ = new ArrayList();

    public ValidatorElementImpl() {
    }

    public void setValidatorId(String validatorId) {
        validatorId_ = validatorId;
    }

    public void setValidatorClass(String validatorClassName) {
        validatorClass_ = validatorClassName;
    }

    public String getValidatorId() {
        return validatorId_;
    }

    public String getValidatorClass() {
        return validatorClass_;
    }

    public void addAttributeElement(AttributeElement attribute) {
        attributeElements_.add(attribute);
    }

    public void addPropertyElement(PropertyElement property) {
        propertyElements_.add(property);
    }

    public List getAttributeElements() {
        return attributeElements_;
    }

    public List getPropertyElements() {
        return propertyElements_;
    }

}
