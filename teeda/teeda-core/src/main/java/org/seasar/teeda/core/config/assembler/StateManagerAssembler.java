package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.application.StateManager;

import org.seasar.teeda.core.util.ClassUtil;


public class StateManagerAssembler implements JsfAssembler {

    private String stateManagerName_;
    
    private Application application_;
    
    public StateManagerAssembler(String stateManagerName, Application application){
        stateManagerName_ = stateManagerName;
        application_ = application;
    }
    
    public void assemble() {
        StateManager previous = application_.getStateManager();
        StateManager stateManager = 
            (StateManager)ClassUtil.createMarshalInstance(stateManagerName_, StateManager.class, previous);
        application_.setStateManager(stateManager);
    }
}
