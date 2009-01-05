/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageScopeHandler;

/**
 * @author shot
 */
public class PageScopeHandlerImpl implements PageScopeHandler {

    private Map pageNamesMap = new HashMap();

    public boolean toPage(final PageDesc pageDesc, final FacesContext context) {
        if (pageDesc == null || context == null
                || !pageDesc.hasPageScopeProperty()) {
            return false;
        }
        boolean changed = false;
        final Map pageScopeValues = ScopeValueHelper
                .getOrCreatePageScopeValues(context);
        final String pageName = pageDesc.getPageName();
        final Object page = DIContainerUtil.getComponent(pageName);
        if (page == null) {
            return false;
        }
        String[] names = null;
        if (!pageNamesMap.containsKey(pageName)) {
            names = pageDesc.getPageScopePropertyNames();
        } else {
            List list = (List) pageNamesMap.get(pageName);
            names = (String[]) list.toArray(new String[list.size()]);
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        for (int i = 0; i < names.length; i++) {
            String propertyName = names[i];
            if (beanDesc.hasPropertyDesc(propertyName)) {
                PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
                final Object pageValue = (pd.isReadable()) ? pd.getValue(page)
                        : null;
                final Object scopeValue = pageScopeValues.get(propertyName);
                if (pageValue != null && !pageValue.equals(scopeValue)) {
                    changed = true;
                    continue;
                }
                if (pd.isWritable()) {
                    pd.setValue(page, scopeValue);
                }
            }
        }
        return changed;
    }

    public boolean toScope(PageDesc pageDesc, FacesContext context) {
        if (pageDesc == null || context == null
                || !pageDesc.hasPageScopeProperty()) {
            return false;
        }
        final Map pageScopeValues = ScopeValueHelper
                .getOrCreatePageScopeValues(context);
        final String pageName = pageDesc.getPageName();
        final Object page = DIContainerUtil.getComponent(pageName);
        List list = null;
        final boolean cached = pageNamesMap.containsKey(pageName);
        if (!cached) {
            list = new ArrayList();
            pageNamesMap.put(pageName, list);
        }
        final String[] names = pageDesc.getPageScopePropertyNames();
        if (page == null) {
            return false;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        for (int i = 0; i < names.length; i++) {
            String propertyName = names[i];
            if (beanDesc.hasPropertyDesc(propertyName)) {
                if (!cached) {
                    list.add(propertyName);
                }
                PropertyDesc propertyDesc = beanDesc
                        .getPropertyDesc(propertyName);
                if (propertyDesc.isReadable()) {
                    final Object value = propertyDesc.getValue(page);
                    pageScopeValues.put(propertyName, value);
                }
            }
        }
        return true;
    }

}