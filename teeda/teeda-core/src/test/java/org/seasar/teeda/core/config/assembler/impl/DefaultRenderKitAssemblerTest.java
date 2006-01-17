/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.assembler.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.impl.RenderKitElementImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultRenderKitAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for DefaultRenderKitAssemblerTestr.
     * @param name
     */
    public DefaultRenderKitAssemblerTest(String name) {
        super(name);
    }

    public void testGetRenderKitClassName() throws Exception {
        Map map = new HashMap();
        DefaultRenderKitAssembler assembler = new DefaultRenderKitAssembler(map);
        RenderKitElement element = new RenderKitElementImpl();
        
        String className = assembler.getRenderKitClassName(element);
        
        assertEquals(JsfConstants.DEFAULT_RENDERKIT_CLASS, className);
    }
}
