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
package org.seasar.teeda.core.application;

/**
 * @author shot
 */
public class ConverterConfiguration {

    private String propertyName_;
    private String propertyClass_;
    private String defaultValue_;
    public ConverterConfiguration(String propertyName, String propertyClass, String defaultValue) {
        propertyName_ = propertyName;
        propertyClass_ = propertyClass;
        defaultValue_ = defaultValue;
    }
    public String getDefaultValue() {
        return defaultValue_;
    }
    public String getPropertyClass() {
        return propertyClass_;
    }
    public String getPropertyName() {
        return propertyName_;
    }
}
