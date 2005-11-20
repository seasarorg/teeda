package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;


public class ServletInitParameterMap extends
        AbstractUnmodifiableExternalContextMap {

    private final ServletContext context_;
    public ServletInitParameterMap(final ServletContext context){
        context_ = context;
    }
    
    protected Object getAttribute(String key) {
        return context_.getInitParameter(key);
    }

    protected Enumeration getAttributeNames() {
        return context_.getInitParameterNames();
    }

}
