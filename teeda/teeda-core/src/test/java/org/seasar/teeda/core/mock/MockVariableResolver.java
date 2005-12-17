package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

public class MockVariableResolver extends VariableResolver {

    private Map values_ = new HashMap();
    public MockVariableResolver(){
    }
    
    public void putValue(String key, Object value){
        values_.put(key, value);
    }
    
    public Object resolveVariable(FacesContext context, String name) 
    	throws EvaluationException {
        return values_.get(name);
    }

}
