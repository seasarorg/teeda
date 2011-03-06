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

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.core.ParamTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TOutputLinkTag;

/**
 * @author higa
 */
public class OutputLinkFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new OutputLinkFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "outputLink", TOutputLinkTag.class);
        registerTagElement(JsfConstants.JSF_CORE_URI, "param", ParamTag.class);
    }

    public void testIsMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "goHoge");
        properties.put("href", "hoge.html");
        ElementNode elementNode = createElementNode("a", properties);
        assertTrue("1", factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse("2", factory.isMatch(elementNode2, null, null));
        Map properties2 = new HashMap();
        properties2.put("id", "aaa");
        properties2.put("href", "hoge.html");
        ElementNode elementNode3 = createElementNode("a", properties2);
        assertFalse("3", factory.isMatch(elementNode3, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "goHoge");
        properties.put("href", "hoge.html?aaa=111&bbb=222&fixed_ccc=1");
        ElementNode elementNode = createElementNode("a", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TOutputLinkTag.class, processor.getTagClass());
        assertEquals("hoge.html?bbb=222&ccc=1", processor.getProperty("value"));
        assertEquals(1, processor.getChildSize());

        ElementProcessor paramProcessor = (ElementProcessor) processor
                .getChild(0);
        assertEquals(ParamTag.class, paramProcessor.getTagClass());
        assertEquals("aaa", paramProcessor.getProperty("name"));
        assertEquals("#{fooPage.aaa}", paramProcessor.getProperty("value"));
    }

    public void testCreateFactory2() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "goHoge");
        properties.put("href", "hoge.html?aaa=111&amp;bbb=222&amp;fixed_ccc=1");
        ElementNode elementNode = createElementNode("a", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TOutputLinkTag.class, processor.getTagClass());
        assertEquals("hoge.html?bbb=222&ccc=1", processor.getProperty("value"));
        assertEquals(1, processor.getChildSize());

        ElementProcessor paramProcessor = (ElementProcessor) processor
                .getChild(0);
        assertEquals(ParamTag.class, paramProcessor.getTagClass());
        assertEquals("aaa", paramProcessor.getProperty("name"));
        assertEquals("#{fooPage.aaa}", paramProcessor.getProperty("value"));
    }

}
