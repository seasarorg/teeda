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
package javax.faces.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.render.ComponentIdLookupStrategy;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;

/**
 * @author manhole
 */
public abstract class AbstractRendererTest extends TestCase {

    private MockFacesContext mockFacesContext_;

    private ComponentIdLookupStrategy idLookupStrategy_;

    protected void setUp() throws Exception {
        super.setUp();
        mockFacesContext_ = new MockFacesContextImpl();
        idLookupStrategy_ = new DefaultComponentIdLookupStrategy();
    }

    protected void tearDown() throws Exception {
        mockFacesContext_.release();
        idLookupStrategy_ = null;
        super.tearDown();
    }

    protected MockFacesContext getFacesContext() {
        return mockFacesContext_;
    }

    protected ComponentIdLookupStrategy getComponentIdLookupStrategy() {
        return idLookupStrategy_;
    }

    protected String getResponseText() throws IOException {
        HtmlResponseWriter htmlResponseWriter = ((HtmlResponseWriter) mockFacesContext_
                .getResponseWriter());
        return htmlResponseWriter.getWriter().toString();
    }
    
    protected void encodeByRenderer(Renderer renderer,
            UIComponent component) throws IOException {
        encodeByRenderer(renderer, getFacesContext(), component);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        if (renderer.getRendersChildren()) {
            renderer.encodeChildren(context, component);
        }
        renderer.encodeEnd(context, component);
    }

}
