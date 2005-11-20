package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.util.EmptyEnumeration;
import org.seasar.teeda.core.context.AbstractExternalContextMap;


public class HttpSessionMap extends AbstractExternalContextMap {

    private static final Enumeration EMPTY_ENUMERATION = new EmptyEnumeration();
    private HttpServletRequest request_;
    public HttpSessionMap(HttpServletRequest request){
        request_ = request;
    }
    
    protected Object getAttribute(String key) {
        HttpSession session = getSession();
        return (session != null) ? session.getAttribute(key) : null;
    }

    protected void setAttribute(String key, Object value) {
        request_.getSession(true).setAttribute(key, value);
    }

    protected Enumeration getAttributeNames() {
        HttpSession session = getSession();
        return (session != null) ? 
                session.getAttributeNames() : EMPTY_ENUMERATION;
    }

    protected void removeAttribute(String key) {
        HttpSession session = getSession();
        if(session != null){
            session.removeAttribute(key);
        }
    }

    private HttpSession getSession(){
        return request_.getSession(false);
    }
    
}
