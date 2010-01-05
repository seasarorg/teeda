/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author shot
 */
public class TViewRootRendererTest extends TeedaTestCase {

    public void testInvoke1() throws Exception {
        final boolean[] calls = { false, false };
        TViewRootRenderer renderer = new TViewRootRenderer();
        renderer.setHtmlComponentInvoker(new HtmlComponentInvoker() {

            public String getComponentName(String path, String methodName) {
                return "hoge";
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
                return invoke(context, componentName,
                        HtmlComponentInvoker.INITIALIZE);
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return invoke(context, componentName,
                        HtmlComponentInvoker.PRERENDER);
            }

            public boolean isInitialized(FacesContext context,
                    String componentName) {
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
                return "hoge";
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
                return invoke(context, componentName,
                        HtmlComponentInvoker.INITIALIZE);
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return invoke(context, componentName,
                        HtmlComponentInvoker.PRERENDER);
            }

            public boolean isInitialized(FacesContext context,
                    String componentName) {
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
                return "hoge";
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
                return invoke(context, componentName,
                        HtmlComponentInvoker.INITIALIZE);
            }

            public String invokePrerender(FacesContext context,
                    String componentName) {
                return invoke(context, componentName,
                        HtmlComponentInvoker.PRERENDER);
            }

            public boolean isInitialized(FacesContext context,
                    String componentName) {
                return false;
            }

        });
        MockFacesContext context = getFacesContext();
        renderer.invoke(context, "aaa");
        assertFalse(calls[0]);
        assertTrue(calls[1]);
    }

}
