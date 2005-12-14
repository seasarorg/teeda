package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.PropertyElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ConverterElementImpl implements ConverterElement {

    private String converterId_;
    private String converterForClass_;
    private String converterClass_;
    private List attributeElements_;
    private List propertyElements_;
    public ConverterElementImpl(){
        attributeElements_ = new ArrayList();
        propertyElements_ = new ArrayList();
    }

    public void setConverterId(String converterId){
        converterId_ = converterId;
    }
    
    public void setConverterForClass(String converterForClass){
        converterClass_ = converterForClass;
    }
    
    public void setConverterClass(String converterClass){
        converterClass_ = converterClass; 
    }
    
    public String getConverterClass() {
        return converterClass_;
    }
    
    public String getConverterForClass() {
        return converterForClass_;
    }
    
    public String getConverterId() {
        return converterId_;
    }

    public void addAttributeElement(AttributeElement attributeTag) {
        attributeElements_.add(attributeTag);
    }

    public void addPropertyElement(PropertyElement propertyTag) {
        propertyElements_.add(propertyTag);
    }

    public List getAttributeElements() {
        return attributeElements_;
    }

    public List getPropertyElements() {
        return propertyElements_;
    }
}
