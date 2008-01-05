/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.config.faces.element.ListEntriesElement;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.config.faces.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.faces.element.MapEntriesElement;

public class ManagedBeanElementImpl implements ManagedBeanElement {

    private String managedBeanName_;

    private String managedBeanClass_;

    private String managedBeanScope_;

    private List managedProperties_ = new ArrayList();

    private ListEntriesElement listEntries_;

    private MapEntriesElement mapEntries_;

    public ManagedBeanElementImpl() {
    }

    public void setManagedBeanName(String managedBeanName) {
        managedBeanName_ = managedBeanName;
    }

    public void setManagedBeanClass(String managedBeanClass) {
        managedBeanClass_ = managedBeanClass;
    }

    public void setManagedBeanScope(String managedBeanScope) {
        managedBeanScope_ = managedBeanScope;
    }

    public String getManagedBeanName() {
        return managedBeanName_;
    }

    public String getManagedBeanClass() {
        return managedBeanClass_;
    }

    public String getManagedBeanScope() {
        return managedBeanScope_;
    }

    public void addManagedPropertyElement(ManagedPropertyElement managedProperty) {
        managedProperties_.add(managedProperty);
    }

    public void setListEntries(ListEntriesElement listEntries) {
        listEntries_ = listEntries;
    }

    public ListEntriesElement getListEntries() {
        return listEntries_;
    }

    public void setMapEntries(MapEntriesElement mapEntries) {
        mapEntries_ = mapEntries;
    }

    public MapEntriesElement getMapEntries() {
        return mapEntries_;
    }

    public List getManagedProperties() {
        return managedProperties_;
    }

}
