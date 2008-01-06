/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.util;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.impl.RedirectDescImpl;

/**
 * @author koichik
 * 
 */
public class ExtensionRedirectUrlResolverImplTest extends TeedaTestCase {

    public void testresolveUrl() throws Exception {
        ExtensionRedirectUrlResolverImpl resolver = new ExtensionRedirectUrlResolverImpl();
        String url1 = resolver.resolveUrl("/hoge.html", getFacesContext(),
                getRequest(), getResponse());
        System.out.println(url1);
        assertTrue(url1.startsWith("/hoge.html?te-uniquekey="));

        Thread.sleep(100);
        String url2 = resolver.resolveUrl("/hoge.html", getFacesContext(),
                getRequest(), getResponse());
        System.out.println(url2);
        assertTrue(url2.startsWith("/hoge.html?te-uniquekey="));

        assertFalse(url1.equals(url2));

        resolver.setAddUniqueKeyParameter(false);
        String url3 = resolver.resolveUrl("/hoge.html", getFacesContext(),
                getRequest(), getResponse());
        assertTrue(url3.startsWith("/hoge.html"));
    }

    public void testresolveUrl_withParam() throws Exception {
        ExtensionRedirectUrlResolverImpl resolver = new ExtensionRedirectUrlResolverImpl();
        String url1 = resolver.resolveUrl("/hoge.html?wid=1",
                getFacesContext(), getRequest(), getResponse());
        System.out.println(url1);
        assertTrue(url1.startsWith("/hoge.html?wid=1&te-uniquekey="));
    }

    public void testresolveUrl_override() throws Exception {
        ExtensionRedirectUrlResolverImpl resolver = new ExtensionRedirectUrlResolverImpl();
        String url1 = resolver.resolveUrl(
                "/hoge.html?wid=1&te-uniquekey=1&newwindow=true",
                getFacesContext(), getRequest(), getResponse());
        System.out.println(url1);
        assertTrue(url1.startsWith("/hoge.html?wid=1&te-uniquekey="));
        assertTrue(url1.endsWith("&newwindow=true"));
    }

    public void testBuildRedirectUrl() throws Exception {
        MockHttpServletRequest request = getRequest();
        ExtensionRedirectUrlResolverImpl resolver = new ExtensionRedirectUrlResolverImpl();
        assertEquals("https://localhost/hoge.html", resolver.buildRedirectUrl(
                "/hoge.html", getRequest(), new RedirectDescImpl("https", -1)));
    }

}
