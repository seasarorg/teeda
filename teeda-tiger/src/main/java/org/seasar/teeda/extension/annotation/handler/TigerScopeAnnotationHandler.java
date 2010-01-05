/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.lang.reflect.Field;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.RedirectScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

/**
 * @author higa
 * @author shot
 */
public class TigerScopeAnnotationHandler extends ConstantScopeAnnotationHandler {

	@Override
	@SuppressWarnings("unchecked")
	public void setupPropertyScopes(BeanDesc beanDesc, Map scopes) {
		for (int i = 0; i < beanDesc.getFieldSize(); ++i) {
			Field field = beanDesc.getField(i);
			handlePageScope(field, scopes);
			handleRedirectScope(field, scopes);
			handleSubapplicationScope(field, scopes);
		}
		super.setupPropertyScopes(beanDesc, scopes);
	}

	protected void handlePageScope(Field field, Map<String, Integer> scopes) {
		PageScope pageScope = field.getAnnotation(PageScope.class);
		if (pageScope != null) {
			scopes.put(field.getName(), ExtensionConstants.PAGE_SCOPE);
		}
	}

	protected void handleRedirectScope(Field field, Map<String, Integer> scopes) {
		RedirectScope redirectScope = field.getAnnotation(RedirectScope.class);
		if (redirectScope != null) {
			scopes.put(field.getName(), ExtensionConstants.REDIRECT_SCOPE);
		}
	}

	protected void handleSubapplicationScope(Field field,
			Map<String, Integer> scopes) {
		SubapplicationScope subapplicationScope = field
				.getAnnotation(SubapplicationScope.class);
		if (subapplicationScope != null) {
			scopes.put(field.getName(), ExtensionConstants.SUBAPP_SCOPE);
		}
	}

}
