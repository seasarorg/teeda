package org.seasar.teeda.core.mock;

import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;


public class MockPropertyResolver extends PropertyResolver {

    private Object base_;
    private Object property_;
    private int index_;
    public MockPropertyResolver(){
    }
    
    public Class getType(Object base, int index) throws EvaluationException,
            PropertyNotFoundException {
        return null;
    }

    public Class getType(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException {
        return null;
    }

    public Object getValue(Object base, int index) throws EvaluationException,
            PropertyNotFoundException {
        return null;
    }

    public Object getValue(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException {
        return null;
    }

    public boolean isReadOnly(Object base, int index)
            throws EvaluationException, PropertyNotFoundException {
        return false;
    }

    public boolean isReadOnly(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException {
        return false;
    }

    public void setValue(Object base, int index, Object value)
            throws EvaluationException, PropertyNotFoundException {
    }

    public void setValue(Object base, Object property, Object value)
            throws EvaluationException, PropertyNotFoundException {
    }

    public void setBase(Object base){
        base_ = base;
    }
    
    public Object getBase(){
        return base_;
    }
    
    public void setProperty(Object property){
        property_ = property;
    }
    
    public Object getProperty(){
        return property_;
    }
    
    public void setIndex(int index){
        index_ = index;
    }
    
    public int getIndex(){
        return index_;
    }
}
