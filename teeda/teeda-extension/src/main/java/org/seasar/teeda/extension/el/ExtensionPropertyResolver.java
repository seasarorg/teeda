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
package org.seasar.teeda.extension.el;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.OutputStreamUtil;
import org.seasar.teeda.core.el.TeedaPropertyResolver;
import org.seasar.teeda.core.util.ObjectInputStreamUtil;
import org.seasar.teeda.core.util.ObjectOutputStreamUtil;

/**
 * @author shot
 * @author manhole
 */
public class ExtensionPropertyResolver extends TeedaPropertyResolver {

    public Object getValue(final Object base, final Object property)
            throws EvaluationException, PropertyNotFoundException {
        final PropertyDesc pd = getPropertyDesc(base, property);
        final Class propertyType = pd.getPropertyType();
        if (propertyType.isArray()) {
            final Object[] value = (Object[]) getProperty(base, property
                    .toString());
            if (value == null) {
                return null;
            }
            final List mapList = new ArrayList();
            for (int i = 0; i < value.length; i++) {
                final Map map = new HashMap();
                copyToMap(value[i], map);
                mapList.add(map);
            }
            return serialize(mapList);
        } else if (propertyType.isAssignableFrom(List.class)) {
            final List value = (List) getProperty(base, property.toString());
            if (value == null) {
                return null;
            }
            return serialize(value);
        } else {
            return super.getValue(base, property);
        }
    }

    protected Class getPropertyType(final Object base, final Object property) {
        final PropertyDesc pd = getPropertyDesc(base, property);
        return pd.getPropertyType();
    }

    private PropertyDesc getPropertyDesc(final Object base,
            final Object property) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
        return beanDesc.getPropertyDesc(property.toString());
    }

    public void setValue(final Object base, final Object property,
            final Object newValue) throws EvaluationException,
            PropertyNotFoundException {
        final PropertyDesc pd = getPropertyDesc(base, property);
        final Class propertyType = pd.getPropertyType();
        if (propertyType.isArray()) {
            if (newValue == null) {
                return;
            }
            final Object o = deserialize((String) newValue);
            if (o instanceof List) {
                final List list = (List) o;
                final Class componentType = pd.getPropertyType()
                        .getComponentType();
                final int size = list.size();
                final Object[] array = (Object[]) Array.newInstance(
                        componentType, size);
                for (int i = 0; i < size; i++) {
                    final Object bean = ClassUtil.newInstance(componentType);
                    final Map map = (Map) list.get(i);
                    copyToBean(map, bean);
                    array[i] = bean;
                }
                pd.setValue(base, array);
            }
        } else if (propertyType.isAssignableFrom(List.class)) {
            if (newValue == null) {
                return;
            }
            /*
             * 例外はスルーする必要があるかも?
             * HotDeployでclassがなくなっちゃってClassNotFoundとか。
             */
            final Object o = deserialize((String) newValue);
            if (o instanceof List) {
                final List list = (List) o;
                pd.setValue(base, list);
            }
        } else {
            super.setValue(base, property, newValue);
        }
    }

    private String serialize(final List mapList) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = ObjectOutputStreamUtil.getOutputStream(bos);
            ObjectOutputStreamUtil.writeObject(oos, mapList);
            return Base64Util.encode(bos.toByteArray());
        } finally {
            OutputStreamUtil.close(oos);
            OutputStreamUtil.close(bos);
        }
    }

    private Object deserialize(final String value) {
        final byte[] data = Base64Util.decode(value);
        final InputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = null;
        try {
            ois = ObjectInputStreamUtil.getInputStream(bis);
            return ObjectInputStreamUtil.readObject(ois);
        } finally {
            InputStreamUtil.close(ois);
            InputStreamUtil.close(bis);
        }
    }

    protected void copyToMap(final Object bean, final Map map) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final Object value = pd.getValue(bean);
                map.put(pd.getPropertyName(), value);
            }
        }
    }

    protected void copyToBean(final Map map, final Object bean) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final String propertyName = pd.getPropertyName();
                if (!map.containsKey(propertyName)) {
                    continue;
                }
                final Object value = map.get(propertyName);
                if (value == null) {
                    continue;
                }
                if (ClassUtil.isAssignableFrom(pd.getPropertyType(), value
                        .getClass())) {
                    pd.setValue(bean, value);
                }
            }
        }
    }

}
