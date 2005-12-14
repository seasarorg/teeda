package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.element.ValidatorElement;


public abstract class ValidatorsAssembler extends AbstractJsfAssembler {

    private Map validators_ = Collections.EMPTY_MAP;
    
    public ValidatorsAssembler(Map validators){
        isAllSuitableJsfElement(validators.values(), ValidatorElement.class);
        validators_ = validators;
        setupChildAssembler();
    }
    
    protected final Map getValidators(){
        return validators_;
    }
}