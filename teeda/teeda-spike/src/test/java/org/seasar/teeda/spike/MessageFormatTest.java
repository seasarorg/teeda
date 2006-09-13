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
package org.seasar.teeda.spike;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class MessageFormatTest extends TestCase {

    // messageにフォーマットを指定すれば、Doubleは丸まらない
    public void testDouble_Format() throws Exception {
        final String message = "{0,number,00.0000}";
        final Locale locale = Locale.getDefault();
        final Object[] args = { new Double(99.9999) };
        final String format = new MessageFormat(message, locale).format(args);
        assertEquals("99.9999", format);
    }

    // messageにフォーマットを指定しないと、文字列が丸まってしまう。
    public void testDouble_NoFormat() throws Exception {
        final Locale locale = Locale.getDefault();
        final Object[] args = { new Double(99.9999) };
        final String message = "{0}";
        final String format = new MessageFormat(message, locale).format(args);
        assertEquals("100", format);
    }

    // 入力をBigDecimalにしたところで、やはり丸まってしまう
    public void testBigDecimal_NoFormat() throws Exception {
        final Locale locale = Locale.getDefault();
        final Object[] args = { new BigDecimal("99.9999") };
        final String message = "{0}";
        final String format = new MessageFormat(message, locale).format(args);
        assertEquals("100", format);
    }

    public void testToString() throws Exception {
        assertEquals("1.0E19", new Double(9999999999999999999.999999999999999)
                .toString());
        assertEquals("9999999999999999999.999999999999999", new BigDecimal(
                "9999999999999999999.999999999999999").toString());
    }

    public void test1() throws Exception {
        {
            final DecimalFormat decimalFormat = new DecimalFormat("###.###");
            final Number parsed = decimalFormat.parse("123.456");
            System.out.println(parsed);
            System.out.println(parsed.getClass());
        }
        {
            final DecimalFormat decimalFormat = new DecimalFormat("##.000");
            final Number parsed = decimalFormat.parse("123.4567");
            System.out.println(parsed);
            System.out.println(parsed.getClass());
        }
    }

}
