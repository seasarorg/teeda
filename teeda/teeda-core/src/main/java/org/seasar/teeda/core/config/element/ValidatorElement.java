package org.seasar.teeda.core.config.element;

public interface ValidatorElement extends JsfConfig, AttributeHolder, PropertyHolder{
    
    public void setValidatorId(String validatorId);

    public void setValidatorClass(String validatorClass);

    public String getValidatorId();

    public String getValidatorClass();

}