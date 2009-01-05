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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.THolidayCalendarTag;

/**
 * @author shot
 */
public class HolidayCalendarFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new HolidayCalendarFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "holidayCalendar", THolidayCalendarTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "januaryHolidays");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_invalid_id() throws Exception {
        Map props = new HashMap();
        props.put("id", "hoge");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertFalse(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_pageDesc_null() throws Exception {
        Map props = new HashMap();
        props.put("id", "januaryHolidays");
        ElementNode elementNode = createElementNode("span", props);
        assertFalse(factory.isMatch(elementNode, null, null));
    }

    public void testCreateProcessor() throws Exception {
        Map props = new HashMap();
        props.put("id", "januaryHolidays");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);
        assertNotNull(processor);
        assertEquals(THolidayCalendarTag.class, processor.getTagClass());
        assertEquals("#{fooPage.januaryHolidays}", processor
                .getProperty("value"));
        assertEquals("#{fooPage.holidaysYear}", processor.getProperty("year"));
        assertEquals("0", processor.getProperty("month"));
    }
}