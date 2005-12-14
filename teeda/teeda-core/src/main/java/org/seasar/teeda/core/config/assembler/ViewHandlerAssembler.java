package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;

import org.seasar.teeda.core.util.ClassUtil;

public class ViewHandlerAssembler implements JsfAssembler {

    private String handlerName_;
    
    private Application application_;
    
    public ViewHandlerAssembler(String handlerName, Application application){
        handlerName_ = handlerName;
        application_ = application;
    }
    
    public void assemble() {
        ViewHandler previous = application_.getViewHandler();
        ViewHandler handler = 
            (ViewHandler)ClassUtil.createMarshalInstance(handlerName_, ViewHandler.class, previous);
        application_.setViewHandler(handler);
    }

}
