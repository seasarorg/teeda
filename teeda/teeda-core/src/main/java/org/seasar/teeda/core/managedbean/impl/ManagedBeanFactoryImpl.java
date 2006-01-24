/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.DestroyMethodDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;

/**
 * @author shot
 */
public class ManagedBeanFactoryImpl implements ManagedBeanFactory {

	private ManagedBeanScopeSaver managedBeanScopeSaver_;

	private ScopeManager scopeManager_;

	public ManagedBeanFactoryImpl() {
	}

	public Object getManagedBean(String name) {
		S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
		try{
			return container.getComponent(name);
		}catch(ComponentNotFoundRuntimeException e){
			return null;
		}
	}

	public void setManagedBean(String name, Class type, Scope scope) {
        S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
		ComponentDef componentDef = new ComponentDefImpl(type, name);
		setInstanceType(componentDef, scope);
		container.register(componentDef);
	}

	public void setManagedBean(String name, Class type, Scope scope,
			String initMethodName, String destroyMethodName) {
        S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
		ComponentDef componentDef = new ComponentDefImpl(type, name);
		setInstanceType(componentDef, scope);
		componentDef.addInitMethodDef(new InitMethodDefImpl(initMethodName));
		componentDef.addDestroyMethodDef(new DestroyMethodDefImpl(destroyMethodName));
		container.register(componentDef);
	}

    public Scope getManagedBeanScope(String name){
        S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
        ComponentDef componentDef = null;
        try{
        	componentDef = container.getComponentDef(name);
        }catch(ComponentNotFoundRuntimeException e){
        	return null;
        }
        Scope scope = null;
        if(componentDef != null){
            InstanceDef instanceDef = componentDef.getInstanceDef();
            scope = scopeManager_.getScopeTranslator().toScope(instanceDef);
        }
        return scope;
    }

    public void setScopeManager(ScopeManager scopeManager){
    	scopeManager_ = scopeManager;
    }
    
    public ScopeManager getScopeManager(){
    	return scopeManager_;
    }
    
	public void setManagedBeanScopeSaver(ManagedBeanScopeSaver managedBeanScopeSaver) {
		managedBeanScopeSaver_ = managedBeanScopeSaver;
	}

	public ManagedBeanScopeSaver getManagedBeanScopeSaver() {
		return managedBeanScopeSaver_;
	}

	private void setInstanceType(ComponentDef componentDef, Scope scope){
		InstanceDef instanceDef = 
			(InstanceDef)scopeManager_.getScopeTranslator().toExternalComponentScope(scope);
		componentDef.setInstanceDef(instanceDef);
	}

}
