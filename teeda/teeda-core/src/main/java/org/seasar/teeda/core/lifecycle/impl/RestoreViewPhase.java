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
package org.seasar.teeda.core.lifecycle.impl;

import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.internal.WindowIdUtil;
import javax.portlet.PortletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.core.lifecycle.AbstractPhase;
import org.seasar.teeda.core.portlet.FacesPortlet;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.ServletExternalContextUtil;

/**
 * @author shot
 */
public class RestoreViewPhase extends AbstractPhase {

    private static final String VIEW_ID_LRU_ATTR = RestoreViewPhase.class
            .getName()
            + ".VIEW_ID_LRU";

    private static final String PREVIOUS_VIEW_ID = "previousViewId";

    private int viewIdLruSize = 16;

    public RestoreViewPhase() {
    }

    public int getViewIdLruSize() {
        return viewIdLruSize;
    }

    public void setViewIdLruSize(int viewIdLruSize) {
        this.viewIdLruSize = viewIdLruSize;
    }

    protected void executePhase(final FacesContext context)
            throws FacesException {
        final RestoreValueHolder holder = setUpRestoreViewPhase(context);
        final String viewId = holder.getCurrentViewId();
        final String wid = holder.getWid();

        final UIViewRoot viewRoot = composeViewRoot(context, viewId);
        context.setViewRoot(viewRoot);

        final ExternalContext externalContext = context.getExternalContext();
        saveViewIdToSession(externalContext.getSessionMap(), wid, viewId);
        initializeChildren(context, viewRoot);
        Map requestParameterMap = externalContext.getRequestParameterMap();
        if (requestParameterMap.isEmpty()) {
            context.renderResponse();
        }
    }

    protected RestoreValueHolder setUpRestoreViewPhase(
            final FacesContext context) {
        RestoreValueHolder holder = new RestoreValueHolder(context);
        final ExternalContext externalContext = context.getExternalContext();
        final String viewId = holder.getCurrentViewId();
        final String previousViewId = holder.getPreviousViewId();
        Map requestMap = externalContext.getRequestMap();
        requestMap.put(PREVIOUS_VIEW_ID, previousViewId);
        PostbackUtil.setPostback(requestMap, viewId.equals(previousViewId)
                && ServletExternalContextUtil.isPost(externalContext));
        return holder;
    }

    private UIViewRoot composeViewRoot(final FacesContext context,
            final String viewId) {
        final ViewHandler viewHandler = FacesContextUtil
                .getViewHandler(context);
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot != null) {
            final Locale locale = viewHandler.calculateLocale(context);
            if (locale != null) {
                viewRoot.setLocale(locale);
            }
        } else {
            viewRoot = viewHandler.createView(context, viewId);
        }
        return viewRoot;
    }

    private String getViewId(final FacesContext context,
            final ExternalContext externalContext) {
        // PortletSupport
        if (PortletUtil.isPortlet(context)) {
            final PortletRequest request = (PortletRequest) externalContext
                    .getRequest();
            return request.getParameter(FacesPortlet.VIEW_ID);
        } else {
            return ExternalContextUtil.getViewId(externalContext);
        }
    }

    protected String setupWindowId(final ExternalContext externalContext)
            throws FacesException {
        String wid = null;
        if (WindowIdUtil
                .needNewWindow(externalContext.getRequestParameterMap())) {
            wid = WindowIdUtil.createWindowId();
            Object response = externalContext.getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse res = (HttpServletResponse) response;
                Cookie cookie = new Cookie(WindowIdUtil.TEEDA_WID, wid);
                res.addCookie(cookie);
            }
        } else {
            final Map cookieMap = externalContext.getRequestCookieMap();
            if (cookieMap == null) {
                return null;
            }
            Cookie cookie = (Cookie) cookieMap.get(WindowIdUtil.TEEDA_WID);
            if (cookie != null) {
                wid = cookie.getValue();
            }
        }
        return wid;
    }

    protected String getViewIdFromSession(final Map sessionMap,
            final String windowId) {
        LruHashMap mru = getViewIdLruFromSession(sessionMap);
        return (String) mru.get(windowId);
    }

    protected LruHashMap getViewIdLruFromSession(final Map sessionMap) {
        LruHashMap lru = (LruHashMap) sessionMap.get(VIEW_ID_LRU_ATTR);
        if (lru == null) {
            lru = new LruHashMap(viewIdLruSize);
            sessionMap.put(VIEW_ID_LRU_ATTR, lru);
        }
        return lru;
    }

    protected void saveViewIdToSession(final Map sessionMap,
            final String windowId, final String viewId) {
        LruHashMap lru = getViewIdLruFromSession(sessionMap);
        lru.put(windowId, viewId);
    }

    protected PhaseId getCurrentPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    private class RestoreValueHolder {

        private String currentViewId;

        private String wid;

        private String previousViewId;

        public RestoreValueHolder(FacesContext context) {
            final ExternalContext externalContext = context
                    .getExternalContext();
            this.currentViewId = getViewId(context, externalContext);
            this.wid = setupWindowId(externalContext);
            this.previousViewId = getViewIdFromSession(externalContext
                    .getSessionMap(), this.wid);
        }

        public String getCurrentViewId() {
            return currentViewId;
        }

        public String getWid() {
            return wid;
        }

        public String getPreviousViewId() {
            return previousViewId;
        }

    }

}