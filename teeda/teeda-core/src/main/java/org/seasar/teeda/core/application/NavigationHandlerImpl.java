/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationContextFactory;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class NavigationHandlerImpl extends NavigationHandler {

    public void handleNavigation(FacesContext context, String fromAction,
            String outcome) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        if (outcome == null) {
            return;
        }
        ExternalContext externalContext = context.getExternalContext();
        String viewId = context.getViewRoot().getViewId();
        NavigationContext navigationContext = getExactMatchNavigationCases(viewId, context);
        NavigationCaseContext navigationCaseContext = null;
        if(navigationContext != null){
            navigationCaseContext = getNavigationCaseContext(navigationContext, fromAction, outcome);
        }
        if(navigationCaseContext == null){
            navigationContext = getWildCardMatchNavigationCases(viewId, context);
            navigationCaseContext = getNavigationCaseContext(navigationContext, fromAction, outcome);
        }
        if(navigationCaseContext != null){
            ViewHandler viewHandler = context.getApplication().getViewHandler();
            String newViewId = navigationCaseContext.getToViewId();
            if(isRedirect(navigationCaseContext)) {
                String redirectPath = getRedirectActionPath(context, viewHandler, newViewId);
                redirect(context, externalContext, redirectPath, newViewId);
            } else {
                render(context, viewHandler, newViewId);
            }
        }else{
            //Stay current ViewRoot.
        }
    }

    protected String getRedirectActionPath(FacesContext context, ViewHandler viewHandler, String newViewId){
        return viewHandler.getActionURL(context, newViewId);
    }
    
    protected void redirect(FacesContext context, ExternalContext externalContext, String redirectPath, String newViewId) {
        try {
            externalContext.redirect(externalContext.encodeActionURL(redirectPath));
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        context.responseComplete();
    }
    
    protected void render(FacesContext context, ViewHandler viewHandler, String newViewId) {
        UIViewRoot viewRoot = viewHandler.createView(context, newViewId);
        viewRoot.setViewId(newViewId);
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    protected boolean isRedirect(NavigationCaseContext caseContext){
        return caseContext.isRedirect();
    }
    
    protected NavigationCaseContext getNavigationCaseContext(NavigationContext navContext, String fromAction, String outcome){
        if(navContext == null){
            return null;
        }
        List navCases = navContext.getNavigationCases();
        final NavigationCaseContext inCaseContext = new NavigationCaseContext(fromAction, outcome); 
        for(Iterator itr = IteratorUtil.getIterator(navCases); itr.hasNext();){
            NavigationCaseContext caseContext = (NavigationCaseContext)itr.next();
            if(caseContext.equals(inCaseContext)){
                return caseContext;
            }
        }
        return null;
    }

    protected NavigationContext getExactMatchNavigationCases(String viewId, FacesContext context) {
        Map map = NavigationContextFactory.getNavigationContexts(context);
        if(map != null){
            return (NavigationContext)map.get(viewId);
        }
        return null;
    }
    
    protected NavigationContext getWildCardMatchNavigationCases(String viewId, FacesContext context) {
        Map map = NavigationContextFactory.getWildCardMatchNavigationContexts(context);
        if(map != null){
            return (NavigationContext)map.get(viewId);
        }
        return null;
    }
    
}