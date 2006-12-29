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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UICommandUtil;
import javax.faces.internal.WindowIdUtil;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ArrayUtil;
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
import org.seasar.teeda.extension.util.PagePersistenceUtil;

/**
 * @author higa
 * @author shot
 * @author manhole
 */
public class SessionPagePersistence implements PagePersistence {

    private static final long serialVersionUID = 1L;

    private int pageSize = 10;

    private int windowSize = 10;

    private PageDescCache pageDescCache;

    private ActionDescCache actionDescCache;

    private NamingConvention namingConvention;

    private static final String ERROR_MESSAGE_PERSISTE_KEY = "Teeda.FacesMessages";

    public void save(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final UIViewRoot viewRoot = context.getViewRoot();
        final String previousViewId = (viewRoot != null) ? viewRoot.getViewId()
                : null;
        Map lru = getOrCreatePageLru(extCtx);
        Map pageData = getPageData(context, viewId, previousViewId);
        pageData = FacesMessageUtil.saveFacesMessagesToMap(context, pageData);
        lru.put(viewId, pageData);
    }

    public void restore(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final Map lru = getPageLru(extCtx);
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

    //TODO not use previousViewId?
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
        return convertPageData(context, beanDesc, previousViewId, pageDesc,
                page, nextPageProperties);
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
            String previousViewId, PageDesc pageDesc, Object page,
            Set nextPageProperties) {
        String methodName = UICommandUtil.getSubmittedCommand(context);
        ActionDesc actionDesc = actionDescCache.getActionDesc(previousViewId);
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
        Object value = pd.getValue(page);
        map.put(pd.getPropertyName(), value);
    }

    protected Map getWindowLru(ExternalContext extCtx) {
        Map sessionMap = extCtx.getSessionMap();
        return (Map) sessionMap.get(getClass().getName());
    }

    protected Map getOrCreateWindowLru(ExternalContext extCtx) {
        Map lru = getWindowLru(extCtx);
        if (lru != null) {
            return lru;
        }
        lru = new LruHashMap(windowSize);
        Map sessionMap = extCtx.getSessionMap();
        sessionMap.put(getClass().getName(), lru);
        return lru;
    }

    protected Map getPageLru(ExternalContext extCtx) {
        Map windowLru = getWindowLru(extCtx);
        if (windowLru == null) {
            return null;
        }
        String wid = WindowIdUtil.getWindowId(extCtx);
        return (Map) windowLru.get(wid);
    }

    protected Map getOrCreatePageLru(ExternalContext extCtx) {
        Map windowLru = getOrCreateWindowLru(extCtx);
        String wid = WindowIdUtil.getWindowId(extCtx);
        Map pageLru = (Map) windowLru.get(wid);
        if (pageLru == null) {
            pageLru = new LruHashMap(pageSize);
            windowLru.put(wid, pageLru);
        }
        return pageLru;
    }

    protected void restorePageDataMap(Map from, Map to) {
        for (Iterator itr = from.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            to.put(key, HotdeployUtil.rebuildValue(value));
        }
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    public void removeSubApplicationPages(final FacesContext context) {
        if (context == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final Map lru = getPageLru(extCtx);
        if (lru == null) {
            return;
        }
        final String subAppPath = getSubApplicationPath(context);
        if (subAppPath == null) {
            return;
        }
        final List list = new ArrayList();
        for (Iterator i = lru.keySet().iterator(); i.hasNext();) {
            String path = (String) i.next();
            if (path.startsWith(subAppPath)) {
                list.add(path);
            }
        }
        final String[] paths = (String[]) list.toArray(new String[list.size()]);
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

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
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

    protected static String getSubApplicationPath(final FacesContext context) {
        final UIViewRoot root = context.getViewRoot();
        if (root == null) {
            return null;
        }
        String viewId = root.getViewId();
        return viewId.substring(0, viewId.lastIndexOf("/"));
    }
}