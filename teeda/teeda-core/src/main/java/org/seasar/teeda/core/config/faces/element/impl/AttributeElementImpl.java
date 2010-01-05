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
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.AttributeElement;

public class AttributeElementImpl implements AttributeElement {

    private String attributeName_;

    private String attributeClass_;

    private String defaultValue_;

    private String suggestedValue_;

    private List attributeExtensions_ = new ArrayList();

    public AttributeElementImpl() {
    }

    public void setAttributeName(String attributeName) {
        attributeName_ = attributeName;
    }

    public void setAttributeClass(String attributeClass) {
        attributeClass_ = attributeClass;
    }

    public void setDefaultValue(String defaultValue) {
        defaultValue_ = defaultValue;
    }

    public void setSuggestedValue(String suggestedValue) {
        suggestedValue_ = suggestedValue;
    }

    public String getAttributeName() {
        return attributeName_;
    }

    public String getAttributeClass() {
        return attributeClass_;
    }

    public String getDefaultValue() {
        return defaultValue_;
    }

    public String getSuggestedValue() {
        return suggestedValue_;
    }

    public void addAttributeExtension(String attributeExtension) {
        attributeExtensions_.add(attributeExtension);
    }

    public List getAttributeExtensions() {
        return attributeExtensions_;
    }

}
