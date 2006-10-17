/**
 * 
 */
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;
import org.seasar.teeda.extension.html.TakeOverDesc;

/**
 * @author higa
 * 
 */
public class TigerTakeOverDescAnnotationHandler extends
		ConstantTakeOverDescAnnotationHandler {

	@Override
	protected Map getTakeOverDescs(S2Container container,
			ComponentDef componentDef, Class componentClass,
			String componentName, BeanDesc beanDesc) {
		Map<String, TakeOverDesc> ret = new HashMap<String, TakeOverDesc>();
		Method[] methods = componentClass.getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			if (!methodName.startsWith("do") && !methodName.startsWith("go")
					&& !methodName.startsWith("jump")) {
				continue;
			}
			TakeOver takeOver = method.getAnnotation(TakeOver.class);
			if (takeOver == null) {
				continue;
			}
			TakeOverDesc takeOverDesc = createTakeOverDesc(takeOver.type()
					.getName(), takeOver.properties());
			ret.put(methodName, takeOverDesc);
		}
		if (!ret.isEmpty()) {
			return ret;
		}
		return super.getTakeOverDescs(container, componentDef, componentClass,
				componentName, beanDesc);
	}

}
