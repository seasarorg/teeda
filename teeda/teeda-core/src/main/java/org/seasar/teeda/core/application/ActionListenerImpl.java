package org.seasar.teeda.core.application;

import javax.faces.component.ActionSource;
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


public class ActionListenerImpl implements ActionListener {

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        ActionSource source = (ActionSource)actionEvent.getComponent();
        UIParameterUtil.saveParametersToRequest(source, context);
        MethodBinding methodBinding = source.getActionListener();
        String fromAction = null;
        String outCome = null;
        if(methodBinding != null){
            fromAction = methodBinding.getExpressionString();
            try{
                outCome = MethodBindingUtil.invoke(methodBinding, context);
            }catch(MethodNotFoundException e){
                throw new ExtendMethodNotFoundExceptin(e, methodBinding);
            }catch(EvaluationException e){
                throw new ExtendEvaluationException(e, methodBinding);
            }
        }
        NavigationHandlerUtil.handleNavigation(context, fromAction, outCome);
        
        context.renderResponse();
    }

}
