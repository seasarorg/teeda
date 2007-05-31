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
import org.seasar.teeda.extension.html.factory.sub.web.foo.Foo2Page;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TCommandButtonTag;

public class CommandButtonFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new CommandButtonFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "commandButton", TCommandButtonTag.class);
    }

    public void testCreateProcessor_pageDescNull() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "goNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        factory.createProcessor(elementNode, null, null);
    }

    public void testIsMatch_go() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "goNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_do() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "doBbb");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        assertTrue(factory.isMatch(elementNode, pageDesc, actionDesc));

        Map properties2 = new HashMap();
        properties2.put("type", "submit");
        properties2.put("id", "doCcc");
        ElementNode elementNode2 = createElementNode("input", properties2);
        assertTrue(factory.isMatch(elementNode2, pageDesc, actionDesc));
        assertTrue(factory.isMatch(elementNode2, null, actionDesc));
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_jump() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "jumpNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_button() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "button");
        properties.put("id", "goNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_image() throws Exception {
        Map properties = new HashMap();
        properties.put("type", "image");
        properties.put("id", "doBbb");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));

        Map properties2 = new HashMap();
        properties2.put("id", "doCcc");
        properties2.put("type", "submit");
        ElementNode elementNode2 = createElementNode("input", properties2);
        ElementProcessor processor2 = factory.createProcessor(elementNode2,
                pageDesc, actionDesc);
        assertEquals("#{fooAction.doCcc}", processor2.getProperty("action"));

        Map properties3 = new HashMap();
        properties3.put("id", "goNextPage");
        properties3.put("type", "submit");
        ElementNode elementNode3 = createElementNode("input", properties3);
        ElementProcessor processor3 = factory.createProcessor(elementNode3,
                pageDesc, actionDesc);
        assertEquals("nextPage", processor3.getProperty("action"));

        Map properties4 = new HashMap();
        properties4.put("id", "jumpNextPage");
        properties4.put("type", "submit");
        ElementNode elementNode4 = createElementNode("input", properties4);
        ElementProcessor processor4 = factory.createProcessor(elementNode4,
                pageDesc, actionDesc);
        assertEquals("nextPage", processor4.getProperty("action"));
        assertEquals("true", processor4.getProperty("immediate"));
    }

    public void testCreateFactory_locationHrefRemove1() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "location.href='hoge.html'");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("", processor.getProperty("onclick"));
    }

    public void testCreateFactory_locationHrefRemove2() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "location.href='hoge.html';foo();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("foo();", processor.getProperty("onclick"));
    }

    public void testCreateFactory_locationHrefRemove3() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "hoge();location.href='foo.html';bar();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("hoge();bar();", processor.getProperty("onclick"));
    }

    public void testCreateFactory_historyBackRemove1() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "history.back();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("", processor.getProperty("onclick"));
    }

    public void testCreateFactory_historyBackRemove2() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "history.back();foo();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("foo();", processor.getProperty("onclick"));
    }

    public void testCreateFactory_historyBackRemove3() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "hoge();history.back();bar();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("hoge();bar();", processor.getProperty("onclick"));
    }

    public void testCreateFactory_onclickIsDynamicProperty() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "hoge");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(Foo2Page.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("#{fooPage.doBbbOnclick}", processor
                .getProperty("onclick"));
        System.out.println(processor.getProperty("onclick"));
    }

    public void testCreateFactory_image() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "image");
        properties.put("src", "hoge.gif");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TCommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("hoge.gif", processor.getProperty("image"));
    }
}
