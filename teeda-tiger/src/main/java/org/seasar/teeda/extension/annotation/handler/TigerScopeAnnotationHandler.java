/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.annotation.scope.RedirectScope;

/**
 * @author higa
 * 
 */
public class TigerScopeAnnotationHandler extends ConstantScopeAnnotationHandler {

	@Override
	@SuppressWarnings("unchecked")
	public void setupPropertyScopes(BeanDesc beanDesc, Map scopes) {
		for (int i = 0; i < beanDesc.getFieldSize(); ++i) {
			Field field = beanDesc.getField(i);
			RedirectScope redirectScope = field
					.getAnnotation(RedirectScope.class);
			if (redirectScope == null) {
				continue;
			}
			scopes.put(field.getName(), ExtensionConstants.REDIRECT_SCOPE);
		}
		super.setupPropertyScopes(beanDesc, scopes);
	}
}
