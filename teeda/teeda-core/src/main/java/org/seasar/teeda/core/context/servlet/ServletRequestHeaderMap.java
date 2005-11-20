package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;


public class ServletRequestHeaderMap extends
        AbstractUnmodifiableExternalContextMap {

    private final HttpServletRequest request_;
    public ServletRequestHeaderMap(final HttpServletRequest request){
        request_ = request;
    }

    protected Object getAttribute(String key) {
        return request_.getHeader(key);
    }

    protected Enumeration getAttributeNames() {
        return request_.getHeaderNames();
    }

}
