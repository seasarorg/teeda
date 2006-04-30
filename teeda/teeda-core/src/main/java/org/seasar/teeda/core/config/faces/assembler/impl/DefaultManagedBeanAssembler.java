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

import java.util.Iterator;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.annotation.factory.ValidatorAnnotationHandlerFactory;
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

    private ValidatorAnnotationHandlerFactory annotationHandlerFactory_;

    public DefaultManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void setupBeforeAssemble() {
        managedBeanFactory_ = (ManagedBeanFactory) DIContainerUtil
                .getComponent(ManagedBeanFactory.class);
        scopeManager_ = (ScopeManager) DIContainerUtil
                .getComponent(ScopeManager.class);
        annotationHandlerFactory_ = (ValidatorAnnotationHandlerFactory) DIContainerUtil
                .getComponentNoException(ValidatorAnnotationHandlerFactory.class);
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

            Scope scope = scopeManager_.getScope(mbScope);
            Class clazz = ClassUtil.forName(mbClassName);
            ComponentDef cDef = new ComponentDefImpl(clazz, mbName);
            managedBeanFactory_.setManagedBean(cDef, scope);
            if(annotationHandlerFactory_ != null) {
                annotationHandlerFactory_.getAnnotationHandler().registerValidator(
                        cDef);
            }
        }
    }

    private void assertNotRegisteredYet(String managedBeanName) {
        Object managedBean = managedBeanFactory_
                .getManagedBean(managedBeanName);
        if (managedBean != null) {
            throw new ManagedBeanDuplicateRegisterException(managedBeanName);
        }
    }
}
