package org.seasar.teeda.core.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;


public class NavigationHandlerUtil {

    private NavigationHandlerUtil(){
    }
    
    public static void handleNavigation(FacesContext context, String fromAction, String outCome){
        NavigationHandler handler = 
        	context.getApplication().getNavigationHandler();
        handler.handleNavigation(context, fromAction, outCome);
    }
}
