/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TSelectBooleanCheckboxTag;

public class SelectBooleanCheckboxFactoryTest extends
        ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new SelectBooleanCheckboxFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectBooleanCheckbox", TSelectBooleanCheckboxTag.class);
    }

    public void testIsMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "checkbox");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
        properties.put("id", "xxx");
        ElementNode elementNode3 = createElementNode("input", properties);
        assertFalse(factory.isMatch(elementNode3, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "checkbox");
        properties.put("title", "arg1");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TSelectBooleanCheckboxTag.class, processor
                .getTagClass());
        assertEquals("3", "#{fooPage.aaa}", processor.getProperty("value"));
        assertEquals("4", null, processor.getProperty("label"));
    }
}