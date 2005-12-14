package org.seasar.teeda.core.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;


public class FacesContextUtil {

    private FacesContextUtil(){
    }
    
    public static NavigationHandler getNavigationHandler(FacesContext context){
        return context.getApplication().getNavigationHandler();
    }
}
