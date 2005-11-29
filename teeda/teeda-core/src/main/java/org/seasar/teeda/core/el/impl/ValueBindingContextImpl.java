package org.seasar.teeda.core.el.impl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.el.ValueBindingContext;


public class ValueBindingContextImpl implements ValueBindingContext {

    private Map cache_ = new HashMap();
    private String valueBindingName_;
    public ValueBindingContextImpl(){
    }
    
    public void setValueBindingName(String valueBindingName) {
        valueBindingName_ = valueBindingName;
    }

    public String getValueBindingName() {
        return valueBindingName_;
    }

    public ValueBinding createValueBinding(Application application,
            String expression) {
        if(StringUtil.isEmpty(expression)){
            throw new EmptyRuntimeException("Expression string");
        }
        if(application == null){
            throw new EmptyRuntimeException("Application");
        }
        ValueBinding vb = getValueBindingFromCache(application, expression);
        if(vb != null){
            return vb;
        }
        Class clazz = ClassUtil.forName(valueBindingName_);
        Class[] argTypes = new Class[]{Application.class, String.class};
        try{
            Constructor c = clazz.getConstructor(argTypes);
            vb = (ValueBinding)ConstructorUtil.newInstance(c, new Object[]{application, expression});
            cache_.put(expression, vb);
            return vb;
        }catch (NoSuchMethodException e){
            vb = (ValueBinding)ClassUtil.newInstance(clazz);
            if(vb instanceof StateHolder){
                return getRestoredValueBinding((StateHolder)vb, expression);
            }
        }
        return null;
    }

    private ValueBinding getRestoredValueBinding(StateHolder holder, String expression){
        FacesContext context = FacesContext.getCurrentInstance();
        holder.restoreState(context, expression);
        return (ValueBinding)holder;
    }
    
    private ValueBinding getValueBindingFromCache(Application application, String expression){
        ValueBinding vb = (ValueBinding)cache_.get(expression);
        if(vb != null && vb instanceof StateHolder){
            vb = getRestoredValueBinding((StateHolder)vb, expression);
        }
        return vb;
    }
}
