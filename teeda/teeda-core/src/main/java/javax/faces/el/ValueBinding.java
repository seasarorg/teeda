package javax.faces.el;

import javax.faces.context.FacesContext;

/**
 * @deprecated
 */
public abstract class ValueBinding {

	public ValueBinding() {
	}

	public abstract Object getValue(FacesContext facescontext)
			throws EvaluationException, PropertyNotFoundException;

	public abstract void setValue(FacesContext facescontext, Object obj)
			throws EvaluationException, PropertyNotFoundException;

	public abstract boolean isReadOnly(FacesContext facescontext)
			throws EvaluationException, PropertyNotFoundException;

	public abstract Class getType(FacesContext facescontext)
			throws EvaluationException, PropertyNotFoundException;

	public String getExpressionString() {
		return null;
	}
}
