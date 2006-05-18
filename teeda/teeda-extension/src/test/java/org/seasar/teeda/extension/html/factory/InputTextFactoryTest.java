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

import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.config.taglib.TaglibElementBuilder;
import org.seasar.teeda.extension.config.taglib.impl.FileSystemTaglibManagerImpl;
import org.seasar.teeda.extension.config.taglib.impl.TaglibElementBuilderImpl;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;

/**
 * @author manhole
 */
public class InputTextFactoryTest extends TestCase {

    public void testIsMatch() throws Exception {
        InputTextFactory factory = new InputTextFactory();
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "text");
        ElementNode elementNode = new ElementNodeImpl("input", properties);
        assertTrue("1", factory.isMatch(elementNode));
        ElementNode elementNode2 = new ElementNodeImpl("hoge", properties);
        assertFalse("2", factory.isMatch(elementNode2));
    }
    
    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        FileSystemTaglibManagerImpl taglibManager = new FileSystemTaglibManagerImpl();
        MockServletContextImpl servletContext = new MockServletContextImpl(null);
        taglibManager.setServletContext(servletContext);
        TaglibElementBuilder builder = new TaglibElementBuilderImpl();
        taglibManager.setBuilder(builder);
        InputTextFactory factory = new InputTextFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "text");
        ElementNode elementNode = new ElementNodeImpl("input", properties);

        // ## Act ##
        taglibManager.init("org/seasar/teeda/extension/config/taglib/impl/tlds");
        ElementProcessor processor = factory.createProcessor(elementNode);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", InputTextTag.class, processor.getTagClass());
    }
}
