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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UICommandUtil;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.exception.PageNotFoundRuntimeException;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.TakeOverTypeDesc;
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

    private ActionDescCache actionDescCache;

    private NamingConvention namingConvention;

    private static final String ERROR_MESSAGE_PERSISTE_KEY = "Teeda.FacesMessages";

    public void save(FacesContext context, String viewId) {
        final ExternalContext extCtx = context.getExternalContext();
        final Map sessionMap = extCtx.getSessionMap();
        final String previousViewId = context.getViewRoot().getViewId();
        LruHashMap lru = (LruHashMap) sessionMap.get(getClass().getName());
        if (lru == null) {
            lru = new LruHashMap(pageSize);
            sessionMap.put(getClass().getName(), lru);
        }
        final Map pageData = getPageData(context, viewId, previousViewId);
        FacesMessageUtil.saveFacesMessagesToMap(context, pageData);
        lru.put(viewId, pageData);
    }

    public void restore(FacesContext context, String viewId) {
        final ExternalContext extCtx = context.getExternalContext();
        final Map lru = getLru(extCtx);
        if (lru == null) {
            return;
        }
        final Map savedData = (Map) lru.get(viewId);
        if (savedData == null) {
            return;
        }
        final Map requestMap = extCtx.getRequestMap();
        restorePageDataMap(savedData, requestMap);
        FacesMessageUtil.restoreFacesMessagesFromMap(savedData, context);
    }

    protected void saveFacesMessage(FacesContext from, Map to) {
        final FacesMessage[] messages = FacesMessageUtil.getAllMessages(from);
        to.put(ERROR_MESSAGE_PERSISTE_KEY, messages);
    }

    protected void restoreFacesMessages(Map from, FacesContext to) {
        final FacesMessage[] messages = (FacesMessage[]) from
                .remove(ERROR_MESSAGE_PERSISTE_KEY);
        if (messages == null) {
            return;
        }
        for (int i = 0; i < messages.length; i++) {
            to.addMessage(null, messages[i]);
        }
    }

    protected Map getPageData(FacesContext context, String viewId,
            String previousViewId) {
        PageDesc pageDesc = pageDescCache.getPageDesc(previousViewId);
        if (pageDesc == null) {
            return null;
        }
        Object page = DIContainerUtil.getComponent(pageDesc.getPageName());
        if (page == null) {
            throw new PageNotFoundRuntimeException();
        }
        return convertPageData(context, page, viewId, previousViewId, pageDesc);
    }

    protected Map convertPageData(FacesContext context, Object page,
            String viewId, String previousViewId, PageDesc pageDesc) {
        Class pageClass = page.getClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(pageClass);
        Set nextPageProperties = getNextPageProperties(viewId);
        if (nextPageProperties.isEmpty()) {
            return new HashMap();
        }
        return convertPageData(context, beanDesc, viewId, pageDesc, page,
                nextPageProperties);
    }

    /*
     * postbackとpreviousViewIdはPage間で引き継がない
     */
    protected Set getNextPageProperties(final String viewId) {
        final Set set = new HashSet();
        String nextPageName = namingConvention.fromPathToPageName(viewId);
        Class nextPageClass = namingConvention
                .fromComponentNameToClass(nextPageName);
        if (nextPageClass == null) {
            return set;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(nextPageClass);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasWriteMethod()) {
                continue;
            }
            final String propertyName = pd.getPropertyName();
            if (JsfConstants.POSTBACK.equals(propertyName)) {
                continue;
            }
            if (JsfConstants.PREVIOUS_VIEW_ID.equals(propertyName)) {
                continue;
            }
            set.add(propertyName);
        }
        return set;
    }

    protected Map convertPageData(FacesContext context, BeanDesc beanDesc,
            String viewId, PageDesc pageDesc, Object page,
            Set nextPageProperties) {
        String methodName = UICommandUtil.getSubmittedCommand(context);
        ActionDesc actionDesc = actionDescCache.getActionDesc(viewId);
        if (methodName != null && actionDesc != null
                && actionDesc.hasTakeOverDesc(methodName)) {
            return convertPageData(context, beanDesc, actionDesc
                    .getTakeOverDesc(methodName), page, nextPageProperties);
        }
        if (methodName != null && pageDesc != null
                && pageDesc.hasTakeOverDesc(methodName)) {
            return convertPageData(context, beanDesc, pageDesc
                    .getTakeOverDesc(methodName), page, nextPageProperties);
        }
        return convertDefaultPageData(context, beanDesc, page,
                nextPageProperties);
    }

    protected Map convertPageData(FacesContext context, BeanDesc beanDesc,
            TakeOverDesc takeOverDesc, Object page, Set nextPageProperties) {
        TakeOverTypeDesc takeOverTypeDesc = takeOverDesc.getTakeOverTypeDesc();
        if (takeOverTypeDesc.equals(TakeOverTypeDescFactory.NEVER)) {
            return new HashMap();
        }
        if (takeOverTypeDesc.equals(TakeOverTypeDescFactory.INCLUDE)
                && takeOverDesc.getProperties().length != 0) {
            return convertIncludePageData(context, beanDesc, page, takeOverDesc
                    .getProperties(), nextPageProperties);
        }
        if (takeOverTypeDesc.equals(TakeOverTypeDescFactory.EXCLUDE)
                && takeOverDesc.getProperties().length != 0) {
            return convertExcludePageData(context, beanDesc, page, takeOverDesc
                    .getProperties(), nextPageProperties);
        }
        return convertDefaultPageData(context, beanDesc, page,
                nextPageProperties);
    }

    protected Map convertIncludePageData(FacesContext context,
            BeanDesc beanDesc, Object page, String[] properties,
            Set nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < properties.length; ++i) {
            String propertyName = properties[i];
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (!pd.hasReadMethod()
                    || !nextPageProperties.contains(pd.getPropertyName())) {
                continue;
            }
            putValue(map, page, pd);
        }
        return map;
    }

    protected Map convertExcludePageData(FacesContext context,
            BeanDesc beanDesc, Object page, String[] properties,
            Set nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !nextPageProperties.contains(pd.getPropertyName())) {
                continue;
            }
            if (ArrayUtil.contains(properties, pd.getPropertyName())) {
                continue;
            }
            putValue(map, page, pd);
        }
        return map;
    }

    protected Map convertDefaultPageData(FacesContext context,
            BeanDesc beanDesc, Object page, Set nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !nextPageProperties.contains(pd.getPropertyName())) {
                continue;
            }
            if (!pd.getPropertyType().isArray()
                    && !Collection.class.isAssignableFrom(pd.getPropertyType())
                    && !PagePersistenceUtil.isPersistenceType(pd
                            .getPropertyType())) {
                continue;
            }
            putValue(map, page, pd);
        }
        return map;
    }

    protected void putValue(Map map, Object page, PropertyDesc pd) {
        final Object value = pd.getValue(page);
        final ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        if (holder != null) {
            map.put(pd.getPropertyName(), holder);
        } else {
            map.put(pd.getPropertyName(), value);
        }
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
        if (lru == null) {
            return;
        }
        List list = new ArrayList();
        for (Iterator i = lru.keySet().iterator(); i.hasNext();) {
            String path = (String) i.next();
            if (path.startsWith(subAppPath)) {
                list.add(path);
            }
        }
        String[] paths = (String[]) list.toArray(new String[list.size()]);
        for (int i = 0; i < paths.length; i++) {
            lru.remove(paths[i]);
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public ActionDescCache getActionDescCache() {
        return actionDescCache;
    }

    public void setActionDescCache(ActionDescCache actionDescCache) {
        this.actionDescCache = actionDescCache;
    }

    protected static String getSubApplicationPath(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        return viewId.substring(0, viewId.lastIndexOf("/"));
    }
}