/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package javax.faces.internal.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.WindowIdUtil;

import org.seasar.framework.util.LruHashMap;

/**
 * @author higa
 *
 */
public class VariableScope {

    private String key;

    private int windowSize = 20;

    public VariableScope(String key) {
        this.key = key;
    }

    public VariableScope(String key, int windowSize) {
        this.key = key;
        this.windowSize = windowSize;
    }

    public Map getContext(FacesContext context) throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        Map contexts = getContexts(extCtx);
        String wid = WindowIdUtil.getWindowId(extCtx);
        return (Map) contexts.get(wid);
    }

    public synchronized Map getOrCreateContext(FacesContext context)
            throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        Map contexts = getContexts(extCtx);
        String wid = WindowIdUtil.getWindowId(extCtx);
        Map ctx = (Map) contexts.get(wid);
        if (ctx == null) {
            ctx = new HashMap();
            contexts.put(wid, ctx);
        }
        return ctx;
    }

    public void removeContext(FacesContext context) throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        String wid = WindowIdUtil.getWindowId(extCtx);
        removeContext(context, wid);
    }

    public void removeContext(FacesContext context, String wid)
            throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        Map contexts = getContexts(extCtx);
        contexts.remove(wid);
    }

    public void clearContext(FacesContext context) throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        String wid = WindowIdUtil.getWindowId(extCtx);
        clearContext(context, wid);
    }

    public void clearContext(FacesContext context, String wid)
            throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        Map contexts = getContexts(extCtx);
        Map m = (Map) contexts.get(wid);
        if (m != null) {
            m.clear();
        }
    }

    protected synchronized Map getContexts(ExternalContext externalContext)
            throws FacesException {
        Map sessionMap = externalContext.getSessionMap();
        Map contexts = (Map) sessionMap.get(key);
        if (contexts == null) {
            contexts = Collections.synchronizedMap(new LruHashMap(windowSize));
            sessionMap.put(key, contexts);
        }
        return contexts;
    }
}