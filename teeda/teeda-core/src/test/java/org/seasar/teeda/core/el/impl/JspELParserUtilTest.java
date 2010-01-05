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
package org.seasar.teeda.core.el.impl;

import junit.framework.TestCase;

import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * @author shot
 */
public class JspELParserUtilTest extends TestCase {

    public void testConvertToJspExpression0() {
        try {
            JspELParserUtil.convertToJspExpression(null);
            fail();
        } catch (EmptyRuntimeException e) {
            assertTrue(true);
        }
    }

    public void testConvertToJspExpression1() {
        assertEquals("1", "${}", JspELParserUtil.convertToJspExpression("#{}"));
    }

    public void testConvertToJspExpression2() {
        assertEquals("2", "{}", JspELParserUtil.convertToJspExpression("{}"));
    }

    public void testConvertToJspExpression3() {
        assertEquals("3", "{aa} {bb}", JspELParserUtil
                .convertToJspExpression("{aa} {bb}"));
    }

    public void testConvertToJspExpression4() {
        assertEquals("4", "{aa}{bb}", JspELParserUtil
                .convertToJspExpression("{aa}{bb}"));
    }

    public void testConvertToJspExpression5() {
        assertEquals("5", "1${'<'}{0,number,integer}", JspELParserUtil
                .convertToJspExpression("1#{'<'}{0,number,integer}"));
    }

    public void testConvertToJspExpression6() {
        assertEquals(
                "6",
                "{0,choice,0#${scrollerNoItem_}|1#${scrollerOneItem_}|1${'<'}{0,number,integer} ${scrollerItems_}} ${dataScrollerMsg.found_displaying} {1} to {2}. ${dataScrollerMsg.Page} {3} / {4}",
                JspELParserUtil
                        .convertToJspExpression("{0,choice,0##{scrollerNoItem_}|1##{scrollerOneItem_}|1#{'<'}{0,number,integer} #{scrollerItems_}} #{dataScrollerMsg.found_displaying} {1} to {2}. #{dataScrollerMsg.Page} {3} / {4}"));
    }

    public void testConvertToJspExpression7() {
        assertEquals("7", "${foo}", JspELParserUtil
                .convertToJspExpression("#{foo}"));
    }

    public void testConvertToJspExpression8() {
        assertEquals("8", "${#{foo}}", JspELParserUtil
                .convertToJspExpression("#{#{foo}}"));
    }

    public void testConvertToJspExpression9() {
        assertEquals("9", "foo${foo}bar", JspELParserUtil
                .convertToJspExpression("foo#{foo}bar"));
    }

    public void testConvertToJspExpression10() {
        assertEquals("10", "foo\\${foo}", JspELParserUtil
                .convertToJspExpression("foo\\#{foo}"));
    }

    public void testConvertToJspExpression11() {
        assertEquals("11", "foo${foo = '#{foo}'}", JspELParserUtil
                .convertToJspExpression("foo#{foo = '#{foo}'}"));
    }

    public void testConvertToJspExpression12() {
        assertEquals("12", "foo${foo = '\\'#{foo}'}", JspELParserUtil
                .convertToJspExpression("foo#{foo = '\\'#{foo}'}"));
    }

    public void testConvertToJspExpression13() {
        assertEquals("13", "foo${foo = \"\\\"#{foo}\"}", JspELParserUtil
                .convertToJspExpression("foo#{foo = \"\\\"#{foo}\"}"));
    }

    public void testConvertToJspExpression14() {
        assertEquals("14", "foo's${bar}", JspELParserUtil
                .convertToJspExpression("foo's#{bar}"));
    }

    public void testConvertToJspExpression15() {
        assertEquals("15", "foo${bar}${baz}", JspELParserUtil
                .convertToJspExpression("foo#{bar}#{baz}"));
    }

    public void testConvertToJspExpression16() {
        assertEquals("16", "foo${bar}{baz}", JspELParserUtil
                .convertToJspExpression("foo#{bar}{baz}"));
    }

    public void testConvertToJspExpression17() {
        assertEquals("17", "foo${'}'}", JspELParserUtil
                .convertToJspExpression("foo#{'}'}"));
    }

    public void testConvertToJspExpression18() {
        assertEquals("18", "foo${bar'}", JspELParserUtil
                .convertToJspExpression("foo#{bar'}"));
    }

    public void testConvertToJspExpression19() {
        assertEquals("19", "${bar == null}", JspELParserUtil
                .convertToJspExpression("#{bar == null}"));
    }

    public void testConvertToJspExpression20() {
        assertEquals("20", "'${hoge}'", JspELParserUtil
                .convertToJspExpression("'#{hoge}'"));
    }

    public void testConvertToJspExpression21() {
        assertEquals("21", "'${'hoge'}'", JspELParserUtil
                .convertToJspExpression("'#{'hoge'}'"));
    }
}
