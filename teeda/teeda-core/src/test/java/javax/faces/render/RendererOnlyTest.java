/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.NullRenderer;

public class RendererOnlyTest extends AbstractRendererTest {

    public final void testEncodeChildren_simple() throws Exception {
        Renderer renderer = createRenderer();
        MockFacesContext context = getFacesContext();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        final boolean[] calls = new boolean[3];
        MockUIComponent child1 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return false;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

        };
        child1.setId("child1");
        parent.getChildren().add(child1);
        renderer.encodeChildren(context, parent);
        assertTrue(calls[0]);
        assertFalse(calls[1]);
        assertTrue(calls[2]);
    }

    public final void testEncodeChidlren_simple2() throws Exception {
        Renderer renderer = createRenderer();
        MockFacesContext context = getFacesContext();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        final boolean[] calls = new boolean[3];
        MockUIComponent child1 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return true;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

        };
        child1.setId("child1");
        parent.getChildren().add(child1);
        renderer.encodeChildren(context, parent);
        assertTrue(calls[0]);
        assertTrue(calls[1]);
        assertTrue(calls[2]);
    }

    public final void testEncodeChidlren_simple3() throws Exception {
        Renderer renderer = createRenderer();
        MockFacesContext context = getFacesContext();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        final boolean[] calls = new boolean[3];
        MockUIComponent child1 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return true;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
                context.responseComplete();
            }

        };
        child1.setId("child1");
        parent.getChildren().add(child1);
        MockUIComponent child2 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return true;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = false;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = false;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = false;
            }

        };
        child2.setId("child2");
        parent.getChildren().add(child2);
        renderer.encodeChildren(context, parent);
        assertTrue(calls[0]);
        assertTrue(calls[1]);
        assertTrue(calls[2]);
    }

    public final void testEncodeChidlren_hasGrandchild() throws Exception {
        Renderer renderer = createRenderer();
        MockFacesContext context = getFacesContext();
        MockUIComponent parent = new MockUIComponent();
        parent.setId("parent");
        final boolean[] calls = new boolean[3];
        MockUIComponent child1 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return false;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls[2] = true;
            }

        };
        child1.setId("child1");
        parent.getChildren().add(child1);
        final boolean[] calls2 = new boolean[3];
        MockUIComponent child2 = new MockUIComponent() {

            public boolean getRendersChildren() {
                return true;
            }

            public void encodeBegin(FacesContext context) throws IOException {
                calls2[0] = true;
            }

            public void encodeChildren(FacesContext context) throws IOException {
                calls2[1] = true;
            }

            public void encodeEnd(FacesContext context) throws IOException {
                calls2[2] = true;
            }

        };
        child2.setId("child2");
        child1.getChildren().add(child2);
        renderer.encodeChildren(context, parent);
        assertTrue(calls[0]);
        assertFalse(calls[1]);
        assertTrue(calls[2]);
        assertTrue(calls2[0]);
        assertTrue(calls2[1]);
        assertTrue(calls2[2]);
    }

    protected Renderer createRenderer() {
        return new NullRenderer();
    }

}
