/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.teeda.extension.annotation.transition.Redirect;
import org.seasar.teeda.extension.html.RedirectDesc;

/**
 * 
 * @author koichik
 */
public class TigerRedirectDescAnnotationHandler extends
		ConstantRedirectDescAnnotationHandler {

	@SuppressWarnings("unchecked")
	@Override
	protected Map getRedirectDescs(final S2Container container,
			final ComponentDef componentDef, final Class componentClass,
			final String componentName, final BeanDesc beanDesc) {
		final Map<String, RedirectDesc> ret = CollectionsUtil.newHashMap();
		for (final Method method : componentClass.getMethods()) {
			if (method.isBridge() || method.isSynthetic()) {
				continue;
			}
			final String methodName = method.getName();
			if (!methodName.equals("initialize")
					&& !methodName.equals("prerender")
					&& !methodName.startsWith("do")) {
				continue;
			}
			final Redirect redirect = method.getAnnotation(Redirect.class);
			if (redirect == null) {
				continue;
			}
			final RedirectDesc redirectDesc = createRedirectDesc(redirect
					.protocol().getExternalForm(), redirect.port());
			ret.put(methodName, redirectDesc);
		}
		final Map<String, RedirectDesc> m = super.getRedirectDescs(container,
				componentDef, componentClass, componentName, beanDesc);
		if (!m.isEmpty()) {
			ret.putAll(m);
		}
		return ret;
	}
}
