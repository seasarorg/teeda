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
package org.seasar.teeda.extension.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 * 
 */
public class VirtualResourceTest extends TeedaTestCase {

    public void testAddAndGetJsResources() throws Exception {
        final MockFacesContext context = getFacesContext();
        VirtualResource.addJsResource(context, "/aaa/bbb/ccc.js");
        final Set resources = VirtualResource.getJsResources(context);
        assertNotNull(resources);
        assertEquals("/aaa/bbb/ccc.js", resources.iterator().next());
    }

    public void testAddAndGetCssResources() throws Exception {
        final MockFacesContext context = getFacesContext();
        VirtualResource.addCssResource(context, "/aaa/bbb/ccc.css");
        final Set resources = VirtualResource.getCssResources(context);
        assertNotNull(resources);
        assertEquals("/aaa/bbb/ccc.css", resources.iterator().next());
    }

    public void testAddAndGetInlineJsResources() throws Exception {
        final MockFacesContext context = getFacesContext();
        VirtualResource
                .addInlineJsResource(context, "aaa", "<script></script>");
        final Map resources = VirtualResource.getInlineJsResources(context);
        assertNotNull(resources);
        assertEquals("<script></script>", resources.get("aaa"));
    }

    public void testAddAndGetInlineCssResources() throws Exception {
        final MockFacesContext context = getFacesContext();
        VirtualResource.addInlineCssResource(context, "aaa", "<style></style>");
        final Map resources = VirtualResource.getInlineCssResources(context);
        assertNotNull(resources);
        assertEquals("<style></style>", resources.get("aaa"));
    }

    public void testIsVirtualPath() throws Exception {
        final MockHttpServletRequest req = new MockHttpServletRequestImpl(
                getServletContext(), "hoge") {

            public String getRequestURI() {
                return "/teedaExtension/";
            }
        };
        assertTrue(VirtualResource.isVirtualPath(req));
    }

    public void testIsNotVirtualPath() throws Exception {
        final MockHttpServletRequest req = new MockHttpServletRequestImpl(
                getServletContext(), "hoge") {

            public String getRequestURI() {
                return "/it_is_not_virtual_path/";
            }
        };
        assertFalse(VirtualResource.isVirtualPath(req));
    }

}
