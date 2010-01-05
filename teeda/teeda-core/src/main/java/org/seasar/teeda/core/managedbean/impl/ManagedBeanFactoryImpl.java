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
package org.seasar.teeda.core.managedbean.impl;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.DestroyMethodDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;

/**
 * @author shot
 *
 * TODO need support ValueBinding for list-entries, map-entries, and managed-property
 */
public class ManagedBeanFactoryImpl implements ManagedBeanFactory {

    private ManagedBeanScopeSaver managedBeanScopeSaver;

    private ScopeManager scopeManager;

    private S2Container container;

    public ManagedBeanFactoryImpl() {
    }

    public Object getManagedBean(String name) {
        if (container.hasComponentDef(name)) {
            return container.getComponent(name);
        } else {
            return null;
        }
    }

    public void registerManagedBean(String name, Class type, Scope scope) {
        ComponentDef componentDef = new ComponentDefImpl(type, name);
        registerManagedBean(componentDef, scope);
    }

    public void registerManagedBean(String name, Class type, Scope scope,
            String initMethodName, String destroyMethodName) {
        ComponentDef componentDef = new ComponentDefImpl(type, name);
        componentDef.addInitMethodDef(new InitMethodDefImpl(initMethodName));
        componentDef.addDestroyMethodDef(new DestroyMethodDefImpl(
                destroyMethodName));
        registerManagedBean(componentDef, scope);
    }

    public void registerManagedBean(ComponentDef componentDef, Scope scope) {
        setManagedBean(container, componentDef, scope);
    }

    protected void setManagedBean(S2Container container,
            ComponentDef componentDef, Scope scope) {
        setInstanceTypeFor(componentDef, scope);
        container.register(componentDef);
    }

    public Scope getManagedBeanScope(String name) {
        Scope scope = null;
        if (container.hasComponentDef(name)) {
            InstanceDef instanceDef = container.getComponentDef(name)
                    .getInstanceDef();
            scope = scopeManager.getScopeTranslator().toScope(instanceDef);
        }
        return scope;
    }

    public void setScopeManager(ScopeManager scopeManager) {
        this.scopeManager = scopeManager;
    }

    public ScopeManager getScopeManager() {
        return scopeManager;
    }

    public void setManagedBeanScopeSaver(
            ManagedBeanScopeSaver managedBeanScopeSaver) {
        this.managedBeanScopeSaver = managedBeanScopeSaver;
    }

    public ManagedBeanScopeSaver getManagedBeanScopeSaver() {
        return managedBeanScopeSaver;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
        if (container != null) {
            this.container = container.getRoot();
        }
    }

    private void setInstanceTypeFor(ComponentDef componentDef, Scope scope) {
        InstanceDef instanceDef = (InstanceDef) scopeManager
                .getScopeTranslator().toExternalComponentScope(scope);
        componentDef.setInstanceDef(instanceDef);
    }

}
