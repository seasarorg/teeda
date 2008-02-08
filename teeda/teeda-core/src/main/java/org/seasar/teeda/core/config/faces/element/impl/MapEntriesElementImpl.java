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

import org.seasar.teeda.core.config.faces.element.MapEntriesElement;
import org.seasar.teeda.core.config.faces.element.MapEntryElement;

public class MapEntriesElementImpl implements MapEntriesElement {

    private String keyClassName_;

    private String valueClassName_;

    private List mapEntries_ = new ArrayList();

    public MapEntriesElementImpl() {
    }

    public void setKeyClass(String keyClassName) {
        keyClassName_ = keyClassName;
    }

    public void setValueClass(String valueClassName) {
        valueClassName_ = valueClassName;
    }

    public void addMapEntry(MapEntryElement mapEntry) {
        mapEntries_.add(mapEntry);
    }

    public String getKeyClass() {
        return keyClassName_;
    }

    public String getValueClass() {
        return valueClassName_;
    }

    public List getMapEntries() {
        return mapEntries_;
    }

}
