/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ConverterResource;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author yone
 */
public class ConverterUtil {

    private ConverterUtil() {
    }

    public static Converter getConverter(final UIComponent component) {
        Converter converter = null;
        final ValueBinding vb = component
                .getValueBinding(JsfConstants.VALUE_ATTR);
        if (vb != null) {
            String expression = vb.getExpressionString();
            converter = ConverterResource.getConverter(expression);
        }
        if (converter != null) {
            return converter;
        }
        if (component instanceof ValueHolder) {
            return ((ValueHolder) component).getConverter();
        }
        return null;
    }

    public static int convertToInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert "
                        + value.toString() + " to int");
            }
        } else {
            throw new IllegalArgumentException("Cannot convert "
                    + value.toString() + " to int");
        }
    }

    public static boolean convertToBoolean(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        } else if (value instanceof String) {
            return Boolean.valueOf((String) value).booleanValue();
        } else {
            throw new IllegalArgumentException("Cannot convert "
                    + value.toString() + " to boolean");
        }
    }

    public static long convertToLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert "
                        + value.toString() + " to long");
            }
        } else {
            throw new IllegalArgumentException("Cannot convert "
                    + value.toString() + " to long");
        }
    }

    public static double convertToDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert "
                        + value.toString() + " to double");
            }
        } else {
            throw new IllegalArgumentException("Cannot convert "
                    + value.toString() + " to double");
        }
    }

}
