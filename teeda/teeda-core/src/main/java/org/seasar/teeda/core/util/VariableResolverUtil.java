package org.seasar.teeda.core.util;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.JsfConstants;


public class VariableResolverUtil {

    private static final String[] DEFAULT_SCOPES = {
        JsfConstants.REQUEST_SCOPE, JsfConstants.SESSION_SCOPE, JsfConstants.APPLICATION_SCOPE};
    
    private VariableResolverUtil(){
    }
    
    public static Object resolveVariable(FacesContext context, String name){
        if(context == null){
            context = FacesContext.getCurrentInstance();
        }
        return context.getApplication().getVariableResolver().resolveVariable(context, name);
    }
    
    public static Map getDefaultScopeMap(FacesContext context, VariableResolver resolver, String key){
    	return getScopeMap(context, resolver, key, DEFAULT_SCOPES);
    }
        
    private static Map getScopeMap(FacesContext context, VariableResolver resolver, String key, String[] scopes){
        for(int i = 0; i < scopes.length; i++){
            Map scopeMap = (Map)resolver.resolveVariable(context, scopes[i]);
            if(scopeMap.get(key) != null){
                return scopeMap;
            }
        }
        return null;
    	
    }
}
