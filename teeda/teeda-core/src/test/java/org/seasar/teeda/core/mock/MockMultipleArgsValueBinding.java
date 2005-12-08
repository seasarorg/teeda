package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ELParser;

public class MockMultipleArgsValueBinding extends ValueBinding{

    private Application app_;
    private String expression_;
    private ELParser parser_;
    public MockMultipleArgsValueBinding(Application app, String expression, ELParser parser){
        app_ = app;
        expression_ = expression;
        parser_ = parser;
    }
    
    public Object getValue(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return null;
    }

    public void setValue(FacesContext context, Object obj)
        throws EvaluationException, PropertyNotFoundException {
    }

    public boolean isReadOnly(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return false;
    }

    public Class getType(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return null;
    }
    
}
