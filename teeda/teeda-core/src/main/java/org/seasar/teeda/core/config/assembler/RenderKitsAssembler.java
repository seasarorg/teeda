package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.element.RenderKitElement;


public abstract class RenderKitsAssembler extends AbstractJsfAssembler {

    private Map renderKits_ = Collections.EMPTY_MAP;
    
    public RenderKitsAssembler(Map renderKits){
        isAllSuitableJsfElement(renderKits.values(), RenderKitElement.class);
        renderKits_ = renderKits;
        setupChildAssembler();
    }
     
    protected final Map getRenderKits(){
        return renderKits_;
    }
        
}
