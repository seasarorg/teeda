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
import org.seasar.teeda.core.taglib.html.SelectOneListboxTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TSelectItemsTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class SelectOneListboxFactoryTest extends TeedaExtensionTestCase {

    private MockTaglibManager taglibManager;

    protected void setUp() throws Exception {
        super.setUp();
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "selectOneListbox",
                SelectOneListboxTag.class);
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectItems", TSelectItemsTag.class);
        taglibManager = getTaglibManager();
    }

    public void testIsMatch() throws Exception {
        SelectOneListboxFactory selectFactory = new SelectOneListboxFactory();
        Map map = new HashMap();
        map.put("id", "hogeItems");
        ElementNodeImpl elementNode = new ElementNodeImpl("select", map);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue(selectFactory.isMatch(elementNode, pageDesc, actionDesc));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        SelectOneListboxFactory factory = new SelectOneListboxFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "hogeItems");
        ElementNode elementNode = new ElementNodeImpl("span", properties);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor parentProcessor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", parentProcessor);
        assertEquals("2", SelectOneListboxTag.class, parentProcessor
                .getTagClass());
        assertEquals("3", "#{fooPage.hoge}", parentProcessor
                .getProperty("value"));

        ElementProcessor child = (ElementProcessor) parentProcessor.getChild(0);
        assertNotNull("4", child);
        assertEquals("5", TSelectItemsTag.class, child.getTagClass());
        assertEquals("6", "#{fooPage.hogeItems}", child.getProperty("value"));
    }

}
