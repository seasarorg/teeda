package org.seasar.teeda.core.util;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


public class ValueBindingUtil {

    private ValueBindingUtil(){
    }
    
    public static Object getValue(ValueBinding vb, FacesContext context){
        return vb.getValue(context);
    }
    
    public static void setValue(ValueBinding vb, FacesContext context, Object value){
        vb.setValue(context, value);
    }
}
