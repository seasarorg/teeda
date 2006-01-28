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
package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.PropertyElement;

/**
 * @author ohtani
 */
public class PropertyElementImpl implements PropertyElement {

    private String propertyName_;
    private String propertyClass_;
    private String defaultValue_;
    private String suggestedValue_;
    private List propertyExtensions_ = new ArrayList();
    public PropertyElementImpl(){
    }
    
    public void setPropertyName(String propertyName) {
        propertyName_ = propertyName;
    }

    public void setPropertyClass(String propertyClass) {
        propertyClass_ = propertyClass;
    }

    public void setDefaultValue(String defaultValue) {
        defaultValue_ = defaultValue;
    }

    public void setSuggestedValue(String suggestedValue) {
        suggestedValue_ = suggestedValue;
    }

    public String getPropertyName() {
        return propertyName_;
    }

    public String getPropertyClass() {
        return propertyClass_;
    }

    public String getDefaultValue() {
        return defaultValue_;
    }

    public String getSuggestedValue() {
        return suggestedValue_;
    }

    public void addPropertyExtension(String propertyExtension) {
        propertyExtensions_.add(propertyExtension);
    }

    public List getPropertyExtensions() {
        return propertyExtensions_;
    }

}
