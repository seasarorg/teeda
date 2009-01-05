/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.convert;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class TimestampConverterTest extends TeedaTestCase {

    public void testGetAsObject() throws Exception {
        TimestampConverter converter = (TimestampConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.getDefault());
        converter.setTimeZone(TimeZone.getDefault());

        final String pattern = "yyyy/MM/dd";
        converter.setPattern(pattern);

        String dateValue = "2006/06/06";
        Object o = converter.getAsObject(context, new NullUIComponent(),
                dateValue);

        assertNotNull(o);
        assertTrue(o instanceof Timestamp);
    }

    public void testGetAsObject2() throws Exception {
        TimestampConverter converter = (TimestampConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.getDefault());
        converter.setTimeZone(TimeZone.getDefault());

        final String pattern = "yyyy/MM/dd hh:mm:ss";
        converter.setPattern(pattern);

        String dateValue = "2006/06/06 12:45:50";
        Timestamp timeStamp = (Timestamp) converter.getAsObject(context,
                new NullUIComponent(), dateValue);

        Timestamp timestampTarget = createTimestampTarget(pattern, Locale
                .getDefault(), dateValue);
        assertEquals(timeStamp, timestampTarget);
    }

    public void testGetAsObject3() throws Exception {
        TimestampConverter converter = (TimestampConverter) createConverter();
        FacesContext context = getFacesContext();
        converter.setLocale(Locale.getDefault());
        converter.setTimeZone(TimeZone.getDefault());

        final String pattern = "yyyy/MM/dd hh:mm:ss";
        converter.setPattern(pattern);

        String dateValue = null;
        assertNull(converter.getAsObject(context, new NullUIComponent(),
                dateValue));
    }

    private Timestamp createTimestampTarget(String pattern, Locale locale,
            String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(pattern, locale);
        return new Timestamp(formatter.parse(date).getTime());
    }

    protected Converter createConverter() {
        return createTimestampConverter();
    }

    private TimestampConverter createTimestampConverter() {
        return new TimestampConverter();
    }

}