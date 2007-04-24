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
package org.seasar.teeda.core.util;

import java.lang.reflect.Array;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.seasar.teeda.core.exception.NoValueHolderRuntimeException;

/**
 * @author manhole
 */
public class ValueHolderUtil {

    public static Object getValue(final UIComponent component) {
        if (!(component instanceof ValueHolder)) {
            throw new NoValueHolderRuntimeException(component.getClass());
        }
        final ValueHolder vh = (ValueHolder) component;
        return vh.getValue();
    }

    public static String getValueForRender(final FacesContext context,
            final UIComponent component) {
        if (!(component instanceof ValueHolder)) {
            throw new IllegalArgumentException("component must be ValueHolder");
        }
        if (component instanceof EditableValueHolder) {
            final EditableValueHolder evh = (EditableValueHolder) component;
            final Object submittedValue = evh.getSubmittedValue();
            if (submittedValue != null) {
                if (submittedValue instanceof String) {
                    return (String) submittedValue;
                }
                return submittedValue.toString();
            }
        }
        final ValueHolder vh = (ValueHolder) component;
        final Object value = vh.getValue();
        final Converter converter = ConverterUtil.getConverter(component);
        return UIValueUtil.getValueAsString(context, component, value,
                converter);
    }

    public static String[] getValuesForRender(final FacesContext context,
            final UIComponent component) {
        if (!(component instanceof ValueHolder)) {
            throw new IllegalArgumentException("component must be ValueHolder");
        }
        if (component instanceof EditableValueHolder) {
            final EditableValueHolder evh = (EditableValueHolder) component;
            final Object submittedValue = evh.getSubmittedValue();
            if (submittedValue instanceof String[]) {
                return (String[]) submittedValue;
            }
        }
        final ValueHolder vh = (ValueHolder) component;
        final Object value = vh.getValue();
        if (value == null) {
            return new String[0];
        }
        final Converter converter = vh.getConverter();
        final int length = Array.getLength(value);
        final String[] values = new String[length];
        for (int i = 0; i < length; ++i) {
            values[i] = UIValueUtil.getValueAsString(context, component, Array
                    .get(value, i), converter);
        }
        return values;
    }

}
