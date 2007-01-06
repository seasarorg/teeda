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
package org.seasar.teeda.core.scope.impl;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author shot
 * @author yone
 */
public class DispatchScopeFactory {

    public static final String DISPATCH_SCOPE_KEY = DispatchScopeFactory.class
            .getName()
            + ".DISPATCH_SCOPE_KEY";

    public static void create() {
        Map requestMap = getRequestMap();
        requestMap.put(DISPATCH_SCOPE_KEY, new DispatchScope());
    }

    public static DispatchScope getDispatchScope() {
        if (!initialized()) {
            create();
        }
        return (DispatchScope) getRequestMap().get(DISPATCH_SCOPE_KEY);
    }

    private static boolean initialized() {
        Map requestMap = getRequestMap();
        if (requestMap == null) {
            return false;
        }
        return requestMap.containsKey(DISPATCH_SCOPE_KEY);
    }

    public static void destroy() {
        Map requestMap = getRequestMap();
        if (requestMap == null) {
            return;
        }
        getRequestMap().remove(DISPATCH_SCOPE_KEY);
        create();
    }

    protected static Map getRequestMap() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return null;
        }
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.getRequestMap();
    }

}
