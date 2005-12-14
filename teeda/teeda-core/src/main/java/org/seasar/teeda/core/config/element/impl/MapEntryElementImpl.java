package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.MapEntryElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class MapEntryElementImpl implements MapEntryElement {

    private String key_;
    private String value_;
    private boolean nullValue_ = false;
    public MapEntryElementImpl(){
    }
    
    public void setKey(String key) {
        key_ = key;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public String getKey() {
        return key_;
    }

    public String getValue() {
        return value_;
    }

    public void setNullValue(boolean nullValue) {
        nullValue_ = nullValue;
    }

    public boolean isNullValue() {
        return nullValue_;
    }

}
