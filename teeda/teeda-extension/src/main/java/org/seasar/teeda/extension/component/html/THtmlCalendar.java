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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.extension.convert.TDateTimeConverter;

/**
 * @author higa
 */
public class THtmlCalendar extends HtmlInputText {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlCalendar";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlCalendar";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlCalendar";;

    private String monthYearRowClass = null;

    private String weekRowClass = null;

    private String dayCellClass = null;

    private String currentDayCellClass = null;

    public THtmlCalendar() {
        setRendererType(DEFAULT_RENDERER_TYPE);
        setConverter(new TDateTimeConverter());
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setMonthYearRowClass(String monthYearRowClass) {
        this.monthYearRowClass = monthYearRowClass;
    }

    public String getMonthYearRowClass() {
        if (monthYearRowClass != null)
            return monthYearRowClass;
        ValueBinding vb = getValueBinding("monthYearRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWeekRowClass(String weekRowClass) {
        this.weekRowClass = weekRowClass;
    }

    public String getWeekRowClass() {
        if (weekRowClass != null)
            return weekRowClass;
        ValueBinding vb = getValueBinding("weekRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDayCellClass(String dayCellClass) {
        this.dayCellClass = dayCellClass;
    }

    public String getDayCellClass() {
        if (dayCellClass != null)
            return dayCellClass;
        ValueBinding vb = getValueBinding("dayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCurrentDayCellClass(String currentDayCellClass) {
        this.currentDayCellClass = currentDayCellClass;
    }

    public String getCurrentDayCellClass() {
        if (currentDayCellClass != null)
            return currentDayCellClass;
        ValueBinding vb = getValueBinding("currentDayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[20];
        values[0] = super.saveState(context);
        values[1] = monthYearRowClass;
        values[2] = weekRowClass;
        values[3] = dayCellClass;
        values[4] = currentDayCellClass;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        monthYearRowClass = (String) values[1];
        weekRowClass = (String) values[2];
        dayCellClass = (String) values[3];
        currentDayCellClass = (String) values[4];
    }
}
