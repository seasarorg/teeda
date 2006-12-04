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
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TElementTag;

/**
 * @author higa
 */
public class GenericElementFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new GenericElementFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "element",
                TElementTag.class);
    }

    public void testIsMatch() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeRow");
        properties.put("style", "background-color:red");
        ElementNode elementNode = createElementNode("tr", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertEquals(true, factory.isMatch(elementNode, pageDesc, null));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "hogeRow");
        properties.put("style", "background-color:red");
        ElementNode elementNode = createElementNode("tr", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(TElementTag.class, processor.getTagClass());
        assertEquals("#{fooPage.hogeRowStyle}", processor.getProperty("style"));
    }
}
