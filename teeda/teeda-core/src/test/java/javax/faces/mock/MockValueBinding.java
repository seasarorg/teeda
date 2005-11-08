package javax.faces.mock;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;


public class MockValueBinding extends ValueBinding {

	private Object obj_ = null;
	
	public Object getValue(FacesContext facescontext)
			throws EvaluationException, PropertyNotFoundException {
		return obj_;
	}

	public void setValue(FacesContext facescontext, Object obj)
			throws EvaluationException, PropertyNotFoundException {
		obj_ = obj;
	}

	public boolean isReadOnly(FacesContext facescontext)
			throws EvaluationException, PropertyNotFoundException {
		return false;
	}

	public Class getType(FacesContext facescontext) throws EvaluationException,
			PropertyNotFoundException {
		return null;
	}

}
