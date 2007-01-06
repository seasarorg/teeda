/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;

import junit.framework.TestCase;

import org.seasar.teeda.core.config.faces.element.FactoryElement;
import org.seasar.teeda.core.config.faces.element.impl.FactoryElementImpl;
import org.seasar.teeda.core.mock.MockApplicationFactory;

public class DefaultFactoryAssemblerTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestSimpleFactoriesAssembler.
     * @param arg0
     */
    public DefaultFactoryAssemblerTest(String arg0) {
        super(arg0);
    }

    public void testAssemble() {
        FactoryElement factoryElement = new FactoryElementImpl();
        factoryElement
                .addApplicationFactory("org.seasar.teeda.core.mock.MockApplicationFactory");
        List list = new ArrayList();
        list.add(factoryElement);
        DefaultFactoryAssembler assembler = new DefaultFactoryAssembler(list);
        assembler.assemble();

        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);

        assertNotNull(appFactory);
        assertTrue(appFactory instanceof MockApplicationFactory);

    }
}
