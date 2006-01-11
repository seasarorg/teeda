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

/**
 * @author manhole
 */
public class RendererTest extends TestCase {

    public void testConvertClientId1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.convertClientId(null, "fooClientId");
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testConvertClientId2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.convertClientId(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testDecode1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.decode(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testDecode2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeBegin1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeBegin(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeBegin2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeBegin(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeChildren1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeChildren(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeChildren2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeChildren(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeEnd1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testEncodeEnd2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testGetConvertedValue1() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.getConvertedValue(null, new NullUIComponent(),
                new Object());
            fail();
        } catch (NullPointerException npe) {
        }
    }

    public void testGetConvertedValue2() throws Exception {
        Renderer renderer = new NullRenderer();
        try {
            renderer.getConvertedValue(new NullFacesContext(), null,
                new Object());
            fail();
        } catch (NullPointerException npe) {
        }
    }

}
