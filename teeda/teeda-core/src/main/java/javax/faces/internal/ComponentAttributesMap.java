/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * @author manhole
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentAttributesMap implements Map, Serializable {

    private static final long serialVersionUID = 1L;

    private UIComponent component = null;

    private Map attributes = null;

    private BeanDesc beanDesc = null;

    private static final int INITIAL_CAPACITY = 64;

    public ComponentAttributesMap(UIComponent component) {
        this.component = component;
        this.attributes = new HashMap(INITIAL_CAPACITY);
        setupPropertyDesc();
    }

    public ComponentAttributesMap(UIComponent component, Map attributes) {
        this.component = component;
        this.attributes = new HashMap(attributes);
        setupPropertyDesc();
    }

    public int size() {
        return attributes.size();
    }

    public void clear() {
        attributes.clear();
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    //it is a specification of JSF1.1.
    public boolean containsKey(Object key) {
        if (beanDesc.hasPropertyDesc((String) key)) {
            return false;
        } else {
            return attributes.containsKey(key);
        }
    }

    public boolean containsValue(Object value) {
        return attributes.containsValue(value);
    }

    public Collection values() {
        return attributes.values();
    }

    public void putAll(Map map) {
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set entrySet() {
        return attributes.entrySet();
    }

    public Set keySet() {
        return attributes.keySet();
    }

    public Object get(Object key) {
        String k = (String) key;
        PropertyDesc propertyDesc = null;
        if (beanDesc.hasPropertyDesc(k)) {
            propertyDesc = beanDesc.getPropertyDesc(k);
        }
        if (propertyDesc != null && propertyDesc.isReadable()) {
            Object value = getComponentProperty(propertyDesc);
            if (value != null) {
                return value;
            }
            ValueBinding vb = component.getValueBinding((String) key);
            return (vb != null) ? vb
                    .getValue(FacesContext.getCurrentInstance()) : null;
        } else {
            return attributes.get((String) key);
        }
    }

    public Object remove(Object key) {
        String k = (String) key;
        if (beanDesc.hasPropertyDesc(k)) {
            throw new IllegalArgumentException(
                    "can't remove component property");
        }
        return attributes.remove(key);
    }

    public Object put(Object key, Object value) {
        AssertionUtil.assertNotNull("key", key);
        AssertionUtil.assertNotNull("value", value);
        assertKeyIsString(key);
        String k = (String) key;
        PropertyDesc propertyDesc = null;
        if (beanDesc.hasPropertyDesc(k)) {
            propertyDesc = beanDesc.getPropertyDesc(k);
        }
        Object returnValue = null;
        if (propertyDesc != null && propertyDesc.isWritable()) {
            if (propertyDesc.isReadable()) {
                returnValue = getComponentProperty(propertyDesc);
            }
            setComponentProperty(propertyDesc, value);
        } else {
            returnValue = attributes.put(key, value);
        }
        return returnValue;
    }

    private void setupPropertyDesc() {
        Class clazz = component.getClass();
        beanDesc = BeanDescFactory.getBeanDesc(clazz);
    }

    private void setComponentProperty(PropertyDesc propertyDesc, Object value) {
        propertyDesc.setValue(component, value);
    }

    private Object getComponentProperty(PropertyDesc propertyDesc) {
        return propertyDesc.getValue(component);
    }

    public Map getAttributesActual() {
        return attributes;
    }

    private static void assertKeyIsString(Object key) {
        if (!(key instanceof String)) {
            throw new ClassCastException("key must be a String");
        }
    }

}
