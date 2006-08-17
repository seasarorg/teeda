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
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TConditionTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class ConditionFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        ConditionFactory factory = new ConditionFactory();
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
    }

    public void testIsMatch2() throws Exception {
        ConditionFactory factory = new ConditionFactory();
        Map props2 = new HashMap();
        props2.put("id", "isNotBbb");
        ElementNode elementNode2 = createElementNode("div", props2);
        PageDesc pageDesc2 = createPageDesc(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode2, pageDesc2, null));
    }

    public void testIsMatch3() throws Exception {
        ConditionFactory factory = new ConditionFactory();
        Map props3 = new HashMap();
        props3.put("id", "ssNotBbb");
        ElementNode elementNode3 = createElementNode("div", props3);
        PageDesc pageDesc3 = createPageDesc(AaaPage.class, "aaaPage");
        assertFalse(factory.isMatch(elementNode3, pageDesc3, null));
    }

    public void testCreateFactory1() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement tdaExt = new TaglibElementImpl();
        tdaExt.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("condition");
        tagElement.setTagClass(TConditionTag.class);
        tdaExt.addTagElement(tagElement);
        taglibManager.addTaglibElement(tdaExt);
        ConditionFactory factory = new ConditionFactory();
        factory.setTaglibManager(taglibManager);
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb == true}", processor
                .getProperty("rendered"));
    }

    public void testCreateFactory2() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement tdaExt = new TaglibElementImpl();
        tdaExt.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("condition");
        tagElement.setTagClass(TConditionTag.class);
        tdaExt.addTagElement(tagElement);
        taglibManager.addTaglibElement(tdaExt);
        ConditionFactory factory = new ConditionFactory();
        factory.setTaglibManager(taglibManager);
        Map props = new HashMap();
        props.put("id", "isNotBbb");
        ElementNode elementNode = createElementNode("div", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TConditionTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.bbb == false}", processor
                .getProperty("rendered"));
    }

}
