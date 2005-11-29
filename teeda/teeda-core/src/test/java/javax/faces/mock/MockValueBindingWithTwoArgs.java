package javax.faces.mock;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

public class MockValueBindingWithTwoArgs extends ValueBinding{

    private Application app_;
    private String expression_;
    public MockValueBindingWithTwoArgs(Application app, String expression){
        app_ = app;
        expression_ = expression;
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
