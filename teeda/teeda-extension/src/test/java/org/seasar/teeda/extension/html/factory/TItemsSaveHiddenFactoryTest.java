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
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TItemsSaveHiddenTag;

/**
 * @author shot
 */
public class TItemsSaveHiddenFactoryTest extends
        ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new TItemsSaveHiddenFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "inputHidden", TItemsSaveHiddenTag.class);
    }

    public void testIsMatch() throws Exception {
        Map map = new HashMap();
        map.put("type", "hidden");
        map.put("id", "hogeItemsSave");
        ElementNode elementNode = createElementNode("input", map);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(elementNode, pageDesc, actionDesc));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("type", "hidden");
        properties.put("id", "hogeItemsSave");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor parentProcessor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", parentProcessor);
        assertEquals("2", TItemsSaveHiddenTag.class, parentProcessor
                .getTagClass());
        assertEquals("3", "#{fooPage.hogeItems}", parentProcessor
                .getProperty("value"));
    }
}