/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.html.factory.sub.web.AaaPage;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TInputCommaTextTag;

public class InputCommaTextFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new InputCommaTextFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "inputCommaText", TInputCommaTextTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "text");
        props.put("class", "T_currency");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        ElementNode elementNode2 = createElementNode("hoge", props);
        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
        Map props2 = new HashMap();
        props2.put("id", "aaa");
        props2.put("type", "text");
        ElementNode elementNode3 = createElementNode("input", props2);
        assertFalse(factory.isMatch(elementNode3, pageDesc, null));
        Map props3 = new HashMap();
        props3.put("id", "xxx");
        props3.put("type", "text");
        ElementNode elementNode4 = createElementNode("input", props3);
        assertFalse(factory.isMatch(elementNode4, pageDesc, null));

        Map props4 = new HashMap();
        props4.put("id", "aaa");
        props4.put("type", "text");
        props4.put("class", "T_currency hoge");
        ElementNode elementNode5 = createElementNode("input", props4);
        assertTrue(factory.isMatch(elementNode5, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "text");
        props.put("class", "T_Currency");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TInputCommaTextTag.class, processor.getTagClass());
        assertEquals("#{aaaPage.aaa}", processor.getProperty("value"));
        assertEquals("#{aaaPage.aaaFraction}", processor
                .getProperty("fraction"));
        assertEquals("#{aaaPage.aaaFractionSeparator}", processor
                .getProperty("fractionSeparator"));
        assertEquals("#{aaaPage.aaaGroupingSeparator}", processor
                .getProperty("groupingSeparator"));
    }

    public void testCreateFactory_TEEDA314() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "ccc");
        props.put("type", "text");
        props.put("class", "T_Currency");
        props.put("fraction", "2");
        props.put("fractionSeparator", ".");
        props.put("groupingSeparator", ",");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertEquals("2", processor.getProperty("fraction"));
        assertEquals(".", processor.getProperty("fractionSeparator"));
        assertEquals(",", processor.getProperty("groupingSeparator"));
    }
}
