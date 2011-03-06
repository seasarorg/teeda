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
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.Foo2Page;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TForEachTag;

/**
 * @author higa
 */
public class ForEachFactoryTest extends ElementProcessorFactoryTestCase {

    /**
     * 
     */
    public ForEachFactoryTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected AbstractElementProcessorFactory createFactory() {
        return new ForEachFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "forEach",
                TForEachTag.class);
    }

    public void testIsMatch_div() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("div", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_span() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_table() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("table", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_tbody() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("tbody", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_ul() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("ul", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_ol() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("ol", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_dl() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("dl", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch_notMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode2 = createElementNode("hoge", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
        properties.put("id", "xxx");
        ElementNode elementNode3 = createElementNode("input", properties);
        assertFalse(factory.isMatch(elementNode3, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("div", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TForEachTag.class, processor.getTagClass());
        assertEquals("fooPage", processor
                .getProperty(ExtensionConstants.PAGE_NAME_ATTR));
        assertEquals("hogeItems", processor
                .getProperty(ExtensionConstants.ITEMS_NAME_ATTR));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        properties.put("title", "dummy");
        ElementNode elementNode = createElementNode("tr", properties);
        PageDesc pageDesc = createPageDesc(Foo2Page.class, "fooPage");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TForEachTag.class, processor.getTagClass());
        assertEquals("#{fooPage.hogeItemsTitle}", processor
                .getProperty("title"));
    }

}
