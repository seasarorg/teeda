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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.component.html.THtmlPopupCalendar;

/**
 * @author higa
 */
public class TPopupCalendarTag extends InputTextTag {

    private String popupGotoString;

    private String popupTodayString;

    private String popupWeekString;

    private String popupScrollLeftMessage;

    private String popupScrollRightMessage;

    private String popupSelectMonthMessage;

    private String popupSelectYearMessage;

    private String popupSelectDateMessage;

    public String getComponentType() {
        return THtmlPopupCalendar.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlPopupCalendar.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        popupGotoString = null;
        popupTodayString = null;
        popupWeekString = null;
        popupScrollLeftMessage = null;
        popupScrollRightMessage = null;
        popupSelectMonthMessage = null;
        popupSelectYearMessage = null;
        popupSelectDateMessage = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "popupGotoString", popupGotoString);
        setComponentProperty(component, "popupTodayString", popupTodayString);
        setComponentProperty(component, "popupWeekString", popupWeekString);
        setComponentProperty(component, "popupScrollLeftMessage",
                popupScrollLeftMessage);
        setComponentProperty(component, "popupScrollRightMessage",
                popupScrollRightMessage);
        setComponentProperty(component, "popupSelectMonthMessage",
                popupSelectMonthMessage);
        setComponentProperty(component, "popupSelectYearMessage",
                popupSelectYearMessage);
        setComponentProperty(component, "popupSelectDateMessage",
                popupSelectDateMessage);
    }

    public void setPopupGotoString(String popupGotoString) {
        this.popupGotoString = popupGotoString;
    }

    public void setPopupScrollLeftMessage(String popupScrollLeftMessage) {
        this.popupScrollLeftMessage = popupScrollLeftMessage;
    }

    public void setPopupScrollRightMessage(String popupScrollRightMessage) {
        this.popupScrollRightMessage = popupScrollRightMessage;
    }

    public void setPopupSelectDateMessage(String popupSelectDateMessage) {
        this.popupSelectDateMessage = popupSelectDateMessage;
    }

    public void setPopupSelectMonthMessage(String popupSelectMonthMessage) {
        this.popupSelectMonthMessage = popupSelectMonthMessage;
    }

    public void setPopupSelectYearMessage(String popupSelectYearMessage) {
        this.popupSelectYearMessage = popupSelectYearMessage;
    }

    public void setPopupTodayString(String popupTodayString) {
        this.popupTodayString = popupTodayString;
    }

    public void setPopupWeekString(String popupWeekString) {
        this.popupWeekString = popupWeekString;
    }
}
