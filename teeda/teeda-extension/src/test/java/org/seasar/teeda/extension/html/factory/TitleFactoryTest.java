/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TTitleTag;

/**
 * @author shot
 */
public class TitleFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new TitleFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "title",
                TTitleTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "aaa");
        ElementNode elementNode = createElementNode("title", props);
        assertTrue(factory.isMatch(elementNode, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        NamingConventionImpl namingConvention = new NamingConventionImpl();
        namingConvention
                .addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        ((TitleFactory) factory).setNamingConvention(namingConvention);
        FacesConfigOptions.setDefaultSuffix(".html");

        Map properties = new HashMap();
        properties.put("id", "abc");
        ElementNode elementNode = createElementNode("title", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TTitleTag.class, processor.getTagClass());
        assertEquals("3", "#{fooPage.abcValue}", processor.getProperty("value"));
    }

    public void testCreateFactory2() throws Exception {
        // ## Arrange ##
        NamingConventionImpl namingConvention = new NamingConventionImpl();
        namingConvention
                .addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        ((TitleFactory) factory).setNamingConvention(namingConvention);
        FacesConfigOptions.setDefaultSuffix(".html");

        Map properties = new HashMap();
        properties.put("id", "abc");
        ElementNode elementNode = createElementNode("title", properties);
        elementNode.addText("hoge");
        elementNode.endElement();
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TTitleTag.class, processor.getTagClass());
        assertEquals("3", "#{fooPage.abcValue}", processor.getProperty("value"));
        assertEquals("4", "hoge", processor.getProperty("templateValue"));
    }

}
