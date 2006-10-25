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

import javax.faces.component.ActionSource;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.seasar.teeda.core.util.MethodBindingUtil;
import org.seasar.teeda.core.util.NavigationHandlerUtil;

/**
 * @author higa
 * @author shot
 */
public class ActionListenerImpl implements ActionListener {

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        ActionSource actionSource = (ActionSource) actionEvent.getComponent();
        MethodBinding mb = actionSource.getAction();
        String fromAction = null;
        String outcome = null;
        if (mb != null) {
            fromAction = mb.getExpressionString();
            outcome = MethodBindingUtil.invoke(mb, context);
            processAfterInvoke(context, fromAction, outcome);
        }
        NavigationHandlerUtil.handleNavigation(context, fromAction, outcome);
        context.renderResponse();
    }

    protected void processAfterInvoke(FacesContext context, String fromAction,
            String outcome) {
    }

}
