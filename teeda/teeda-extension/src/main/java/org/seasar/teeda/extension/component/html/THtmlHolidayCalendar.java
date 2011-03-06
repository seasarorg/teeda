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
package org.seasar.teeda.extension.component.html;

import java.io.IOException;

import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.convert.BooleanConverter;
import javax.faces.el.ValueBinding;
import javax.faces.internal.RenderPreparable;
import javax.faces.internal.RenderPreparableUtil;

/**
 * @author higa
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class THtmlHolidayCalendar extends HtmlSelectManyCheckbox implements
        RenderPreparable {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlHolidayCalendar";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlHolidayCalendar";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlHolidayCalendar";;

    private Integer year = null;

    private Integer month = null;

    private String calendarClass = "te-holidaycalendar";

    private String monthYearRowClass = "te-holidaycalendar-monthyear";

    private String weekRowClass = "te-holidaycalendar-weekday";

    private String dayCellClass = "te-holidaycalendar-normal-day";

    private String holidayCellClass = "te-holidaycalendar-holiday";

    private Boolean showYear = Boolean.FALSE;

    public THtmlHolidayCalendar() {
        setRendererType(DEFAULT_RENDERER_TYPE);
        setConverter(new BooleanConverter());
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Integer getYear() {
        if (year != null) {
            return year;
        }
        ValueBinding vb = getValueBinding("year");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        if (month != null) {
            return month;
        }
        ValueBinding vb = getValueBinding("month");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getCalendarClass() {
        if (calendarClass != null) {
            return calendarClass;
        }
        ValueBinding vb = getValueBinding("calendarClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCalendarClass(String calendarClass) {
        this.calendarClass = calendarClass;
    }

    public String getMonthYearRowClass() {
        if (monthYearRowClass != null) {
            return monthYearRowClass;
        }
        ValueBinding vb = getValueBinding("monthYearRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setMonthYearRowClass(String monthYearRowClass) {
        this.monthYearRowClass = monthYearRowClass;
    }

    public String getWeekRowClass() {
        if (weekRowClass != null) {
            return weekRowClass;
        }
        ValueBinding vb = getValueBinding("weekRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWeekRowClass(String weekRowClass) {
        this.weekRowClass = weekRowClass;
    }

    public String getDayCellClass() {
        if (dayCellClass != null) {
            return dayCellClass;
        }
        ValueBinding vb = getValueBinding("dayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDayCellClass(String dayCellClass) {
        this.dayCellClass = dayCellClass;
    }

    public String getHolidayCellClass() {
        if (holidayCellClass != null) {
            return holidayCellClass;
        }
        ValueBinding vb = getValueBinding("holidayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setHolidayCellClass(String holidayCellClass) {
        this.holidayCellClass = holidayCellClass;
    }

    public Boolean isShowYear() {
        if (showYear != null) {
            return showYear;
        }
        ValueBinding vb = getValueBinding("showYear");
        return vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
    }

    public void setShowYear(Boolean showYear) {
        this.showYear = showYear;
    }

    public void preEncodeBegin(FacesContext context) throws IOException {
        RenderPreparableUtil.encodeBeforeForRenderer(context, this);
    }

    public void postEncodeEnd(FacesContext context) throws IOException {
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[9];
        values[0] = super.saveState(context);
        values[1] = year;
        values[2] = month;
        values[3] = calendarClass;
        values[4] = monthYearRowClass;
        values[5] = weekRowClass;
        values[6] = dayCellClass;
        values[7] = holidayCellClass;
        values[8] = showYear;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        year = (Integer) values[1];
        month = (Integer) values[2];
        calendarClass = (String) values[3];
        monthYearRowClass = (String) values[4];
        weekRowClass = (String) values[5];
        dayCellClass = (String) values[6];
        holidayCellClass = (String) values[7];
        showYear = (Boolean) values[8];
    }
}