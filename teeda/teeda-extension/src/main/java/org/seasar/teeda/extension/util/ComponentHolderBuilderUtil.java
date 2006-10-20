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
package org.seasar.teeda.extension.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.util.NumberConversionUtil;

/**
 * @author shot
 */
public class ComponentHolderBuilderUtil {

    protected ComponentHolderBuilderUtil() {
    }

    public static ComponentHolder build(Object value) {
        if (value == null) {
            return null;
        }
        final Class valueClass = value.getClass();
        ComponentHolder holder = null;
        if (value instanceof List) {
            holder = buildListTypeHolder(((List) value));
        } else if (valueClass.isArray()) {
            final int length = Array.getLength(value);
            final Class valueType = value.getClass().getComponentType();
            if (valueType.isPrimitive()) {
                List list = new ArrayList();
                for (int i = 0; i < length; i++) {
                    final Object target = Array.get(value, i);
                    final Object wrappedValue = NumberConversionUtil
                            .convertPrimitiveWrapper(valueType, target);
                    list.add(i, wrappedValue);
                }
                holder = buildListTypeHolder(list);
            } else {
                holder = buildArrayTypeHolder(valueClass, (Object[]) value);
            }
        }
        return holder;
    }

    private static ComponentHolder buildListTypeHolder(final List valueList) {
        final ComponentHolder holder = new ComponentHolder();
        final List list = new ArrayList();
        if (!valueList.isEmpty()) {
            final Class componentClass = valueList.get(0).getClass();
            holder.setComponentClassName(componentClass.getName());
            if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                list.addAll(valueList);
            } else {
                for (final Iterator itr = valueList.iterator(); itr.hasNext();) {
                    final Object bean = itr.next();
                    final Map m = new HashMap();
                    BeanUtil.copyProperties(bean, m);
                    list.add(m);
                }
            }
        }
        holder.setValue(list);
        return holder;
    }

    private static ComponentHolder buildArrayTypeHolder(final Class valueClass,
            final Object[] valueArray) {
        final ComponentHolder holder = new ComponentHolder();
        holder.setArrayClassName(valueClass.getComponentType().getName());
        final List list = new ArrayList();
        if (0 < valueArray.length) {
            final Class componentClass = valueArray[0].getClass();
            holder.setComponentClassName(componentClass.getName());
            if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                list.addAll(Arrays.asList(valueArray));
            } else {
                for (int i = 0; i < valueArray.length; i++) {
                    final Object bean = valueArray[i];
                    final Map m = new HashMap();
                    BeanUtil.copyProperties(bean, m);
                    list.add(m);
                }
            }
        }
        holder.setValue(list);
        return holder;
    }

}
