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
package javax.faces.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 * @author manhole
 * 
 * This class might be changed without a previous notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentAttributesMap implements Map, Serializable {

    private static final long serialVersionUID = 1L;

    private UIComponent component_ = null;

    private Map attributes_ = null;

    private Map propertyDescriptorMap_ = null;

    private static Object[] EMPTY_ARGS = new Object[0];

    public ComponentAttributesMap(UIComponent component) {
        this(component, new HashMap());
    }

    public ComponentAttributesMap(UIComponent component, Map attributes) {
        component_ = component;
        attributes_ = attributes;
        propertyDescriptorMap_ = new HashMap();
        setupPropertyDescriptor();
    }

    public int size() {
        return attributes_.size();
    }

    public void clear() {
        attributes_.clear();
    }

    public boolean isEmpty() {
        return attributes_.isEmpty();
    }

    public boolean containsKey(Object key) {
        if (getPropertyDescriptor((String) key) != null) {
            return false;
        } else {
            return attributes_.containsKey(key);
        }
    }

    public boolean containsValue(Object value) {
        return attributes_.containsValue(value);
    }

    public Collection values() {
        return attributes_.values();
    }

    public void putAll(Map map) {
        for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set entrySet() {
        return attributes_.entrySet();
    }

    public Set keySet() {
        return attributes_.keySet();
    }

    public Object get(Object key) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor((String) key);
        if (propertyDescriptor != null) {
            Object value = getComponentProperty(propertyDescriptor);
            if (value != null) {
                return value;
            }
            ValueBinding vb = component_.getValueBinding((String) key);
            return (vb != null) ? vb
                    .getValue(FacesContext.getCurrentInstance()) : null;
        } else {
            return attributes_.get((String) key);
        }
    }

    public Object remove(Object key) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor((String) key);
        if (propertyDescriptor != null) {
            throw new IllegalArgumentException(
                    "can't remove component property");
        }
        return attributes_.remove(key);
    }

    public Object put(Object key, Object value) {
        AssertionUtil.assertNotNull("key", key);
        AssertionUtil.assertNotNull("value", value);
        verifyKeyIsString(key);

        PropertyDescriptor propertyDescriptor = getPropertyDescriptor((String) key);
        Object returnValue = null;
        if (propertyDescriptor != null) {
            if (hasReadMethod(propertyDescriptor)) {
                returnValue = getComponentProperty(propertyDescriptor);
                setComponentProperty(propertyDescriptor, value);
            }
        } else {
            returnValue = attributes_.put(key, value);
        }
        return returnValue;
    }

    private void verifyKeyIsString(Object key) {
        if (!(key instanceof String)) {
            throw new ClassCastException("key must be a String");
        }
    }

    private void setupPropertyDescriptor() {
        Class clazz = component_.getClass();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new FacesException(e);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            if (hasReadMethod(propertyDescriptor)) {
                propertyDescriptorMap_.put(propertyDescriptor.getName(),
                        propertyDescriptor);
            }
        }
    }

    private PropertyDescriptor getPropertyDescriptor(String key) {
        return (PropertyDescriptor) propertyDescriptorMap_.get(key);
    }

    private void setComponentProperty(PropertyDescriptor propertyDescriptor,
            Object value) {

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            throw new IllegalArgumentException("component property ["
                    + propertyDescriptor.getName() + "] is not writeable");
        }
        try {
            writeMethod.invoke(component_, new Object[] { value });
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getTargetException());
        }
    }

    private Object getComponentProperty(PropertyDescriptor propertyDescriptor) {
        Method readMethod = propertyDescriptor.getReadMethod();
        try {
            return readMethod.invoke(component_, EMPTY_ARGS);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getTargetException());
        }
    }

    public Map getAttributesActual() {
        return attributes_;
    }

    private static boolean hasReadMethod(PropertyDescriptor propertyDescriptor) {
        return (propertyDescriptor.getReadMethod() != null);
    }

}
