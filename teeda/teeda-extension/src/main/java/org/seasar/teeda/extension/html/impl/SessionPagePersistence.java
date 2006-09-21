/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author higa
 * @author shot
 * @author manhole
 */
public class SessionPagePersistence implements PagePersistence {

    private static final long serialVersionUID = 1L;

    private static final String PREVIOUS_VIEW_ID = "previousViewId";

    private int pageSize = 10;

    private PageDescCache pageDescCache;

    private NamingConvention namingConvention;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public void save(FacesContext context, String viewId) {
        ExternalContext extCtx = context.getExternalContext();
        Map sessionMap = extCtx.getSessionMap();
        LruHashMap lru = (LruHashMap) sessionMap.get(getClass().getName());
        if (lru == null) {
            lru = new LruHashMap(pageSize);
            sessionMap.put(getClass().getName(), lru);
        }
        String previousViewId = context.getViewRoot().getViewId();
        lru.put(viewId, getPageData(viewId, previousViewId));
    }

    protected Map getPageData(String viewId, String previousViewId) {
        PageDesc pageDesc = pageDescCache.getPageDesc(previousViewId);
        if (pageDesc == null) {
            return null;
        }
        Object page = DIContainerUtil.getComponent(pageDesc.getPageName());
        return convertPageData(page, viewId, previousViewId);
    }

    public Map convertPageData(Object page, String viewId, String previousViewId) {
        Map map = new HashMap();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        String pageName = namingConvention.fromPathToPageName(viewId);
        Class c = namingConvention.fromComponentNameToClass(pageName);
        if (c == null) {
            return map;
        }
        BeanDesc nextBeanDesc = BeanDescFactory.getBeanDesc(c);
        List list = getNextPageProperties(nextBeanDesc);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !hasSameProperties(list, pd.getPropertyName())) {
                continue;
            }
            Object value = pd.getValue(page);
            map.put(pd.getPropertyName(), value);
        }
        map.put(PREVIOUS_VIEW_ID, previousViewId);
        return map;
    }

    protected boolean hasSameProperties(List list, String propertyName) {
        for (Iterator itr = list.iterator(); itr.hasNext();) {
            String name = (String) itr.next();
            if (propertyName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    protected static List getNextPageProperties(BeanDesc beanDesc) {
        List list = new ArrayList();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            list.add(pd.getPropertyName());
        }
        return list;
    }

    public void restore(FacesContext context, String viewId) {
        ExternalContext extCtx = context.getExternalContext();
        Map sessionMap = extCtx.getSessionMap();
        LruHashMap lru = (LruHashMap) sessionMap.get(getClass().getName());
        if (lru == null) {
            return;
        }
        Map pageData = (Map) lru.get(viewId);
        if (pageData == null) {
            return;
        }
        Map requestMap = extCtx.getRequestMap();
        requestMap.putAll(pageData);
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

}