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
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlCalendar;
import org.seasar.teeda.extension.render.RenderPreparableRenderer;
import org.seasar.teeda.extension.util.DateFormatSymbolsUtil;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author higa
 */
public class THtmlCalendarRenderer extends AbstractInputRenderer implements
        RenderPreparableRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlCalendar";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlCalendar";

    private static final String JAVASCRIPT_ENCODED = "org.seasar.teeda.extension.calendar.JAVASCRIPT_ENCODED";

    private static final String JAVASCRIPT_ENCODED2 = "org.seasar.teeda.extension.calendar.JAVASCRIPT_ENCODED2";

    private static final String RESOURCE_ROOT = "org/seasar/teeda/extension/render/html/calendar/";

    public void encodePrepare(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlCalendarPrepare(context, (THtmlCalendar) component);
    }

    protected void encodeHtmlCalendarPrepare(final FacesContext context,
            final THtmlCalendar htmlCalendar) throws IOException {
        if (context.getExternalContext().getRequestMap().containsKey(
                JAVASCRIPT_ENCODED)) {
            return;
        }
        Locale currentLocale = context.getViewRoot().getLocale();
        Calendar timeKeeper = Calendar.getInstance(currentLocale);
        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);
        String[] months = DateFormatSymbolsUtil.getMonths(symbols);
        int firstDayOfWeek = timeKeeper.getFirstDayOfWeek();
        VirtualResource
                .addCSSResource(context, RESOURCE_ROOT + "css/theme.css");
        VirtualResource.addJSResource(context, RESOURCE_ROOT
                + "js/popcalendar_init.js");
        VirtualResource.addJSResource(context, RESOURCE_ROOT
                + "js/popcalendar.js");

        StringBuffer script = new StringBuffer();
        appendImageDirectory(script, context);
        script.append(JsfConstants.LINE_SP);
        script.append(getLocalizedLanguageScript(symbols, months,
                firstDayOfWeek, htmlCalendar));
        script.append(JsfConstants.LINE_SP);
        script.append("loadPopupScript();");
        VirtualResource.addInlineJSResource(context, JAVASCRIPT_ENCODED, script
                .toString());

        context.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED,
                Boolean.TRUE);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        THtmlCalendar htmlCalendar = (THtmlCalendar) component;
        Locale currentLocale = facesContext.getViewRoot().getLocale();
        Date value = (Date) htmlCalendar.getValue();

        Calendar timeKeeper = Calendar.getInstance(currentLocale);
        timeKeeper.setTime(value != null ? value : new Date());

        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);

        String[] weekdays = DateFormatSymbolsUtil.getWeekdays(symbols);
        String[] months = DateFormatSymbolsUtil.getMonths(symbols);

        if (htmlCalendar.isRenderAsPopup()) {

            Application application = facesContext.getApplication();

            HtmlInputText inputText = getOrCreateInputTextChild(htmlCalendar,
                    application);

            copyHtmlInputTextAttributes(htmlCalendar, inputText);

            inputText.setConverter(null); // value for this transient component will already be converted
            inputText.setTransient(true);

            if (value == null && htmlCalendar.getSubmittedValue() != null) {
                inputText.setValue(htmlCalendar.getSubmittedValue());
            } else {
                inputText.setValue(getConverter(htmlCalendar).getAsString(
                        facesContext, htmlCalendar, value));
            }
            inputText.setDisabled(htmlCalendar.isDisabled());
            inputText.setReadonly(htmlCalendar.isReadonly());

            htmlCalendar.setId(htmlCalendar.getId() + "tempId");

            htmlCalendar.getChildren().add(inputText);

            RendererUtil.renderChild(facesContext, inputText);

            htmlCalendar.getChildren().remove(inputText);

            //Set back the correct id to the input calendar
            htmlCalendar.setId(inputText.getId());

            if (!htmlCalendar.isDisabled()) {
                ResponseWriter writer = facesContext.getResponseWriter();
                String dateFormat = CalendarDateTimeConverter
                        .createJSPopupFormat(facesContext, htmlCalendar
                                .getPopupDateFormat());
                writer.startElement(JsfConstants.SCRIPT_ELEM, component);
                writer.writeAttribute(JsfConstants.TYPE_ATTR,
                        JsfConstants.TEXT_JAVASCRIPT_VALUE, null);
                writer.writeText(getScriptBtn(facesContext, htmlCalendar,
                        dateFormat, htmlCalendar.getPopupButtonString()), null);
                writer.endElement(JsfConstants.SCRIPT_ELEM);
            }
        } else {

            int lastDayInMonth = timeKeeper
                    .getActualMaximum(Calendar.DAY_OF_MONTH);

            int currentDay = timeKeeper.get(Calendar.DAY_OF_MONTH);

            if (currentDay > lastDayInMonth)
                currentDay = lastDayInMonth;

            timeKeeper.set(Calendar.DAY_OF_MONTH, 1);

            int weekDayOfFirstDayOfMonth = mapCalendarDayToCommonDay(timeKeeper
                    .get(Calendar.DAY_OF_WEEK));

            int weekStartsAtDayIndex = mapCalendarDayToCommonDay(timeKeeper
                    .getFirstDayOfWeek());

            ResponseWriter writer = facesContext.getResponseWriter();
            writer.write(JsfConstants.LINE_SP);
            writer.write(JsfConstants.LINE_SP);
            writer.startElement(JsfConstants.TABLE_ELEM, component);
            writer.flush();

            writer.write(JsfConstants.LINE_SP);

            writer.startElement(JsfConstants.TR_ELEM, component);

            if (htmlCalendar.getMonthYearRowClass() != null)
                writer.writeAttribute(JsfConstants.CLASS_ATTR, htmlCalendar
                        .getMonthYearRowClass(), null);

            writeMonthYearHeader(facesContext, writer, htmlCalendar,
                    timeKeeper, currentDay, weekdays, months);

            writer.endElement(JsfConstants.TR_ELEM);

            writer.write(JsfConstants.LINE_SP);

            writer.startElement(JsfConstants.TR_ELEM, component);

            if (htmlCalendar.getWeekRowClass() != null)
                writer.writeAttribute(JsfConstants.CLASS_ATTR, htmlCalendar
                        .getWeekRowClass(), null);

            writeWeekDayNameHeader(weekStartsAtDayIndex, weekdays,
                    facesContext, writer, htmlCalendar);

            writer.endElement(JsfConstants.TR_ELEM);

            writer.write(JsfConstants.LINE_SP);

            writeDays(facesContext, writer, htmlCalendar, timeKeeper,
                    currentDay, weekStartsAtDayIndex, weekDayOfFirstDayOfMonth,
                    lastDayInMonth, weekdays);

            writer.endElement(JsfConstants.TABLE_ELEM);
        }
    }

    public static void copyHtmlInputTextAttributes(HtmlInputText src,
            HtmlInputText dest) {
        dest.setId(src.getId());
        dest.setImmediate(src.isImmediate());
        dest.setTransient(src.isTransient());
        dest.setAccesskey(src.getAccesskey());
        dest.setAlt(src.getAlt());
        dest.setConverter(src.getConverter());
        dest.setDir(src.getDir());
        dest.setDisabled(src.isDisabled());
        dest.setLang(src.getLang());
        dest.setLocalValueSet(src.isLocalValueSet());
        dest.setMaxlength(src.getMaxlength());
        dest.setOnblur(src.getOnblur());
        dest.setOnchange(src.getOnchange());
        dest.setOnclick(src.getOnclick());
        dest.setOndblclick(src.getOndblclick());
        dest.setOnfocus(src.getOnfocus());
        dest.setOnkeydown(src.getOnkeydown());
        dest.setOnkeypress(src.getOnkeypress());
        dest.setOnkeyup(src.getOnkeyup());
        dest.setOnmousedown(src.getOnmousedown());
        dest.setOnmousemove(src.getOnmousemove());
        dest.setOnmouseout(src.getOnmouseout());
        dest.setOnmouseover(src.getOnmouseover());
        dest.setOnmouseup(src.getOnmouseup());
        dest.setOnselect(src.getOnselect());
        dest.setReadonly(src.isReadonly());
        dest.setRendered(src.isRendered());
        dest.setRequired(src.isRequired());
        dest.setSize(src.getSize());
        dest.setStyle(src.getStyle());
        dest.setStyleClass(src.getStyleClass());
        dest.setTabindex(src.getTabindex());
        dest.setTitle(src.getTitle());
        dest.setValidator(src.getValidator());
    }

    private HtmlInputText getOrCreateInputTextChild(THtmlCalendar htmlCalendar,
            Application application) {
        HtmlInputText inputText = null;

        List li = htmlCalendar.getChildren();

        for (int i = 0; i < li.size(); i++) {
            UIComponent uiComponent = (UIComponent) li.get(i);

            if (uiComponent instanceof HtmlInputText) {
                inputText = (HtmlInputText) uiComponent;
                break;
            }
        }

        if (inputText == null) {
            inputText = (HtmlInputText) application
                    .createComponent(HtmlInputText.COMPONENT_TYPE);
        }
        return inputText;
    }

    /**
     * Used by the x:inputDate renderer : HTMLDateRenderer
     * @throws IOException
     */
    static public void addScriptAndCSSResources(FacesContext facesContext,
            UIComponent uiComponent) throws IOException {
        // check to see if javascript has already been written (which could happen if more than one calendar on the same page)
        if (facesContext.getExternalContext().getRequestMap().containsKey(
                JAVASCRIPT_ENCODED2)) {
            return;
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(JsfConstants.SCRIPT_ELEM, uiComponent);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_JAVASCRIPT_VALUE, null);
        StringBuffer script = new StringBuffer();
        script.append("loadPopupScript();");
        appendImageDirectory(script, facesContext);
        writer.writeText(script.toString(), null);
        writer.endElement(JsfConstants.SCRIPT_ELEM);

        facesContext.getExternalContext().getRequestMap().put(
                JAVASCRIPT_ENCODED2, Boolean.TRUE);
    }

    private static void appendImageDirectory(StringBuffer script,
            FacesContext facesContext) {
        script.append("jscalendarSetImageDirectory('");
        script.append(VirtualResource.convertVirtualPath(facesContext,
                RESOURCE_ROOT + "images/"));
        script.append("');");
    }

    public static String getLocalizedLanguageScript(DateFormatSymbols symbols,
            String[] months, int firstDayOfWeek, UIComponent uiComponent) {
        int realFirstDayOfWeek = firstDayOfWeek - 1/*Java has different starting-point*/;

        String[] weekDays;

        if (realFirstDayOfWeek == 0) {
            weekDays = DateFormatSymbolsUtil
                    .getWeekdaysStartingWithSunday(symbols);
        } else if (realFirstDayOfWeek == 1) {
            weekDays = DateFormatSymbolsUtil.getWeekdays(symbols);
        } else
            throw new IllegalStateException(
                    "Week may only start with sunday or monday.");

        StringBuffer script = new StringBuffer();
        defineStringArray(script, "jscalendarMonthName", months);
        defineStringArray(script, "jscalendarMonthName2", months);
        defineStringArray(script, "jscalendarDayName", weekDays);
        setIntegerVariable(script, "jscalendarStartAt", realFirstDayOfWeek);

        if (uiComponent instanceof THtmlCalendar) {

            THtmlCalendar inputCalendar = (THtmlCalendar) uiComponent;

            if (inputCalendar.getPopupGotoString() != null)
                setStringVariable(script, "jscalendarGotoString", inputCalendar
                        .getPopupGotoString());
            if (inputCalendar.getPopupTodayString() != null)
                setStringVariable(script, "jscalendarTodayString",
                        inputCalendar.getPopupTodayString());
            if (inputCalendar.getPopupWeekString() != null)
                setStringVariable(script, "jscalendarWeekString", inputCalendar
                        .getPopupWeekString());
            if (inputCalendar.getPopupScrollLeftMessage() != null)
                setStringVariable(script, "jscalendarScrollLeftMessage",
                        inputCalendar.getPopupScrollLeftMessage());
            if (inputCalendar.getPopupScrollRightMessage() != null)
                setStringVariable(script, "jscalendarScrollRightMessage",
                        inputCalendar.getPopupScrollRightMessage());
            if (inputCalendar.getPopupSelectMonthMessage() != null)
                setStringVariable(script, "jscalendarSelectMonthMessage",
                        inputCalendar.getPopupSelectMonthMessage());
            if (inputCalendar.getPopupSelectYearMessage() != null)
                setStringVariable(script, "jscalendarSelectYearMessage",
                        inputCalendar.getPopupSelectYearMessage());
            if (inputCalendar.getPopupSelectDateMessage() != null)
                setStringVariable(script, "jscalendarSelectDateMessage",
                        inputCalendar.getPopupSelectDateMessage());
        }

        return script.toString();
    }

    private static void setIntegerVariable(StringBuffer script, String name,
            int value) {
        script.append(name);
        script.append(" = ");
        script.append(value);
        script.append(";\n");
    }

    private static void setStringVariable(StringBuffer script, String name,
            String value) {
        script.append(name);
        script.append(" = \"");
        script.append(value);
        script.append("\";\n");
    }

    private static void defineStringArray(StringBuffer script,
            String arrayName, String[] array) {
        script.append(arrayName);
        script.append(" = new Array(");

        for (int i = 0; i < array.length; i++) {
            if (i != 0)
                script.append(",");

            script.append("\"");
            script.append(array[i]);
            script.append("\"");
        }

        script.append(");");
    }

    private String getScriptBtn(FacesContext facesContext,
            UIComponent uiComponent, String dateFormat, String popupButtonString)
            throws IOException {
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(new StringWriter());
        THtmlCalendar calendar = (THtmlCalendar) uiComponent;
        boolean renderButtonAsImage = calendar.isRenderPopupButtonAsImage();

        writer.write("if (!document.layers) {\n");
        writer.write("document.write('");

        if (!renderButtonAsImage) {
            // render the button
            writer.startElement(JsfConstants.INPUT_ELEM, uiComponent);
            writer.writeAttribute(JsfConstants.TYPE_ATTR,
                    JsfConstants.BUTTON_VALUE, null);

            writeOnclickJsCalendarFunctionCall(writer, facesContext,
                    uiComponent, dateFormat);

            if (popupButtonString == null)
                popupButtonString = "...";
            writer.writeAttribute(JsfConstants.VALUE_ATTR, popupButtonString,
                    null);
            writer.endElement(JsfConstants.INPUT_ELEM);
        } else {
            // render the image
            writer.startElement(JsfConstants.IMG_ELEM, uiComponent);
            writer.writeAttribute(JsfConstants.SRC_ATTR, VirtualResource
                    .convertVirtualPath(facesContext, RESOURCE_ROOT
                            + "images/calendar.gif"), null);
            writer.writeAttribute(JsfConstants.STYLE_ATTR,
                    "vertical-align:bottom;", null);

            writeOnclickJsCalendarFunctionCall(writer, facesContext,
                    uiComponent, dateFormat);

            writer.endElement(JsfConstants.IMG_ELEM);
        }

        writer.write("');");
        writer.write("\n}");

        return writer.toString();
    }

    private void writeOnclickJsCalendarFunctionCall(ResponseWriter writer,
            FacesContext facesContext, UIComponent uiComponent,
            String dateFormat) throws IOException {
        String clientId = uiComponent.getClientId(facesContext);

        String jsCalendarFunctionCall = "jscalendarPopUpCalendar(this,document.getElementById(\\'"
                + clientId + "\\'),\\'" + dateFormat + "\\')";
        writer.writeAttribute(JsfConstants.ONCLICK_ATTR,
                jsCalendarFunctionCall, null);
    }

    private void writeMonthYearHeader(FacesContext facesContext,
            ResponseWriter writer, UIInput inputComponent, Calendar timeKeeper,
            int currentDay, String[] weekdays, String[] months)
            throws IOException {
        Calendar cal = shiftMonth(facesContext, timeKeeper, currentDay, -1);

        writeCell(facesContext, writer, inputComponent, "<", cal.getTime(),
                null);

        writer.startElement(JsfConstants.TD_ELEM, inputComponent);
        writer.writeAttribute(JsfConstants.COLSPAN_ATTR, new Integer(
                weekdays.length - 2), null);
        writer.writeText(months[timeKeeper.get(Calendar.MONTH)] + " "
                + timeKeeper.get(Calendar.YEAR), null);
        writer.endElement(JsfConstants.TD_ELEM);

        cal = shiftMonth(facesContext, timeKeeper, currentDay, 1);

        writeCell(facesContext, writer, inputComponent, ">", cal.getTime(),
                null);
    }

    private Calendar shiftMonth(FacesContext facesContext, Calendar timeKeeper,
            int currentDay, int shift) {
        Calendar cal = copyCalendar(facesContext, timeKeeper);

        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + shift);

        if (currentDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            currentDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, currentDay);
        return cal;
    }

    private Calendar copyCalendar(FacesContext facesContext, Calendar timeKeeper) {
        Calendar cal = Calendar.getInstance(facesContext.getViewRoot()
                .getLocale());
        cal.set(Calendar.YEAR, timeKeeper.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, timeKeeper.get(Calendar.MONTH));
        cal.set(Calendar.HOUR_OF_DAY, timeKeeper.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, timeKeeper.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, timeKeeper.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, timeKeeper.get(Calendar.MILLISECOND));
        return cal;
    }

    private void writeWeekDayNameHeader(int weekStartsAtDayIndex,
            String[] weekdays, FacesContext facesContext,
            ResponseWriter writer, UIInput inputComponent) throws IOException {
        for (int i = weekStartsAtDayIndex; i < weekdays.length; i++)
            writeCell(facesContext, writer, inputComponent, weekdays[i], null,
                    null);

        for (int i = 0; i < weekStartsAtDayIndex; i++)
            writeCell(facesContext, writer, inputComponent, weekdays[i], null,
                    null);
    }

    private void writeDays(FacesContext facesContext, ResponseWriter writer,
            THtmlCalendar inputComponent, Calendar timeKeeper, int currentDay,
            int weekStartsAtDayIndex, int weekDayOfFirstDayOfMonth,
            int lastDayInMonth, String[] weekdays) throws IOException {
        Calendar cal;

        int space = (weekStartsAtDayIndex < weekDayOfFirstDayOfMonth) ? (weekDayOfFirstDayOfMonth - weekStartsAtDayIndex)
                : (weekdays.length - weekStartsAtDayIndex + weekDayOfFirstDayOfMonth);

        if (space == weekdays.length)
            space = 0;

        int columnIndexCounter = 0;

        for (int i = 0; i < space; i++) {
            if (columnIndexCounter == 0) {
                writer.startElement(JsfConstants.TR_ELEM, inputComponent);
            }

            writeCell(facesContext, writer, inputComponent, "", null,
                    inputComponent.getDayCellClass());
            columnIndexCounter++;
        }

        for (int i = 0; i < lastDayInMonth; i++) {
            if (columnIndexCounter == 0) {
                writer.startElement(JsfConstants.TR_ELEM, inputComponent);
            }

            cal = copyCalendar(facesContext, timeKeeper);
            cal.set(Calendar.DAY_OF_MONTH, i + 1);

            String cellStyle = inputComponent.getDayCellClass();

            if ((currentDay - 1) == i)
                cellStyle = inputComponent.getCurrentDayCellClass();

            writeCell(facesContext, writer, inputComponent, String
                    .valueOf(i + 1), cal.getTime(), cellStyle);

            columnIndexCounter++;

            if (columnIndexCounter == weekdays.length) {
                writer.endElement(JsfConstants.TR_ELEM);
                writer.write(JsfConstants.LINE_SP);
                columnIndexCounter = 0;
            }
        }

        if (columnIndexCounter != 0) {
            for (int i = columnIndexCounter; i < weekdays.length; i++) {
                writeCell(facesContext, writer, inputComponent, "", null,
                        inputComponent.getDayCellClass());
            }

            writer.endElement(JsfConstants.TR_ELEM);
            writer.write(JsfConstants.LINE_SP);
        }
    }

    private void writeCell(FacesContext facesContext, ResponseWriter writer,
            UIInput component, String content, Date valueForLink,
            String styleClass) throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, component);

        if (styleClass != null)
            writer.writeAttribute(JsfConstants.CLASS_ATTR, styleClass, null);

        if (valueForLink == null)
            writer.writeText(content, JsfConstants.VALUE_ATTR);
        else {
            writeLink(content, component, facesContext, valueForLink);
        }

        writer.endElement(JsfConstants.TD_ELEM);
    }

    private void writeLink(String content, UIInput component,
            FacesContext facesContext, Date valueForLink) throws IOException {
        Converter converter = getConverter(component);

        Application application = facesContext.getApplication();
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
        parameter.setName(component.getClientId(facesContext));
        parameter.setValue(converter.getAsString(facesContext, component,
                valueForLink));

        THtmlCalendar calendar = (THtmlCalendar) component;
        if (calendar.isDisabled() || calendar.isReadonly()) {
            component.getChildren().add(text);

            RendererUtil.renderChild(facesContext, text);
        } else {
            component.getChildren().add(link);
            link.getChildren().add(parameter);
            link.getChildren().add(text);

            RendererUtil.renderChild(facesContext, link);
        }
    }

    private Converter getConverter(UIInput component) {
        Converter converter = component.getConverter();

        if (converter == null) {
            converter = new CalendarDateTimeConverter();
        }
        return converter;
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
        this.getDecoder().decode(context, component);
    }

    public Object getConvertedValue(FacesContext facesContext,
            UIComponent uiComponent, Object submittedValue)
            throws ConverterException {

        UIInput uiInput = (UIInput) uiComponent;

        Converter converter = uiInput.getConverter();

        if (converter == null)
            converter = new CalendarDateTimeConverter();

        if (submittedValue != null && !(submittedValue instanceof String)) {
            throw new IllegalArgumentException(
                    "Submitted value of type String expected");
        }

        return converter.getAsObject(facesContext, uiComponent,
                (String) submittedValue);
    }

    public interface DateConverter extends Converter {
    }

    public static class CalendarDateTimeConverter implements DateConverter {
        private static final String CONVERSION_MESSAGE_ID = "org.apache.myfaces.calendar.CONVERSION";

        public Object getAsObject(FacesContext facesContext,
                UIComponent uiComponent, String s) {
            if (s == null || s.trim().length() == 0)
                return null;

            DateFormat dateFormat = null;

            if (uiComponent instanceof THtmlCalendar
                    && ((THtmlCalendar) uiComponent).isRenderAsPopup()) {
                String popupDateFormat = ((THtmlCalendar) uiComponent)
                        .getPopupDateFormat();

                dateFormat = new SimpleDateFormat(createJSPopupFormat(
                        facesContext, popupDateFormat));
            } else {
                dateFormat = createStandardDateFormat(facesContext);
            }

            try {
                return dateFormat.parse(s);
            } catch (ParseException e) {
                FacesMessage msg = FacesMessageUtil.getMessage(facesContext,
                        CONVERSION_MESSAGE_ID, new Object[] {
                                uiComponent.getId(), s });
                throw new ConverterException(msg, e);
            }
        }

        public static String createJSPopupFormat(FacesContext facesContext,
                String popupDateFormat) {

            if (popupDateFormat == null) {
                SimpleDateFormat defaultDateFormat = createStandardDateFormat(facesContext);
                popupDateFormat = defaultDateFormat.toPattern();
            }

            StringBuffer jsPopupDateFormat = new StringBuffer();

            for (int i = 0; i < popupDateFormat.length(); i++) {
                char c = popupDateFormat.charAt(i);

                if (c == 'M' || c == 'd' || c == 'y' || c == ' ' || c == '.'
                        || c == '/' || c == '-')
                    jsPopupDateFormat.append(c);
            }
            return jsPopupDateFormat.toString().trim();
        }

        public String getAsString(FacesContext facesContext,
                UIComponent uiComponent, Object o) {
            Date date = (Date) o;

            if (date == null)
                return null;

            DateFormat dateFormat = null;

            if (uiComponent instanceof THtmlCalendar
                    && ((THtmlCalendar) uiComponent).isRenderAsPopup()) {
                String popupDateFormat = ((THtmlCalendar) uiComponent)
                        .getPopupDateFormat();

                dateFormat = new SimpleDateFormat(createJSPopupFormat(
                        facesContext, popupDateFormat));
            } else {
                dateFormat = createStandardDateFormat(facesContext);
            }

            return dateFormat.format(date);
        }

        private static SimpleDateFormat createStandardDateFormat(
                FacesContext facesContext) {
            DateFormat dateFormat;
            dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                    DateFormat.SHORT, facesContext.getViewRoot().getLocale());

            if (dateFormat instanceof SimpleDateFormat)
                return (SimpleDateFormat) dateFormat;
            else
                return new SimpleDateFormat("dd.MM.yyyy");
        }

    }
}
