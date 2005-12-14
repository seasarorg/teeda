package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.util.ClassUtil;


public class VariableResolverAssembler implements JsfAssembler {

    private String resolverName_;
    
    private Application application_;
    
    public VariableResolverAssembler(String resolverName, Application application){
        resolverName_ = resolverName;
        application_ = application;
    }
    
    public void assemble() {
        VariableResolver previous = application_.getVariableResolver();
        VariableResolver resolver = 
            (VariableResolver)ClassUtil.createMarshalInstance(resolverName_, VariableResolver.class, previous);
        application_.setVariableResolver(resolver);

    }

}
