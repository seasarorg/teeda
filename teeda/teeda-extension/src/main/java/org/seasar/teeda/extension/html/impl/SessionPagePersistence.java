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
package org.seasar.teeda.extension.html.impl;

import java.util.ArrayList;
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
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageFormatter;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;
import org.seasar.teeda.extension.html.HtmlSuffix;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.TakeOverTypeDesc;
import org.seasar.teeda.extension.util.PagePersistenceUtil;
import org.seasar.teeda.extension.util.TeedaExtensionErrorPageManagerImpl;

/**
 * @author higa
 * @author shot
 * @author manhole
 */
public class SessionPagePersistence implements PagePersistence {

    private static final long serialVersionUID = 1L;

    private PageDescCache pageDescCache;

    private ActionDescCache actionDescCache;

    private NamingConvention namingConvention;

    private TeedaStateManager stateManager;

    private HtmlSuffix htmlSuffix;

    private static final String ERROR_MESSAGE_PERSISTE_KEY = "teeda.FacesMessages";

    private static final Logger logger = Logger
            .getLogger(SessionPagePersistence.class);

    public void save(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        UIViewRoot viewRoot = context.getViewRoot();
        String previousViewId = (viewRoot != null) ? viewRoot.getViewId()
                : null;
        Map subappValues = ScopeValueHelper
                .getOrCreateSubApplicationScopeValues(context);
        Map redirectValues = ScopeValueHelper
                .getOrCreateRedirectScopeValues(context);
        savePageValues(subappValues, redirectValues, context, viewId,
                previousViewId);
    }

