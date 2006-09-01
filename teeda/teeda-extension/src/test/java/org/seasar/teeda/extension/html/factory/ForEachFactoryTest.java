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

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.web.foo.FooPage;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TForEachTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public class ForEachFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        ForEachFactory factory = new ForEachFactory();
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = createElementNode("div", properties);
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
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement taglibElem = new TaglibElementImpl();
        taglibElem.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("forEach");
        tagElement.setTagClass(TForEachTag.class);
        taglibElem.addTagElement(tagElement);
        taglibManager.addTaglibElement(taglibElem);
        ForEachFactory factory = new ForEachFactory();
        factory.setTaglibManager(taglibManager);
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
}
