package org.seasar.teeda.core.util;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.exception.IllegalClassTypeException;


public class ApplicationUtil {

    private ApplicationUtil(){
    }
    
    public static Application getApplicationFromFactory(){
        ApplicationFactory appFactory = FactoryFinderUtil.getApplicationFactory();
        return appFactory.getApplication();
    }
    
    public static Application getApplicationFromContext(){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication();
    }
    
    public static void verifyClassType(Class expected, Class actual){
        if(!ClassUtil.isAssignableFrom(expected, actual)){
            throw new IllegalClassTypeException(expected, actual);
        }

    }
}
