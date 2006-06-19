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
package org.seasar.teeda.core.application;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.core.util.IteratorUtil;
import org.seasar.teeda.core.util.PortletUtil;

/**
 * @author shot
 */
public class NavigationHandlerImpl extends NavigationHandler {

    private static final Logger logger_ = Logger
            .getLogger(NavigationHandlerImpl.class);

    public void handleNavigation(FacesContext context, String fromAction,
            String outcome) {
        AssertionUtil.assertNotNull("context is null.", context);
        if (outcome == null) {
            return;
        }
        ExternalContext externalContext = context.getExternalContext();
        String viewId = context.getViewRoot().getViewId();
        NavigationCaseContext navigationCaseContext = getNavigationCaseContext(
                fromAction, outcome, viewId);
        if (navigationCaseContext != null) {
            ViewHandler viewHandler = context.getApplication().getViewHandler();
            String newViewId = navigationCaseContext.getToViewId();
            if (isRedirect(context, navigationCaseContext)) {
                String redirectPath = getRedirectActionPath(context,
                        viewHandler, newViewId);
                redirect(context, externalContext, redirectPath, newViewId);
            } else {
                render(context, viewHandler, newViewId);
            }
        } else {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Stay current ViewRoot");
            }
        }
    }

    protected String getRedirectActionPath(FacesContext context,
            ViewHandler viewHandler, String newViewId) {
        return viewHandler.getActionURL(context, newViewId);
    }

    protected void redirect(FacesContext context,
            ExternalContext externalContext, String redirectPath,
            String newViewId) {
        try {
            externalContext.redirect(externalContext
                    .encodeActionURL(redirectPath));
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        context.responseComplete();
    }

    protected void render(FacesContext context, ViewHandler viewHandler,
            String newViewId) {
        UIViewRoot viewRoot = viewHandler.createView(context, newViewId);
        viewRoot.setViewId(newViewId);
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    protected boolean isRedirect(FacesContext context, NavigationCaseContext caseContext) {
        if(PortletUtil.isPortlet(context)) {
           return false; 
        }
        return caseContext.isRedirect();
    }

    protected NavigationCaseContext getNavigationCaseContext(
            String fromAction, String outcome,
            String viewId) {
        //exact match
        List navigationList = getExactMatchNavigationCases(viewId);
        NavigationCaseContext navigationCaseContext = getNavigationCaseContextInternal(
                navigationList, fromAction, outcome);
        //wildcard match
        if (navigationCaseContext == null) {
            navigationList = getWildCardMatchNavigationCases(viewId);
            navigationCaseContext = getNavigationCaseContextInternal(
                    navigationList, fromAction, outcome);
        }
        //and * match
        if (navigationCaseContext == null) {
            navigationList = getDefaultNavigationCases(viewId);
            navigationCaseContext = getNavigationCaseContextInternal(
                    navigationList, fromAction, outcome);
        }
        return navigationCaseContext;
    }

    private NavigationCaseContext getNavigationCaseContextInternal(
            List navigationList, String fromAction, String outcome) {
        for (Iterator itr = IteratorUtil.getIterator(navigationList); itr
                .hasNext();) {
            NavigationContext navContext = (NavigationContext) itr.next();
            NavigationCaseContext caseContext = navContext.getNavigationCase(
                    fromAction, outcome);
            if (caseContext != null) {
                return caseContext;
            }
        }
        return null;
    }

    protected List getExactMatchNavigationCases(String viewId) {
        Map map = NavigationResource.getNavigationContexts();
        if (map != null) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Exact macth. viewId = " + viewId);
            }
            return (List) map.get(viewId);
        }
        return null;
    }

    protected List getWildCardMatchNavigationCases(String viewId) {
        Map map = NavigationResource
                .getWildCardMatchNavigationContexts();
        if (map != null) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Wildcard macth. viewId = " + viewId);
            }
            for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
                String key = (String) itr.next();
                key = key.substring(0, key.lastIndexOf("*"));
                if (viewId.startsWith(key)) {
                    return (List) map.get(key + "*");
                }
            }
        }
        return null;
    }

    protected List getDefaultNavigationCases(String viewId) {
        Map map = NavigationResource
                .getDefaultMatchNavigationContexts();
        if (map != null) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Default macth. viewId = " + viewId);
            }
            return (List) map.get("*");
        }
        return null;
    }
}