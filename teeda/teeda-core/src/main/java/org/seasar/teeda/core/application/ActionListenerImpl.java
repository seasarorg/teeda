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

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.seasar.teeda.core.exception.ExtendEvaluationException;
import org.seasar.teeda.core.exception.ExtendMethodNotFoundExceptin;
import org.seasar.teeda.core.util.MethodBindingUtil;
import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.core.util.UIParameterUtil;

/**
 * @author higa
 */
public class ActionListenerImpl implements ActionListener {

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        UICommand command = (UICommand) actionEvent.getComponent();
        UIParameterUtil.saveParametersToRequest(command, context);
        MethodBinding methodBinding = command.getAction();
        String fromAction = null;
        String outCome = null;
        if (methodBinding != null) {
            fromAction = methodBinding.getExpressionString();
            try {
                outCome = MethodBindingUtil.invoke(methodBinding, context);
            } catch (MethodNotFoundException e) {
                throw new ExtendMethodNotFoundExceptin(e, methodBinding);
            } catch (EvaluationException e) {
                throw new ExtendEvaluationException(e, methodBinding);
            }
        }
        NavigationHandlerUtil.handleNavigation(context, fromAction, outCome);
        context.renderResponse();
    }

}
