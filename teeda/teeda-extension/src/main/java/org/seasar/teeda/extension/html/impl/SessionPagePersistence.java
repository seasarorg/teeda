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

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.SLinkedList;
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
        PersistenceData pd = (PersistenceData) sessionMap.get(getClass()
                .getName());
        if (pd == null) {
            pd = new PersistenceData(pageSize);
            sessionMap.put(getClass().getName(), pd);
        }
        String previousViewId = context.getViewRoot().getViewId();
        pd.set(viewId, getPageData(previousViewId));
    }

    protected Map getPageData(String viewId) {
        PageDesc pageDesc = pageDescCache.getPageDesc(viewId);
        if (pageDesc == null) {
            return null;
        }
        Object page = DIContainerUtil.getComponent(pageDesc.getPageName());
        return convertPageData(page, viewId);
    }

    public static Map convertPageData(Object page, String viewId) {
        Map map = new HashMap();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !isPersistenceInputValueType(pd.getPropertyType())) {
                continue;
            }
            Object value = pd.getValue(page);
            map.put(pd.getPropertyName(), value);
        }
        map.put(PREVIOUS_VIEW_ID, viewId);
        return map;
    }

    protected static boolean isPersistenceInputValueType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        return clazz.equals(String.class) || clazz.equals(Boolean.class)
                || Number.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz) || clazz.isArray();
    }

    public void restore(FacesContext context, String viewId) {
        ExternalContext extCtx = context.getExternalContext();
        Map sessionMap = extCtx.getSessionMap();
        PersistenceData pd = (PersistenceData) sessionMap.get(getClass()
                .getName());
        if (pd == null) {
            return;
        }
        Map pageData = pd.get(viewId);
        if (pageData == null) {
            return;
        }
        Map requestMap = extCtx.getRequestMap();
        requestMap.putAll(pageData);
    }

    public static class PersistenceData implements Serializable {

        private static final long serialVersionUID = 1L;

        private SLinkedList list = new SLinkedList();

        private Map map = new HashMap();

        private int pageSize;

        public PersistenceData(int pageSize) {
            this.pageSize = pageSize;
        }

        public synchronized Map get(String viewId) {
            list.remove(viewId);
            list.addFirst(viewId);
            return (Map) map.get(viewId);
        }

        public synchronized void set(String viewId, Map data) {
            if (map.size() >= pageSize) {
                String vid = (String) list.removeLast();
                map.remove(vid);
            }
            list.remove(viewId);
            list.addFirst(viewId);
            map.put(viewId, data);
        }

        public synchronized int getSize() {
            return map.size();
        }
    }
}
