package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;


public class ServletRequestParameterValuesMap extends
        AbstractUnmodifiableExternalContextMap {

    private final ServletRequest request_;
    public ServletRequestParameterValuesMap(final ServletRequest request){
        request_ = request;
    }

    protected Object getAttribute(String key) {
        return request_.getParameterValues(key);
    }

    protected Enumeration getAttributeNames() {
        return request_.getParameterNames();
    }

}
