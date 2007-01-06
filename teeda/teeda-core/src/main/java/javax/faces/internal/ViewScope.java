/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.LruHashMap;

/**
 * @author higa
 *
 */
public abstract class ViewScope {

    private static final int WINDOW_SIZE = 10;

    private static final String CONTEXTS_KEY = ViewScope.class.getName();

    protected ViewScope() {
    }

    public static Map getContext(FacesContext context) throws FacesException {
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

    public static void removeContext(FacesContext context, String wid)
            throws FacesException {
        ExternalContext extCtx = context.getExternalContext();
        Map contexts = getContexts(extCtx);
        contexts.remove(wid);
    }

    protected static Map getContexts(ExternalContext externalContext)
            throws FacesException {
        Map sessionMap = externalContext.getSessionMap();
        Map contexts = (Map) sessionMap.get(CONTEXTS_KEY);
        if (contexts == null) {
            contexts = new LruHashMap(WINDOW_SIZE);
            sessionMap.put(CONTEXTS_KEY, contexts);
        }
        return contexts;
    }
}