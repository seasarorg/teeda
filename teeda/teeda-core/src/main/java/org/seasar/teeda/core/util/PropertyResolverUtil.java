package org.seasar.teeda.core.util;

import javax.faces.application.Application;
import javax.faces.el.PropertyResolver;


public class PropertyResolverUtil {

    private PropertyResolverUtil(){
    }
    
    public static Object getValue(Application application, Object base, Object property, Integer index){
        PropertyResolver resolver = application.getPropertyResolver();
        return (index == null) ? resolver.getValue(base, property) : resolver.getValue(base, index.intValue());
    }

    public static boolean isReadOnly(Application application, Object base, Object property, Integer index) {
        PropertyResolver resolver = application.getPropertyResolver();
        return (index == null) ? resolver.isReadOnly(base, property) : resolver.isReadOnly(base, index.intValue());
    }

    public static Class getType(Application application, Object base, Object property, Integer index) {
        PropertyResolver resolver = application.getPropertyResolver();
        return (index == null) ? resolver.getType(base, property) : resolver.getType(base, index.intValue());
    }

}
