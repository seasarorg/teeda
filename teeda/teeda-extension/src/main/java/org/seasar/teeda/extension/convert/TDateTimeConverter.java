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
package org.seasar.teeda.extension.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.internal.ConvertUtil;

import org.seasar.framework.util.DateConversionUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class TDateTimeConverter extends DateTimeConverter {

    private Integer threshold = null;

    private static final int LAST_CENTURY = 1900;

    private static int defaultCenturyStart = 0;

    static {
        defaultCenturyStart = GregorianCalendar.getInstance()
                .get(Calendar.YEAR) - 80;
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        Date date = (Date) super.getAsObject(context, component, value);
        if (date == null) {
            return null;
        }
        String pattern = getPattern();
        String delim = DateConversionUtil.findDelimiter(value);
        if (delim == null) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw ConvertUtil.wrappedByConverterException(this, context, args);
        }
        final SimpleDateFormat formatter = DateConversionUtil.getDateFormat(
                value, getLocale());
        if (pattern == null) {
            pattern = formatter.toLocalizedPattern();
        }
        final boolean isY4Pattern = (pattern.indexOf("yyyy") >= 0);
        final boolean isY2Pattern = (!isY4Pattern && StringUtil.startsWith(
                pattern, "yy"));
        if (!isY2Pattern) {
            return date;
        }
        date = adjustDate(date);
        if (threshold == null) {
            return date;
        }
        if (delim != null) {
            setPattern("yyyy" + delim + "MM" + delim + "dd");
        }
        int start = getPattern().indexOf("yyyy");
        value = getAsString(context, component, date);
        String target = value.substring(start + 2, start + 4);
        int len1 = target.length();
        int yy = Integer.valueOf(target).intValue();
        final int t = threshold.intValue();
        final int len2 = String.valueOf(t).length();
        if (t <= yy && len1 == len2) {
            value = String.valueOf(LAST_CENTURY + yy)
                    + value.substring(4, value.length());
        }
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw ConvertUtil.wrappedByConverterException(this, context, args,
                    e);
        } finally {
            setPattern(pattern);
        }

    }

    public Date adjustDate(Date date) {
        int n = getCalculatedDate(date);
        if (n >= defaultCenturyStart) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, n + 100);
            date = calendar.getTime();
        }
        return date;
    }

    protected int getCalculatedDate(Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yy");
        String yy = formatter.format(date);
        return LAST_CENTURY + Integer.valueOf(yy).intValue();
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        return super.getAsString(context, component, value);
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = threshold;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        threshold = (Integer) values[1];
    }

}
