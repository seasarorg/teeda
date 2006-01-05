package org.seasar.teeda.core.mock;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;


public class MockValueBinding extends ValueBinding implements StateHolder{

	private Object obj_ = null;
	private boolean transientValue_ = false;
    public MockValueBinding(){
    }
    
	public Object getValue(FacesContext context)
			throws EvaluationException, PropertyNotFoundException {
		return obj_;
	}

	public void setValue(FacesContext context, Object obj)
			throws EvaluationException, PropertyNotFoundException {
		obj_ = obj;
	}

	public boolean isReadOnly(FacesContext context)
			throws EvaluationException, PropertyNotFoundException {
		return false;
	}

	public Class getType(FacesContext context) throws EvaluationException,
			PropertyNotFoundException {
		return null;
	}

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
    }

}
