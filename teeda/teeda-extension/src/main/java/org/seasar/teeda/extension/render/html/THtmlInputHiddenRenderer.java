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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.render.EncodeConverter;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlInputHidden;
import org.seasar.teeda.extension.component.html.THtmlInputHidden.ComponentHolder;

/**
 * @author manhole
 */
public class THtmlInputHiddenRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = THtmlInputHidden.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlInputHidden.DEFAULT_RENDERER_TYPE;

    private EncodeConverter encodeConverter;

    public void encodeEnd(final FacesContext context,
            final UIComponent component) throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlInputHiddenEnd(context, (THtmlInputHidden) component);
    }

    protected void encodeHtmlInputHiddenEnd(final FacesContext context,
            final THtmlInputHidden htmlInputHidden) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputHidden);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputHidden,
                getIdForRender(context, htmlInputHidden));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputHidden.getClientId(context));

        final String value = getValueForRender(context, htmlInputHidden);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        renderAttributes(htmlInputHidden, writer);

        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected String getValueForRender(final FacesContext context,
            final THtmlInputHidden htmlInputHidden) {
        final Object submittedValue = htmlInputHidden.getSubmittedValue();
        if (submittedValue != null) {
            if (submittedValue instanceof String) {
                return (String) submittedValue;
            }
            return submittedValue.toString();
        }
        final Object value = htmlInputHidden.getValue();
        if (value == null) {
            return "";
        }
        final Class clazz = value.getClass();
        if (value instanceof List) {
            final List valueList = ((List) value);
            final ComponentHolder holder = new ComponentHolder();
            final List list = new ArrayList();
            if (!valueList.isEmpty()) {
                final Class componentClass = valueList.get(0).getClass();
                holder.setComponentClassName(componentClass.getName());
                if (isNoEscapeType(componentClass)) {
                    list.addAll(valueList);
                } else {
                    for (final Iterator it = valueList.iterator(); it.hasNext();) {
                        final Object bean = it.next();
                        final Map map = new HashMap();
                        copyToMap(bean, map);
                        list.add(map);
                    }
                }
            }
            holder.setValue(list);
            return serialize(holder);
        } else if (clazz.isArray()) {
            final Object[] valueArray = (Object[]) value;
            final ComponentHolder holder = new ComponentHolder();
            holder.setArrayClassName(clazz.getComponentType().getName());
            final List list = new ArrayList();
            if (0 < valueArray.length) {
                final Class componentClass = valueArray[0].getClass();
                holder.setComponentClassName(componentClass.getName());
                if (isNoEscapeType(componentClass)) {
                    list.addAll(Arrays.asList(valueArray));
                } else {
                    for (int i = 0; i < valueArray.length; i++) {
                        final Object bean = valueArray[i];
                        final Map map = new HashMap();
                        copyToMap(bean, map);
                        list.add(map);
                    }
                }
            }
            holder.setValue(list);
            return serialize(holder);
        }
        throw new IllegalArgumentException(
                "value type of THtmlInputHidden should be Array or List");
    }

    // TODO 他の型についても追加する。Integerとか。
    // いっそのこと、パッケージ名が"java."で始まったらOKにしてみる?
    private boolean isNoEscapeType(Class clazz) {
        return String.class.isAssignableFrom(clazz)
                || Map.class.isAssignableFrom(clazz);
    }

    public void decode(final FacesContext context, final UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlInputHidden(context, (THtmlInputHidden) component);
    }

    protected void decodeHtmlInputHidden(final FacesContext context,
            final THtmlInputHidden htmlInputHidden) {
        getDecoder().decode(context, htmlInputHidden);
    }

    protected void copyToMap(final Object from, final Map to) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(from.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final Object value = pd.getValue(from);
                to.put(pd.getPropertyName(), value);
            }
        }
    }

    protected void copyToBean(final Map from, final Object to) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(to.getClass());
        final int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod() && pd.hasWriteMethod()) {
                final String propertyName = pd.getPropertyName();
                if (!from.containsKey(propertyName)) {
                    continue;
                }
                final Object value = from.get(propertyName);
                if (value == null) {
                    continue;
                }
                if (ClassUtil.isAssignableFrom(pd.getPropertyType(), value
                        .getClass())) {
                    pd.setValue(to, value);
                }
            }
        }
    }

    private String serialize(final Object target) {
        return encodeConverter.getAsEncodeString(target);
    }

    private Object deserialize(final String value) {
        return encodeConverter.getAsDecodeObject(value);
    }

    public void setEncodeConverter(EncodeConverter encodeConverter) {
        this.encodeConverter = encodeConverter;
    }

    public Object getConvertedValue(final FacesContext context,
            final UIComponent component, final Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        final ComponentHolder holder = (ComponentHolder) deserialize((String) submittedValue);
        final String arrayClassName = holder.getArrayClassName();
        final String componentClassName = holder.getComponentClassName();
        final Class componentClass = ClassUtil.forName(componentClassName);
        final List restoredList = holder.getValue();
        if (arrayClassName != null) {
            final Class arrayClass = ClassUtil.forName(arrayClassName);
            final Object[] array = (Object[]) Array.newInstance(arrayClass,
                    restoredList.size());
            if (isNoEscapeType(componentClass)) {
                restoredList.toArray(array);
                return array;
            }
            final List beanList = mapListToBeanList(componentClass,
                    restoredList);
            beanList.toArray(array);
            return array;
        } else {
            if (isNoEscapeType(componentClass)) {
                return restoredList;
            }
            final List beanList = mapListToBeanList(componentClass,
                    restoredList);
            return beanList;
        }
    }

    private List mapListToBeanList(final Class componentClass,
            final List restoredList) {
        final int size = restoredList.size();
        final List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            final Object bean = ClassUtil.newInstance(componentClass);
            final Map map = (Map) restoredList.get(i);
            copyToBean(map, bean);
            list.add(bean);
        }
        return list;
    }

}
