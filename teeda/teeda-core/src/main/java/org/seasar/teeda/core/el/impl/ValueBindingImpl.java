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

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.util.PropertyResolverUtil;
import org.seasar.teeda.core.util.VariableResolverUtil;

/**
 * @author Shinpei Ohtani
 */
public class ValueBindingImpl extends ValueBindingBase{

    private Application application_;
    private String expressionString_;
    private ELParser parser_;
    private Object expression_;
    private boolean transientValue_ = false;
    
    public ValueBindingImpl(){
    }
    
    public ValueBindingImpl(Application application, String expressionString, ELParser parser){
        application_ = application;
        expressionString_ = expressionString;
        parser_ = parser;
        expression_ = parser_.parse(expressionString_);
    }
    
    public Object getValue(FacesContext context)
            throws EvaluationException, PropertyNotFoundException {
        try{
            return parser_.getExpressionProcessor().evaluate(context, expression_);
        }catch(IndexOutOfBoundsException e){
            throw new PropertyNotFoundException(e);
        }catch(EvaluationException e){
            throw e;
        }
    }

    public void setValue(FacesContext context, Object newValue)
            throws EvaluationException, PropertyNotFoundException {
        try{
            if(newValue == null){
                throw new NullPointerException();
            }
            ExpressionProcessor processor = parser_.getExpressionProcessor(); 
            Object obj = processor.resolveBase(context, expression_);
            if(obj == null){
                throw new EvaluationException();
            }
            if(obj instanceof String){
                String name = (String)obj;
                if(isImplicitObject(name)){
                    throw new ReferenceSyntaxException("Prohibited to set to implicit object.");
                }
                setValueInScope(context, name, newValue);
            }else{
                Object[] bases = (Object[])obj;
                Object base = bases[0];
                Object property = bases[1];
                Integer index = parser_.getExpressionProcessor().toIndex(base, property);
                PropertyResolver resolver = application_.getPropertyResolver();
                Class clazz = PropertyResolverUtil.getType(application_, base, property, index);
                if(index == null){
                    resolver.setValue(base, property, processor.getCoercedObject(newValue, clazz));
                }else{
                    resolver.setValue(base, index.intValue(), processor.getCoercedObject(newValue, clazz));
                }
            }
        }catch(IndexOutOfBoundsException e){
            throw new PropertyNotFoundException(e);
        }
    }

    protected void setValueInScope(FacesContext context, String name, Object newValue){
        VariableResolver resolver = application_.getVariableResolver();
        ExpressionProcessor processor = parser_.getExpressionProcessor();
        Map scopeMap = VariableResolverUtil.getDefaultScopeMap(context, resolver, name);
        if(scopeMap != null){
            Object previous = scopeMap.get(name);
            if(previous != null){
                scopeMap.put(name, processor.getCoercedObject(newValue, previous.getClass()));
                return;
            }        	
        }
        ManagedBeanFactory managedBeanFactory = getManagedBeanFactory();
        Scope scope = managedBeanFactory.getManagedBeanScope(name);
        ManagedBeanScopeSaver saver = managedBeanFactory.getManagedBeanScopeSaver();
        if(scope != null){
        	saver.saveToScope(context, scope, name, newValue);
            return;
        }
        //if no target, put to request.
        saver.saveToScope(context, Scope.REQUEST, name, newValue);
    }
        
    public boolean isReadOnly(FacesContext context)
            throws EvaluationException, PropertyNotFoundException {
        Object obj = parser_.getExpressionProcessor().resolveBase(context, expression_);
        if(obj == null){
            return true;
        }
        if(obj instanceof String){
            return isImplicitObject((String)obj);
        }else{
            Object[] bases = (Object[])obj;
            Object base = bases[0];
            Object property = bases[1];
            Integer index = parser_.getExpressionProcessor().toIndex(base, property);
            return PropertyResolverUtil.isReadOnly(application_, base, property, index); 
        }
    }
    
    public Class getType(FacesContext context) throws EvaluationException,
            PropertyNotFoundException {
        Object obj = parser_.getExpressionProcessor().resolveBase(context, expression_);
        if(obj == null){
            return getValue(context).getClass();
        }
        if(obj instanceof String){
            String name = (String)obj;
            ManagedBeanFactory managedBeanFactory = getManagedBeanFactory();
            Object managedBean = managedBeanFactory.getManagedBean(name);
            if(managedBean != null){
                return managedBean.getClass();
            }
            Object value = application_.getVariableResolver().resolveVariable(context, name);
            return (value != null) ? value.getClass() : Object.class;
        }else{
            Object[] bases = (Object[])obj;
            Object base = bases[0];
            Object property = bases[1];
            Integer index = parser_.getExpressionProcessor().toIndex(base, property);
            return PropertyResolverUtil.getType(application_, base, property, index); 
        }
    }

    public String getExpressionString() {
        return expressionString_;
    }

    public Object getExpression(){
    	return expression_;
    }
    
    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return expressionString_;
    }

    public void restoreState(FacesContext context, Object obj) {
        application_ = context.getApplication();
        Object[] state = (Object[])obj;
        expressionString_ = (String)state[0];
        parser_ = (ELParser)state[1];
        expression_ = parser_.parse(expressionString_);
    }
    
}
