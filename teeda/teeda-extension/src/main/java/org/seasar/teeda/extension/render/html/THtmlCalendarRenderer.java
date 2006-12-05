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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlCalendar;
import org.seasar.teeda.extension.util.DateFormatSymbolsUtil;

/**
 * @author higa
 */
public class THtmlCalendarRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlCalendar";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlCalendar";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        THtmlCalendar htmlCalendar = (THtmlCalendar) component;
        Locale currentLocale = context.getViewRoot().getLocale();
        Date value = (Date) htmlCalendar.getValue();

        Calendar timeKeeper = Calendar.getInstance(currentLocale);
        timeKeeper.setTime(value != null ? value : new Date());

        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);

        String[] weekdays = DateFormatSymbolsUtil.getWeekdays(symbols);
        String[] months = DateFormatSymbolsUtil.getMonths(symbols);

        int lastDayInMonth = timeKeeper.getActualMaximum(Calendar.DAY_OF_MONTH);

        int currentDay = timeKeeper.get(Calendar.DAY_OF_MONTH);

        if (currentDay > lastDayInMonth) {
            currentDay = lastDayInMonth;
        }

        timeKeeper.set(Calendar.DAY_OF_MONTH, 1);

        int weekDayOfFirstDayOfMonth = mapCalendarDayToCommonDay(timeKeeper
                .get(Calendar.DAY_OF_WEEK));

        int weekStartsAtDayIndex = mapCalendarDayToCommonDay(timeKeeper
                .getFirstDayOfWeek());

        ResponseWriter writer = context.getResponseWriter();
        writer.write(JsfConstants.LINE_SP);
        writer.write(JsfConstants.LINE_SP);
        writer.startElement(JsfConstants.TABLE_ELEM, component);
        writer.flush();

        writer.write(JsfConstants.LINE_SP);

        writer.startElement(JsfConstants.TR_ELEM, component);

        if (htmlCalendar.getMonthYearRowClass() != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, htmlCalendar
                    .getMonthYearRowClass(), null);
        }

        writeMonthYearHeader(context, writer, htmlCalendar, timeKeeper,
                currentDay, weekdays, months);

        writer.endElement(JsfConstants.TR_ELEM);

        writer.write(JsfConstants.LINE_SP);

        writer.startElement(JsfConstants.TR_ELEM, component);

        if (htmlCalendar.getWeekRowClass() != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, htmlCalendar
                    .getWeekRowClass(), null);
        }

        writeWeekDayNameHeader(context, writer, htmlCalendar,
                weekStartsAtDayIndex, weekdays);

        writer.endElement(JsfConstants.TR_ELEM);

        writer.write(JsfConstants.LINE_SP);

        writeDays(context, writer, htmlCalendar, timeKeeper, currentDay,
                weekStartsAtDayIndex, weekDayOfFirstDayOfMonth, lastDayInMonth,
                weekdays);

        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    private void writeMonthYearHeader(FacesContext context,
            ResponseWriter writer, UIInput component, Calendar timeKeeper,
            int currentDay, String[] weekdays, String[] months)
            throws IOException {
        Calendar cal = shiftMonth(context, timeKeeper, currentDay, -1);

        writeCell(context, writer, component, "<", cal.getTime(), null);

        writer.startElement(JsfConstants.TD_ELEM, component);
        writer.writeAttribute(JsfConstants.COLSPAN_ATTR, new Integer(
                weekdays.length - 2), null);
        writer.writeText(months[timeKeeper.get(Calendar.MONTH)] + " "
                + timeKeeper.get(Calendar.YEAR), null);
        writer.endElement(JsfConstants.TD_ELEM);

        cal = shiftMonth(context, timeKeeper, currentDay, 1);

        writeCell(context, writer, component, ">", cal.getTime(), null);
    }

    private Calendar shiftMonth(FacesContext context, Calendar timeKeeper,
            int currentDay, int shift) {
        Calendar cal = copyCalendar(context, timeKeeper);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + shift);
        if (currentDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            currentDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        cal.set(Calendar.DAY_OF_MONTH, currentDay);
        return cal;
    }

    private Calendar copyCalendar(FacesContext context, Calendar timeKeeper) {
        Calendar cal = Calendar.getInstance(context.getViewRoot().getLocale());
        cal.set(Calendar.YEAR, timeKeeper.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, timeKeeper.get(Calendar.MONTH));
        cal.set(Calendar.HOUR_OF_DAY, timeKeeper.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, timeKeeper.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, timeKeeper.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, timeKeeper.get(Calendar.MILLISECOND));
        return cal;
    }

    private void writeWeekDayNameHeader(FacesContext context,
            ResponseWriter writer, UIInput component, int weekStartsAtDayIndex,
            String[] weekdays) throws IOException {
        for (int i = weekStartsAtDayIndex; i < weekdays.length; i++) {
            writeCell(context, writer, component, weekdays[i], null, null);
        }
        for (int i = 0; i < weekStartsAtDayIndex; i++) {
            writeCell(context, writer, component, weekdays[i], null, null);
        }
    }

    private void writeDays(FacesContext context, ResponseWriter writer,
            THtmlCalendar component, Calendar timeKeeper, int currentDay,
            int weekStartsAtDayIndex, int weekDayOfFirstDayOfMonth,
            int lastDayInMonth, String[] weekdays) throws IOException {
        Calendar cal = null;
        int space = (weekStartsAtDayIndex < weekDayOfFirstDayOfMonth) ? (weekDayOfFirstDayOfMonth - weekStartsAtDayIndex)
                : (weekdays.length - weekStartsAtDayIndex + weekDayOfFirstDayOfMonth);
        if (space == weekdays.length) {
            space = 0;
        }
        int columnIndexCounter = 0;
        for (int i = 0; i < space; i++) {
            if (columnIndexCounter == 0) {
                writer.startElement(JsfConstants.TR_ELEM, component);
            }
            writeCell(context, writer, component, "", null, component
                    .getDayCellClass());
            columnIndexCounter++;
        }
        for (int i = 0; i < lastDayInMonth; i++) {
            if (columnIndexCounter == 0) {
                writer.startElement(JsfConstants.TR_ELEM, component);
            }
            cal = copyCalendar(context, timeKeeper);
            cal.set(Calendar.DAY_OF_MONTH, i + 1);

            String cellStyle = component.getDayCellClass();

            if ((currentDay - 1) == i) {
                cellStyle = component.getCurrentDayCellClass();
            }
            writeCell(context, writer, component, String.valueOf(i + 1), cal
                    .getTime(), cellStyle);

            columnIndexCounter++;

            if (columnIndexCounter == weekdays.length) {
                writer.endElement(JsfConstants.TR_ELEM);
                writer.write(JsfConstants.LINE_SP);
                columnIndexCounter = 0;
            }
        }

        if (columnIndexCounter != 0) {
            for (int i = columnIndexCounter; i < weekdays.length; i++) {
                writeCell(context, writer, component, "", null, component
                        .getDayCellClass());
            }
            writer.endElement(JsfConstants.TR_ELEM);
            writer.write(JsfConstants.LINE_SP);
        }
    }

    private void writeCell(FacesContext context, ResponseWriter writer,
            UIInput component, String content, Date valueForLink,
            String styleClass) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, component);
        if (styleClass != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, styleClass, null);
        }
        if (valueForLink == null) {
            writer.writeText(content, JsfConstants.VALUE_ATTR);
        } else {
            writeLink(context, component, content, valueForLink);
        }
        writer.endElement(JsfConstants.TD_ELEM);
    }

    private void writeLink(FacesContext context, UIInput component,
            String content, Date valueForLink) throws IOException {
        Converter converter = THtmlCalendarRendererUtil.getConverter(context,
                component);

        Application application = context.getApplication();
        HtmlCommandLink link = (HtmlCommandLink) application
                .createComponent(HtmlCommandLink.COMPONENT_TYPE);
        link.setId(component.getId() + "_" + valueForLink.getTime() + "_link");
        link.setTransient(true);
        link.setImmediate(component.isImmediate());

        HtmlOutputText text = (HtmlOutputText) application
                .createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue(content);
        text.setId(component.getId() + "_" + valueForLink.getTime() + "_text");
        text.setTransient(true);

        UIParameter parameter = (UIParameter) application
                .createComponent(UIParameter.COMPONENT_TYPE);
        parameter.setId(component.getId() + "_" + valueForLink.getTime()
                + "_param");
        parameter.setTransient(true);
        parameter.setName(component.getClientId(context));
        parameter.setValue(converter.getAsString(context, component,
                valueForLink));

        THtmlCalendar calendar = (THtmlCalendar) component;
        if (calendar.isDisabled() || calendar.isReadonly()) {
            component.getChildren().add(text);

            RendererUtil.renderChild(context, text);
        } else {
            component.getChildren().add(link);
            link.getChildren().add(parameter);
            link.getChildren().add(text);

            RendererUtil.renderChild(context, link);
        }
    }

    private int mapCalendarDayToCommonDay(int day) {
        switch (day) {
        case Calendar.TUESDAY:
            return 1;
        case Calendar.WEDNESDAY:
            return 2;
        case Calendar.THURSDAY:
            return 3;
        case Calendar.FRIDAY:
            return 4;
        case Calendar.SATURDAY:
            return 5;
        case Calendar.SUNDAY:
            return 6;
        default:
            return 0;
        }
    }
}