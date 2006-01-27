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
import java.util.Collections;
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
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingContext;

/**
 * @author Shinpei Ohtani
 */
public class ValueBindingContextImpl implements ValueBindingContext {

    private Map cache_ = Collections.synchronizedMap(new HashMap());
    private String valueBindingName_;
    private ELParser parser_;
    public ValueBindingContextImpl(){
    }
    
    public void setValueBindingName(String valueBindingName) {
        valueBindingName_ = valueBindingName;
    }

    public String getValueBindingName() {
        return valueBindingName_;
    }

    public ELParser getELParser(){
        return parser_;
    }
    
    public void setELParser(ELParser parser){
        parser_ = parser;
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
        Class[] argTypes = new Class[]{Application.class, String.class, ELParser.class};
        try{
            Constructor c = clazz.getConstructor(argTypes);
            vb = (ValueBinding)ConstructorUtil.newInstance(c, new Object[]{application, expression, parser_});
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
        holder.restoreState(context, new Object[]{expression, parser_});
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
