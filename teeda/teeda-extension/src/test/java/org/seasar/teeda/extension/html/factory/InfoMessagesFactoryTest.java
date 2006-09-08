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
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TInfoMessagesTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author ohtani
 *
 */
public class InfoMessagesFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        InfoMessagesFactory factory = new InfoMessagesFactory();
        Map properties = new HashMap();
        properties.put("id", "infoMessages");
        ElementNode elementNode = createElementNode("span", properties);
        assertTrue(factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
        ElementNode elementNode3 = createElementNode("span", new HashMap());
        assertFalse(factory.isMatch(elementNode3, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "infoMessages", TInfoMessagesTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        InfoMessagesFactory factory = new InfoMessagesFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "infoMessages");
        ElementNode elementNode = createElementNode("span", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TInfoMessagesTag.class, processor.getTagClass());
        assertEquals("false", processor
                .getProperty(JsfConstants.GLOBAL_ONLY_ATTR));
        assertEquals("false", processor
                .getProperty(JsfConstants.SHOW_SUMMARY_ATTR));
        assertEquals("true", processor
                .getProperty(JsfConstants.SHOW_DETAIL_ATTR));
    }

}
