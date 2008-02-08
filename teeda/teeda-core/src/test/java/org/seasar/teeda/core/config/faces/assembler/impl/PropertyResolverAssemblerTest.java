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
package org.seasar.teeda.core.config.faces.assembler.impl;

import javax.faces.el.PropertyResolver;

import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockSingleConstructorPropertyResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class PropertyResolverAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for PropertyResolverAssemblerTest.
     * @param name
     */
    public PropertyResolverAssemblerTest(String name) {
        super(name);
    }

    public void testSimpleAssemblePropertyResolver() throws Exception {
        // ## Arrange ##
        String resolverName = "org.seasar.teeda.core.mock.MockPropertyResolver";
        PropertyResolverAssembler assembler = new PropertyResolverAssembler(
                resolverName, getApplication());

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        PropertyResolver resolver = getApplication().getPropertyResolver();
        assertNotNull(resolver);
        assertTrue(resolver instanceof MockPropertyResolver);
    }

    public void testMarshalAssemblePropertyResolver1() throws Exception {
        // ## Arrange ##
        getApplication().setPropertyResolver(new MockPropertyResolver());
        String resolverName = "org.seasar.teeda.core.mock.MockSingleConstructorPropertyResolver";
        PropertyResolverAssembler assembler = new PropertyResolverAssembler(
                resolverName, getApplication());

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        PropertyResolver resolver = getApplication().getPropertyResolver();
        assertNotNull(resolver);
        assertTrue(resolver instanceof MockSingleConstructorPropertyResolver);
        MockSingleConstructorPropertyResolver r = (MockSingleConstructorPropertyResolver) resolver;
        assertTrue(r.getOriginal() instanceof MockPropertyResolver);
    }
}
