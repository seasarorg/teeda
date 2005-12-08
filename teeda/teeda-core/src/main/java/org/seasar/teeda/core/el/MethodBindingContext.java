package org.seasar.teeda.core.el;

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;


public interface MethodBindingContext {

    public void setMethodBindingName(String methodBindingName);
    
    public String getMethodBindingName();
    
    public void setValueBindingContext(ValueBindingContext valueBindingContext);
    
    public MethodBinding createMethodBinding(Application application, String ref, Class[] params);
}
