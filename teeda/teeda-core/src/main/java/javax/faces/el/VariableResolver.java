package javax.faces.el;

import javax.faces.context.FacesContext;

public abstract class VariableResolver {

    public abstract Object resolveVariable(FacesContext context, String name)
            throws EvaluationException;

}
