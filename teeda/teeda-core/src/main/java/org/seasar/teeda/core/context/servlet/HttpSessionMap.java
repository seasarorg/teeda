/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.util.EmptyEnumeration;
import org.seasar.teeda.core.context.AbstractExternalContextMap;

/**
 * @author Shinpei Ohtani
 */
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
