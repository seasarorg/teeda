package org.seasar.teeda.core.context.servlet;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;


public class ServletRequestHeaderValuesMap extends
        AbstractUnmodifiableExternalContextMap {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final HttpServletRequest request_;
    public ServletRequestHeaderValuesMap(final HttpServletRequest request){
        request_ = request;
    }

    protected Object getAttribute(String key) {
        Enumeration e = request_.getHeaders(key);
        return toStringArray(e);
    }

    protected Enumeration getAttributeNames() {
        return request_.getHeaderNames();
    }

    private String[] toStringArray(Enumeration e){
        if(e == null){
            return EMPTY_STRING_ARRAY;
        }
        List list = new ArrayList();
        while(e.hasMoreElements()){
            list.add(e.nextElement());
        }
        return (String[])list.toArray(new String[list.size()]);
    }
}
