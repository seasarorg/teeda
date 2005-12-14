package org.seasar.teeda.core.util;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;


public class MethodBindingUtil {

    private MethodBindingUtil(){
    }
    
    public static String invoke(MethodBinding methodBinding, FacesContext context){
        try {
            return (String)methodBinding.invoke(context, null);
        } catch (EvaluationException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof AbortProcessingException) {
                throw (AbortProcessingException)cause;
            }
            throw e;
        }
    }
}
