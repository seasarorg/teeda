/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class NavigationContextFactory {

    private static final String NAVIGATON_CONTEXTS = NavigationContextFactory.class
            .getName()
            + "_NAVIGATION_CONTEXTS";

    private static final String WILDCARD_NAVIGATION_CONTEXTS = NavigationContextFactory.class
            .getName()
            + "_WILDCARD_NAVIGATION_CONTEXTS";

    private NavigationContextFactory() {
    }

    public static void addNavigationContext(ExternalContext externalContext,
            NavigationContext navigationContext) {
        if (externalContext == null || navigationContext == null) {
            throw new IllegalArgumentException();
        }
        Map applicationMap = externalContext.getApplicationMap();
        String fromViewId = navigationContext.getFromViewId();
        if (navigationContext.isWildCardMatch()) {
            Map wildCardMatchContexts = (Map) applicationMap
                    .get(WILDCARD_NAVIGATION_CONTEXTS);
            if (wildCardMatchContexts == null) {
                wildCardMatchContexts = Collections
                        .synchronizedMap(new HashMap());
                applicationMap.put(WILDCARD_NAVIGATION_CONTEXTS,
                        wildCardMatchContexts);
            }
            wildCardMatchContexts.put(fromViewId, navigationContext);
        } else {
            Map navigationContexts = (Map) applicationMap
                    .get(NAVIGATON_CONTEXTS);
            if (navigationContexts == null) {
                navigationContexts = Collections.synchronizedMap(new HashMap());
                applicationMap.put(NAVIGATON_CONTEXTS, navigationContexts);
            }
            navigationContexts.put(fromViewId, navigationContext);
        }
    }

    public static Map getNavigationContexts(FacesContext context) {
        Map appMap = context.getExternalContext().getApplicationMap();
        return (Map) appMap.get(NAVIGATON_CONTEXTS);
    }

    public static Map getWildCardMatchNavigationContexts(FacesContext context) {
        Map appMap = context.getExternalContext().getApplicationMap();
        return (Map) appMap.get(WILDCARD_NAVIGATION_CONTEXTS);
    }

}