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

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author manhole
 */
public class RendererTest extends TestCase {

    public void testConvertClientId_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.convertClientId(null, "fooClientId");
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testConvertClientId_ClientIdIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.convertClientId(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testDecode_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.decode(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testDecode_UIComponentIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeBegin_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeBegin(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeBegin_UIComponentIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeBegin(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeChildren_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeChildren(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeChildren_UIComponentIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeChildren(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeEnd_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testEncodeEnd_UIComponentIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testGetConvertedValue_FacesContextIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.getConvertedValue(null, new NullUIComponent(),
                new Object());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testGetConvertedValue_UIComponentIsNull() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.getConvertedValue(new NullFacesContext(), null,
                new Object());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    private void assertExceptionMessage(Exception exception) {
        AssertUtil.assertExceptionMessageExist(exception);
    }

}
