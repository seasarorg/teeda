/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeAlreadyRegisteredException;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.scope.ScopeTranslator;

/**
 * @author shot
 */
public class ScopeManagerImpl implements ScopeManager {

    private ScopeTranslator translator_;

    private static final Map SCOPES = new HashMap();

    private static final Map DEFAULT_SCOPES;

    static {
        SCOPES.put(Scope.APPLICATION.getScopeKey(), Scope.APPLICATION);
        SCOPES.put(Scope.SESSION.getScopeKey(), Scope.SESSION);
        SCOPES.put(Scope.REQUEST.getScopeKey(), Scope.REQUEST);
        SCOPES.put(Scope.NONE.getScopeKey(), Scope.NONE);
        Map map = new HashMap();
        map.put(Scope.APPLICATION.getScopeKey(), Scope.APPLICATION);
        map.put(Scope.SESSION.getScopeKey(), Scope.SESSION);
        map.put(Scope.REQUEST.getScopeKey(), Scope.REQUEST);
        map.put(Scope.NONE.getScopeKey(), Scope.NONE);
        DEFAULT_SCOPES = Collections.unmodifiableMap(map);
    }

    public ScopeManagerImpl() {
    }

    public Scope getScope(String scopeKey) {
        if (scopeKey == null) {
            throw new IllegalArgumentException();
        }
        return (Scope) SCOPES.get(scopeKey);
    }

    public void addScope(Scope scope, Object outerComponentScope)
            throws ScopeAlreadyRegisteredException {
        String scopeKey = scope.getScopeKey();
        if (SCOPES.containsKey(scopeKey)) {
            throw new ScopeAlreadyRegisteredException(new Object[] { scopeKey });
        }
        SCOPES.put(scopeKey, scope);
        if (translator_ != null) {
            translator_.addScope(scope, outerComponentScope);
        }
    }

    public boolean isDefaultScope(Scope scope) {
        return DEFAULT_SCOPES.containsValue(scope);
    }

    public void setScopeTranslator(ScopeTranslator translator) {
        translator_ = translator;
    }

    public ScopeTranslator getScopeTranslator() {
        return translator_;
    }

}
