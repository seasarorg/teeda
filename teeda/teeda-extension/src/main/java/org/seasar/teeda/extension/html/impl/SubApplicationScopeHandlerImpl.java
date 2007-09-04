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
package org.seasar.teeda.extension.html.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.SubApplicationScopeHandler;

/**
 * @author shot
 */
public class SubApplicationScopeHandlerImpl implements
        SubApplicationScopeHandler {

    public boolean toScope(PageDesc pageDesc, FacesContext context) {
        if (pageDesc == null || !pageDesc.hasSubapplicationScopeProperty()) {
            return false;
        }
        final String pageName = pageDesc.getPageName();
        final Object page = DIContainerUtil.getComponent(pageName);
        final Map subApplicationScopeValues = ScopeValueHelper
                .getOrCreateSubApplicationScopeValues(context);
        final String[] propertyNames = pageDesc
                .getSubapplicationScopePropertyNames();
        saveValueToScope(page, subApplicationScopeValues, propertyNames);
        return true;
    }

    protected void saveValueToScope(Object component, Map scopeContext,
            String[] scopePropertyNames) {
        if (component == null) {
            return;
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component
                .getClass());
        for (int i = 0; i < scopePropertyNames.length; i++) {
            final String propertyName = scopePropertyNames[i];
            if (beanDesc.hasPropertyDesc(propertyName)) {
                final PropertyDesc propertyDesc = beanDesc
                        .getPropertyDesc(propertyName);
                final Object value = propertyDesc.getValue(component);
                scopeContext.put(propertyName, value);
            }
        }
    }

}
