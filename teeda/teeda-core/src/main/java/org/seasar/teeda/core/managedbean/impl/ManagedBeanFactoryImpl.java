package org.seasar.teeda.core.managedbean.impl;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.DestroyMethodDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;

public class ManagedBeanFactoryImpl implements ManagedBeanFactory {

	private ManagedBeanScopeSaver managedBeanScopeSaver_;

	private S2Container container_;

	private ScopeManager scopeManager_;
	
	private static final String TEEDA_MANAGEDBEAN_NAMESPACE = "teeda-managedbean";

	public ManagedBeanFactoryImpl() {
		container_ = new S2ContainerImpl();
		container_.setNamespace(TEEDA_MANAGEDBEAN_NAMESPACE);
		S2Container root = 
			(S2Container) SingletonS2ContainerFactory.getContainer();
		root.include(container_);
	}

	public Object getManagedBean(String name) {
		S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
		try{
			return container.getComponent(TEEDA_MANAGEDBEAN_NAMESPACE + "." + name);
		}catch(ComponentNotFoundRuntimeException e){
			return null;
		}
	}

	public void setManagedBean(String name, Class type, Scope scope) {
		ComponentDef componentDef = new ComponentDefImpl(type, name);
		setInstanceType(componentDef, scope);
		container_.register(componentDef);
	}

	public void setManagedBean(String name, Class type, Scope scope,
			String initMethodName, String destroyMethodName) {
		ComponentDef componentDef = new ComponentDefImpl(type, name);
		setInstanceType(componentDef, scope);
		componentDef.addInitMethodDef(new InitMethodDefImpl(initMethodName));
		componentDef.addDestroyMethodDef(new DestroyMethodDefImpl(destroyMethodName));
		container_.register(componentDef);
	}

    public Scope getManagedBeanScope(String name){
        S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
        ComponentDef componentDef = null;
        try{
        	componentDef = container.getComponentDef(TEEDA_MANAGEDBEAN_NAMESPACE + "." + name);
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
