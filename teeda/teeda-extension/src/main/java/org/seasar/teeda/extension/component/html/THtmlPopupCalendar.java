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
package org.seasar.teeda.extension.component.html;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.RenderPreparable;
import javax.faces.internal.RenderPreparableUtil;

import org.seasar.teeda.extension.convert.TDateTimeConverter;

/**
 * @author higa
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class THtmlPopupCalendar extends HtmlInputText implements
        RenderPreparable {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlPopupCalendar";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlPopupCalendar";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlPopupCalendar";

    private String popupGotoString = null;

    private String popupTodayString = null;

    private String popupWeekString = null;

    private String popupScrollLeftMessage = null;

    private String popupScrollRightMessage = null;

    private String popupSelectMonthMessage = null;

    private String popupSelectYearMessage = null;

    private String popupSelectDateMessage = null;

    public THtmlPopupCalendar() {
        setRendererType(DEFAULT_RENDERER_TYPE);
        setConverter(new TDateTimeConverter());
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setPopupGotoString(String popupGotoString) {
        this.popupGotoString = popupGotoString;
    }

    public String getPopupGotoString() {
        if (popupGotoString != null)
            return popupGotoString;
        ValueBinding vb = getValueBinding("popupGotoString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupTodayString(String popupTodayString) {
        this.popupTodayString = popupTodayString;
    }

    public String getPopupTodayString() {
        if (popupTodayString != null)
            return popupTodayString;
        ValueBinding vb = getValueBinding("popupTodayString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupWeekString(String popupWeekString) {
        this.popupWeekString = popupWeekString;
    }

    public String getPopupWeekString() {
        if (popupWeekString != null)
            return popupWeekString;
        ValueBinding vb = getValueBinding("popupWeekString");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupScrollLeftMessage(String popupScrollLeftMessage) {
        this.popupScrollLeftMessage = popupScrollLeftMessage;
    }

    public String getPopupScrollLeftMessage() {
        if (popupScrollLeftMessage != null)
            return popupScrollLeftMessage;
        ValueBinding vb = getValueBinding("popupScrollLeftMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupScrollRightMessage(String popupScrollRightMessage) {
        this.popupScrollRightMessage = popupScrollRightMessage;
    }

    public String getPopupScrollRightMessage() {
        if (popupScrollRightMessage != null)
            return popupScrollRightMessage;
        ValueBinding vb = getValueBinding("popupScrollRightMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectMonthMessage(String popupSelectMonthMessage) {
        this.popupSelectMonthMessage = popupSelectMonthMessage;
    }

    public String getPopupSelectMonthMessage() {
        if (popupSelectMonthMessage != null)
            return popupSelectMonthMessage;
        ValueBinding vb = getValueBinding("popupSelectMonthMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectYearMessage(String popupSelectYearMessage) {
        this.popupSelectYearMessage = popupSelectYearMessage;
    }

    public String getPopupSelectYearMessage() {
        if (popupSelectYearMessage != null)
            return popupSelectYearMessage;
        ValueBinding vb = getValueBinding("popupSelectYearMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPopupSelectDateMessage(String popupSelectDateMessage) {
        this.popupSelectDateMessage = popupSelectDateMessage;
    }

    public String getPopupSelectDateMessage() {
        if (popupSelectDateMessage != null)
            return popupSelectDateMessage;
        ValueBinding vb = getValueBinding("popupSelectDateMessage");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void encodePrepare(FacesContext context) throws IOException {
        RenderPreparableUtil.encodePrepareForRenderer(context, this);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[20];
        values[0] = super.saveState(context);
        values[1] = popupGotoString;
        values[2] = popupTodayString;
        values[3] = popupWeekString;
        values[4] = popupScrollLeftMessage;
        values[5] = popupScrollRightMessage;
        values[6] = popupSelectMonthMessage;
        values[7] = popupSelectYearMessage;
        values[8] = popupSelectDateMessage;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        popupGotoString = (String) values[1];
        popupTodayString = (String) values[2];
        popupWeekString = (String) values[3];
        popupScrollLeftMessage = (String) values[4];
        popupScrollRightMessage = (String) values[5];
        popupSelectMonthMessage = (String) values[6];
        popupSelectYearMessage = (String) values[7];
        popupSelectDateMessage = (String) values[8];
    }
}