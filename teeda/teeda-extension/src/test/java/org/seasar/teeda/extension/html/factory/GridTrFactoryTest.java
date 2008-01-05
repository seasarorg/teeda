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

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TGridTag;
import org.seasar.teeda.extension.taglib.TGridTrTag;

/**
 * @author manhole
 */
public class GridTrFactoryTest extends ElementProcessorFactoryTestCase {

    private PageDesc pageDesc;

    protected AbstractElementProcessorFactory createFactory() {
        return new GridTrFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "gridTr",
                TGridTrTag.class);
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "grid",
                TGridTag.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        pageDesc = createPageDesc(FooPage.class, "fooPage");
    }

    public void testCreateProcessor_pageDescNull() throws Exception {
        final HashMap gridProp = new HashMap();
        gridProp.put("id", "hogeGrid");
        ElementNode grid = createElementNode("table", gridProp);
        ElementNode elementNode = createElementNode("tr", new HashMap());
        grid.addElement(elementNode);
        factory.createProcessor(elementNode, null, null);
    }

    public void testTrIsMatch1() throws Exception {
        // ## Arrange ##
        final HashMap gridProp = new HashMap();
        gridProp.put("id", "hogeGrid");
        ElementNode grid = createElementNode("table", gridProp);
        ElementNode elementNode = createElementNode("tr", new HashMap());
        grid.addElement(elementNode);

        // ## Act ##
        // ## Assert ##
        assertEquals(true, factory.isMatch(elementNode, pageDesc, null));
    }

    public void testTrIsMatch2() throws Exception {
        // ## Arrange ##
        final HashMap gridProp = new HashMap();
        ElementNode grid = createElementNode("table", gridProp);

        ElementNode elementNode = createElementNode("tr", new HashMap());
        grid.addElement(elementNode);

        // ## Act ##
        // ## Assert ##
        assertEquals(false, factory.isMatch(elementNode, pageDesc, null));
    }

    public void testCustomizeDynamicProperties() throws Exception {
        // ## Arrange ##
        final HashMap gridProp = new HashMap();
        gridProp.put("id", "hogeGrid");
        ElementNode grid = createElementNode("table", gridProp);

        final HashMap trProp = new HashMap();
        trProp.put("styleClass", "hoge");
        trProp.put("style", "bar");
        ElementNode elementNode = createElementNode("tr", trProp);
        grid.addElement(elementNode);

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);

        // ## Assert ##
        assertEquals("#{fooPage.hogeRowStyleClass}", processor
                .getProperty("styleClass"));
        assertEquals("#{fooPage.hogeRowStyle}", processor.getProperty("style"));
    }

    public void testCustomizeDynamicProperties2() throws Exception {
        // ## Arrange ##
        final HashMap gridProp = new HashMap();
        gridProp.put("id", "hogeGrid");
        ElementNode grid = createElementNode("table", gridProp);

        final HashMap trProp = new HashMap();
        //trProp.put("styleClass", "hoge");
        ElementNode elementNode = createElementNode("tr", trProp);
        grid.addElement(elementNode);

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, null);

        // ## Assert ##
        assertEquals("#{fooPage.hogeRowStyleClass}", processor
                .getProperty("styleClass"));
    }
}
