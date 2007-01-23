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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlHolidayCalendar;
import org.seasar.teeda.extension.render.RenderPreparableRenderer;
import org.seasar.teeda.extension.util.DateFormatSymbolsUtil;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author higa
 */
public class THtmlHolidayCalendarRenderer extends AbstractInputRenderer
        implements RenderPreparableRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlHolidayCalendar";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlHolidayCalendar";

    private static final String JS_CSS_ENCODED = "org.seasar.teeda.extension.holidaycalendar.JAVASCRIPT_ENCODED";

    private static final String RESOURCE_ROOT = "org/seasar/teeda/extension/render/html/holidaycalendar/";

    public static final String WEEKDAYS_MESSAGE_ID = "org.seasar.teeda.extension.component.HtmlHolidayCalendar.WEEKDAYS";

    public void encodePrepare(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlHolidayCalendarPrepare(context,
                (THtmlHolidayCalendar) component);
    }

    protected void encodeHtmlHolidayCalendarPrepare(final FacesContext context,
            final THtmlHolidayCalendar htmlCalendar) throws IOException {
        if (context.getExternalContext().getRequestMap().containsKey(
                JS_CSS_ENCODED)) {
            return;
        }
        VirtualResource
                .addCSSResource(context, RESOURCE_ROOT + "css/theme.css");
        VirtualResource.addJSResource(context, RESOURCE_ROOT
                + "js/holidaycalendar.js");
        context.getExternalContext().getRequestMap().put(JS_CSS_ENCODED,
                Boolean.TRUE);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        THtmlHolidayCalendar htmlCalendar = (THtmlHolidayCalendar) component;
        Locale currentLocale = context.getViewRoot().getLocale();
        Boolean[] value = (Boolean[]) htmlCalendar.getValue();
        Calendar base = Calendar.getInstance(currentLocale);
        base.setTime(new Date());
        Integer year = htmlCalendar.getYear();
        if (year != null) {
            base.set(Calendar.YEAR, year.intValue());
        }
        Integer month = htmlCalendar.getMonth();
        if (month != null) {
            base.set(Calendar.MONTH, month.intValue());
        }
        base.set(Calendar.DAY_OF_MONTH, 1);
        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);
        String[] weekdays = getWeekDays(context, symbols);
        String[] months = DateFormatSymbolsUtil.getMonths(symbols);
        int lastDayInMonth = base.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekDayOfFirstDayOfMonth = mapCalendarDayToCommonDay(base
                .get(Calendar.DAY_OF_WEEK));
        int weekStartsAtDayIndex = mapCalendarDayToCommonDay(base
                .getFirstDayOfWeek());
        ResponseWriter writer = context.getResponseWriter();
        writer.write(JsfConstants.LINE_SP);
        writer.write(JsfConstants.LINE_SP);
        writer.startElement(JsfConstants.TABLE_ELEM, component);
        String calendarClass = htmlCalendar.getCalendarClass();
        if (calendarClass != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, calendarClass, null);
        }
        final int border = htmlCalendar.getBorder();
        writer.writeAttribute(JsfConstants.BORDER_ATTR, new Integer(border),
                null);
        writer.write(JsfConstants.LINE_SP);
        writer.startElement(JsfConstants.TR_ELEM, component);
        if (htmlCalendar.getMonthYearRowClass() != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, htmlCalendar
                    .getMonthYearRowClass(), null);
        }
        writeMonthYearHeader(context, writer, htmlCalendar, months[base
                .get(Calendar.MONTH)], String.valueOf(base.get(Calendar.YEAR)));
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
        writeDays(context, writer, htmlCalendar, value, weekStartsAtDayIndex,
                weekDayOfFirstDayOfMonth, lastDayInMonth);

        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    protected String[] getWeekDays(FacesContext context,
            DateFormatSymbols symbols) {
        String summary = FacesMessageUtil.getSummary(context,
                WEEKDAYS_MESSAGE_ID, new Object[] {});
        if (summary != null) {
            String[] array = StringUtil.split(summary, ",");
            if (array.length == 7) {
                for (int i = 0; i < array.length; i++) {
                    array[i] = array[i].trim();
                }
                return array;
            }
        }
        return DateFormatSymbolsUtil.getWeekdays(symbols);
    }

    protected void writeMonthYearHeader(FacesContext context,
            ResponseWriter writer, THtmlHolidayCalendar component,
            String monthName, String yearName) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, component);
        writer.writeAttribute(JsfConstants.COLSPAN_ATTR, new Integer(7), null);
        String header = monthName;
        if (component.isShowYear().booleanValue()) {
            header += " " + yearName;
        }
        writer.writeText(header, null);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    protected void writeWeekDayNameHeader(FacesContext context,
            ResponseWriter writer, UIInput component, int weekStartsAtDayIndex,
            String[] weekdays) throws IOException {
        for (int i = weekStartsAtDayIndex; i < weekdays.length; i++) {
            writer.startElement(JsfConstants.TD_ELEM, component);
            writer.writeText(weekdays[i], null);
            writer.endElement(JsfConstants.TD_ELEM);
        }
        for (int i = 0; i < weekStartsAtDayIndex; i++) {
            writer.startElement(JsfConstants.TD_ELEM, component);
            writer.writeText(weekdays[i], null);
            writer.endElement(JsfConstants.TD_ELEM);
        }
    }

    protected void writeDays(FacesContext context, ResponseWriter writer,
            THtmlHolidayCalendar component, Boolean[] value,
            int weekStartsAtDayIndex, int weekDayOfFirstDayOfMonth,
            int lastDayInMonth) throws IOException {
        int columnIndexCounter = writeFirstSpace(context, writer, component,
                weekStartsAtDayIndex, weekDayOfFirstDayOfMonth);
        int trNum = columnIndexCounter == 0 ? 0 : 1;
        for (int i = 0; i < lastDayInMonth; i++) {
            if (columnIndexCounter == 0) {
                writer.startElement(JsfConstants.TR_ELEM, component);
                trNum++;
            }
            boolean holiday = false;
            if (value != null && value.length > i && value[i] != null) {
                holiday = value[i].booleanValue();
            }
            writeCell(context, writer, component, String.valueOf(i + 1),
                    holiday);
            columnIndexCounter++;
            if (columnIndexCounter == 7) {
                writer.endElement(JsfConstants.TR_ELEM);
                writer.write(JsfConstants.LINE_SP);
                columnIndexCounter = 0;
            }
        }
        writeLastSpace(context, writer, component, columnIndexCounter, trNum);
    }

    protected int writeFirstSpace(FacesContext context, ResponseWriter writer,
            THtmlHolidayCalendar component, int weekStartsAtDayIndex,
            int weekDayOfFirstDayOfMonth) throws IOException {
        int index = (weekStartsAtDayIndex < weekDayOfFirstDayOfMonth) ? (weekDayOfFirstDayOfMonth - weekStartsAtDayIndex)
                : (7 - weekStartsAtDayIndex + weekDayOfFirstDayOfMonth);
        if (index == 7) {
            index = 0;
        }
        if (index == 0) {
            return 0;
        }
        writer.startElement(JsfConstants.TR_ELEM, component);
        for (int i = 0; i < index; i++) {
            writeEmptyCell(context, writer, component);
        }
        return index;
    }

    protected void writeEmptyCell(FacesContext context, ResponseWriter writer,
            THtmlHolidayCalendar component) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, component);
        writer.writeAttribute(JsfConstants.STYLE_ATTR, "visibility : hidden",
                null);
        writer.writeText("1", JsfConstants.VALUE_ATTR);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    protected void writeLastSpace(FacesContext context, ResponseWriter writer,
            THtmlHolidayCalendar component, int columnIndexCounter, int trNum)
            throws IOException {
        if (columnIndexCounter != 0) {
            for (int i = columnIndexCounter; i < 7; i++) {
                writeEmptyCell(context, writer, component);
            }
            writer.endElement(JsfConstants.TR_ELEM);
            writer.write(JsfConstants.LINE_SP);
        }
        for (int i = trNum; i < 6; i++) {
            writer.startElement(JsfConstants.TR_ELEM, component);
            for (int j = 0; j < 7; j++) {
                writeEmptyCell(context, writer, component);
            }
            writer.endElement(JsfConstants.TR_ELEM);
            writer.write(JsfConstants.LINE_SP);
        }
    }

    private void writeCell(FacesContext context, ResponseWriter writer,
            THtmlHolidayCalendar component, String dateName, boolean holiday)
            throws IOException {
        String holidayCellClass = component.getHolidayCellClass();
        String dayCellClass = component.getDayCellClass();
        writer.startElement(JsfConstants.TD_ELEM, component);
        String clientId = component.getClientId(context);
        String tdId = clientId + "-td" + dateName;
        String hiddenId = clientId + "-hidden" + dateName;
        writer.writeAttribute(JsfConstants.ID_ATTR, tdId, null);
        String styleClass = holiday ? holidayCellClass : dayCellClass;
        if (styleClass != null) {
            writer.writeAttribute(JsfConstants.CLASS_ATTR, styleClass, null);
        }
        String onclick = "Teeda.THolidayCalendar.toggleCell('" + tdId + "','"
                + hiddenId + "','" + holidayCellClass + "','" + dayCellClass
                + "');";
        writer.writeAttribute(JsfConstants.ONCLICK_ATTR, onclick, null);
        writer.writeText(dateName, JsfConstants.VALUE_ATTR);
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.ID_ATTR, hiddenId, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, clientId, null);
        writer.writeAttribute(JsfConstants.VALUE_ATTR, String.valueOf(holiday),
                null);
        writer.endElement(JsfConstants.INPUT_ELEM);
        writer.endElement(JsfConstants.TD_ELEM);
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

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        getDecoder().decodeMany(context, component);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        return RendererUtil.getConvertedUIOutputValues(context,
                (UIOutput) component, submittedValue);
    }
}