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
package org.seasar.teeda.extension.convert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;

/**
 * @author shot
 */
public class TDateTimeConverter extends DateTimeConverter {

    private Integer threshold = null;

    private static final int LAST_CENTURY = 1900;

    private static final int CURRENT_CENTURY = 2000;

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        Date date = (Date) super.getAsObject(context, component, value);
        if (threshold == null) {
            return date;
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final String yearString = String.valueOf(year);
        final int length = yearString.length();
        if (length == 4) {
            final int t = threshold.intValue();
            final int len1 = String.valueOf(t).length();
            final int y = Math.abs(year - CURRENT_CENTURY);
            final int len2 = String.valueOf(y).length();
            int res = year;
            if (t <= y && len1 == len2) {
                res = LAST_CENTURY + y;
            }
            calendar.set(Calendar.YEAR, res);
        }
        return calendar.getTime();
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
