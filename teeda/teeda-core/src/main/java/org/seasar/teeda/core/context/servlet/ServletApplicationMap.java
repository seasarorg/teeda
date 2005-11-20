package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.seasar.teeda.core.context.AbstractExternalContextMap;


public class ServletApplicationMap extends AbstractExternalContextMap {

    private ServletContext context_;
    public ServletApplicationMap(ServletContext context){
        if(context == null){
            throw new NullPointerException();
        }
        context_ = context;
    }

    protected Object getAttribute(String key) {
        return context_.getAttribute(key);
    }

    protected Enumeration getAttributeNames() {
        return context_.getAttributeNames();
    }

    protected void removeAttribute(String key) {
        context_.removeAttribute(key);
    }

    protected void setAttribute(String key, Object value) {
        context_.setAttribute(key, value);
    }

}
