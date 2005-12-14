package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Collections;

import org.seasar.teeda.core.config.element.LifecycleElement;


public abstract class LifecycleAssembler extends AbstractJsfAssembler {

    private List lifecycles_ = Collections.EMPTY_LIST;
    
    public LifecycleAssembler(List lifecycles){
        isAllSuitableJsfElement(lifecycles, LifecycleElement.class);
        lifecycles_ = lifecycles;
        setupChildAssembler();
    }
     
    protected final List getLifecycles(){
        return lifecycles_;
    }
        
}
