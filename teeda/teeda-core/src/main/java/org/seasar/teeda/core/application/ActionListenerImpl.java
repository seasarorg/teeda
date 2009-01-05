/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.component.ActionSource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.seasar.framework.log.Logger;
import org.seasar.teeda.core.util.ErrorPageManager;
import org.seasar.teeda.core.util.MethodBindingUtil;
import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.core.util.NullErrorPageManagerImpl;

/**
 * @author higa
 */
public class ActionListenerImpl implements ActionListener {

    private static Logger logger = Logger.getLogger(ActionListenerImpl.class);

    public static final String errorManager_BINDING = "bindingType=may";

    private ErrorPageManager errorPageManager = new NullErrorPageManagerImpl();

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        ActionSource actionSource = (ActionSource) actionEvent.getComponent();
        MethodBinding mb = actionSource.getAction();
        String fromAction = null;
        String outcome = null;
        if (mb != null) {
            fromAction = mb.getExpressionString();
            try {
                outcome = MethodBindingUtil.invoke(mb, context);
                processAfterInvoke(context, fromAction, outcome);
            } catch (EvaluationException ex) {
                Throwable cause = ex.getCause();
                try {
                    ExternalContext extContext = context.getExternalContext();
                    if (errorPageManager.handleException(cause, context,
                            extContext)) {
                        context.responseComplete();
                    } else {
                        throw ex;
                    }
                } catch (IOException ioe) {
                    logger.log(ioe);
                    throw ex;
                }
            }
        }
        NavigationHandlerUtil.handleNavigation(context, fromAction, outcome);
        context.renderResponse();
    }

    protected void processAfterInvoke(FacesContext context, String fromAction,
            String outcome) {
    }

    public ErrorPageManager getErrorPageManager() {
        return errorPageManager;
    }

    public void setErrorPageManager(ErrorPageManager errorPageManager) {
        this.errorPageManager = errorPageManager;
    }

}
