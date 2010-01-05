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

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.config.faces.element.impl.ComponentElementImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultComponentAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for DefaultComponentAssemblerTest.
     * 
     * @param name
     */
    public DefaultComponentAssemblerTest(String name) {
        super(name);
    }

    public void testAssemble1() throws Exception {
        // # Arrange #
        Map map = new HashMap();
        ComponentElement element = new ComponentElementImpl();
        element.setComponentType("javax.faces.mock");
        element.setComponentClass("org.seasar.teeda.core.mock.MockUIComponent");
        map.put("javax.faces.mock", element);
        DefaultComponentAssembler assembler = new DefaultComponentAssembler(map);

        // # Act #
        assembler.assemble();

        // # Assert #
        UIComponent component = getApplication().createComponent(
                "javax.faces.mock");
        assertNotNull(component);
        assertTrue(component instanceof MockUIComponent);
    }
}
