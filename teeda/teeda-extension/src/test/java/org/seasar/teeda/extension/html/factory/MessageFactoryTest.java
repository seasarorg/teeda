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

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.MessageTag;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public class MessageFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        MessageFactory factory = new MessageFactory();
        Map properties = new HashMap();
        properties.put("id", "aaaMessage");
        ElementNode elementNode = createElementNode("span", properties);
        assertTrue(factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
        ElementNode elementNode3 = createElementNode("span", new HashMap());
        assertFalse(factory.isMatch(elementNode3, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("message");
        tagElement.setTagClass(MessageTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        MessageFactory factory = new MessageFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "aaaMessage");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(MessageTag.class, processor.getTagClass());
        assertEquals("aaa", processor.getProperty(JsfConstants.FOR_ATTR));
    }
}
