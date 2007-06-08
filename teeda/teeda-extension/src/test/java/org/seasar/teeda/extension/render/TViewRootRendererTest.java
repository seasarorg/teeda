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

import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author shot
 */
public class TViewRootRendererTest extends TeedaTestCase {

    public void testPopIncludedBody_listNull() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        requestMap.put(TViewRootRenderer.LIST_KEY, null);
        assertNull(TViewRootRenderer.popIncludedBody(getFacesContext()));
    }

    public void testPopIncludedBody_indexNull() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        requestMap.put(TViewRootRenderer.LIST_KEY, new ArrayList());
        requestMap.put(TViewRootRenderer.POP_INDEX_KEY, null);
        assertNull(TViewRootRenderer.popIncludedBody(getFacesContext()));
    }

    public void testPopIncludedBody() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        ArrayList list = new ArrayList();
        list.add(new IncludedBody("aaa", new ArrayList()));
        requestMap.put(TViewRootRenderer.LIST_KEY, list);
        requestMap.put(TViewRootRenderer.POP_INDEX_KEY, new Integer(0));
        IncludedBody popIncludedBody = TViewRootRenderer
                .popIncludedBody(getFacesContext());
        assertNotNull(popIncludedBody);
        assertEquals("aaa", popIncludedBody.getViewId());
    }

    public void testInvoke1() throws Exception {
        final boolean[] calls = { false, false };
        TViewRootRenderer renderer = new TViewRootRenderer();
        renderer.setHtmlComponentInvoker(new HtmlComponentInvoker() {

            public String getComponentName(String path, String methodName) {
                return null;
            }

            public String invoke(FacesContext context, String componentName,
                    String methodName) {
                if (HtmlComponentInvoker.INITIALIZE.equals(methodName)) {
                    calls[0] = true;
                } else if (HtmlComponentInvoker.PRERENDER.equals(methodName)) {
                    calls[1] = true;
                }
                return "hoge";
            }

            public String invokeInitialize(FacesContext context,
                    String componentName) {
                return null;
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return null;
            }

            public boolean isInitialized(FacesContext context) {
                return false;
            }

        });
        MockFacesContext context = getFacesContext();
        renderer.invoke(context, "aaa");
        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }

    public void testInvoke2_responseCompleted() throws Exception {
        final boolean[] calls = { false, false };
        TViewRootRenderer renderer = new TViewRootRenderer();
        renderer.setHtmlComponentInvoker(new HtmlComponentInvoker() {

            public String getComponentName(String path, String methodName) {
                return null;
            }

            public String invoke(FacesContext context, String componentName,
                    String methodName) {
                context.responseComplete();
                if (HtmlComponentInvoker.INITIALIZE.equals(methodName)) {
                    calls[0] = true;
                } else if (HtmlComponentInvoker.PRERENDER.equals(methodName)) {
                    calls[1] = true;
                }
                return "hoge";
            }

            public String invokeInitialize(FacesContext context,
                    String componentName) {
                return null;
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return null;
            }

            public boolean isInitialized(FacesContext context) {
                return false;
            }

        });
        MockFacesContext context = getFacesContext();
        renderer.invoke(context, "aaa");
        assertTrue(calls[0]);
        assertFalse(calls[1]);
    }

    public void testInvoke3_postback() throws Exception {
        final boolean[] calls = { false, false };
        PostbackUtil.setPostback(getFacesContext().getExternalContext()
                .getRequestMap(), true);
        TViewRootRenderer renderer = new TViewRootRenderer();
        renderer.setHtmlComponentInvoker(new HtmlComponentInvoker() {

            public String getComponentName(String path, String methodName) {
                return null;
            }

            public String invoke(FacesContext context, String componentName,
                    String methodName) {
                if (HtmlComponentInvoker.INITIALIZE.equals(methodName)) {
                    calls[0] = true;
                } else if (HtmlComponentInvoker.PRERENDER.equals(methodName)) {
                    calls[1] = true;
                }
                return "hoge";
            }

            public String invokeInitialize(FacesContext context,
                    String componentName) {
                return null;
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return null;
            }

            public boolean isInitialized(FacesContext context) {
                return false;
            }

        });
        MockFacesContext context = getFacesContext();
        renderer.invoke(context, "aaa");
        assertFalse(calls[0]);
        assertTrue(calls[1]);
    }

}
