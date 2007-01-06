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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.component.html.THtmlCalendar;

/**
 * @author higa
 */
public class TCalendarTag extends InputTextTag {

    private String monthYearRowClass;

    private String weekRowClass;

    private String dayCellClass;

    private String currentDayCellClass;

    public String getComponentType() {
        return THtmlCalendar.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlCalendar.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        monthYearRowClass = null;
        weekRowClass = null;
        dayCellClass = null;
        currentDayCellClass = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, "monthYearRowClass", monthYearRowClass);
        setComponentProperty(component, "weekRowClass", weekRowClass);
        setComponentProperty(component, "dayCellClass", dayCellClass);
        setComponentProperty(component, "currentDayCellClass",
                currentDayCellClass);
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

    public void setCurrentDayCellClass(String currentDayCellClass) {
        this.currentDayCellClass = currentDayCellClass;
    }
}