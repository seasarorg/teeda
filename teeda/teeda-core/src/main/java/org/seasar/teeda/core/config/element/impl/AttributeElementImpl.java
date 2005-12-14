package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.AttributeElement;


public class AttributeElementImpl implements AttributeElement {

    private String attributeName_;
    private String attributeClass_;
    private String defaultValue_;
    private String suggestedValue_;
    private List attributeExtensions_ = new ArrayList();
    public AttributeElementImpl(){
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
