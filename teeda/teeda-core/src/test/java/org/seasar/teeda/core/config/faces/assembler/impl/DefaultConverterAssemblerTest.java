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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.Converter;

import org.seasar.teeda.core.config.faces.assembler.impl.DefaultConverterAssembler;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.impl.ConverterElementImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultConverterAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for DefaultConverterAssemblerTest.
     * @param name
     */
    public DefaultConverterAssemblerTest(String name) {
        super(name);
    }

    public void testAssemble1() throws Exception {
        // # Arrange #
        ConverterElement element = new ConverterElementImpl();
        element.setConverterForClass("org.seasar.teeda.core.mock.MockUIComponent");
        element.setConverterClass("org.seasar.teeda.core.mock.MockConverter");
        Map map = new HashMap();
        map.put(element.getConverterForClass(), element);
        DefaultConverterAssembler assembler = new DefaultConverterAssembler(map, Collections.EMPTY_MAP);
        
        // # Act #
        assembler.assemble();
        
        // # Assert #
        Converter converter = getApplication().createConverter(MockUIComponent.class);
        assertNotNull(converter);
        assertTrue(converter instanceof MockConverter);
    }

    public void testAssemble2() throws Exception {
        // # Arrange #
        ConverterElement element = new ConverterElementImpl();
        element.setConverterId("hoge");
        element.setConverterClass("org.seasar.teeda.core.mock.MockConverter");
        Map map = new HashMap();
        map.put(element.getConverterId(), element);
        DefaultConverterAssembler assembler = new DefaultConverterAssembler(Collections.EMPTY_MAP, map);
        
        // # Act #
        assembler.assemble();
        
        // # Assert #
        Converter converter = getApplication().createConverter("hoge");
        assertNotNull(converter);
        assertTrue(converter instanceof MockConverter);
    }
    
    //TODO if need to support property for Converter, do more tests.
}
