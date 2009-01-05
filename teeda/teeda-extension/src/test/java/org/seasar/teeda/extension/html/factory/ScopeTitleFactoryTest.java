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

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.taglib.TTitleTag;

/**
 * @author shot
 */
public class ScopeTitleFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new ScopeTitleFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "title",
                TTitleTag.class);
    }

    public void testIsMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "requestScope_aaa");
        ElementNode elementNode = createElementNode("title", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("foo", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));

        properties.put("id", "hoge");
        ElementNode elementNode3 = createElementNode("span", properties);
        assertFalse(factory.isMatch(elementNode3, null, null));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "requestScope_aaa");
        ElementNode elementNode = createElementNode("title", properties);

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode, null,
                null);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TTitleTag.class, processor.getTagClass());
        assertEquals("#{requestScope.aaa}", processor.getProperty("value"));
    }

}
