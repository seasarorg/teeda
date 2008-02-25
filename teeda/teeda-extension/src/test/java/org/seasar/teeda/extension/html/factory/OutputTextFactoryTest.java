/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.taglib.TOutputTextTag;
import org.seasar.teeda.extension.util.TeedaExtensionConfiguration;

/**
 * @author shot
 */
public class OutputTextFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new OutputTextFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "outputText", TOutputTextTag.class);
    }

    public void testIsMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "aaa");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));

        elementNode = createElementNode("div", properties);
        assertTrue(factory.isMatch(elementNode, pageDesc, null));

        elementNode = createElementNode("caption", properties);
        assertTrue(factory.isMatch(elementNode, pageDesc, null));

        elementNode = createElementNode("foo", properties);
        assertFalse(factory.isMatch(elementNode, pageDesc, null));

        Map properties2 = new HashMap();
        properties2.put("href", "bbb");
        elementNode = createElementNode("span", properties2);
        assertFalse(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_Legacy() throws Exception {
        TeedaExtensionConfiguration config = new TeedaExtensionConfiguration();
        config.outputTextLabelUnderAnchorOnly = true;
        register(config);
        Map properties = new HashMap();
        properties.put("id", "aaaLabel");
        ElementNode parentNode = createElementNode("a", new HashMap());
        ElementNode elementNode = createElementNode("span", properties);
        elementNode.setParent(parentNode);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue("1", factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_Legacy_NotMatch1() throws Exception {
        TeedaExtensionConfiguration config = new TeedaExtensionConfiguration();
        config.outputTextSpanOnly = true;
        register(config);
        Map properties = new HashMap();
        properties.put("id", "aaa");
        ElementNode elementNode = createElementNode("div", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertFalse(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_Legacy_NotMatch2() throws Exception {
        TeedaExtensionConfiguration config = new TeedaExtensionConfiguration();
        config.outputTextLabelUnderAnchorOnly = true;
        register(config);
        Map properties = new HashMap();
        properties.put("id", "aaaLabel");
        ElementNode parentNode = createElementNode("table", new HashMap());
        ElementNode elementNode = createElementNode("span", properties);
        elementNode.setParent(parentNode);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertFalse("1", factory.isMatch(elementNode, pageDesc, null));
    }

    public void testCustomizeProperties() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "aaaLabel");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        Map props = new HashMap();
        factory.customizeProperties(props, elementNode, pageDesc, null);
        assertEquals("#{labelProvider.aaa}", props.get("value"));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "aaa");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TOutputTextTag.class, processor.getTagClass());
        assertEquals("#{fooPage.aaa}", processor.getProperty("value"));
    }
}