/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.internal.scope.PageScope;
import javax.faces.internal.scope.RedirectScope;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author shot
 */
public class ScopeValueHelper {

    private ScopeValueHelper() {
    }

    public static Map getSubApplicationScopeValues(FacesContext context) {
        Map scopeContext = SubApplicationScope.getContext(context);
        if (scopeContext == null) {
            return null;
        }
        return (Map) scopeContext.get(PagePersistence.SUBAPPLICATION_SCOPE_KEY);
    }

    public static Map getOrCreateSubApplicationScopeValues(FacesContext context) {
        Map scopeContext = SubApplicationScope.getOrCreateContext(context);
        Map values = (Map) scopeContext
                .get(PagePersistence.SUBAPPLICATION_SCOPE_KEY);
        if (values == null) {
            values = new HashMap();
            scopeContext.put(PagePersistence.SUBAPPLICATION_SCOPE_KEY, values);
        }
        return values;
    }

    public static Map getRedirectScopeValues(FacesContext context) {
        Map scopeContext = RedirectScope.getContext(context);
        if (scopeContext == null) {
            return null;
        }
        return (Map) scopeContext.get(PagePersistence.REDIRECT_SCOPE_KEY);
    }

    public static Map getOrCreateRedirectScopeValues(FacesContext context) {
        Map scopeContext = RedirectScope.getOrCreateContext(context);
        Map values = (Map) scopeContext.get(PagePersistence.REDIRECT_SCOPE_KEY);
        if (values == null) {
            values = new HashMap();
            scopeContext.put(PagePersistence.REDIRECT_SCOPE_KEY, values);
        }
        return values;
    }

    public static Map getPageScopeValues(FacesContext context) {
        Map scopeContext = PageScope.getContext(context);
        if (scopeContext == null) {
            return null;
        }
        return (Map) scopeContext.get(PagePersistence.PAGE_SCOPE_KEY);
    }

    public static Map getOrCreatePageScopeValues(FacesContext context) {
        Map scopeContext = PageScope.getOrCreateContext(context);
        Map values = (Map) scopeContext.get(PagePersistence.PAGE_SCOPE_KEY);
        if (values == null) {
            values = new HashMap();
            scopeContext.put(PagePersistence.PAGE_SCOPE_KEY, values);
        }
        return values;
    }

    public static void removeIfDoFinish(final String methodName,
            final FacesContext context) {
        if (methodName == null) {
            return;
        }
        if (methodName.startsWith(ExtensionConstants.DO_FINISH) ||
                methodName.startsWith(ExtensionConstants.DO_ONCE_FINISH)) {
            SubApplicationScope.removeContext(context);
        }
    }
}
