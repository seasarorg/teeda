package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.el.PropertyResolver;

import org.seasar.teeda.core.util.ClassUtil;


public class PropertyResolverAssembler implements JsfAssembler {

    private String resolverName_;
    
    private Application application_;
    
    public PropertyResolverAssembler(String resolverName, Application application){
        resolverName_ = resolverName;
        application_ = application;
    }
    
    public void assemble() {
        PropertyResolver previous = application_.getPropertyResolver();
        PropertyResolver resolver = 
            (PropertyResolver)ClassUtil.createMarshalInstance(resolverName_, PropertyResolver.class, previous);
        application_.setPropertyResolver(resolver);

    }

}
