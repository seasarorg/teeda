package org.seasar.teeda.core.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;


public interface ExpressionProcessor{
    
    public void processExpression(Object o, Class type);
    
    public Object evaluate(FacesContext context, Object expression)
        throws EvaluationException;
    
    public Integer toIndex(Object base, Object index)
        throws ReferenceSyntaxException;
        
    public Object resolveBase(FacesContext context, Object expression)
        throws PropertyNotFoundException;
    
    public Object getCoercedObject(Object newValue, Class type)
        throws EvaluationException;
}