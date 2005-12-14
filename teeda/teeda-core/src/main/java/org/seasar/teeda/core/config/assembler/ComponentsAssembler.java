package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.element.ComponentElement;


public abstract class ComponentsAssembler extends AbstractJsfAssembler {

    private Map components_ = Collections.EMPTY_MAP;
    
    public ComponentsAssembler(Map components){
        isAllSuitableJsfElement(components.values(), ComponentElement.class);
        components_ = components;
        setupChildAssembler();
    }
    
    protected final Map getComponents(){
        return components_;
    }
}
