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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.exception.ManagedBeanDuplicateRegisterException;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultManagedBeanAssembler extends ManagedBeanAssembler {

    private ManagedBeanFactory managedBeanFactory_;

    private ScopeManager scopeManager_;

    public DefaultManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void setupBeforeAssemble() {
        managedBeanFactory_ = (ManagedBeanFactory) DIContainerUtil
                .getComponent(ManagedBeanFactory.class);
        scopeManager_ = (ScopeManager) DIContainerUtil
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
        return managedBeanFactory_;
    }

    protected ScopeManager getScopeManager() {
        return scopeManager_;
    }

    private void assertNotRegisteredYet(String managedBeanName) {
        Object managedBean = managedBeanFactory_
                .getManagedBean(managedBeanName);
        if (managedBean != null) {
            throw new ManagedBeanDuplicateRegisterException(managedBeanName);
        }
    }

    protected static class ManagedBeans {
        private List beans_ = new ArrayList();

        public ManagedBeans() {
        }

        public void addManagedBeanInfo(String name, Scope scope, Class beanClass) {
            beans_.add(new ManagedBean(name, scope, beanClass));
        }

        public boolean hasNext() {
            return beans_.iterator().hasNext();
        }

        public ManagedBean getManagedBean() {
            if (!hasNext()) {
                return null;
            }
            return (ManagedBean) beans_.iterator().next();
        }
    }

    private static class ManagedBean {
        private String name_;

        private Scope scope_;

        private Class beanClass_;

        public ManagedBean(String name, Scope scope, Class beanClass) {
            name_ = name;
            scope_ = scope;
            beanClass_ = beanClass;
        }

        public Class getBeanClass() {
            return beanClass_;
        }

        public void setBeanClass(Class beanClass) {
            beanClass_ = beanClass;
        }

        public String getName() {
            return name_;
        }

        public void setName(String name) {
            name_ = name;
        }

        public Scope getScope() {
            return scope_;
        }

        public void setScope(Scope scope) {
            scope_ = scope;
        }
    }
}
