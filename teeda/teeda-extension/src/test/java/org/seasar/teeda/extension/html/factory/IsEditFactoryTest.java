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
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TInputCommaTextTag;

/**
 * @author shot
 */
public class IsEditFactoryTest extends TestCase {

    public void testIsMatch() throws Exception {
        IsEditFactory factory = new IsEditFactory();
        Map props = new HashMap();
        props.put("id", "isBbb");
        ElementNode elementNode = new ElementNodeImpl("div", props);
        PageDesc pageDesc = new PageDescImpl(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));

        Map props2 = new HashMap();
        props2.put("id", "isNotBbb");
        ElementNode elementNode2 = new ElementNodeImpl("div", props2);
        PageDesc pageDesc2 = new PageDescImpl(AaaPage.class, "aaaPage");
        assertTrue(factory.isMatch(elementNode2, pageDesc2, null));

        Map props3 = new HashMap();
        props3.put("id", "ssNotBbb");
        ElementNode elementNode3 = new ElementNodeImpl("div", props3);
        PageDesc pageDesc3 = new PageDescImpl(AaaPage.class, "aaaPage");
        assertFalse(factory.isMatch(elementNode3, pageDesc3, null));

        //        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
        //        Map props2 = new HashMap();
        //        props2.put("id", "aaa");
        //        props2.put("type", "text");
        //        ElementNode elementNode3 = new ElementNodeImpl("input", props2);
        //        assertFalse(factory.isMatch(elementNode3, pageDesc, null));
        //        Map props3 = new HashMap();
        //        props3.put("id", "xxx");
        //        props3.put("type", "text");
        //        ElementNode elementNode4 = new ElementNodeImpl("input", props3);
        //        assertFalse(factory.isMatch(elementNode4, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("inputCommaText");
        tagElement.setTagClass(TInputCommaTextTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        InputCommaTextFactory factory = new InputCommaTextFactory();
        factory.setTaglibManager(taglibManager);
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "text");
        props.put("class", "T_Currency");
        ElementNode elementNode = new ElementNodeImpl("input", props);
        PageDesc pageDesc = new PageDescImpl(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TInputCommaTextTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.aaa}", processor.getProperty("value"));
        assertEquals("4", "#{aaaPage.aaaFraction}", processor
                .getProperty("fraction"));
        assertEquals("5", "#{aaaPage.aaaFractionSeparator}", processor
                .getProperty("fractionSeparator"));
        assertEquals("6", "#{aaaPage.aaaGroupingSeparator}", processor
                .getProperty("groupingSeparator"));
    }

}
