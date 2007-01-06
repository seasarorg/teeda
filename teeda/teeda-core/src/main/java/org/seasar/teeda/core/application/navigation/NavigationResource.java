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
package org.seasar.teeda.core.application.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shot
 */
public class NavigationResource {

    private static final String NAVIGATON_CONTEXTS = NavigationResource.class
            .getName()
            + "_NAVIGATION_CONTEXTS";

    private static final String WILDCARD_NAVIGATION_CONTEXTS = NavigationResource.class
            .getName()
            + "_WILDCARD_NAVIGATION_CONTEXTS";

    private static final String DEFAULT_NAVIGATION_CONTEXTS = NavigationResource.class
            .getName()
            + "_DEFAULT_NAVIGATION_CONTEXTS";
    
    private static Map cache = new HashMap();

    private NavigationResource() {
    }

    public static void addNavigationContext(NavigationContext navigationContext) {
        if (navigationContext == null) {
            throw new IllegalArgumentException("navigationContext");
        }
        String fromViewId = navigationContext.getFromViewId();
        if (navigationContext.isWildCardMatch()) {
            //if default match
            if (NavigationContext.WILDCARD.equals(fromViewId)) {
                storeNavigationContext(fromViewId,
                        navigationContext, DEFAULT_NAVIGATION_CONTEXTS);
            } else {
                storeNavigationContext(fromViewId,
                        navigationContext, WILDCARD_NAVIGATION_CONTEXTS);
            }

        } else {
            storeNavigationContext(fromViewId,
                    navigationContext, NAVIGATON_CONTEXTS);
        }
    }
    
    public static void removeNavigationContext(String fromViewId) {
        if (fromViewId == null) {
            throw new IllegalArgumentException(fromViewId);
        }
        Map navContextsMap = (Map) cache.get(NAVIGATON_CONTEXTS);
        if (navContextsMap == null) {
            return;
        }
        navContextsMap.remove(fromViewId);
    }

    public static Map getNavigationContexts() {
        return (Map) cache.get(NAVIGATON_CONTEXTS);
    }

    public static Map getWildCardMatchNavigationContexts() {
        return (Map) cache.get(WILDCARD_NAVIGATION_CONTEXTS);
    }

    public static Map getDefaultMatchNavigationContexts() {
        return (Map) cache.get(DEFAULT_NAVIGATION_CONTEXTS);
    }
    
    public static void removeAll() {
        cache.clear();
    }

    protected static void storeNavigationContext(
            String fromViewId, NavigationContext navContext,
            String applicationKey) {
        Map navContextsMap = (Map) cache.get(applicationKey);
        if (navContextsMap == null) {
            navContextsMap = Collections.synchronizedMap(new HashMap());
            cache.put(applicationKey, navContextsMap);
        }
        List navContextList = (List) navContextsMap.get(fromViewId);
        if (navContextList == null) {
            navContextList = new ArrayList();
        }
        navContextList.add(navContext);
        navContextsMap.put(fromViewId, navContextList);
    }

}