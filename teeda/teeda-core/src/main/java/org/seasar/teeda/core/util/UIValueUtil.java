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

import java.lang.reflect.Array;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.seasar.framework.log.Logger;

/**
 * @author manhole
 */
public class UIValueUtil {

    private static Logger logger_ = Logger.getLogger(UIValueUtil.class);

    public static String getValueAsString(FacesContext context,
            UIComponent component, Object value, Converter converter) {
        if (converter == null && value != null) {
            if (value instanceof String) {
                return (String) value;
            }
            try {
                converter = context.getApplication().createConverter(
                        value.getClass());
            } catch (FacesException ex) {
                logger_.log(ex);
            }
        }
        if (converter == null) {
            if (value == null) {
                return "";
            }
            return value.toString();
        }
        return converter.getAsString(context, component, value);
    }

    public static final boolean isManyEmpty(Object value) {
        return value == null
                || (value.getClass().isArray() && Array.getLength(value) == 0)
                || ((value instanceof List) && ((List) value).isEmpty());
    }

}
