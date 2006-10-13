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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.util.ComponentHolder;
import org.seasar.teeda.extension.util.ComponentHolderBuilderUtil;
import org.seasar.teeda.extension.util.PagePersistenceUtil;

/**
 * @author higa
 * @author shot
 * @author manhole
 */
public class SessionPagePersistence implements PagePersistence {

    private static final long serialVersionUID = 1L;

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
        return convertPageData(page, viewId);
    }

    protected Map convertPageData(Object page, String viewId) {
        Map map = new HashMap();
        Class pageClass = page.getClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(pageClass);
        String nextPageName = namingConvention.fromPathToPageName(viewId);
        Class nextPageClass = namingConvention
                .fromComponentNameToClass(nextPageName);
        if (nextPageClass == null) {
            return map;
        }
        BeanDesc nextPageBeanDesc = BeanDescFactory.getBeanDesc(nextPageClass);
        List nextPageProperties = getNextPageProperties(nextPageBeanDesc);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !hasSameProperties(nextPageProperties, pd
                            .getPropertyName())) {
                continue;
            }
            if (!pd.getPropertyType().isArray()
                    && !Collection.class.isAssignableFrom(pd.getPropertyType())
                    && !PagePersistenceUtil.isPersistenceType(pd
                            .getPropertyType())) {
                continue;
            }
            final Object value = pd.getValue(page);
            final ComponentHolder holder = ComponentHolderBuilderUtil
                    .build(value);
            if (holder != null) {
                map.put(pd.getPropertyName(), holder);
            } else {
                map.put(pd.getPropertyName(), value);
            }
        }
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

    /*
     * postbackとpreviousViewIdはPage間で引き継がない
     */
    protected List getNextPageProperties(final BeanDesc beanDesc) {
        final List list = new ArrayList();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (JsfConstants.POSTBACK.equals(propertyName)) {
                continue;
            }
            if (JsfConstants.PREVIOUS_VIEW_ID.equals(propertyName)) {
                continue;
            }
            list.add(propertyName);
        }
        return list;
    }

    public void restore(FacesContext context, String viewId) {
        ExternalContext extCtx = context.getExternalContext();
        if (!ExternalContextUtil.isRedirect(extCtx)) {
            return;
        }
        Map lru = getLru(extCtx);
        if (lru == null) {
            return;
        }
        Map savedData = (Map) lru.get(viewId);
        if (savedData == null) {
            return;
        }
        Map requestMap = extCtx.getRequestMap();
        restorePageDataMap(savedData, requestMap);
    }

    protected Map getLru(ExternalContext extCtx) {
        Map sessionMap = extCtx.getSessionMap();
        return (Map) sessionMap.get(getClass().getName());
    }

    protected void restorePageDataMap(Map from, Map to) {
        for (Iterator itr = from.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof ComponentHolder) {
                final ComponentHolder holder = (ComponentHolder) value;
                final String arrayClassName = holder.getArrayClassName();
                final String componentClassName = holder
                        .getComponentClassName();
                if (componentClassName == null) {
                    continue;
                }
                final Class componentClass = ClassUtil
                        .forName(componentClassName);
                final List restoredList = holder.getValue();
                if (arrayClassName != null) {
                    final Class arrayClass = ClassUtil.forName(arrayClassName);
                    final Object[] array = (Object[]) Array.newInstance(
                            arrayClass, restoredList.size());
                    if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                        restoredList.toArray(array);
                    } else {
                        final List beanList = mapListToBeanList(componentClass,
                                restoredList);
                        beanList.toArray(array);
                    }
                    to.put(key, array);
                } else {
                    if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                        to.put(key, restoredList);
                    } else {
                        final List beanList = mapListToBeanList(componentClass,
                                restoredList);
                        to.put(key, beanList);
                    }
                }
            } else {
                to.put(key, value);
            }
        }
    }

    private List mapListToBeanList(final Class componentClass,
            final List restoredList) {
        final int size = restoredList.size();
        final List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            final Object bean = ClassUtil.newInstance(componentClass);
            final Object o = restoredList.get(i);
            if (!(o instanceof Map)) {
                throw new IllegalArgumentException(
                        "restoredList should be included map.");
            }
            final Map map = (Map) o;
            BeanUtil.copyProperties(map, bean);
            list.add(bean);
        }
        return list;
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    public void removeSubApplicationPages(FacesContext context) {
        String subAppPath = getSubApplicationPath(context);
        ExternalContext extCtx = context.getExternalContext();
        Map lru = getLru(extCtx);
        for (Iterator i = lru.keySet().iterator(); i.hasNext();) {
            String path = (String) i.next();
            if (path.startsWith(subAppPath)) {
                lru.remove(path);
            }
        }
    }

    protected static String getSubApplicationPath(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        return viewId.substring(0, viewId.lastIndexOf("/"));
    }
}