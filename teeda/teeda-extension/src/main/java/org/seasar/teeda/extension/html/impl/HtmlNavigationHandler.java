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

import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.NavigationHandlerImpl;
import org.seasar.teeda.core.portlet.FacesPortlet;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author higa
 *
 */
public class HtmlNavigationHandler extends NavigationHandlerImpl {

    private static final String REDIRECTING_PATH = HtmlNavigationHandler.class
            .getName()
            + ".REDIRECTING_PATH";

    private PagePersistence pagePersistence;

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    protected void redirect(FacesContext context,
            ExternalContext externalContext, String redirectPath,
            String newViewId) {
        pagePersistence.save(context, newViewId);
        setupRedirectingPath(externalContext, redirectPath);
        super.redirect(context, externalContext, redirectPath, newViewId);
    }

    protected void setupRedirectingPath(ExternalContext extCtx,
            String redirectPath) {
        Map sessionMap = extCtx.getSessionMap();
        sessionMap.put(REDIRECTING_PATH, redirectPath);
    }

    public static void clearRedirectingPath(ExternalContext extCtx) {
        Map sessionMap = extCtx.getSessionMap();
        sessionMap.remove(REDIRECTING_PATH);
    }

    public static String getRedirectingPath(ExternalContext extCtx) {
        Map sessionMap = extCtx.getSessionMap();
        return (String) sessionMap.get(REDIRECTING_PATH);
    }

    public void handleNavigation(FacesContext context, String fromAction,
            String outcome) {
        super.handleNavigation(context, fromAction, outcome);
        if (context.getResponseComplete() || context.getRenderResponse()) {
            return;
        }
        String viewId = context.getViewRoot().getViewId();
        String path = calcPathFromOutcome(viewId, outcome);
        if (path == null) {
            return;
        }
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        // PortletSupport
        if (!PortletUtil.isPortlet(context)) {
            String redirectPath = getRedirectActionPath(context, viewHandler,
                    path);
            redirect(context, context.getExternalContext(), redirectPath, path);
        } else {
            context.getExternalContext().getRequestMap().put(
                    FacesPortlet.REDIRECT_TO_PORTLET, Boolean.TRUE);
            super.render(context, viewHandler, path);
        }

    }

    protected static String calcPathFromOutcome(String viewId, String outcome) {
        if (outcome == null) {
            return null;
        }
        int pos = viewId.lastIndexOf('/');
        int pos2 = viewId.lastIndexOf('.');
        String pathFirst = viewId.substring(0, pos + 1);
        String pathLast = viewId.substring(pos2);
        String[] names = StringUtil.split(outcome, "_");
        if (names.length == 1) {
            return pathFirst + outcome + pathLast;
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < names.length; i++) {
            buf.append(StringUtil.decapitalize(names[i]));
            if (i != names.length - 1) {
                buf.append("/");
            }
        }
        pos = viewId.indexOf('/', 1);
        pathFirst = viewId.substring(0, pos + 1);
        return pathFirst + buf + pathLast;
    }
}
