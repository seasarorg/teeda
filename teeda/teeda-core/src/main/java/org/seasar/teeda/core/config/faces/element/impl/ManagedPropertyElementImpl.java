/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.config.faces.element.ListEntriesElement;
import org.seasar.teeda.core.config.faces.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.faces.element.MapEntriesElement;

/**
 * @author shot
 */
public class ManagedPropertyElementImpl implements ManagedPropertyElement {

    private String propertyName_;

    private String propertyClass_;

    private String value_;

    private ListEntriesElement listEntries_;

    private MapEntriesElement mapEntries_;

    private boolean nullValue_ = false;

    public ManagedPropertyElementImpl() {
    }

    public void setPropertyName(String propertyName) {
        propertyName_ = propertyName;
    }

    public void setPropertyClass(String propertyClass) {
        propertyClass_ = propertyClass;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public void setListEntries(ListEntriesElement listEntries) {
        listEntries_ = listEntries;
    }

    public void setMapEntries(MapEntriesElement mapEntries) {
        mapEntries_ = mapEntries;
    }

    public String getPropertyName() {
        return propertyName_;
    }

    public String getPropertyClass() {
        return propertyClass_;
    }

    public String getValue() {
        return value_;
    }

    public ListEntriesElement getListEntries() {
        return listEntries_;
    }

    public MapEntriesElement getMapEntries() {
        return mapEntries_;
    }

    public void setNullValue(boolean nullValue) {
        nullValue_ = nullValue;
    }

    public boolean isNullValue() {
        return nullValue_;
    }

}
