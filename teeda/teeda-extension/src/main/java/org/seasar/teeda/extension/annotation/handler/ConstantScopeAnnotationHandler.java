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

import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author higa
 * @author shot
 */
public class ConstantScopeAnnotationHandler extends
        AbstractScopeAnnotationHandler {

    private static final String REDIRECT_SCOPE_KEY = "REDIRECT_SCOPE";

    private static final String SUBAPPLICATION_SCOPE_KEY = "SUBAPPLICATION_SCOPE";

    private static final String PAGE_SCOPE_KEY = "PAGE_SCOPE";

    public void setupPropertyScopes(final BeanDesc beanDesc, Map scopes) {
        handleScope(beanDesc, scopes, REDIRECT_SCOPE_KEY,
                ExtensionConstants.REDIRECT_SCOPE);
        handleScope(beanDesc, scopes, SUBAPPLICATION_SCOPE_KEY,
                ExtensionConstants.SUBAPP_SCOPE);
        handleScope(beanDesc, scopes, PAGE_SCOPE_KEY,
                ExtensionConstants.PAGE_SCOPE);
    }

    protected void handleScope(BeanDesc beanDesc, Map scopes, String scopeKey,
            Integer scope) {
        if (beanDesc.hasField(scopeKey)) {
            final String redirectScopeValue = (String) beanDesc.getFieldValue(
                    scopeKey, null);
            storePropertyScope(scopes, redirectScopeValue, scope);
        }
    }

    private void storePropertyScope(Map scopes, String value, Integer scope) {
        String[] array = StringUtil.split(value, ", ");
        for (int i = 0; i < array.length; ++i) {
            scopes.put(array[i], scope);
        }
    }

}
