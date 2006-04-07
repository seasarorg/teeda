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
package javax.faces.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.NullUIComponent;

/**
 * @author shot
 */
public class DateTimeConverterTest extends AbstractConverterTestCase {

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
        DateTimeConverter converter = (DateTimeConverter) createConverter();
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

    public void testGetAsObject2() throws Exception {
        FacesContext context = getFacesContext();
        DateTimeConverter converter = (DateTimeConverter) createConverter();
        final Locale locale = Locale.JAPANESE;
        converter.setLocale(locale);
        converter.setTimeZone(TimeZone.getTimeZone("JST"));

        final String dateValue = "2005/08/01";
        Date date = (Date) converter.getAsObject(context,
                new NullUIComponent(), dateValue);
        Date dateTarget = createDateTarget("yyyy/MM/dd", locale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject3() throws Exception {
        FacesContext context = getFacesContext();
        DateTimeConverter converter = new DateTimeConverter();

        UIViewRoot viewRoot = new UIViewRoot();
        final Locale locale = Locale.US;
        viewRoot.setLocale(locale);
        context.setViewRoot(viewRoot);

        TimeZone.setDefault(converter.getTimeZone());

        String pattern = "M/d/yy";
        converter.setPattern(pattern);

        String dateValue = "8/3/05";
        Date date = (Date) converter.getAsObject(context, new NullUIComponent(),
                dateValue);
        Date dateTarget = createDateTarget(pattern, locale, dateValue);
        assertEquals(date, dateTarget);
    }

    public void testGetAsObject4() throws Exception {
        FacesContext context = getFacesContext();
        DateTimeConverter converter = new DateTimeConverter();
        converter.setTimeZone(TimeZone.getDefault());

        UIViewRoot viewRoot = new UIViewRoot();
        Locale locale = Locale.GERMAN;
        viewRoot.setLocale(locale);
        context.setViewRoot(viewRoot);

        String style = DateTimeConverter.STYLE_MEDIUM;
        converter.setDateStyle(style);

        String type = DateTimeConverter.TYPE_DATE;
        converter.setType(type);

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
        DateTimeConverter converter = new DateTimeConverter();

        converter.setTimeZone(TimeZone.getDefault());
        converter.setPattern("yyyy/MM/dd");
        converter.setLocale(defaultLocale);
        try {
            converter.getAsObject(context, new MockUIComponent(), "200508/04");
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

        DateTimeConverter converter2 = new DateTimeConverter();
        String value2 = converter2.getAsString(context, component, "a");

        assertEquals(value2, "a");

        DateTimeConverter converter3 = new DateTimeConverter();
        Locale defaultLocale = Locale.getDefault();
        converter3.setLocale(defaultLocale);

        String pattern3 = "yy/MM/dd";
        converter3.setPattern(pattern3);

        converter3.setTimeZone(TimeZone.getDefault());

        Date date3 = new Date();
        String value3 = converter3.getAsString(context, component, date3);

        String val = createDateFormat(pattern3, defaultLocale, date3);
        assertEquals(value3, val);

        DateTimeConverter converter4 = new DateTimeConverter();
        Locale usLocale = Locale.US;
        converter4.setLocale(usLocale);
        converter4.setTimeZone(TimeZone.getDefault());

        Date date4 = new Date();
        String value4 = converter4.getAsString(context, component, date4);

        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat
                .getDateInstance(3, usLocale);
        String val4 = formatter.format(date4);

        assertNotSame(value4, val4);
        System.out.println(value4);
        System.out.println(val4);

    }

    public void testIsTransient() {
        DateTimeConverter converter = new DateTimeConverter();
        converter.setTransient(true);
        assertTrue(converter.isTransient());
    }

    public void testSetTransient() {

        DateTimeConverter converter = new DateTimeConverter();
        converter.setTransient(true);
        assertTrue(converter.isTransient());
    }

    public void testSaveState() {
        DateTimeConverter converter = new DateTimeConverter();
        MockFacesContext context = getFacesContext();

        String dateStyle = DateTimeConverter.STYLE_FULL;
        Locale locale = Locale.getDefault();
        String pattern = "yy.MM/dd";
        String timeStyle = DateTimeConverter.STYLE_MEDIUM;
        TimeZone timeZone = TimeZone.getDefault();
        String type = DateTimeConverter.TYPE_DATE;

        converter.setDateStyle(dateStyle);
        converter.setLocale(locale);
        converter.setPattern(pattern);
        converter.setTimeStyle(timeStyle);
        converter.setTimeZone(timeZone);
        converter.setType(type);

        Object o = converter.saveState(context);
        assertTrue(o instanceof Object[]);

        Object[] values = (Object[]) o;

        assertEquals(values[0], dateStyle);

    }

    public void testRestoreState() {
        DateTimeConverter converter = new DateTimeConverter();
        MockFacesContext context = getFacesContext();

        String dateStyle = DateTimeConverter.STYLE_FULL;
        Locale locale = Locale.getDefault();
        String pattern = "yy.MM/dd";
        String timeStyle = DateTimeConverter.STYLE_MEDIUM;
        TimeZone timeZone = TimeZone.getDefault();
        String type = DateTimeConverter.TYPE_DATE;

        Object[] values = new Object[] { dateStyle, locale, pattern, timeStyle,
                timeZone, type };

        converter.restoreState(context, values);

        assertEquals(converter.getLocale(), locale);

    }

    public void testGetDateStyle() {

        DateTimeConverter converter = new DateTimeConverter();
        assertEquals(DateTimeConverter.STYLE_DEFAULT, converter.getDateStyle());

        converter.setDateStyle(DateTimeConverter.STYLE_FULL);
        assertEquals(DateTimeConverter.STYLE_FULL, converter.getDateStyle());
    }

    public void testSetDateStyle() {

        DateTimeConverter converter = new DateTimeConverter();

        converter.setDateStyle(DateTimeConverter.STYLE_LONG);
        assertEquals(DateTimeConverter.STYLE_LONG, converter.getDateStyle());
    }

    public void testGetLocale() {
        DateTimeConverter converter = new DateTimeConverter();
        Locale locale = Locale.getDefault();

        converter.setLocale(locale);
        assertEquals(locale, converter.getLocale());

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
        assertEquals("javax.faces.DateTime", DateTimeConverter.CONVERTER_ID);
    }

    protected Converter createConverter() {
        return createDateTimeConverter();
    }

    private DateTimeConverter createDateTimeConverter() {
        return new DateTimeConverter();
    }

}
