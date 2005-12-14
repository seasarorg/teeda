package org.seasar.teeda.core.managedbean.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.DestroyMethodDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.Scope;

public class ManagedBeanFactoryImpl implements ManagedBeanFactory {

	private S2Container container_;

	private static final String TEEDA_MANAGEDBEAN_NAMESPACE = "teeda-managedbean";

	private static final Map SCOPE_MAP = new HashMap();
	static {
		SCOPE_MAP.put(Scope.NONE, InstanceDefFactory.SINGLETON);
		SCOPE_MAP.put(Scope.REQUEST, InstanceDefFactory.REQUEST);
		SCOPE_MAP.put(Scope.SESSION, InstanceDefFactory.SESSION);

		// this has to be changed to new scope, application
		SCOPE_MAP.put(Scope.APPLICATION, InstanceDefFactory.SINGLETON);
	}

	public ManagedBeanFactoryImpl() {
		container_ = new S2ContainerImpl();
		container_.setNamespace(TEEDA_MANAGEDBEAN_NAMESPACE);
		S2Container root = (S2Container) SingletonS2ContainerFactory
				.getContainer();
		root.include(container_);
	}

	public Object getManagedBean(String name) {
		S2Container container = (S2Container) SingletonS2ContainerFactory.getContainer();
		return container.getComponent(TEEDA_MANAGEDBEAN_NAMESPACE + "." + name);
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

	private void setInstanceType(ComponentDef componentDef, Scope scope){
		InstanceDef instanceDef = (InstanceDef) SCOPE_MAP.get(scope);
		componentDef.setInstanceDef(instanceDef);
	}
}
