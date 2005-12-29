package org.seasar.teeda.core.util;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeTranslator;

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
		ScopeTranslator translator = (ScopeTranslator)container.getComponent(ScopeTranslator.class);
		InstanceDef instanceDef = 
			(InstanceDef)translator.toExternalComponentScope(scope);
		componentDef.setInstanceDef(instanceDef);
		container.register(componentDef);
	}
}
