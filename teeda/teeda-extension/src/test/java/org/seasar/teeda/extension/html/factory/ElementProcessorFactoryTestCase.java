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

import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public abstract class ElementProcessorFactoryTestCase extends
        TeedaExtensionTestCase {

    protected AbstractElementProcessorFactory factory;

    protected void setUpContainer() throws Throwable {
        super.setUpContainer();
        registerTagElements();
        factory = createFactory();
        factory.setTaglibManager(taglibManager);
    }

    protected void tearDownContainer() throws Throwable {
        super.tearDownContainer();
        factory = null;
    }

    public void testCreateProcessor_pageDescNull() throws Exception {
        ElementNode elementNode = createElementNode("html", new HashMap());
        factory.createProcessor(elementNode, null, null);
    }

    public void testIsMatch_pageDescNull() throws Exception {
        ElementNode elementNode = createElementNode("html", new HashMap());
        factory.isMatch(elementNode, null, null);
    }

    protected abstract AbstractElementProcessorFactory createFactory();

    protected abstract void registerTagElements();

}
