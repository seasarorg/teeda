package org.seasar.teeda.core.util;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class DIContainerUtil {

	private DIContainerUtil(){
	}
	
	public static Object getComponent(Class clazz){
		S2Container container = SingletonS2ContainerFactory.getContainer();
		return container.getComponent(clazz);
	}
	
}
