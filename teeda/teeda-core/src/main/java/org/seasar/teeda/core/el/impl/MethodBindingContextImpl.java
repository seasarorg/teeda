/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.el.impl;

import java.lang.reflect.Constructor;

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.ConstructorUtil;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.MethodBindingContext;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.exception.IllegalClassTypeException;
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
        /*
        if(params == null){
            throw new EmptyRuntimeException("parameters");
        }
        */
        ValueBinding vb = valueBindingContext_.createValueBinding(application, ref);
        ELParser parser = valueBindingContext_.getELParser();
        if(!(vb instanceof ValueBindingBase)){
        	throw new IllegalClassTypeException(ValueBindingBase.class, ValueBinding.class);
        }
        Class clazz = ClassUtil.forName(methodBindingName_);
        Class[] argTypes = new Class[]{ValueBindingBase.class, Class[].class, ELParser.class};
        Constructor c = null;
        Object o = null;
        try{
            c = clazz.getConstructor(argTypes);
            o = ConstructorUtil.newInstance(c, new Object[]{vb, params, parser});
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
