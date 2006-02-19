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
package org.seasar.teeda.core.render;

import java.util.Iterator;

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockRenderKitImpl;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class RenderKitFactoryImplTest extends TeedaTestCase {

    /**
     * Constructor for RenderKitFactoryImplTest.
     * 
     * @param name
     */
    public RenderKitFactoryImplTest(String name) {
        super(name);
    }

    public void testAddRenderKit() throws Exception {
        RenderKitFactoryImpl renderKit = new RenderKitFactoryImpl();
        MockRenderKit mock = new MockRenderKitImpl();
        mock.addRenderer("family", "type", new MockRenderer());
        renderKit.addRenderKit("id", mock);
        RenderKit r = renderKit.getRenderKit(getFacesContext(), "id");
        assertNotNull(r);
        assertTrue(r instanceof MockRenderKitImpl);
        Renderer renderer = r.getRenderer("family", "type");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer);

        Iterator itr = renderKit.getRenderKitIds();
        assertEquals("id", itr.next());
    }

    public void testAddRenderKit_withDI() throws Exception {
        getContainer().register(MockRenderKitImpl.class);
        RenderKitFactoryImpl renderKit = new RenderKitFactoryImpl();
        RenderKit kit = renderKit.getRenderKit(getFacesContext(),
                RenderKitFactory.HTML_BASIC_RENDER_KIT);
        assertNotNull(kit);
        assertTrue(kit instanceof MockRenderKitImpl);
        Iterator itr = renderKit.getRenderKitIds();
        assertEquals(RenderKitFactory.HTML_BASIC_RENDER_KIT, itr.next());
    }
}
