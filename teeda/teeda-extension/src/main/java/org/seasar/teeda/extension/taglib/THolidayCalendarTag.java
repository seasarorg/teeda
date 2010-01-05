/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlHolidayCalendar;

/**
 * @author higa
 */
public class THolidayCalendarTag extends InputTextTag {

    private String year;

    private String month;

    private String calendarClass;

    private String monthYearRowClass;

    private String weekRowClass;

    private String dayCellClass;

    private String holidayCellClass;

    private String showYear;

    public String getComponentType() {
        return THtmlHolidayCalendar.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlHolidayCalendar.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        year = null;
        month = null;
        calendarClass = null;
        monthYearRowClass = null;
        weekRowClass = null;
        dayCellClass = null;
        holidayCellClass = null;
        showYear = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, ExtensionConstants.YEAR_ATTR, year);
        setComponentProperty(component, ExtensionConstants.MONTH_ATTR, month);
        setComponentProperty(component, ExtensionConstants.CALENDAR_CLASS_ATTR,
                calendarClass);
        setComponentProperty(component, "monthYearRowClass", monthYearRowClass);
        setComponentProperty(component, "weekRowClass", weekRowClass);
        setComponentProperty(component, "dayCellClass", dayCellClass);
        setComponentProperty(component, "holidayCellClass", holidayCellClass);
        setComponentProperty(component, "showYear", showYear);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setMonthYearRowClass(String monthYearRowClass) {
        this.monthYearRowClass = monthYearRowClass;
    }

    public void setWeekRowClass(String weekRowClass) {
        this.weekRowClass = weekRowClass;
    }

    public void setDayCellClass(String dayCellClass) {
        this.dayCellClass = dayCellClass;
    }

    public void setHolidayCellClass(String holidayCellClass) {
        this.holidayCellClass = holidayCellClass;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }
}