package org.seasar.teeda.core.el;

import javax.faces.application.Application;
import javax.faces.el.ValueBinding;


public interface ValueBindingContext{
    
    public void setValueBindingName(String valueBindingName);
    
    public String getValueBindingName();
    
    public ValueBinding createValueBinding(Application application, String expression);
    
    public ELParser getELParser();
    
    public void setELParser(ELParser parser);
}
