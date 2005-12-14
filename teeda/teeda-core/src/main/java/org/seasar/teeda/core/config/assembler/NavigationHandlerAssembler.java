package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;

import org.seasar.teeda.core.util.ClassUtil;

public class NavigationHandlerAssembler implements JsfAssembler {

    private String handlerName_;
    
    private Application application_;
    
    public NavigationHandlerAssembler(String handlerName, Application application){
        handlerName_ = handlerName;
        application_ = application;
    }
    
    public void assemble() {
        NavigationHandler previous = application_.getNavigationHandler();
        NavigationHandler handler = 
            (NavigationHandler)ClassUtil.createMarshalInstance(handlerName_, NavigationHandler.class, previous);
        application_.setNavigationHandler(handler);
    }

}
