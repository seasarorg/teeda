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
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.render.EncodeConverter;
import org.seasar.teeda.core.util.DecodeUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlInputHidden;
import org.seasar.teeda.extension.component.html.THtmlInputHidden.ComponentHolder;

/**
 * @author manhole
 */
public class THtmlInputHiddenRenderer extends AbstractRenderer {

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
            final THtmlInputHidden.ComponentHolder holder = new ComponentHolder();
            final List mapList = new ArrayList();
            boolean first = true;
            for (final Iterator it = ((List) value).iterator(); it.hasNext();) {
                final Object bean = it.next();
                final Map map = new HashMap();
                copyToMap(bean, map);
                mapList.add(map);
                if (first) {
                    first = false;
                    holder.setComponentClassName(bean.getClass().getName());
                }
            }
            holder.setValue(mapList);
            return serialize(holder);
        } else if (clazz.isArray()) {
            final THtmlInputHidden.ComponentHolder holder = new ComponentHolder();
            final Object[] values = (Object[]) value;
            final List mapList = new ArrayList();
            boolean first = true;
            for (int i = 0; i < values.length; i++) {
                final Object bean = values[i];
                final Map map = new HashMap();
                copyToMap(bean, map);
                mapList.add(map);
                if (first) {
                    first = false;
                    holder.setComponentClassName(bean.getClass().getName());
                }
            }
            holder.setValue(mapList);
            holder.setComponentClassName(clazz.getComponentType().getName());
            holder.setArrayClassName(clazz.getComponentType().getName());
            return serialize(holder);
        }
        throw new IllegalArgumentException(
                "value type of THtmlInputHidden should be Array or List");
    }

    public void decode(final FacesContext context, final UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlInputHidden(context, (THtmlInputHidden) component);
    }

    protected void decodeHtmlInputHidden(final FacesContext context,
            final THtmlInputHidden htmlInputHidden) {
        DecodeUtil.decode(context, htmlInputHidden);
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

    String serialize(final Object target) {
        return encodeConverter.getAsEncodeString(target);
    }

    Object deserialize(final String value) {
        return encodeConverter.getAsDecodeObject(value);
    }

    public void setEncodeConverter(EncodeConverter encodeConverter) {
        this.encodeConverter = encodeConverter;
    }

    // TODO Auto-generated method stub
    public Object getConvertedValue(final FacesContext context,
            final UIComponent component, final Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        final ComponentHolder holder = (ComponentHolder) deserialize((String) submittedValue);
        final String arrayClassName = holder.getArrayClassName();
        final String componentClassName = holder.getComponentClassName();
        final List restoredList = holder.getValue();
        final int size = restoredList.size();
        if (arrayClassName != null) {
            final Class arrayClass = ClassUtil.forName(arrayClassName);
            final Object[] array = (Object[]) Array.newInstance(arrayClass,
                    size);
            for (int i = 0; i < size; i++) {
                final Object bean = ClassUtil.newInstance(componentClassName);
                final Map map = (Map) restoredList.get(i);
                copyToBean(map, bean);
                array[i] = bean;
            }
            return array;
        } else {
            final List list = new ArrayList();
            for (int i = 0; i < size; i++) {
                final Object bean = ClassUtil.newInstance(componentClassName);
                final Map map = (Map) restoredList.get(i);
                copyToBean(map, bean);
                list.add(bean);
            }
            return list;
        }
    }

}
