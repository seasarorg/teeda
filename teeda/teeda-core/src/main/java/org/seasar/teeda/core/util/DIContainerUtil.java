package org.seasar.teeda.core.util;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.managedbean.Scope;

public class DIContainerUtil {

	private DIContainerUtil(){
	}
	
	public static Object getComponent(Class clazz){
		S2Container container = SingletonS2ContainerFactory.getContainer();
		return container.getComponent(clazz);
	}
	
	public static void registerComponent(String name, Class type, Scope scope){
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ComponentDef componentDef = new ComponentDefImpl(type, name);
		InstanceDef instanceDef = ScopeUtil.toInstanceDef(scope);
		componentDef.setInstanceDef(instanceDef);
		container.register(componentDef);
	}
}
