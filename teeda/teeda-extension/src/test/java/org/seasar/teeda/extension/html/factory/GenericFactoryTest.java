/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public class GenericFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        registerTagElement(JsfConstants.JSF_HTML_URI, "inputText",
                InputTextTag.class);
        GenericFactory factory = new GenericFactory();
        factory.setTaglibManager(getTaglibManager());
        Map properties = new HashMap();
        ElementNode elementNode = createElementNode(JsfConstants.JSF_HTML_URI,
                "inputText", null, properties);
        assertTrue(factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        registerTagElement(JsfConstants.JSF_HTML_URI, "inputText",
                InputTextTag.class);
        GenericFactory factory = new GenericFactory();
        factory.setTaglibManager(getTaglibManager());
        Map properties = new HashMap();
        properties.put("value", "#{fooPage.aaa}");
        ElementNode elementNode = createElementNode(JsfConstants.JSF_HTML_URI,
                "inputText", null, properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(InputTextTag.class, processor.getTagClass());
        assertEquals("#{fooPage.aaa}", processor.getProperty("value"));
    }
}
