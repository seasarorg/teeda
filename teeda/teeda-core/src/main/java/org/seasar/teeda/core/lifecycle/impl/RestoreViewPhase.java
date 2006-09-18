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

import org.seasar.framework.util.Mru;
import org.seasar.teeda.core.lifecycle.AbstractPhase;
import org.seasar.teeda.core.portlet.FacesPortlet;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.PostbackUtil;

/**
 * @author shot
 */
public class RestoreViewPhase extends AbstractPhase {

    private static final String VIEW_ID_MRU_ATTR = RestoreViewPhase.class
            .getName()
            + ".VIEW_ID_MRU";

    private int viewIdMruSize = 16;

    public RestoreViewPhase() {
    }

    public int getViewIdMruSize() {
        return viewIdMruSize;
    }

    public void setViewIdMruSize(int viewIdMruSize) {
        this.viewIdMruSize = viewIdMruSize;
    }

    protected void executePhase(final FacesContext context)
            throws FacesException {
        final ExternalContext externalContext = context.getExternalContext();
        final String viewId = getViewId(context, externalContext);
        final String wid = setupWindowId(externalContext);
        final Map sessionMap = externalContext.getSessionMap();
        final String previousViewId = getViewIdFromSession(sessionMap, wid);
        PostbackUtil.setPostback(externalContext.getRequestMap(), viewId
                .equals(previousViewId));
        final UIViewRoot viewRoot = getViewRoot(context, viewId);
        context.setViewRoot(viewRoot);
        saveViewIdToSession(sessionMap, wid, viewId);
        initializeChildren(context, viewRoot);
        if (externalContext.getRequestParameterMap().isEmpty()) {
            context.renderResponse();
        }
    }

    private UIViewRoot getViewRoot(final FacesContext context,
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
        Mru mru = getViewIdMruFromSession(sessionMap);
        return (String) mru.get(windowId);
    }

    protected Mru getViewIdMruFromSession(final Map sessionMap) {
        Mru mru = (Mru) sessionMap.get(VIEW_ID_MRU_ATTR);
        if (mru == null) {
            mru = new Mru(viewIdMruSize);
            sessionMap.put(VIEW_ID_MRU_ATTR, mru);
        }
        return mru;
    }

    protected void saveViewIdToSession(final Map sessionMap,
            final String windowId, final String viewId) {

        Mru mru = getViewIdMruFromSession(sessionMap);
        mru.put(windowId, viewId);
    }

    protected PhaseId getCurrentPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}