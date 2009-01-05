/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.el;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.exception.SetterNotFoundRuntimeException;

/**
 * @author higa
 * @author shot
 */
public class TeedaPropertyResolver extends PropertyResolver {

    public Object getValue(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException {

        if (base == null || property == null || property instanceof String
                && ((String) property).length() == 0) {

            return null;
        }
        if (base instanceof Map) {
            return ((Map) base).get(property);
        }
        if (base instanceof UIComponent) {
            UIComponent baseComponent = (UIComponent) base;
            for (Iterator children = baseComponent.getChildren().iterator(); children
                    .hasNext();) {
                UIComponent child = (UIComponent) children.next();
                if (property.equals(child.getId())) {
                    return child;
                }
            }
            return null;
        }
        return getProperty(base, property.toString());
    }

    public Object getValue(Object base, int index) throws EvaluationException,
            PropertyNotFoundException {

        if (base == null) {
            return null;
        }
        try {
            if (base.getClass().isArray()) {
                return Array.get(base, index);
            }
            if (base instanceof List) {
                return ((List) base).get(index);
            }
            if (base instanceof UIComponent) {
                return ((UIComponent) base).getChildren().get(index);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        throw new ReferenceSyntaxException("Must be array or List. Bean: "
                + base.getClass().getName() + ", index " + index);
    }

    public void setValue(Object base, Object property, Object newValue)
            throws EvaluationException, PropertyNotFoundException {

        if (base == null || property == null || property instanceof String
                && ((String) property).length() == 0) {

            return;
        }
        if (base instanceof Map) {
            ((Map) base).put(property, newValue);

            return;
        }
        if (base instanceof UIComponent) {
            throw new PropertyNotFoundException(
                    "Bean must not be UIComponent, property: " + property);
        }
        setProperty(base, property.toString(), newValue);
    }

    public void setValue(Object base, int index, Object newValue)
            throws EvaluationException, PropertyNotFoundException {

        if (base == null) {
            return;
        }
        if (base.getClass().isArray()) {
            Array.set(base, index, newValue);

            return;
        }
        if (base instanceof List) {
            ((List) base).set(index, newValue);
            return;
        }
        throw new EvaluationException("Bean must be array or List. Bean: "
                + base.getClass().getName() + ", index " + index);
    }

    public boolean isReadOnly(Object base, Object property) {
        if (base == null || property == null || property instanceof String
                && ((String) property).length() == 0) {

            return false;
        }
        if (base instanceof Map) {
            return false;
        }
        if (base instanceof UIComponent) {
            return true;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
        String propertyName = property.toString();
        if (!beanDesc.hasPropertyDesc(propertyName)) {
            return false;
        }
        PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
        return !pd.isWritable();
    }

    public boolean isReadOnly(Object base, int index) {
        if (base == null) {
            return false;
        }
        if (base instanceof List || base.getClass().isArray()) {
            return false;
        }
        if (base instanceof UIComponent) {
            return true;
        }
        return false;
    }

    public Class getType(Object base, Object property) {
        if (base == null || property == null || property instanceof String
                && ((String) property).length() == 0) {

            return null;
        }
        if (base instanceof Map) {
            Object value = ((Map) base).get(property);
            return (value != null) ? value.getClass() : Object.class;
        }
        if (base instanceof UIComponent) {
            return UIComponent.class;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
        String propertyName = property.toString();
        if (!beanDesc.hasPropertyDesc(propertyName)) {
            return null;
        }
        PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
        return pd.getPropertyType();
    }

    public Class getType(Object base, int index) {
        if (base == null) {
            return null;
        }
        if (base.getClass().isArray()) {
            return base.getClass().getComponentType();
        }
        if (base instanceof List) {
            Object value = ((List) base).get(index);
            return (value != null) ? value.getClass() : Object.class;
        }
        if (base instanceof UIComponent) {
            return UIComponent.class;
        }
        return null;
    }

    protected void setProperty(Object base, String name, Object newValue) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
        PropertyDesc pd = beanDesc.getPropertyDesc(name);
        if (pd.isWritable()) {
            pd.setValue(base, newValue);
        } else {
            throw new SetterNotFoundRuntimeException(name);
        }
    }

    protected Object getProperty(Object base, String name) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
        PropertyDesc pd = beanDesc.getPropertyDesc(name);
        if (pd.isReadable()) {
            return pd.getValue(base);
        } else {
            return null;
        }
    }

}