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
package org.seasar.teeda.core.context.servlet;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.internal.WindowIdUtil;
import javax.faces.mock.servlet.MockServletRequestImpl;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import junitx.framework.ArrayAssert;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;

/**
 * @author shot
 */
public class ServletExternalContextImplTest extends S2TestCase {

    public void testGetRequestServletPath() {
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                getServletContext(), "/hoge");
        ExternalContext context = new ServletExternalContextImpl(
                getServletContext(), request, getResponse());
        assertEquals("/hoge", context.getRequestServletPath());

        ServletRequest request2 = new MockServletRequestImpl(
                getServletContext(), "/hoge");
        ExternalContext context2 = new ServletExternalContextImpl(
                getServletContext(), request2, (ServletResponse) getResponse());
        assertNull(context2.getRequestServletPath());
    }

    public void testGetRequestPathInfo() {
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                getServletContext(), "/");
        request.setPathInfo("hoge");
        ExternalContext context = new ServletExternalContextImpl(
                getServletContext(), request, getResponse());
        assertEquals("hoge", context.getRequestPathInfo());

        ServletRequest request2 = new MockServletRequestImpl(
                getServletContext(), "/hoge");
        ExternalContext context2 = new ServletExternalContextImpl(
                getServletContext(), request2, (ServletResponse) getResponse());
        assertNull(context2.getRequestPathInfo());
    }

    public void testGetRequestMap() throws Exception {
        // ## Arrange ##
        MockHttpServletRequest request = getRequest();
        request.setAttribute("a", "A");
        request.setAttribute("b", "B");

        ServletExternalContextImpl externalContext = new ServletExternalContextImpl(
                getServletContext(), request, getResponse());

        // ## Act ##
        Map requestMap = externalContext.getRequestMap();

        // ## Assert ##
        assertEquals("A", requestMap.get("a"));
        assertEquals("B", requestMap.get("b"));
        assertEquals(null, requestMap.get("A"));
    }

    public void testGetRequestParameterMap() throws Exception {
        // ## Arrange ##
        MockHttpServletRequest request = getRequest();
        request.setParameter("a", "A");
        request.setParameter("b", "B");

        ServletExternalContextImpl externalContext = new ServletExternalContextImpl(
                getServletContext(), request, getResponse());

        // ## Act ##
        Map requestParameterMap = externalContext.getRequestParameterMap();

        // ## Assert ##
        assertEquals("A", requestParameterMap.get("a"));
        assertEquals("B", requestParameterMap.get("b"));
        assertEquals(null, requestParameterMap.get("A"));
    }

    public void testGetRequestParameterValuesMap() throws Exception {
        // ## Arrange ##
        MockHttpServletRequest request = getRequest();
        request.addParameter("a", "A1");
        request.addParameter("b", "B1");
        request.addParameter("a", "A2");
        request.addParameter("a", "A3");

        ServletExternalContextImpl externalContext = new ServletExternalContextImpl(
                getServletContext(), request, getResponse());

        // ## Act ##
        Map requestParameterValuesMap = externalContext
                .getRequestParameterValuesMap();

        // ## Assert ##
        String[] a = (String[]) requestParameterValuesMap.get("a");
        assertEquals(3, a.length);
        ArrayAssert.assertEquivalenceArrays(new String[] { "A1", "A2", "A3" },
                a);

        String[] b = (String[]) requestParameterValuesMap.get("b");
        assertEquals(1, b.length);
        assertEquals("B1", b[0]);

        String[] c = (String[]) requestParameterValuesMap.get("c");
        assertEquals(null, c);
    }

    public void testEncodeResourceURL() {
        ExternalContext context = new ServletExternalContextImpl(
                getServletContext(), getRequest(), getResponse());
        assertEquals("hoge", context.encodeResourceURL("hoge"));
        WindowIdUtil.setWindowId(context, "1");
        assertEquals("hoge?wid=1", context.encodeResourceURL("hoge"));
        assertEquals("hoge?aaa=bbb&wid=1", context
                .encodeResourceURL("hoge?aaa=bbb"));
    }
}
