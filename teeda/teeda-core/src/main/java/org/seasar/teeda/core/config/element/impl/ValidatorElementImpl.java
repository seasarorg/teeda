package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.PropertyElement;
import org.seasar.teeda.core.config.element.ValidatorElement;
/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ValidatorElementImpl implements ValidatorElement {

    private String validatorId_;
    private String validatorClass_;
    private List attributeElements_ = new ArrayList();
    private List propertyElements_ = new ArrayList();
    public ValidatorElementImpl(){
    }

    public void setValidatorId(String validatorId) {
        validatorId_ = validatorId;
    }

    public void setValidatorClass(String validatorClassName) {
        validatorClass_ = validatorClassName;
    }

    public String getValidatorId() {
        return validatorId_;
    }

    public String getValidatorClass() {
        return validatorClass_;
    }

    public void addAttributeElement(AttributeElement attribute) {
        attributeElements_.add(attribute);
    }

    public void addPropertyElement(PropertyElement property) {
        propertyElements_.add(property);
    }

    public List getAttributeElements() {
        return attributeElements_;
    }

    public List getPropertyElements() {
        return propertyElements_;
    }
    

}
