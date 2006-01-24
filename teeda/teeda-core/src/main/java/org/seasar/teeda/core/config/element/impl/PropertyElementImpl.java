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
