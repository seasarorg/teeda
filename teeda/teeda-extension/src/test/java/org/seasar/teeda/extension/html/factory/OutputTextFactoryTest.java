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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.OutputTextTag;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;

/**
 * @author shot
 */
public class OutputTextFactoryTest extends TestCase {

    public void testIsMatch() throws Exception {
        OutputTextFactory factory = new OutputTextFactory();
        Map properties = new HashMap();
        properties.put("id", "hoge");
        ElementNode elementNode = new ElementNodeImpl("span", properties);
        assertTrue("1", factory.isMatch(elementNode));

        ElementNode elementNode2 = new ElementNodeImpl("foo", properties);
        assertFalse("2", factory.isMatch(elementNode2));

        Map properties2 = new HashMap();
        properties2.put("href", "bbb");
        ElementNode elementNode3 = new ElementNodeImpl("span", properties2);
        assertFalse("3", factory.isMatch(elementNode3));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("outputText");
        tagElement.setTagClass(OutputTextTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        OutputTextFactory factory = new OutputTextFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "aaa");
        ElementNode elementNode = new ElementNodeImpl("span", properties);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", OutputTextTag.class, processor.getTagClass());
        assertEquals("3", "#{fooPage.aaa}", processor.getProperty("value"));

    }
}
