package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.JsfConstants;

public class MockVariableResolver extends VariableResolver {

    private Map values_ = new HashMap();
    private boolean inited_ = false;
    public MockVariableResolver(){
    }
    
    public void putValue(String key, Object value){
        values_.put(key, value);
    }
    
    public Object resolveVariable(FacesContext context, String name) 
    	throws EvaluationException {
    	if(!inited_){
    		values_.put(JsfConstants.APPLICATION_SCOPE, context.getExternalContext().getApplicationMap());
    		values_.put(JsfConstants.SESSION_SCOPE, context.getExternalContext().getSessionMap());
    		values_.put(JsfConstants.REQUEST_SCOPE, context.getExternalContext().getRequestMap());
    		inited_ = true;
    	}
        return values_.get(name);
    }

}
