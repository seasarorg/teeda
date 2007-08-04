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
import org.seasar.teeda.extension.html.factory.sub.web.Aaa2Page;
import org.seasar.teeda.extension.html.factory.sub.web.AaaPage;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.taglib.TConditionTag;

/**
 * @author shot
 */
public class ConditionFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new ConditionFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "condition",
                TConditionTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch2() throws Exception {
        Map props2 = new HashMap();
        props2.put("id", "isNotBbb");
        ElementNode elementNode2 = createElementNode("div", props2);
        PageDesc pageDesc2 = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode2, pageDesc2, null));
    }

    public void testIsMatch3() throws Exception {
        Map props3 = new HashMap();
        props3.put("id", "ssNotBbb");
        ElementNode elementNode3 = createElementNode("div", props3);
        PageDesc pageDesc3 = createPageDesc(AaaPage.class, "aaaPage");
        assertFalse(factory.isMatch(elementNode3, pageDesc3, null));
    }

    public void testIsMatch4_noSuchPageProperty1() throws Exception {
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(Aaa2Page.class, "aaaPage");
        assertFalse(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch4_noSuchPageProperty2() throws Exception {
        Map props = new HashMap();
        props.put("id", "isNotBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(Aaa2Page.class, "aaaPage");
        assertFalse(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_spanIsOk() throws Exception {
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_spanIsOk2() throws Exception {
        Map props2 = new HashMap();
        props2.put("id", "isNotBbb");
        ElementNode elementNode2 = createElementNode("span", props2);
        PageDesc pageDesc2 = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode2, pageDesc2, null));
    }

    public void testCreateFactory1() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb != null && aaaPage.bbb == true}",
                processor.getProperty("rendered"));
    }

    public void testCreateFactory2() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "isNotBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb == null || aaaPage.bbb == false}",
                processor.getProperty("rendered"));
    }

    public void testCreateFactory_spanIsOk1() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb != null && aaaPage.bbb == true}",
                processor.getProperty("rendered"));
    }

    public void testCreateFactory_spanIsOk2() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "isNotBbb");
        ElementNode elementNode = createElementNode("span", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb == null || aaaPage.bbb == false}",
                processor.getProperty("rendered"));
    }

}