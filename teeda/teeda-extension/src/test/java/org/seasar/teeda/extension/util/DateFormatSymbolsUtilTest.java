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
package org.seasar.teeda.extension.util;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author higa
 */
public class DateFormatSymbolsUtilTest extends TestCase {

    private DateFormatSymbols symbols = new DateFormatSymbols(Locale.JAPAN);

    public void testGetWeekdays() throws Exception {
        String[] weekdays = DateFormatSymbolsUtil.getWeekdays(symbols);
        assertEquals(7, weekdays.length);
        System.out.println(Arrays.toString(weekdays));
    }

    public void testGetMonths() throws Exception {
        String[] months = DateFormatSymbolsUtil.getMonths(symbols);
        assertEquals(12, months.length);
        System.out.println(Arrays.toString(months));
    }
}