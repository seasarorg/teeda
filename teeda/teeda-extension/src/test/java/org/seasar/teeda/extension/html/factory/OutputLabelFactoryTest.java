/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ResourceBundleUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TOutputLabelTag;

/**
 * @author shot
 */
public class OutputLabelFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new OutputLabelFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "outputLabel", TOutputLabelTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "aaa");
        ElementNode elementNode = createElementNode("label", props);
        assertTrue(factory.isMatch(elementNode, null, null));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        NamingConventionImpl namingConvention = new NamingConventionImpl();
        namingConvention
                .addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        ((OutputLabelFactory) factory).setNamingConvention(namingConvention);
        Map properties = new HashMap();
        properties.put("id", "aaaLabel");
        ElementNode elementNode = createElementNode("label", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "foo_fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        FacesConfigOptions.setDefaultSuffix(".html");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TOutputLabelTag.class, processor.getTagClass());
        assertEquals("3", "foo.aaa", processor.getProperty("key"));
        assertEquals("4",
                "org.seasar.teeda.extension.html.factory.sub.web.foo.label",
                processor.getProperty("propertiesName"));
        ResourceBundle bundle = ResourceBundleUtil.getBundle(
                "org.seasar.teeda.extension.html.factory.sub.web.foo.label",
                Locale.ENGLISH);
        assertEquals("AAA", bundle.getString("aaa"));
        assertEquals(processor.getProperty("defaultPropertiesName"),
                "org.seasar.teeda.extension.html.factory.sub.web.label");
        assertEquals("aaa", processor.getProperty("defaultKey"));
        assertEquals("3", "#{foo_fooPage.aaaLabelValue}", processor
                .getProperty("value"));
    }
}