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

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.RendererElement;
import org.seasar.teeda.core.config.element.impl.RenderKitElementImpl;
import org.seasar.teeda.core.config.element.impl.RendererElementImpl;
import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.FactoryFinderUtil;

/**
 * @author shot
 */
public class DefaultRenderKitAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for DefaultRenderKitAssemblerTest.
     * 
     * @param name
     */
    public DefaultRenderKitAssemblerTest(String name) {
        super(name);
    }

    public void testAssemble() throws Exception {
        // ## Arrange ##
        String id = "id";
        RenderKitElement renderKitElement = new RenderKitElementImpl();
        renderKitElement.setRenderKitId(id);
        renderKitElement.setRenderKitClass("org.seasar.teeda.core.mock.MockRenderKit");
        RendererElement rendererElement = new RendererElementImpl();
        rendererElement.setComponentFamily("family");
        rendererElement.setRendererClass("org.seasar.teeda.core.mock.MockRenderer");
        rendererElement.setRendererType("type");
        renderKitElement.addRendererElement(rendererElement);
        Map map = new HashMap();
        map.put(id, renderKitElement);
        DefaultRenderKitAssembler assembler = createAssembler(map);
        
        // ## Act ##
        assembler.assemble();
        
        // ## Assert ##
        RenderKitFactory factory = FactoryFinderUtil.getRenderKitFactory();
        RenderKit kit = factory.getRenderKit(getFacesContext(), id);
        assertNotNull(kit);
        assertTrue(kit instanceof MockRenderKit);
        Renderer renderer = kit.getRenderer("family", "type");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer);
    }
    
    public void testGetRenderKitClassName() throws Exception {
        // ## Arrange ##
        DefaultRenderKitAssembler assembler = createAssembler();
        RenderKitElement element = new RenderKitElementImpl();

        // ## Act ##
        String className = assembler.getRenderKitClassName(element);

        // ## Assert ##
        assertEquals(JsfConstants.DEFAULT_RENDERKIT_CLASS, className);
    }

    public void testCreateRendererSucceed() throws Exception {
        // ## Arrange ##
        DefaultRenderKitAssembler assembler = createAssembler();

        // ## Act ##
        Renderer r = assembler
                .createRenderer("org.seasar.teeda.core.mock.MockRenderer");

        // ## Assert ##
        assertTrue(r instanceof MockRenderer);
    }

    public void testCreateRendererReturnNull() throws Exception {
        // ## Arrange ##
        DefaultRenderKitAssembler assembler = createAssembler();
        Renderer r = null;

        // ## Act ##
        try {
            r = assembler.createRenderer("nosuchpackage.NoSuchRenderer");
        } catch (ClassNotFoundRuntimeException expected) {
        }

        // ## Assert ##
        assertNull(r);
    }

    public void testCreateRenderKitSucceed() throws Exception {
        // ## Arrange ##
        DefaultRenderKitAssembler assembler = createAssembler();

        // ## Act ##
        RenderKit renderKit = assembler
                .createRenderKit("org.seasar.teeda.core.mock.MockRenderKit");

        // ## Assert ##
        assertTrue(renderKit instanceof MockRenderKit);
    }

    private DefaultRenderKitAssembler createAssembler() {
        Map map = new HashMap();
        return createAssembler(map);
    }

    private DefaultRenderKitAssembler createAssembler(Map map) {
        DefaultRenderKitAssembler assembler = new DefaultRenderKitAssembler(map);
        return assembler;
    }
}
