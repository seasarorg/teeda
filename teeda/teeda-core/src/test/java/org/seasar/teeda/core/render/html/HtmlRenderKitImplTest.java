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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.framework.util.SPrintWriter;
import org.seasar.teeda.core.application.impl.DefaultComponentLookupStrategy;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.render.html.support.HtmlRenderKitKeyGenerateUtil;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 * @author manhole
 */
public class HtmlRenderKitImplTest extends TeedaTestCase {

    public void testAddRenderer() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();

        renderKit.setContainer(getContainer());
        try {
            renderKit.addRenderer(null, "a", new MockRenderer());
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        try {
            renderKit.addRenderer("a", null, new MockRenderer());
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        try {
            renderKit.addRenderer("a", "aa", null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        MockRenderer mock = new MockRenderer();
        renderKit.addRenderer("a", "b", mock);
        Renderer r = renderKit.getRenderer("a", "b");
        assertNotNull(r);
        assertEquals(mock, r);
    }

    public void testGetRenderer() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        renderKit.setContainer(getContainer());
        MockRenderer mock = new MockRenderer();
        renderKit.addRenderer("a", "b", mock);
        Renderer r;
        try {
            r = renderKit.getRenderer(null, "b");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        try {
            r = renderKit.getRenderer("a", null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
        r = renderKit.getRenderer("a", "b");
        assertNotNull(r);
        assertEquals(mock, r);
    }

    public void testCreateOutStream() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        MockOutputStream out = new MockOutputStream();
        ResponseStream stream = renderKit.createResponseStream(out);
        stream.write(0);
        stream.write(1);
        List list = out.getResultList();
        assertEquals(new Integer(0), list.get(0));
        assertEquals(new Integer(1), list.get(1));
    }

    public void testCreateResponseWriter2() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        ResponseWriter w = renderKit.createResponseWriter(new SPrintWriter(),
                "text/html, hoge, foo", "Windows-31J");
        assertNotNull(w);
        assertTrue(w instanceof HtmlResponseWriter);
        assertEquals("Windows-31J", w.getCharacterEncoding());
        assertEquals("text/html", w.getContentType());
        assertTrue(((HtmlResponseWriter) w).getWriter() instanceof SPrintWriter);
    }

    public void testCreateResponseWriter_ReturnsDefaultContentTypeWhenContentTypeListNoMatch()
            throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        ResponseWriter w = renderKit.createResponseWriter(new SPrintWriter(),
                "a, b", "Windows-31J");
        assertEquals("[text/html] is Default ContentType", "text/html", w
                .getContentType());
    }

    public void testAddRenderer_withFacesConfig() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        renderKit.addRenderer("a", "b", new MockRenderer());
        Renderer renderer = renderKit.getRenderer("a", "b");
        assertNotNull(renderer);
        assertFalse(renderer.getRendersChildren());
        renderKit.addRenderer("a", "b", new MockRenderer() {

            public boolean getRendersChildren() {
                return true;
            }

        });
        renderer = renderKit.getRenderer("a", "b");
        assertNotNull(renderer);
        assertTrue(renderer.getRendersChildren());
    }

    public void testAddRenderer_withS2Register() throws Exception {
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        getContainer().register(new MockRenderer().getClass(),
                HtmlRenderKitKeyGenerateUtil.getGeneratedKey("a", "b"));
        Renderer renderer = renderKit.getRenderer("a", "b");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer);
    }

    public void testAddRenderer_withDicon1() throws Exception {
        include("child.dicon");
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        Renderer renderer = renderKit.getRenderer("a", "b");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer2);
        renderer = renderKit.getRenderer("b", "c");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer3);
        renderer = renderKit.getRenderer("c", "d");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer4);
    }

    public void testAddRenderer_withFacesConfigAndDicon() throws Exception {
        include("child.dicon");
        HtmlRenderKitImpl renderKit = createHtmlRenderKitImpl();
        renderKit.addRenderer("b", "c", new MockRenderer5());
        Renderer renderer = renderKit.getRenderer("b", "c");
        assertNotNull(renderer);
        assertTrue(renderer instanceof MockRenderer5);
        assertTrue(renderer.getRendersChildren());
    }

    private HtmlRenderKitImpl createHtmlRenderKitImpl() {
        HtmlRenderKitImpl renderKit = new HtmlRenderKitImpl();
        renderKit.setContainer(getContainer());
        renderKit
                .setComponentLookupStrategy(new DefaultComponentLookupStrategy());
        return renderKit;
    }

    private static class MockOutputStream extends OutputStream {

        private List list = new LinkedList();

        public void write(int arg0) throws IOException {
            list.add(new Integer(arg0));
        }

        public List getResultList() {
            return list;
        }
    }

    public static class MockRenderer1 extends MockRenderer {

    }

    public static class MockRenderer2 extends MockRenderer {

    }

    public static class MockRenderer3 extends MockRenderer {

    }

    public static class MockRenderer4 extends MockRenderer {

    }

    public static class MockRenderer5 extends MockRenderer {

        public boolean getRendersChildren() {
            return true;
        }

    }

}
