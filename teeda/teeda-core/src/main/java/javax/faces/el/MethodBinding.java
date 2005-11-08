package javax.faces.el;

import javax.faces.context.FacesContext;

public abstract class MethodBinding {

	public abstract Object invoke(FacesContext context, Object[] params)
		throws EvaluationException, MethodNotFoundException;
	
	public abstract Class getType(FacesContext context) throws MethodNotFoundException;
	
	public String getExpressionString(){
		return null;
	}
}
