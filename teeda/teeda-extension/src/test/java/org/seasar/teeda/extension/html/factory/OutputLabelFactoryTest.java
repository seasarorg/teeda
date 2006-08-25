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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.seasar.framework.util.ResourceBundleUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.taglib.TOutputLabelTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class OutputLabelFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        OutputLabelFactory factory = new OutputLabelFactory();
        Map props = new HashMap();
        props.put("id", "aaa");
        ElementNode elementNode = createElementNode("label", props);
        assertTrue(factory.isMatch(elementNode, null, null));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "outputLabel", TOutputLabelTag.class);
        OutputLabelFactory factory = new OutputLabelFactory();
        factory.setContainer(getContainer());
        factory.setTaglibManager(getTaglibManager());
        Map properties = new HashMap();
        properties.put("id", "aaa");
        ElementNode elementNode = new ElementNodeImpl("label", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TOutputLabelTag.class, processor.getTagClass());
        assertEquals("3", "aaa", processor.getProperty("key"));
        assertEquals("4", "org.seasar.teeda.extension.html.factory.label",
                processor.getProperty("propertiesName"));
        ResourceBundle bundle = ResourceBundleUtil
                .getBundle("org.seasar.teeda.extension.html.factory.label",
                        Locale.ENGLISH);
        assertEquals("AAA", bundle.getString("aaa"));

    }
}
