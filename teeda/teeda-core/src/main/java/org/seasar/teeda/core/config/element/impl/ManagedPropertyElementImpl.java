package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.element.MapEntriesElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ManagedPropertyElementImpl implements ManagedPropertyElement {

    private String propertyName_;
    private String propertyClass_;
    private String value_;
    private ListEntriesElement listEntries_;
    private MapEntriesElement mapEntries_;
    private boolean nullValue_ = false;
    
    public ManagedPropertyElementImpl(){
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

    public String getPropertyClas() {
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
