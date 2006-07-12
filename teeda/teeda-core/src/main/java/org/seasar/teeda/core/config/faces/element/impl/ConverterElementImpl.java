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
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;

/**
 * @author shot
 */
public class ConverterElementImpl implements ConverterElement {

    private String converterId_;

    private String converterForClass_;

    private String converterClass_;

    private List attributeElements_;

    private List propertyElements_;

    public ConverterElementImpl() {
        attributeElements_ = new ArrayList();
        propertyElements_ = new ArrayList();
    }

    public void setConverterId(String converterId) {
        converterId_ = converterId;
    }

    public void setConverterForClass(String converterForClass) {
        converterForClass_ = converterForClass;
    }

    public void setConverterClass(String converterClass) {
        converterClass_ = converterClass;
    }

    public String getConverterClass() {
        return converterClass_;
    }

    public String getConverterForClass() {
        return converterForClass_;
    }

    public String getConverterId() {
        return converterId_;
    }

    public void addAttributeElement(AttributeElement attributeTag) {
        attributeElements_.add(attributeTag);
    }

    public void addPropertyElement(PropertyElement propertyTag) {
        propertyElements_.add(propertyTag);
    }

    public List getAttributeElements() {
        return attributeElements_;
    }

    public List getPropertyElements() {
        return propertyElements_;
    }
}
