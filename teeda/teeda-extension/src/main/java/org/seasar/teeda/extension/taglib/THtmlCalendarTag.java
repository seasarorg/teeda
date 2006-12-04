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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.component.html.THtmlCalendar;

/**
 * @author higa
 */
public class THtmlCalendarTag extends UIComponentTagBase {

    private String accesskey;

    private String align;

    private String alt;

    private String disabled;

    private String maxlength;

    private String onblur;

    private String onchange;

    private String onfocus;

    private String onselect;

    private String size;

    private String tabindex;

    private String monthYearRowClass;

    private String weekRowClass;

    private String dayCellClass;

    private String currentDayCellClass;

    private String renderAsPopup;

    private String addResources;

    private String popupDateFormat;

    private String popupButtonString;

    private String renderPopupButtonAsImage;

    private String popupGotoString = null;

    private String popupTodayString = null;

    private String popupWeekString = null;

    private String popupScrollLeftMessage = null;

    private String popupScrollRightMessage = null;

    private String popupSelectMonthMessage = null;

    private String popupSelectYearMessage = null;

    private String popupSelectDateMessage = null;

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
        renderAsPopup = null;
        addResources = null;
        popupDateFormat = null;
        popupButtonString = null;
        renderPopupButtonAsImage = null;
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

        setComponentProperty(component, JsfConstants.ACCESSKEY_ATTR, accesskey);
        setComponentProperty(component, JsfConstants.ALIGN_ATTR, align);
        setComponentProperty(component, JsfConstants.ALT_ATTR, alt);
        setComponentProperty(component, JsfConstants.DISABLED_ATTR, disabled);
        setComponentProperty(component, JsfConstants.MAXLENGTH_ATTR, maxlength);
        setComponentProperty(component, JsfConstants.ONBLUR_ATTR, onblur);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange);
        setComponentProperty(component, JsfConstants.ONFOCUS_ATTR, onfocus);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect);
        setComponentProperty(component, JsfConstants.SIZE_ATTR, size);
        setComponentProperty(component, JsfConstants.TABINDEX_ATTR, tabindex);

        setComponentProperty(component, "monthYearRowClass", monthYearRowClass);
        setComponentProperty(component, "weekRowClass", weekRowClass);
        setComponentProperty(component, "dayCellClass", dayCellClass);
        setComponentProperty(component, "currentDayCellClass",
                currentDayCellClass);
        setComponentProperty(component, "renderAsPopup", renderAsPopup);
        setComponentProperty(component, "addResources", addResources);
        setComponentProperty(component, "popupDateFormat", popupDateFormat);
        setComponentProperty(component, "popupButtonString", popupButtonString);
        setComponentProperty(component, "renderPopupButtonAsImage",
                renderPopupButtonAsImage);
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

    public void setRenderAsPopup(String renderAsPopup) {
        this.renderAsPopup = renderAsPopup;
    }

    public void setAddResources(String addResources) {
        this.addResources = addResources;
    }

    public void setPopupDateFormat(String popupDateFormat) {
        this.popupDateFormat = popupDateFormat;
    }

    public void setPopupButtonString(String popupButtonString) {
        this.popupButtonString = popupButtonString;
    }

    public void setRenderPopupButtonAsImage(String renderPopupButtonAsImage) {
        this.renderPopupButtonAsImage = renderPopupButtonAsImage;
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

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }
}
