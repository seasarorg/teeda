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

import java.io.IOException;

import javax.faces.component.ActionSource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.teeda.core.application.ActionListenerImpl;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.MethodBindingUtil;
import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.extension.exception.IllegalPageTransitionException;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.util.PageTransitionUtil;

/**
 * @author higa
 *
 */
public class HtmlActionListener extends ActionListenerImpl {

    private PagePersistence pagePersistence;

    private NamingConvention nc;

    private static final Object[] EMPTY_ARGS = new Object[0];

    private static Logger logger = Logger.getLogger(HtmlActionListener.class);

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        final FacesContext context = FacesContext.getCurrentInstance();
        final ActionSource actionSource = (ActionSource) actionEvent
                .getComponent();
        final MethodBinding mb = actionSource.getAction();
        if (mb == null) {
            NavigationHandlerUtil.handleNoNavigation(context);
            context.renderResponse();
            return;
        }
        try {
            final String fromAction = mb.getExpressionString();
            final String componentName = MethodBindingUtil
                    .getComponentName(fromAction);
            final String methodName = MethodBindingUtil
                    .getMethodName(fromAction);
            if (componentName != null && methodName != null) {
                final Object component = DIContainerUtil
                        .getComponentNoException(componentName);
                if (component != null) {
                    final Class pageClass = component.getClass();
                    final BeanDesc bd = BeanDescFactory.getBeanDesc(pageClass);
                    if (isExtendReturnType(bd, methodName)) {
                        Class ret = (Class) bd.invoke(component, methodName,
                                EMPTY_ARGS);
                        final String pageSuffix = nc.getPageSuffix();
                        if (ret != null && !ret.getName().endsWith(pageSuffix)) {
                            throw new IllegalPageTransitionException(ret);
                        }
                        final String outcome = PageTransitionUtil
                                .getNextPageTransition(pageClass, ret, nc);
                        processAfterInvoke(context, fromAction, outcome);
                        NavigationHandlerUtil.handleNavigation(context,
                                fromAction, outcome);
                        context.renderResponse();
                    }
                }
            }
        } catch (Exception e) {
            try {
                ExternalContext extContext = context.getExternalContext();
                if (getErrorPageManager().handleException(e, context,
                        extContext)) {
                    context.responseComplete();
                } else {
                    throw new EvaluationException(e);
                }
            } catch (IOException ioe) {
                logger.log(ioe);
                throw new EvaluationException(e);
            }
        }
        if (!context.getRenderResponse() && !context.getResponseComplete()) {
            super.processAction(actionEvent);
        }
    }

    private static boolean isExtendReturnType(BeanDesc bd, String methodName) {
        if (bd == null || methodName == null) {
            return false;
        }
        return bd.hasMethod(methodName)
                && bd.getMethod(methodName).getReturnType() == Class.class;
    }

    public PagePersistence getPagePersistence() {
        return pagePersistence;
    }

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    public NamingConvention getNamingConvention() {
        return nc;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.nc = namingConvention;
    }

    protected void processAfterInvoke(FacesContext context, String fromAction,
            String outcome) {
        super.processAfterInvoke(context, fromAction, outcome);
        if (fromAction != null && fromAction.indexOf(".doFinish") > 0) {
            pagePersistence.removeSubApplicationPages(context);
        }
    }
}