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
package javax.faces.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ConvertUtil;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.DateConversionUtil;

/**
 * @author higa
 * @author shot
 */
public class DateTimeConverter implements Converter, StateHolder {

    public static final String CONVERTER_ID = "javax.faces.DateTime";

    public static final String CONVERSION_OBJECT_ID = DateTimeConverter.class
            .getName() +
            ".CONVERSION";

    public static final String CONVERSION_STRING_ID = DateTimeConverter.class
            .getName() +
            ".CONVERSION_STRING";

    protected static final String STYLE_DEFAULT = "default";

    protected static final String STYLE_MEDIUM = "medium";

    protected static final String STYLE_SHORT = "short";

    protected static final String STYLE_LONG = "long";

    protected static final String STYLE_FULL = "full";

    protected static final String TYPE_DATE = "date";

    protected static final String TYPE_TIME = "time";

    protected static final String TYPE_BOTH = "both";

    private String dateStyle = STYLE_DEFAULT;

    private boolean transientValue = false;

    private Locale locale = null;

    private String pattern = null;

    private String timeStyle = STYLE_DEFAULT;

    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

    private TimeZone timeZone = DEFAULT_TIME_ZONE;

    private String type = TYPE_DATE;

    public DateTimeConverter() {
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
        Locale locale = getLocale();
        DateFormat format = getDateFormat(value, locale);
        if (format == null) {
            format = DateConversionUtil.getDateFormat(value, locale);
        }
        format.setLenient(false);
        TimeZone timeZone = getTimeZone();
        if (timeZone != null) {
            format.setTimeZone(timeZone);
        }
        try {
            return format.parse(value);
        } catch (ParseException e) {
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
        if (value instanceof String) {
            return (String) value;
        }
        DateFormat formatter = getDateFormat(getLocale());
        TimeZone timeZone = getTimeZone();
        if (timeZone != null) {
            formatter.setTimeZone(timeZone);
        }
        try {
            return formatter.format(value);
        } catch (Exception e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getStringMessageId(), args), e);
        }
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = dateStyle;
        values[1] = locale;
        values[2] = pattern;
        values[3] = timeStyle;
        values[4] = timeZone;
        values[5] = type;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        dateStyle = (String) values[0];
        locale = (Locale) values[1];
        pattern = (String) values[2];
        timeStyle = (String) values[3];
        timeZone = (TimeZone) values[4];
        type = (String) values[5];
    }

    public String getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = getLocale(FacesContext.getCurrentInstance());
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setLocaleAsString(final String locale) {
        final String[] array = locale.split("_");
        if (array.length == 1) {
            setLocale(new Locale(locale));
        } else if (array.length == 2) {
            setLocale(new Locale(array[0], array[1]));
        } else if (array.length == 3) {
            setLocale(new Locale(array[0], array[1], array[2]));
        } else {
            throw new IllegalArgumentException(locale);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTimeStyle() {
        return timeStyle;
    }

    public void setTimeStyle(String timeStyle) {
        this.timeStyle = timeStyle;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Locale getLocale(FacesContext context) {
        return context.getViewRoot().getLocale();
    }

    protected DateFormat getDateFormat(Locale locale) {
        String pattern = getPattern();
        if (pattern != null) {
            return new SimpleDateFormat(pattern, locale);
        }
        if (isDefaultStyle()) {
            return DateConversionUtil.getY4DateFormat(locale);
        }
        return getDateFormatForType();
    }

    protected DateFormat getDateFormat(String value, Locale locale) {
        String pattern = getPattern();
        if (pattern != null) {
            return new SimpleDateFormat(pattern, locale);
        }
        if (isDefaultStyle()) {
            return DateConversionUtil.getDateFormat(value, locale);
        }
        return getDateFormatForType();
    }

    protected boolean isDefaultStyle() {
        return TYPE_DATE.equalsIgnoreCase(getType()) &&
                STYLE_DEFAULT.equalsIgnoreCase(getDateStyle()) &&
                STYLE_DEFAULT.equalsIgnoreCase(getTimeStyle());
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

    protected String getObjectMessageId() {
        return CONVERSION_OBJECT_ID;
    }

    protected String getStringMessageId() {
        return CONVERSION_STRING_ID;
    }

}
