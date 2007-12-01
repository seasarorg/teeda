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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.util.ErrorPageManager;
import org.seasar.teeda.extension.helper.impl.PathHelperImpl;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;
import org.seasar.teeda.extension.html.impl.page.ThrowsExceptionPage;
import org.seasar.teeda.extension.html.impl.web.aaa.BbbPage;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 *
 */
public class HtmlComponentInvokerImplTest extends TeedaExtensionTestCase {

    public void testGetNextPageTransition() throws Exception {
        HtmlComponentInvokerImpl invoker = new HtmlComponentInvokerImpl();
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        PathHelperImpl pathHelper = new PathHelperImpl();
        pathHelper.setNamingConvention(nc);
        invoker.setNamingConvention(nc);
        invoker.setPathHelper(pathHelper);
        assertEquals("aaa_bbb", invoker.getNextPageTransition(BbbPage.class));
    }

    public void testInvoke() throws Exception {
        HtmlComponentInvokerImpl invoker = new HtmlComponentInvokerImpl();
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        invoker.setNamingConvention(nc);
        PageDescCacheImpl pc = new PageDescCacheImpl();
        pc.setHtmlSuffix(new HtmlSuffixImpl());
        pc.setNamingConvention(nc);
        invoker.setPageDescCache(pc);
        createPageDesc(BbbPage.class, "aaa_bbb");
        invoker.invoke(getFacesContext(), "aaa_bbb",
                HtmlComponentInvoker.INITIALIZE);
        assertTrue(invoker.isInitialized(getFacesContext(), "aaa_bbb"));
        BbbPage bbbPage = (BbbPage) getComponent(BbbPage.class);
        assertTrue(bbbPage.isInitialized());
    }

    public void testInvoke_CheckedException() throws Exception {
        HtmlComponentInvokerImpl invoker = new HtmlComponentInvokerImpl();
        invoker.setErrorPageManager(new ErrorPageManager() {
            public void addErrorPage(Class exceptionType, String location) {
            }

            public boolean handleException(Throwable exception,
                    FacesContext context, ExternalContext extContext)
                    throws IOException {
                assertTrue(exception instanceof IOException);
                return true;
            }
        });
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        invoker.setNamingConvention(nc);
        createPageDesc(ThrowsExceptionPage.class, "throwsException");
        invoker.invoke(getFacesContext(), "throwsException",
                HtmlComponentInvoker.INITIALIZE);
    }

    public void testInvoke_RuntimeException() throws Exception {
        HtmlComponentInvokerImpl invoker = new HtmlComponentInvokerImpl();
        invoker.setErrorPageManager(new ErrorPageManager() {
            public void addErrorPage(Class exceptionType, String location) {
            }

            public boolean handleException(Throwable exception,
                    FacesContext context, ExternalContext extContext)
                    throws IOException {
                assertTrue(exception instanceof IllegalStateException);
                return true;
            }
        });
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        invoker.setNamingConvention(nc);
        createPageDesc(ThrowsExceptionPage.class, "throwsException");
        invoker.invoke(getFacesContext(), "throwsException",
                HtmlComponentInvoker.PRERENDER);
    }
}
