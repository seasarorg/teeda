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
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.framework.util.SPrintWriter;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class RendererTest extends TestCase {

    public final void testConvertClientId_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(null, "fooClientId");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testConvertClientId_ClientIdIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testDecode_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testDecode_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeBegin_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeBegin_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeChildren_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeChildren_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeEnd_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeEnd_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetConvertedValue_FacesContextIsNull()
            throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.getConvertedValue(null, new NullUIComponent(),
                    new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetConvertedValue_UIComponentIsNull()
            throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.getConvertedValue(new NullFacesContext(), null,
                    new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    private HtmlResponseWriter responseWriter_;

    private MockFacesContext facesContext_;

    protected void setUp() throws Exception {
        super.setUp();
        facesContext_ = new MockFacesContextImpl();

        responseWriter_ = new HtmlResponseWriter();
        responseWriter_.setWriter(new SPrintWriter());
        facesContext_.setResponseWriter(responseWriter_);

        UIViewRoot viewRoot = new UIViewRoot();
        facesContext_.setViewRoot(viewRoot);
    }

    protected void tearDown() throws Exception {
        facesContext_.release();
        super.tearDown();
    }

    protected MockFacesContext getFacesContext() {
        return facesContext_;
    }

    protected String getResponseText() throws IOException {
        return responseWriter_.getWriter().toString();
    }

    protected Renderer createRenderer() {
        return new NullRenderer();
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
