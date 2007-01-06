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
package org.seasar.teeda.core.util;

import javax.faces.application.ViewHandler;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;

/**
 * @author shot
 */
public class ExternalContextUtilTest extends TestCase {

    public void testGetViewId_getFromRequestPathInfo() throws Exception {
        MockExternalContext context = new MockExternalContextImpl();
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                new MockServletContextImpl("/context"), "/bbb");
        request.setPathInfo("aaa");
        context.setMockHttpServletRequest(request);
        assertEquals("aaa", ExternalContextUtil.getViewId(context));
    }
    
    public void testGetViewId_servletPath() throws Exception {
        MockExternalContext context = new MockExternalContextImpl();
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                new MockServletContextImpl("/context"), "/bbb");
        context.setMockHttpServletRequest(request);
        assertEquals("/bbb", ExternalContextUtil.getViewId(context));
    }
    
    public void testGetViewId_servletPath2() throws Exception {
        MockExternalContext context = new MockExternalContextImpl();
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                new MockServletContextImpl("/context"), "/bbb.html");
        context.setMockHttpServletRequest(request);
        assertEquals("/bbb.jsp", ExternalContextUtil.getViewId(context));
    }

    public void testGetViewId_initParameter() throws Exception {
        MockExternalContext context = new MockExternalContextImpl();
        MockServletContext servletContext = new MockServletContextImpl("/context");
        servletContext.setInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME, ".html");
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                servletContext, "/bbb.hoge");
        context.setMockHttpServletRequest(request);
        context.setMockServletContext(servletContext);
        assertEquals("/bbb.html", ExternalContextUtil.getViewId(context));
    }

}