    public void restore(final FacesContext context, final String viewId) {
        if (context == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final Map requestMap = extCtx.getRequestMap();
        if (isNotTakeOver(extCtx)) {
            return;
        }
        final Map subappValues = ScopeValueHelper
                .getSubApplicationScopeValues(context);
        if (subappValues != null) {
            restoreValues(subappValues, requestMap);
        }
        final Map redirectValues = ScopeValueHelper
                .getRedirectScopeValues(context);
        if (redirectValues != null) {
            restoreValues(redirectValues, requestMap);
        }
        //TEEDA-358 : if postback and there is no input but @PageScope
        if (PostbackUtil.isPostback(requestMap)) {
            final Map pageScopeValues = ScopeValueHelper
                    .getPageScopeValues(context);
            if (pageScopeValues != null) {
                restoreValues(pageScopeValues, requestMap);
            }
        }
        TeedaExtensionErrorPageManagerImpl.restoreMessage(context);
    }

    protected boolean isNotTakeOver(ExternalContext externalContext) {
        final Map map = externalContext.getRequestParameterMap();
        final String takeOver = (String) map
                .get(ExtensionConstants.TAKE_OVER_PARAMETER);
        return TakeOverTypeDesc.NEVER_NAME.equals(takeOver);
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
    protected void savePageValues(Map subappValues, Map redirectValues,
            FacesContext context, String viewId, String previousViewId) {
        PageDesc pageDesc = (previousViewId != null) ? pageDescCache
                .getPageDesc(previousViewId) : null;
        if (pageDesc == null) {
            return;
        }
        Object page = DIContainerUtil.getComponent(pageDesc.getPageName());
        savePageValues(subappValues, redirectValues, context, page, viewId,
                previousViewId, pageDesc);
    }

    protected void savePageValues(Map subappValues, Map redirectValues,
            FacesContext context, Object page, String viewId,
            String previousViewId, PageDesc pageDesc) {
        Class pageClass = page.getClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(pageClass);
        Map nextPageProperties = getNextPageProperties(pageDesc, viewId);
        if (nextPageProperties.isEmpty()) {
            return;
        }
        savePageValues(subappValues, redirectValues, context, beanDesc,
                previousViewId, pageDesc, page, nextPageProperties);
    }

    /*
     * postbackとpreviousViewIdはPage間で引き継がない
     */
    protected Map getNextPageProperties(final PageDesc pageDesc,
            final String viewId) {
        final Map map = new HashMap();
        final String nextPageName = fromPathToPageName(viewId);
        final Class nextPageClass = namingConvention
                .fromComponentNameToClass(nextPageName);
        if (nextPageClass == null) {
            return map;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(nextPageClass);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!pd.isWritable()) {
                continue;
            }
            final String propertyName = pd.getPropertyName();
            if (JsfConstants.POSTBACK.equals(propertyName)) {
                continue;
            }
            if (JsfConstants.PREVIOUS_VIEW_ID.equals(propertyName)) {
                continue;
            }
            if (JsfConstants.FACES_CONTEXT.equals(propertyName)) {
                continue;
            }
            if (pageDesc.isPageScopeProperty(propertyName)) {
                continue;
            }
            if (propertyName.endsWith(namingConvention.getDtoSuffix())) {
                ComponentDef cd = DIContainerUtil
                        .getComponentDefNoException(propertyName);
                if (cd == null) {
                    cd = DIContainerUtil.getComponentDefNoException(pd
                            .getPropertyType());
                }
                if (cd != null &&
                        cd.getInstanceDef().equals(InstanceDefFactory.SESSION)) {
                    continue;
                }
            }
            map.put(propertyName, pd.getPropertyType());
        }
        return map;
    }

    protected String fromPathToPageName(final String viewId) {
        String normalizedPath = (viewId != null) ? htmlSuffix
                .normalizePath(viewId) : viewId;
        return namingConvention.fromPathToPageName(normalizedPath);
    }

    protected void savePageValues(Map subappValues, Map redirectValues,
            FacesContext context, BeanDesc beanDesc, String previousViewId,
            PageDesc pageDesc, Object page, Map nextPageProperties) {
        final String submittedCommand = UICommandUtil
                .getSubmittedCommand(context);
        final int pos = submittedCommand == null ? -1 : submittedCommand
                .indexOf('-');
        final String methodName = pos == -1 ? submittedCommand
                : submittedCommand.substring(0, pos);
        try {
            final ActionDesc actionDesc = actionDescCache
                    .getActionDesc(previousViewId);
            if (methodName != null && actionDesc != null &&
                    actionDesc.hasTakeOverDesc(methodName)) {
                savePageValues(subappValues, redirectValues, context, beanDesc,
                        actionDesc.getTakeOverDesc(methodName), page,
                        nextPageProperties, pageDesc);
            } else if (methodName != null && pageDesc != null &&
                    pageDesc.hasTakeOverDesc(methodName)) {
                savePageValues(subappValues, redirectValues, context, beanDesc,
                        pageDesc.getTakeOverDesc(methodName), page,
                        nextPageProperties, pageDesc);
            } else {
                saveDefaultPageValues(subappValues, redirectValues, context,
                        beanDesc, page, nextPageProperties, pageDesc);
            }
        } finally {
            ScopeValueHelper.removeIfDoFinish(methodName, context);
        }
    }

    protected void savePageValues(Map subappValues, Map redirectValues,
            FacesContext context, BeanDesc beanDesc, TakeOverDesc takeOverDesc,
            Object page, Map nextPageProperties, PageDesc pageDesc) {
        AssertionUtil.assertNotNull("takeOverDesc", takeOverDesc);
        final String[] properties = takeOverDesc.getProperties();

        //TODO TakeOver.INCLUDE/EXCLUEのときにPagePersistenceUtil.isPersistenceType()で
        //チェックしていないので、HotDeployUtilでこける可能性がある.
        if (isTakeOverNever(takeOverDesc)) {
            refreshSubApplicationScopeMap(context, subappValues);
        } else if (isTakeOverInclude(takeOverDesc)) {
            subappValues = refreshSubApplicationScopeMap(context, subappValues);
            saveIncludePageValues(subappValues, redirectValues, context,
                    beanDesc, page, properties, nextPageProperties, pageDesc);
        } else if (isTakeOverExclude(takeOverDesc)) {
            subappValues = refreshSubApplicationScopeMap(context, subappValues);
            saveExcludePageValues(subappValues, context, beanDesc, page,
                    properties, nextPageProperties);
        } else {
            saveDefaultPageValues(subappValues, redirectValues, context,
                    beanDesc, page, nextPageProperties, pageDesc);
        }
    }

    private static boolean isTakeOverNever(TakeOverDesc desc) {
        return TakeOverTypeDescFactory.NEVER.equals(desc.getTakeOverTypeDesc());
    }

    private static boolean isTakeOverInclude(TakeOverDesc desc) {
        return TakeOverTypeDescFactory.INCLUDE.equals(desc
                .getTakeOverTypeDesc()) &&
                desc.getProperties().length != 0;
    }

    private static boolean isTakeOverExclude(TakeOverDesc desc) {
        return TakeOverTypeDescFactory.EXCLUDE.equals(desc
                .getTakeOverTypeDesc()) &&
                desc.getProperties().length != 0;
    }

    protected Map refreshSubApplicationScopeMap(FacesContext context,
            Map subappValues) {
        final Map subAppScopeMap = ScopeValueHelper
                .getSubApplicationScopeValues(context);
        if (subAppScopeMap != null) {
            final Map scopeContext = SubApplicationScope.getContext(context);
            if (scopeContext != null) {
                scopeContext.remove(SUBAPPLICATION_SCOPE_KEY);
                subappValues = ScopeValueHelper
                        .getOrCreateSubApplicationScopeValues(context);
            }
        }
        return subappValues;
    }

    protected void saveIncludePageValues(Map subappValues, Map redirectValues,
            FacesContext context, BeanDesc beanDesc, Object page,
            String[] properties, Map nextPageProperties, PageDesc pageDesc) {
        for (int i = 0; i < properties.length; ++i) {
            String propertyName = properties[i];
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (!nextPageProperties.containsKey(pd.getPropertyName()) ||
                    !PagePersistenceUtil.isPersistenceProperty(pd)) {
                continue;
            }
            if (pageDesc.isRedirectScopeProperty(propertyName)) {
                putValue(redirectValues, page, pd);
            } else {
                putValue(subappValues, page, pd);
            }
        }
    }

    protected void saveExcludePageValues(Map subappValues,
            FacesContext context, BeanDesc beanDesc, Object page,
            String[] properties, Map nextPageProperties) {
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!nextPageProperties.containsKey(pd.getPropertyName()) ||
                    !PagePersistenceUtil.isPersistenceProperty(pd)) {
                continue;
            }
            if (ArrayUtil.contains(properties, pd.getPropertyName())) {
                continue;
            }
            putValue(subappValues, page, pd);
        }
    }

    protected void saveDefaultPageValues(Map subappValues, Map redirectValues,
            FacesContext context, BeanDesc beanDesc, Object page,
            Map nextPageProperties, PageDesc pageDesc) {
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (!nextPageProperties.containsKey(pd.getPropertyName()) ||
                    !PagePersistenceUtil.isPersistenceProperty(pd)) {
                continue;
            }
            final Class thisPageType = pd.getPropertyType();
            final Class nextPageType = (Class) nextPageProperties
                    .get(propertyName);
            if (nextPageType != thisPageType) {
                final Object[] args = new Object[] { propertyName,
                        thisPageType.getName(), nextPageType.getName() };
                final String message = MessageFormatter.getMessage("WTDA0201",
                        args);
                logger.debug(message);
            }
            if (pageDesc.isRedirectScopeProperty(propertyName)) {
                putValue(redirectValues, page, pd);
            } else {
                putValue(subappValues, page, pd);
            }
        }
    }

    protected void putValue(Map map, Object page, PropertyDesc pd) {
        Object value = pd.getValue(page);
        map.put(pd.getPropertyName(), value);
    }

    protected void restoreValues(Map from, Map to) {
        for (Iterator itr = from.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            Object rebuildValue = HotdeployUtil.rebuildValue(value);
            //TODO support other lists.
            if (rebuildValue instanceof ArrayList) {
                ArrayList list = (ArrayList) rebuildValue;
                rebuildValue = list.clone();
            }
            to.put(key, rebuildValue);
        }
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
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

    public void setStateManager(TeedaStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public HtmlSuffix getHtmlSuffix() {
        return htmlSuffix;
    }

    public void setHtmlSuffix(HtmlSuffix htmlSuffix) {
        this.htmlSuffix = htmlSuffix;
    }

}
