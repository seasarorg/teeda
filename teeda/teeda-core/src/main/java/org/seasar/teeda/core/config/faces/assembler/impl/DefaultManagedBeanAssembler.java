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

import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;

public class DefaultManagedBeanAssembler extends ManagedBeanAssembler {

    private ManagedBeanFactory managedBeanFactory_;

    private ScopeManager scopeManager_;

    public DefaultManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void setupBeforeAssemble() {
        managedBeanFactory_ = 
            (ManagedBeanFactory)DIContainerUtil.getComponent(ManagedBeanFactory.class);
        scopeManager_ = 
            (ScopeManager)DIContainerUtil.getComponent(ScopeManager.class);
    }

    public void assemble() {
        String managedBeanName = null;
        ManagedBeanElement element = null;
        for (Iterator itr = IteratorUtil.getEntryIterator(getManagedBeans()); itr
                .hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            managedBeanName = (String)entry.getKey();
            element = (ManagedBeanElement)entry.getValue();
            Class mbClass = ClassUtil.forName(element.getManagedBeanClass());
            Scope scope = scopeManager_.getScope(element.getManagedBeanScope());
            managedBeanFactory_.setManagedBean(managedBeanName, mbClass, scope);
        }
    }

}
