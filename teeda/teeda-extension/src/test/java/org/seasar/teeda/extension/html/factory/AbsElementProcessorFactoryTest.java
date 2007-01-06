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
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TInputTextTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public class AbsElementProcessorFactoryTest extends TeedaExtensionTestCase {

    public void testCustomizeDynamicProperties() throws Exception {
        // ## Arrange ##
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "inputText",
                TInputTextTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        InputTextFactory factory = new InputTextFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "text");
        properties.put("class", "hoge");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        assertEquals("#{fooPage.aaaStyleClass}", processor
                .getProperty("styleClass"));

        factory.createProcessor(elementNode, null, null);
    }

    public void testCustomizeDynamicProperties_styeClass() throws Exception {
        // ## Arrange ##
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "inputText",
                TInputTextTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        InputTextFactory factory = new InputTextFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "ccc");
        properties.put("type", "text");
        properties.put("class", "hoge");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(Foo2Page.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        assertEquals("#{fooPage.cccClass}", processor.getProperty("styleClass"));

        factory.createProcessor(elementNode, null, null);
    }

}
