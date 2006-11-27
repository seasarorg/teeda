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

/**
 * @author higa
 */
public class THtmlCalendar extends HtmlInputText {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlCalendar";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlCalendar";

    private String monthYearRowClass = null;

    private String weekRowClass = null;

    private String dayCellClass = null;

    private String currentDayCellClass = null;

    private Boolean renderAsPopup = null;

    private Boolean addResources = null;

    private String popupButtonString = null;

    private Boolean renderPopupButtonAsImage = null;

    private String popupGotoString = null;

    private String popupTodayString = null;

    private String popupWeekString = null;

    private String popupScrollLeftMessage = null;

    private String popupScrollRightMessage = null;

    private String popupSelectMonthMessage = null;

    private String popupSelectYearMessage = null;

    private String popupSelectDateMessage = null;

    public THtmlCalendar() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setMonthYearRowClass(String monthYearRowClass) {
        this.monthYearRowClass = monthYearRowClass;
    }

    public String getMonthYearRowClass() {
        if (monthYearRowClass != null) {
            return monthYearRowClass;
        }
        ValueBinding vb = getValueBinding("monthYearRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWeekRowClass(String weekRowClass) {
        this.weekRowClass = weekRowClass;
    }

    public String getWeekRowClass() {
        if (weekRowClass != null) {
            return weekRowClass;
        }
        ValueBinding vb = getValueBinding("weekRowClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDayCellClass(String dayCellClass) {
        this.dayCellClass = dayCellClass;
    }

    public String getDayCellClass() {
        if (dayCellClass != null) {
            return dayCellClass;
        }
        ValueBinding vb = getValueBinding("dayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCurrentDayCellClass(String currentDayCellClass) {
        this.currentDayCellClass = currentDayCellClass;
    }

    public String getCurrentDayCellClass() {
        if (currentDayCellClass != null) {
            return currentDayCellClass;
        }
        ValueBinding vb = getValueBinding("currentDayCellClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRenderAsPopup(boolean renderAsPopup) {
        this.renderAsPopup = Boolean.valueOf(renderAsPopup);
    }

    public boolean isRenderAsPopup() {
        if (renderAsPopup != null) {
            return renderAsPopup.booleanValue();
        }
        ValueBinding vb = getValueBinding("renderAsPopup");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : false;
    }

    public void setAddResources(Boolean addResources) {
        this.addResources = addResources == null ? Boolean.TRUE : addResources;
    }

    public void setAddResources(boolean addResources) {
        this.addResources = Boolean.valueOf(addResources);
    }

    public boolean isAddResources() {
        if (addResources != null) {
            return addResources.booleanValue();
        }
        ValueBinding vb = getValueBinding("addResources");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : true;
    }

    public void setPopupButtonString(String popupButtonString) {
        this.popupButtonString = popupButtonString;
    }

    public String getPopupButtonString() {
        if (popupButtonString != null) {
            return popupButtonString;
        }
        ValueBinding vb = getValueBinding("popupButtonString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRenderPopupButtonAsImage(boolean renderPopupButtonAsImage) {
        this.renderPopupButtonAsImage = Boolean
                .valueOf(renderPopupButtonAsImage);
    }

    public boolean isRenderPopupButtonAsImage() {
        if (renderPopupButtonAsImage != null) {
            return renderPopupButtonAsImage.booleanValue();
        }
        ValueBinding vb = getValueBinding("renderPopupButtonAsImage");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : false;
    }

    public void setPopupGotoString(String popupGotoString) {
        this.popupGotoString = popupGotoString;
    }

    public String getPopupGotoString() {
        if (popupGotoString != null) {
            return popupGotoString;
        }
        ValueBinding vb = getValueBinding("popupGotoString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupTodayString(String popupTodayString) {
        this.popupTodayString = popupTodayString;
    }

    public String getPopupTodayString() {
        if (popupTodayString != null) {
            return popupTodayString;
        }
        ValueBinding vb = getValueBinding("popupTodayString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupWeekString(String popupWeekString) {
        this.popupWeekString = popupWeekString;
    }

    public String getPopupWeekString() {
        if (popupWeekString != null) {
            return popupWeekString;
        }
        ValueBinding vb = getValueBinding("popupWeekString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupScrollLeftMessage(String popupScrollLeftMessage) {
        this.popupScrollLeftMessage = popupScrollLeftMessage;
    }

    public String getPopupScrollLeftMessage() {
        if (popupScrollLeftMessage != null) {
            return popupScrollLeftMessage;
        }
        ValueBinding vb = getValueBinding("popupScrollLeftMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupScrollRightMessage(String popupScrollRightMessage) {
        this.popupScrollRightMessage = popupScrollRightMessage;
    }

    public String getPopupScrollRightMessage() {
        if (popupScrollRightMessage != null) {
            return popupScrollRightMessage;
        }
        ValueBinding vb = getValueBinding("popupScrollRightMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectMonthMessage(String popupSelectMonthMessage) {
        this.popupSelectMonthMessage = popupSelectMonthMessage;
    }

    public String getPopupSelectMonthMessage() {
        if (popupSelectMonthMessage != null) {
            return popupSelectMonthMessage;
        }
        ValueBinding vb = getValueBinding("popupSelectMonthMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectYearMessage(String popupSelectYearMessage) {
        this.popupSelectYearMessage = popupSelectYearMessage;
    }

    public String getPopupSelectYearMessage() {
        if (popupSelectYearMessage != null) {
            return popupSelectYearMessage;
        }
        ValueBinding vb = getValueBinding("popupSelectYearMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectDateMessage(String popupSelectDateMessage) {
        this.popupSelectDateMessage = popupSelectDateMessage;
    }

    public String getPopupSelectDateMessage() {
        if (popupSelectDateMessage != null) {
            return popupSelectDateMessage;
        }
        ValueBinding vb = getValueBinding("popupSelectDateMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[20];
        values[0] = super.saveState(context);
        values[1] = monthYearRowClass;
        values[2] = weekRowClass;
        values[3] = dayCellClass;
        values[4] = currentDayCellClass;
        values[5] = renderAsPopup;
        values[6] = addResources;
        values[7] = popupButtonString;
        values[8] = renderPopupButtonAsImage;
        values[9] = popupGotoString;
        values[10] = popupTodayString;
        values[11] = popupWeekString;
        values[12] = popupScrollLeftMessage;
        values[13] = popupScrollRightMessage;
        values[14] = popupSelectMonthMessage;
        values[15] = popupSelectYearMessage;
        values[16] = popupSelectDateMessage;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        monthYearRowClass = (String) values[1];
        weekRowClass = (String) values[2];
        dayCellClass = (String) values[3];
        currentDayCellClass = (String) values[4];
        renderAsPopup = (Boolean) values[5];
        addResources = (Boolean) values[6];
        popupButtonString = (String) values[7];
        renderPopupButtonAsImage = (Boolean) values[8];
        popupGotoString = (String) values[9];
        popupTodayString = (String) values[10];
        popupWeekString = (String) values[11];
        popupScrollLeftMessage = (String) values[12];
        popupScrollRightMessage = (String) values[13];
        popupSelectMonthMessage = (String) values[14];
        popupSelectYearMessage = (String) values[15];
        popupSelectDateMessage = (String) values[16];
    }
}