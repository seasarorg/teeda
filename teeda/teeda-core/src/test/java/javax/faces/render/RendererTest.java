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

import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.AssertUtil;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class RendererTest extends TeedaTestCase {

    public final void testConvertClientId_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(null, "fooClientId");
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testConvertClientId_ClientIdIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testDecode_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testDecode_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeBegin_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeBegin_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeChildren_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeChildren_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeEnd_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testEncodeEnd_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
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
            AssertUtil.assertExceptionMessageExist(npe);
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
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    protected Renderer createRenderer() {
        return new NullRenderer();
    }

    protected String getResponseText() throws IOException {
        return getResponse().getWriter().toString();
    }

}
