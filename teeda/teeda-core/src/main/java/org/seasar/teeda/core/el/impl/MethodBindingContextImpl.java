package org.seasar.teeda.core.el.impl;

import java.lang.reflect.Constructor;

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.ConstructorUtil;
import org.seasar.teeda.core.el.MethodBindingContext;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author Shinpei Ohtani
 */
public class MethodBindingContextImpl implements MethodBindingContext{

    private String methodBindingName_;
    private ValueBindingContext valueBindingContext_;
    public void setMethodBindingName(String methodBindingName) {
        methodBindingName_ = methodBindingName;
    }

    public String getMethodBindingName() {
        return methodBindingName_;
    }

    public void setValueBindingContext(ValueBindingContext valueBindingContext) {
        valueBindingContext_ = valueBindingContext;
    }

    public MethodBinding createMethodBinding(Application application, String ref, Class[] params) {
        if(params == null){
            throw new EmptyRuntimeException("parameters");
        }
        ValueBinding vb = valueBindingContext_.createValueBinding(application, ref);
        Class clazz = ClassUtil.forName(methodBindingName_);
        Class[] argTypes = new Class[]{ValueBinding.class, Class[].class};
        Constructor c = null;
        Object o = null;
        try{
            c = clazz.getConstructor(argTypes);
            o = ConstructorUtil.newInstance(c, new Object[]{vb, params});
            return (MethodBinding)o;
        }catch (NoSuchMethodException e){
            o = ClassUtil.newInstance(clazz);
            if(o instanceof StateHolder && o instanceof MethodBinding){
                return getRestoredMethodBinding((StateHolder)o, ref, params);
            }
        }
        return null;
    }

    private MethodBinding getRestoredMethodBinding(StateHolder holder, String ref, Class[] params){
        FacesContext context = FacesContext.getCurrentInstance();
        holder.restoreState(context, new Object[]{valueBindingContext_, ref, params});
        return (MethodBinding)holder;

    }
}
