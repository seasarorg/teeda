/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.application.NavigationHandler;

import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.mock.MockSingleConstructorNavigationHandler;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class NavigationHandlerAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for NavigationHandlerAssemblerTest.
     * @param name
     */
    public NavigationHandlerAssemblerTest(String name) {
        super(name);
    }

    public void testSimpleAssembleNavigationHandler() throws Exception {
        // ## Arrange ##
        String navHandlerName = "org.seasar.teeda.core.mock.MockNavigationHandler";
        NavigationHandlerAssembler assembler = new NavigationHandlerAssembler(
                navHandlerName, getApplication());

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        NavigationHandler handler = getApplication().getNavigationHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof MockNavigationHandler);
    }

    public void testMarshalAssembleNavigationHandler() throws Exception {
        // ## Arrange ##
        getApplication().setNavigationHandler(new MockNavigationHandler());
        String navHandlerName = "org.seasar.teeda.core.mock.MockSingleConstructorNavigationHandler";
        NavigationHandlerAssembler assembler = new NavigationHandlerAssembler(
                navHandlerName, getApplication());

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        NavigationHandler handler = getApplication().getNavigationHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof MockSingleConstructorNavigationHandler);
        MockSingleConstructorNavigationHandler h = (MockSingleConstructorNavigationHandler) handler;
        assertTrue(h.getOriginal() instanceof MockNavigationHandler);
    }

}
