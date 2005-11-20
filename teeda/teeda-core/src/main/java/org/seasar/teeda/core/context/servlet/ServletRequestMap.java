package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.seasar.teeda.core.context.AbstractExternalContextMap;


public class ServletRequestMap extends AbstractExternalContextMap {

    private ServletRequest request_;
    public ServletRequestMap(ServletRequest request){
        request_ = request;
    }
    
    protected Object getAttribute(String key) {
        return request_.getAttribute(key);
    }

    protected void setAttribute(String key, Object value) {
        request_.setAttribute(key, value);
    }

    protected Enumeration getAttributeNames() {
        return request_.getAttributeNames();
    }

    protected void removeAttribute(String key) {
        request_.removeAttribute(key);
    }

}
