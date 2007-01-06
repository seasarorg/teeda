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
package examples.teeda.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.internal.ConvertUtil;

import org.seasar.framework.util.DateConversionUtil;

/**
 * @author yone
 */
public class TestDateTimeConverter extends DateTimeConverter {

    protected static final String TYPE_DATE = "date";

    protected static final String TYPE_TIME = "time";

    protected static final String TYPE_BOTH = "both";

    protected static final String STYLE_DEFAULT = "default";

    protected static final String STYLE_MEDIUM = "medium";

    protected static final String STYLE_SHORT = "short";

    protected static final String STYLE_LONG = "long";

    protected static final String STYLE_FULL = "full";

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (context == null) {
            throw new NullPointerException("context");
        }
        if (component == null) {
            throw new NullPointerException("component");
        }
        if (value != null) {
            Locale locale = getLocale();
            value = value.trim();
            if (value.length() > 0) {
                DateFormat format = getDateFormat(value, locale);
                if (format == null) {
                    format = DateConversionUtil.getDateFormat(value, locale);
                }
                format.setLenient(false);
                setupTimeZone(format);
                try {
                    return format.parse(value);
                } catch (ParseException e) {
                    Object[] args = ConvertUtil.createExceptionMessageArgs(
                            component, value);
                    throw ConvertUtil.wrappedByConverterException(this,
                            context, args, e);
                }
            }
        }
        return null;
    }

    protected DateFormat getDateFormat(String value, Locale locale) {
        if (getPattern() != null) {
            return new SimpleDateFormat(getPattern(), locale);
        }
        if (isDefaultStyle()) {
            return DateConversionUtil.getDateFormat(value, locale);
        }
        return getDateFormatForType();
    }

    protected boolean isDefaultStyle() {
        return STYLE_DEFAULT.equalsIgnoreCase(getDateStyle())
                && STYLE_DEFAULT.equalsIgnoreCase(getTimeStyle());
    }

    protected DateFormat getDateFormatForType() {
        String type = getType();
        if (type.equals(TYPE_DATE)) {
            return DateFormat.getDateInstance(calcStyle(getDateStyle()),
                    getLocale());
        } else if (type.equals(TYPE_TIME)) {
            return DateFormat.getTimeInstance(calcStyle(getTimeStyle()),
                    getLocale());
        } else if (type.equals(TYPE_BOTH)) {
            return DateFormat.getDateTimeInstance(calcStyle(getDateStyle()),
                    calcStyle(getTimeStyle()), getLocale());
        } else {
            return null;
        }
    }

    protected int calcStyle(String name) {
        if (name.equals(STYLE_DEFAULT)) {
            return DateFormat.DEFAULT;
        }
        if (name.equals(STYLE_MEDIUM)) {
            return DateFormat.MEDIUM;
        }
        if (name.equals(STYLE_SHORT)) {
            return DateFormat.SHORT;
        }
        if (name.equals(STYLE_LONG)) {
            return DateFormat.LONG;
        }
        if (name.equals(STYLE_FULL)) {
            return DateFormat.FULL;
        }
        return DateFormat.DEFAULT;
    }

    /**
     *
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        if (context == null) {
            throw new NullPointerException("context");
        }
        if (component == null) {
            throw new NullPointerException("component");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        DateFormat format = getDateFormat(getLocale());
        setupTimeZone(format);
        try {
            return format.format(value);
        } catch (Exception ignore) {
            return value.toString();
        }
    }

    protected void setupTimeZone(DateFormat dateFormat) {
        if (getTimeZone() != null) {
            dateFormat.setTimeZone(getTimeZone());
        }
    }

    protected DateFormat getDateFormat(Locale locale) {
        if (getPattern() != null) {
            return new SimpleDateFormat(getPattern(), locale);
        }
        if (isDefaultStyle()) {
            return DateConversionUtil.getY4DateFormat(locale);
        }
        return getDateFormatForType();
    }
}