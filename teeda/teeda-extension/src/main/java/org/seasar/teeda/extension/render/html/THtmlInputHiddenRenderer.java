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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.render.EncodeConverter;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputHidden;
import org.seasar.teeda.extension.util.ComponentHolder;
import org.seasar.teeda.extension.util.ComponentHolderBuilderUtil;
import org.seasar.teeda.extension.util.PagePersistenceUtil;

/**
 * @author manhole
 * @author shot
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
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        if (holder == null) {
            throw new IllegalArgumentException(
                    "value type of THtmlInputHidden should be Array or List");
        }
        return serialize(holder);
    }

    public void decode(final FacesContext context, final UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlInputHidden(context, (THtmlInputHidden) component);
    }

    protected void decodeHtmlInputHidden(final FacesContext context,
            final THtmlInputHidden htmlInputHidden) {
        getDecoder().decode(context, htmlInputHidden);
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
        THtmlInputHidden hidden = (THtmlInputHidden) component;
        String s = (String) submittedValue;
        if (s.equals("")) {
            return "";
        }
        final ComponentHolder holder = (ComponentHolder) deserialize(s);
        final String arrayClassName = holder.getArrayClassName();
        final String componentClassName = holder.getComponentClassName();
        if (componentClassName == null) {
            return null;
        }
        final Class componentClass = ClassUtil.forName(componentClassName);
        final List restoredList = holder.getValue();
        if (arrayClassName != null) {
            final Class arrayClass = ClassUtil.forName(arrayClassName);
            final Object[] array = (Object[]) Array.newInstance(arrayClass,
                    restoredList.size());
            if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                restoredList.toArray(array);
            } else {
                final List beanList = mapListToBeanList(componentClass,
                        restoredList);
                beanList.toArray(array);
            }
            restoreItems(context, hidden, array);
            return array;
        } else {
            List retList = null;
            if (PagePersistenceUtil.isPersistenceType(componentClass)) {
                retList = restoredList;
            } else {
                retList = mapListToBeanList(componentClass, restoredList);
            }
            restoreItems(context, hidden, retList);
            return retList;
        }
    }

    protected void restoreItems(FacesContext context, THtmlInputHidden hidden,
            Object items) {
        Object page = hidden.getPage(context);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        final String id = hidden.getId();
        String itemsName = id;
        if (id.endsWith(ExtensionConstants.SAVE_SUFFIX)) {
            itemsName = id.substring(0,
                    (id.length() - ExtensionConstants.SAVE_SUFFIX.length()));
        }
        PropertyDesc propertyDesc = beanDesc.getPropertyDesc(itemsName);
        if (propertyDesc != null) {
            propertyDesc.setValue(page, items);
        }
    }

    private List mapListToBeanList(final Class componentClass,
            final List restoredList) {
        final int size = restoredList.size();
        final List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            final Object bean = ClassUtil.newInstance(componentClass);
            final Map map = (Map) restoredList.get(i);
            BeanUtil.copyProperties(map, bean);
            list.add(bean);
        }
        return list;
    }

}
