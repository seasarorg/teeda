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
package org.seasar.teeda.core.el;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.el.impl.ApplicationScopeResolver;
import org.seasar.teeda.core.el.impl.CookieResolver;
import org.seasar.teeda.core.el.impl.FacesContextResolver;
import org.seasar.teeda.core.el.impl.HeaderResolver;
import org.seasar.teeda.core.el.impl.HeaderValuesResolver;
import org.seasar.teeda.core.el.impl.InitParamResolver;
import org.seasar.teeda.core.el.impl.ParamResolver;
import org.seasar.teeda.core.el.impl.ParamValuesResolver;
import org.seasar.teeda.core.el.impl.RequestScopeResolver;
import org.seasar.teeda.core.el.impl.SessionScopeResolver;
import org.seasar.teeda.core.el.impl.ViewResolver;
import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author higa
 *
 */
public class TeedaVariableResolver extends VariableResolver {

	private static Map facesResolvers = new HashMap();

	static {
		facesResolvers.put(JsfConstants.APPLICATION_SCOPE, new ApplicationScopeResolver());
		facesResolvers.put(JsfConstants.COOKIE, new CookieResolver());
		facesResolvers.put(JsfConstants.FACES_CONTEXT, new FacesContextResolver());
		facesResolvers.put(JsfConstants.HEADER, new HeaderResolver());
		facesResolvers.put(JsfConstants.HEADER_VALUES, new HeaderValuesResolver());
		facesResolvers.put(JsfConstants.INIT_PARAM, new InitParamResolver());
		facesResolvers.put(JsfConstants.PARAM, new ParamResolver());
		facesResolvers.put(JsfConstants.PARAM_VALUES, new ParamValuesResolver());
		facesResolvers.put(JsfConstants.REQUEST_SCOPE, new RequestScopeResolver());
		facesResolvers.put(JsfConstants.SESSION_SCOPE, new SessionScopeResolver());
		facesResolvers.put(JsfConstants.VIEW, new ViewResolver());
	}

	public TeedaVariableResolver() {
	}

	/**
	 * @see javax.faces.el.VariableResolver#resolveVariable(javax.faces.context.FacesContext,
	 *      java.lang.String)
	 */
	public Object resolveVariable(FacesContext context, String name)
			throws EvaluationException {
		FacesResolver resolver = (FacesResolver) facesResolvers.get(name);
		if (resolver != null) {
			return resolver.resolveVariable(context);
		}
		S2Container container = SingletonS2ContainerFactory.getContainer();
		return BindingUtil.getValue(container, name);
	}
}