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
package org.seasar.teeda.core.managedbean.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.managedbean.IllegalManagedBeanScopeException;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeAlreadyRegisteredException;
import org.seasar.teeda.core.scope.ScopeSaver;
import org.seasar.teeda.core.scope.impl.ApplicationScopeSaver;
import org.seasar.teeda.core.scope.impl.NoneScopeSaver;
import org.seasar.teeda.core.scope.impl.RequestScopeSaver;
import org.seasar.teeda.core.scope.impl.SessionScopeSaver;

/**
 * @author shot
 */
public class ManagedBeanScopeSaverImpl implements ManagedBeanScopeSaver {

    private static Map scopeSavers_ = new HashMap();

    static {
        scopeSavers_.put(Scope.APPLICATION, new ApplicationScopeSaver());
        scopeSavers_.put(Scope.REQUEST, new RequestScopeSaver());
        scopeSavers_.put(Scope.SESSION, new SessionScopeSaver());
        scopeSavers_.put(Scope.NONE, new NoneScopeSaver());
    }

    public ManagedBeanScopeSaverImpl() {
    }

    public void saveToScope(FacesContext context, Scope scope, String key,
            Object value) {
        if (scope == null) {
            throw new IllegalArgumentException();
        }
        ScopeSaver saver = (ScopeSaver) scopeSavers_.get(scope);
        if (saver != null) {
            saver.saveToScope(context, key, value);
        } else {
            throw new IllegalManagedBeanScopeException(key, value);
        }
    }

    public void addScope(Scope scope, ScopeSaver saver)
            throws ScopeAlreadyRegisteredException {
        if (scopeSavers_.containsKey(scope)) {
            throw new ScopeAlreadyRegisteredException(new Object[] { scope
                    .getScopeKey() });
        }
        scopeSavers_.put(scope, saver);
    }

}
