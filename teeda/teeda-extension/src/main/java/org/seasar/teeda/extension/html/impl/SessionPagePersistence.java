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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UICommandUtil;
import javax.faces.internal.WindowIdUtil;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageFormatter;
import org.seasar.framework.util.ArrayUtil;
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

    private static final String ERROR_MESSAGE_PERSISTE_KEY = "teeda.FacesMessages";

    private static final String SUB_APPLICATION_SCOPE_KEY = SessionPagePersistence.class
            .getName();

    private static final Logger logger = Logger
            .getLogger(SessionPagePersistence.class);

    public void save(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        UIViewRoot viewRoot = context.getViewRoot();
        String previousViewId = (viewRoot != null) ? viewRoot.getViewId()
                : null;
        Map saveValues = getOrCreateSaveValues(context);
        Map pageData = getPageData(context, viewId, previousViewId);
        pageData = FacesMessageUtil.saveFacesMessagesToMap(context, pageData);
        saveValues.putAll(pageData);
    }

    public void restore(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final Map saveValues = getSaveValues(context);
        if (saveValues == null) {
            return;
        }
        final Map requestMap = extCtx.getRequestMap();
        restorePageDataMap(saveValues, requestMap);
        FacesMessageUtil.restoreFacesMessagesFromMap(saveValues, context);
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
        Map nextPageProperties = getNextPageProperties(viewId);
        if (nextPageProperties.isEmpty()) {
            return new HashMap();
        }
        return convertPageData(context, beanDesc, previousViewId, pageDesc,
                page, nextPageProperties);
    }

    /*
     * postbackとpreviousViewIdはPage間で引き継がない
     */
    protected Map getNextPageProperties(final String viewId) {
        final Map map = new HashMap();
        String nextPageName = namingConvention.fromPathToPageName(viewId);
        Class nextPageClass = namingConvention
                .fromComponentNameToClass(nextPageName);
        if (nextPageClass == null) {
            return map;
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
            map.put(propertyName, pd.getPropertyType());
        }
        return map;
    }

    protected Map convertPageData(FacesContext context, BeanDesc beanDesc,
            String previousViewId, PageDesc pageDesc, Object page,
            Map nextPageProperties) {
        final String methodName = UICommandUtil.getSubmittedCommand(context);
        try {
            final ActionDesc actionDesc = actionDescCache
                    .getActionDesc(previousViewId);
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
        } finally {
            if (methodName != null && methodName.startsWith("doFinish")) {
                SubApplicationScope.removeContext(context);
            }
        }
    }

    protected Map convertPageData(FacesContext context, BeanDesc beanDesc,
            TakeOverDesc takeOverDesc, Object page, Map nextPageProperties) {
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
            Map nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < properties.length; ++i) {
            String propertyName = properties[i];
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (!pd.hasReadMethod()
                    || !nextPageProperties.containsKey(pd.getPropertyName())) {
                continue;
            }
            putValue(map, page, pd);
        }
        return map;
    }

    protected Map convertExcludePageData(FacesContext context,
            BeanDesc beanDesc, Object page, String[] properties,
            Map nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.hasReadMethod()
                    || !nextPageProperties.containsKey(pd.getPropertyName())) {
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
            BeanDesc beanDesc, Object page, Map nextPageProperties) {
        Map map = new HashMap();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (!pd.hasReadMethod()
                    || !nextPageProperties.containsKey(propertyName)) {
                continue;
            }
            final Class thisPageType = pd.getPropertyType();
            if (!thisPageType.isArray()
                    && !Collection.class.isAssignableFrom(thisPageType)
                    && !PagePersistenceUtil.isPersistenceType(thisPageType)) {
                continue;
            }
            Class nextPageType = (Class) nextPageProperties.get(propertyName);
            if (nextPageType != thisPageType) {
                final Object[] args = new Object[] { propertyName,
                        thisPageType.getName(), nextPageType.getName() };
                final String message = MessageFormatter.getMessage("WTDA0201",
                        args);
                logger.debug(message);
            }
            putValue(map, page, pd);
        }
        return map;
    }

    protected void putValue(Map map, Object page, PropertyDesc pd) {
        Object value = pd.getValue(page);
        map.put(pd.getPropertyName(), value);
    }

    protected Map getSaveValues(FacesContext context) {
        Map viewContext = SubApplicationScope.getOrCreateContext(context);
        return (Map) viewContext.get(SUB_APPLICATION_SCOPE_KEY);
    }

    protected Map getOrCreateSaveValues(FacesContext context) {
        Map saveValues = getSaveValues(context);
        if (saveValues == null) {
            Map viewContext = SubApplicationScope.getOrCreateContext(context);
            saveValues = new HashMap();
            viewContext.put(SUB_APPLICATION_SCOPE_KEY, saveValues);
        }
        return saveValues;
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
        ExternalContext extCtx = context.getExternalContext();
        String wid = WindowIdUtil.getWindowId(extCtx);
        SubApplicationScope.removeContext(context, wid);
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

}