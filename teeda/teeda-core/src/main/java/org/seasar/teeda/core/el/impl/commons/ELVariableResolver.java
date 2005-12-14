package org.seasar.teeda.core.el.impl.commons;

import javax.faces.context.FacesContext;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

/**
 * @author Shinpei Ohtani
 * A bridge from javax.servlet.jsp.el.VariableResolver to JSF VariableResolver
 */
public class ELVariableResolver implements VariableResolver {

    private FacesContext context_;
    public ELVariableResolver(FacesContext context){
        context_ = context;
    }
    
    public Object resolveVariable(String name) throws ELException {
        return context_.getApplication().getVariableResolver().resolveVariable(context_, name);
    }

}
