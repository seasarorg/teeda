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
package org.seasar.teeda.extension.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.AbstractConverterTestCase;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.NullUIComponent;

/**
 * @author shot
 * @author yone
 */
public class TDateTimeConverterTest extends AbstractConverterTestCase {

    private Locale defaultLocale;

    private TimeZone defaultTimeZone;

    protected void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
        defaultTimeZone = TimeZone.getDefault();
    }

    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
        TimeZone.setDefault(defaultTimeZone);
        super.tearDown();
    }

    public void testGetAsObject1() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);

        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "2005/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget(pattern, defaultLocale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject1_2() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);

        final String pattern = "yyyy/MM/dd HH:mm:ss";
        converter.setPattern(pattern);

        String dateValue = "2005/08/01 11:12:13";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget(pattern, defaultLocale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject2() throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        final Locale locale = Locale.JAPANESE;
        converter.setLocale(locale);
        converter.setTimeZone(TimeZone.getTimeZone("JST"));
        converter.setThreshold(new Integer(69));

        final String dateValue = "2005/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);
        Date dateTarget = createDateTarget("yyyy/MM/dd", locale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject3() throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = new TDateTimeConverter();

        UIViewRoot viewRoot = new UIViewRoot();
        final Locale locale = Locale.US;
        viewRoot.setLocale(locale);
        context.setViewRoot(viewRoot);

        TimeZone.setDefault(converter.getTimeZone());

        String pattern = "M/d/yy";
        converter.setPattern(pattern);
        converter.setThreshold(new Integer(69));

        String dateValue = "8/3/05";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);
        Date dateTarget = createDateTarget(pattern, locale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject4() throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = new TDateTimeConverter();
        converter.setTimeZone(TimeZone.getDefault());
        converter.setThreshold(new Integer(69));

        UIViewRoot viewRoot = new UIViewRoot();
        Locale locale = Locale.GERMAN;
        viewRoot.setLocale(locale);
        context.setViewRoot(viewRoot);

        converter.setDateStyle("medium");

        converter.setType("date");

        String dateValue = "04.08.2005";
        Object o3 = converter.getAsObject(context, new NullUIComponent(),
                dateValue);

        SimpleDateFormat format = (SimpleDateFormat) DateFormat
                .getDateInstance(DateFormat.MEDIUM, locale);

        Date dateTarget = format.parse(dateValue);
        assertEquals((Date) o3, dateTarget);
    }

    public void testGetAsObject5() throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = new TDateTimeConverter();

        converter.setTimeZone(TimeZone.getDefault());
        converter.setPattern("yyyy/MM/dd");
        converter.setLocale(defaultLocale);
        converter.setThreshold(new Integer(69));
        try {
            converter.getAsObject(context, new MockUIComponent(), "200508/04");
            fail();
        } catch (ConverterException e) {
            // TODO somehow need catching ConverterException
            assertTrue(true);
        }
    }

    public void testGetAsObject6() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.JAPANESE);
        converter.setTimeZone(defaultTimeZone);
        converter.setThreshold(new Integer(69));
        final String pattern = "yy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "26/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget("yyyy/MM/dd", defaultLocale,
                "2026/08/01");
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject7() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.JAPANESE);
        converter.setTimeZone(defaultTimeZone);
        converter.setThreshold(new Integer(69));
        final String pattern = "yy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "69/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget("yyyy/MM/dd", defaultLocale,
                "1969/08/01");
        assertEquals(dateTarget, date);
    }

    public void testGetAsObject8() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.JAPANESE);
        converter.setTimeZone(defaultTimeZone);
        converter.setThreshold(new Integer(69));
        final String pattern = "yy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "70/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget(pattern, Locale.JAPANESE,
                "1970/08/01");
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject9() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);
        converter.setThreshold(new Integer(69));
        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "2170/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget(pattern, defaultLocale, "2170/08/01");
        assertEquals(dateTarget, date);
    }

    public void testGetAsObject11() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.JAPANESE);
        converter.setTimeZone(defaultTimeZone);
        converter.setThreshold(new Integer(69));
        final String pattern = "yy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "27/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget("yy/MM/dd", Locale.JAPANESE,
                "2027/08/01");
        System.out.println(date);
        System.out.println(dateTarget);
        assertEquals(dateTarget, date);
    }

    public void testGetAsObject_noDelimIsPermittedOnlyWithYYYY()
            throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = new TDateTimeConverter();

        converter.setTimeZone(TimeZone.getDefault());
        converter.setPattern("yyyyMMdd");
        converter.setLocale(defaultLocale);
        converter.setThreshold(new Integer(69));
        try {
            Object o = converter.getAsObject(context, new MockUIComponent(),
                    "20050804");
            System.out.println(o);
            assertTrue(true);
        } catch (ConverterException e) {
            fail();
        }
    }

    public void testGetAsObject_noDelimIsNotPermitted() throws Exception {
        FacesContext context = getFacesContext();
        TDateTimeConverter converter = new TDateTimeConverter();

        converter.setTimeZone(TimeZone.getDefault());
        converter.setPattern("yyMMdd");
        converter.setLocale(defaultLocale);
        converter.setThreshold(new Integer(69));
        try {
            converter.getAsObject(context, new MockUIComponent(), "20050804");
            fail();
        } catch (ConverterException e) {
            // TODO somehow need catching ConverterException
            assertTrue(true);
        }
    }

    public void testGetAsString() {
        Converter converter = createConverter();
        FacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();

        String value = converter.getAsString(context, component, null);

        assertEquals(value, "");

        TDateTimeConverter converter2 = new TDateTimeConverter();
        String value2 = converter2.getAsString(context, component, "a");

        assertEquals(value2, "a");

        TDateTimeConverter converter3 = new TDateTimeConverter();
        Locale defaultLocale = Locale.getDefault();
        converter3.setLocale(defaultLocale);

        String pattern3 = "yy/MM/dd";
        converter3.setPattern(pattern3);

        converter3.setTimeZone(TimeZone.getDefault());

        Date date3 = new Date();
        String value3 = converter3.getAsString(context, component, date3);

        String val = createDateFormat(pattern3, defaultLocale, date3);
        assertEquals(value3, val);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date3);
        assertEquals(val, converter3.getAsString(context, component, cal));

        TDateTimeConverter converter4 = new TDateTimeConverter();
        Locale usLocale = Locale.US;
        converter4.setLocale(usLocale);
        converter4.setTimeZone(TimeZone.getDefault());

        Date date4 = new Date();
        String value4 = converter4.getAsString(context, component, date4);

        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat
                .getDateInstance(3, usLocale);
        String val4 = formatter.format(date4);

        assertNotSame(value4, val4);
    }

    public void testIsTransient() {
        TDateTimeConverter converter = new TDateTimeConverter();
        converter.setTransient(true);
        assertTrue(converter.isTransient());
    }

    public void testSetTransient() {

        TDateTimeConverter converter = new TDateTimeConverter();
        converter.setTransient(true);
        assertTrue(converter.isTransient());
    }

    public void testSaveState() {
        TDateTimeConverter converter = new TDateTimeConverter();
        MockFacesContext context = getFacesContext();

        String dateStyle = "full";
        Locale locale = Locale.getDefault();
        String pattern = "yy.MM/dd";
        String timeStyle = "medium";
        TimeZone timeZone = TimeZone.getDefault();
        String type = "date";

        converter.setDateStyle(dateStyle);
        converter.setLocale(locale);
        converter.setPattern(pattern);
        converter.setTimeStyle(timeStyle);
        converter.setTimeZone(timeZone);
        converter.setType(type);
        converter.setThreshold(new Integer(1));

        Object o = converter.saveState(context);
        assertTrue(o instanceof Object[]);

        Object[] values = (Object[]) o;

        assertEquals(new Integer(1), values[1]);

        converter.restoreState(context, values);
        assertEquals(new Integer(1), converter.getThreshold());

    }

    public void testGetLocale() {
        TDateTimeConverter converter = new TDateTimeConverter();
        Locale locale = Locale.getDefault();

        converter.setLocale(locale);
        assertEquals(locale, converter.getLocale());

    }

    public void testConvertTargetPointed() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);
        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");

        String dateValue = "2005/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Date dateTarget = createDateTarget(pattern, defaultLocale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testConvertTargetPointed2() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);
        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);
        converter.setTarget("aaa");

        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");

        String dateValue = "AAA";
        try {
            converter.getAsObject(context, new MockUIComponent(), dateValue);
            fail();
        } catch (ConverterException expected) {
            assertNotNull(expected);
        }
    }

    public void testConvertTargetNotPointed() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);
        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");

        String dateValue = "2005/08/01";
        Object o = converter.getAsObject(context, new NullUIComponent(),
                dateValue);
        assertEquals(dateValue, o);
    }

    public void testConvertTargetNotPointed2() throws Exception {
        TDateTimeConverter converter = (TDateTimeConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(defaultLocale);
        converter.setTimeZone(defaultTimeZone);
        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");

        String dateValue = "AAA";
        Object o = converter.getAsObject(context, new MockUIComponent(),
                dateValue);

        assertEquals(dateValue, o);
    }

    private Date createDateTarget(String pattern, Locale locale, String date)
            throws ParseException {
        DateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.parse(date);
    }

    private static String createDateFormat(String pattern, Locale locale,
            Object date) {
        DateFormat formatter = new SimpleDateFormat(pattern, locale);
        try {
            return formatter.format(date);
        } catch (Exception e) {
            fail();
        }
        return null;
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.DateTime", TDateTimeConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createDateTimeConverter();
    }

    private TDateTimeConverter createDateTimeConverter() {
        return new TDateTimeConverter();
    }
}
