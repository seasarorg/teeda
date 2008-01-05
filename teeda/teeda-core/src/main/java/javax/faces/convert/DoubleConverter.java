/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package javax.faces.convert;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ConvertUtil;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.NumberConversionUtil;

/**
 * @author shot
 */
public class DoubleConverter implements Converter {

    public static final String CONVERTER_ID = "javax.faces.DoubleTime";

    public static final String CONVERSION_OBJECT_ID = DoubleConverter.class
            .getName()
            + ".CONVERSION";

    public static final String CONVERSION_STRING_ID = DoubleConverter.class
            .getName()
            + ".CONVERSION_STRING";

    public DoubleConverter() {
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (value == null) {
            return null;
        }

        value = value.trim();
        if (value.length() < 1) {
            return null;
        }
        Locale locale = context.getViewRoot().getLocale();
        value = NumberConversionUtil.removeDelimeter(value, locale);
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getObjectMessageId(), args), e);
        }
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (value == null) {
            return "";
        }
        try {
            return (value instanceof String) ? (String) value : (Double
                    .toString(((Double) value).doubleValue()));
        } catch (Exception e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getStringMessageId(), args), e);
        }
    }

    protected String getObjectMessageId() {
        return CONVERSION_OBJECT_ID;
    }

    protected String getStringMessageId() {
        return CONVERSION_STRING_ID;
    }

}
