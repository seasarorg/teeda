package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.element.ManagedBeanElement;

public abstract class ManagedBeanAssembler extends AbstractJsfAssembler {

    private Map managedBeans_ = Collections.EMPTY_MAP;
    
    public ManagedBeanAssembler(Map managedBeans){
        if(managedBeans == null){
            throw new IllegalArgumentException("managed bean");
        }
        isAllSuitableJsfElement(managedBeans.values(), ManagedBeanElement.class);
        managedBeans_ = managedBeans;
        setupBeforeAssemble();
    }
    
    protected final Map getManagedBeans(){
        return managedBeans_;
    }
}
