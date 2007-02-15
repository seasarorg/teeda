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
package org.seasar.teeda.extension.render;

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.core.mock.MockFacesContextImpl;

/**
 * @author shot
 */
public class TIncludeRendererTest extends RendererTest {

    private TIncludeRenderer renderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTIncludeRenderer();
    }

    public void testCalcViewId() throws Exception {
        MockFacesContextImpl context = new MockFacesContextImpl();
        context.getViewRoot().setViewId("/view/aaa/bbb.html");
        assertEquals("/view/aaa/bbb.html", renderer.calcViewId(context,
                "bbb.html"));
        assertEquals("/view/aaa/bbb.html", renderer.calcViewId(context,
                "/aaa/bbb.html"));
    }

    private TIncludeRenderer createTIncludeRenderer() {
        return (TIncludeRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        TIncludeRenderer renderer = new TIncludeRenderer();
        renderer.setNamingConvention(new NamingConventionImpl());
        return renderer;
    }

}
