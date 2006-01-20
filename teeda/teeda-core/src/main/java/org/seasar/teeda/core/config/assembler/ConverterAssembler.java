package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.element.ConverterElement;

public abstract class ConverterAssembler extends AbstractJsfAssembler {

    private Map convertersByClass_ = Collections.EMPTY_MAP;
    
    private Map convertersById_ = Collections.EMPTY_MAP;
    
    private static final Class TARGET_CLASS = ConverterElement.class;
    
    public ConverterAssembler(Map convertersByClass, Map convertersById){
        if(convertersByClass == null || convertersById == null){
            throw new IllegalArgumentException("converter maps");
        }
        isAllSuitableJsfElement(convertersByClass.values(), TARGET_CLASS);
        isAllSuitableJsfElement(convertersById.values(), TARGET_CLASS);
        convertersByClass_ = convertersByClass;
        convertersById_ = convertersById;
        setupBeforeAssemble();
    }
    
    protected final Map getConvertersByClass(){
        return convertersByClass_;
    }
    
    protected final Map getConvertersById(){
        return convertersById_;
    }
}
