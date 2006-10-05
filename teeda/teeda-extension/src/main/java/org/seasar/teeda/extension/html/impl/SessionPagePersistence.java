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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
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

    public Map convertPageData(Object page, String viewId) {
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
        List list = getNextPageProperties(nextPageBeanDesc);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !hasSameProperties(list, pd.getPropertyName())) {
                continue;
            }
            Object value = pd.getValue(page);
            ComponentHolder holder = null;
            if (value != null) {
                Class valueClass = value.getClass();
                if (value instanceof List) {
                    holder = buildListTypeHolder(((List) value));
                } else if (valueClass.isArray()) {
                    holder = buildArrayTypeHolder(valueClass, (Object[]) value);
                }
            }
            if (holder != null) {
                map.put(pd.getPropertyName(), holder);
            } else {
                map.put(pd.getPropertyName(), value);
            }
        }
        return map;
    }

    protected ComponentHolder buildListTypeHolder(final List valueList) {
        final ComponentHolder holder = new ComponentHolder();
        final List list = new ArrayList();
        if (!valueList.isEmpty()) {
            final Class componentClass = valueList.get(0).getClass();
            holder.setComponentClassName(componentClass.getName());
            if (isNoEscapeType(componentClass)) {
                list.addAll(valueList);
            } else {
                for (final Iterator itr = valueList.iterator(); itr.hasNext();) {
                    final Object bean = itr.next();
                    final Map m = new HashMap();
                    copyToMap(bean, m);
                    list.add(m);
                }
            }
        }
        holder.setValue(list);
        return holder;
    }

    protected ComponentHolder buildArrayTypeHolder(final Class valueClass,
            final Object[] valueArray) {
        final ComponentHolder holder = new ComponentHolder();
        holder.setArrayClassName(valueClass.getComponentType().getName());
        final List list = new ArrayList();
        if (0 < valueArray.length) {
            final Class componentClass = valueArray[0].getClass();
            holder.setComponentClassName(componentClass.getName());
            if (isNoEscapeType(componentClass)) {
                list.addAll(Arrays.asList(valueArray));
            } else {
                for (int i = 0; i < valueArray.length; i++) {
                    final Object bean = valueArray[i];
                    final Map m = new HashMap();
                    copyToMap(bean, m);
                    list.add(m);
                }
            }
        }
        holder.setValue(list);
        return holder;
    }

    private boolean isNoEscapeType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        return clazz.equals(String.class) || clazz.equals(Boolean.class)
                || Number.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz)
                || Calendar.class.isAssignableFrom(clazz);
    }

    protected void copyToMap(final Object from, final Map to) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(from.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final Object value = pd.getValue(from);
                to.put(pd.getPropertyName(), value);
            }
        }
    }

    protected void copyToBean(final Map from, final Object to) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(to.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final String propertyName = pd.getPropertyName();
                if (!from.containsKey(propertyName)) {
                    continue;
                }
                final Object value = from.get(propertyName);
                if (value == null) {
                    continue;
                }
                if (ClassUtil.isAssignableFrom(pd.getPropertyType(), value
                        .getClass())) {
                    pd.setValue(to, value);
                }
            }
        }
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
        final Map map = new HashMap();
        for (Iterator itr = pageData.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof ComponentHolder) {
                final ComponentHolder holder = (ComponentHolder) value;
                final String arrayClassName = holder.getArrayClassName();
                final String componentClassName = holder
                        .getComponentClassName();
                final Class componentClass = ClassUtil
                        .forName(componentClassName);
                final List restoredList = holder.getValue();
                if (arrayClassName != null) {
                    final Class arrayClass = ClassUtil.forName(arrayClassName);
                    final Object[] array = (Object[]) Array.newInstance(
                            arrayClass, restoredList.size());
                    if (isNoEscapeType(componentClass)) {
                        restoredList.toArray(array);
                    } else {
                        final List beanList = mapListToBeanList(componentClass,
                                restoredList);
                        beanList.toArray(array);
                    }
                    map.put(key, array);
                } else {
                    if (isNoEscapeType(componentClass)) {
                        map.put(key, restoredList);
                    } else {
                        final List beanList = mapListToBeanList(componentClass,
                                restoredList);
                        map.put(key, beanList);
                    }
                }
            } else {
                map.put(key, value);
            }
        }
        Map requestMap = extCtx.getRequestMap();
        requestMap.putAll(map);
    }

    private List mapListToBeanList(final Class componentClass,
            final List restoredList) {
        final int size = restoredList.size();
        final List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            final Object bean = ClassUtil.newInstance(componentClass);
            final Map map = (Map) restoredList.get(i);
            copyToBean(map, bean);
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

    public static class ComponentHolder implements Serializable {

        private static final long serialVersionUID = 1L;

        /*
         * 個々の要素の型
         */
        private String componentClassName;

        /*
         * 配列型の場合に配列型
         */
        private String arrayClassName;

        private List value;

        public List getValue() {
            return value;
        }

        public void setValue(List value) {
            this.value = value;
        }

        public String getComponentClassName() {
            return componentClassName;
        }

        public void setComponentClassName(String componentClassName) {
            this.componentClassName = componentClassName;
        }

        public String getArrayClassName() {
            return arrayClassName;
        }

        public void setArrayClassName(String holderClassName) {
            this.arrayClassName = holderClassName;
        }

    }

}