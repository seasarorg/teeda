package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.seasar.teeda.core.context.AbstractUnmodifiableExternalContextMap;


public class CookieMap extends AbstractUnmodifiableExternalContextMap {

    private static final Cookie[] EMPTY_COOKIE = new Cookie[0];
    private final HttpServletRequest request_;
    public CookieMap(final HttpServletRequest request){
        request_ = request;
    }
    
    public boolean containsKey(Object key) {
        Cookie[] cookies = request_.getCookies();
        if(cookies != null){
           for(int i = 0;i < cookies.length;i++){
               Cookie cookie = cookies[i];
               if(cookie != null && cookie.getName().equals(key)){
                   return true;
               }
           }
        }
        return false;
    }
    
    public boolean containsValue(Object value) {
        if(value == null){
            return false;
        }
        Cookie[] cookies = request_.getCookies();
        if(cookies != null){
           for(int i = 0;i < cookies.length;i++){
               Cookie cookie = cookies[i];
               Object cookieValue = cookie.getValue();
               if(value.equals(cookieValue)){
                   return true;
               }
           }
        }
        return false;
    }
    
    public boolean isEmpty() {
        Cookie[] cookies = request_.getCookies();
        return cookies == null || cookies.length == 0;
    }
    
    public int size() {
        return getCookies().length;
    }
    
    protected Object getAttribute(String key) {
        Cookie[] cookies = request_.getCookies();
        if(cookies != null){
           for(int i = 0;i < cookies.length;i++){
               Cookie cookie = cookies[i];
               if(cookie != null && cookie.getName().equals(key)){
                   return cookie;
               }
           }
        }
        return null;
    }

    protected Enumeration getAttributeNames() {
        final Cookie[] cookies = getCookies();
        return new Enumeration(){
            private int index_ = 0;
            public boolean hasMoreElements() {
                return index_ < cookies.length;
            }

            public Object nextElement() {
                return cookies[index_++].getName();
            }
            
        };
    }

    private Cookie[] getCookies(){
        return (request_.getCookies() != null) ? request_.getCookies() : EMPTY_COOKIE;
    }
}
