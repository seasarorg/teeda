package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.MapEntriesElement;
import org.seasar.teeda.core.config.element.MapEntryElement;


public class MapEntriesElementImpl implements MapEntriesElement {

    private String keyClassName_;
    private String valueClassName_;
    private List mapEntries_ = new ArrayList();
    public MapEntriesElementImpl(){
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
