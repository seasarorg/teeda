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
package org.seasar.teeda.core.el.impl;

import junit.framework.TestCase;

import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * @author shot
 */
public class JspELParserUtilTest extends TestCase {

    public void testConvertToJspExpression() {
        try {
            JspELParserUtil.convertToJspExpression(null);
            fail();
        } catch (EmptyRuntimeException e) {
            assertTrue(true);
        }
        assertEquals("1", "${}", JspELParserUtil.convertToJspExpression("#{}"));
        assertEquals("2", "{}", JspELParserUtil.convertToJspExpression("{}"));
        assertEquals("3", "{aa} {bb}", JspELParserUtil
                .convertToJspExpression("{aa} {bb}"));
        assertEquals("4", "{aa}{bb}", JspELParserUtil
                .convertToJspExpression("{aa}{bb}"));
        assertEquals("5", "1${'<'}{0,number,integer}", JspELParserUtil
                .convertToJspExpression("1#{'<'}{0,number,integer}"));
        assertEquals(
                "6",
                "{0,choice,0#${scrollerNoItem_}|1#${scrollerOneItem_}|1${'<'}{0,number,integer} ${scrollerItems_}} ${dataScrollerMsg.found_displaying} {1} to {2}. ${dataScrollerMsg.Page} {3} / {4}",
                JspELParserUtil
                        .convertToJspExpression("{0,choice,0##{scrollerNoItem_}|1##{scrollerOneItem_}|1#{'<'}{0,number,integer} #{scrollerItems_}} #{dataScrollerMsg.found_displaying} {1} to {2}. #{dataScrollerMsg.Page} {3} / {4}"));
        assertEquals("7", "${foo}", JspELParserUtil
                .convertToJspExpression("#{foo}"));
        assertEquals("8", "${#{foo}}", JspELParserUtil
                .convertToJspExpression("#{#{foo}}"));
        assertEquals("9", "foo${foo}bar", JspELParserUtil
                .convertToJspExpression("foo#{foo}bar"));
        assertEquals("10", "foo\\#{foo}", JspELParserUtil
                .convertToJspExpression("foo\\#{foo}"));
        assertEquals("11", "foo${foo = '#{foo}'}", JspELParserUtil
                .convertToJspExpression("foo#{foo = '#{foo}'}"));
        assertEquals("12", "foo${foo = '\\'#{foo}'}", JspELParserUtil
                .convertToJspExpression("foo#{foo = '\\'#{foo}'}"));
        assertEquals("13", "foo${foo = \"\\\"#{foo}\"}", JspELParserUtil
                .convertToJspExpression("foo#{foo = \"\\\"#{foo}\"}"));
        assertEquals("14", "foo's${bar}", JspELParserUtil
                .convertToJspExpression("foo's#{bar}"));
        assertEquals("15", "foo${bar}${baz}", JspELParserUtil
                .convertToJspExpression("foo#{bar}#{baz}"));
        assertEquals("16", "foo${bar}{baz}", JspELParserUtil
                .convertToJspExpression("foo#{bar}{baz}"));
        assertEquals("17", "foo#{'}'}", JspELParserUtil
                .convertToJspExpression("foo#{'}'}"));
        assertEquals("18", "foo#{bar'}", JspELParserUtil
                .convertToJspExpression("foo#{bar'}"));
        assertEquals("19", "${bar == null}", JspELParserUtil
                .convertToJspExpression("#{bar == null}"));
    }
}
