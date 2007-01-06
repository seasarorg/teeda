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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.Iterator;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.exception.ManagedBeanDuplicateRegisterException;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultManagedBeanAssembler extends ManagedBeanAssembler {

    private ManagedBeanFactory managedBeanFactory;

    private ScopeManager scopeManager;

    public DefaultManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void setupBeforeAssemble() {
        managedBeanFactory = (ManagedBeanFactory) DIContainerUtil
                .getComponent(ManagedBeanFactory.class);
        scopeManager = (ScopeManager) DIContainerUtil
                .getComponent(ScopeManager.class);
    }

    public void assemble() {
        for (Iterator itr = IteratorUtil.getEntryIterator(getManagedBeans()); itr
                .hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            ManagedBeanElement element = (ManagedBeanElement) entry.getValue();
            String mbName = (String) entry.getKey();
            String mbClassName = element.getManagedBeanClass();
            String mbScope = element.getManagedBeanScope();

            assertNotRegisteredYet(mbName);

            Scope scope = getScopeManager().getScope(mbScope);
            Class clazz = ClassUtil.forName(mbClassName);
            ComponentDef cDef = new ComponentDefImpl(clazz, mbName);
            registerManagedBean(cDef, scope);
        }
    }

    protected void registerManagedBean(ComponentDef componentDef, Scope scope) {
        getManagedBeanFactory().registerManagedBean(componentDef, scope);
    }

    protected ManagedBeanFactory getManagedBeanFactory() {
        return this.managedBeanFactory;
    }

    protected ScopeManager getScopeManager() {
        return this.scopeManager;
    }

    private void assertNotRegisteredYet(String managedBeanName) {
        Object managedBean = managedBeanFactory.getManagedBean(managedBeanName);
        if (managedBean != null) {
            throw new ManagedBeanDuplicateRegisterException(managedBeanName);
        }
    }

}
