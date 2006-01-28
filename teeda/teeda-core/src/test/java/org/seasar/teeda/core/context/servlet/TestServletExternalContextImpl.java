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
package org.seasar.teeda.core.context.servlet;

import javax.faces.context.ExternalContext;
import javax.faces.mock.servlet.MockServletRequestImpl;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;


public class TestServletExternalContextImpl extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletExternalContextImpl.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestServletExternalContextImpl.
     * @param arg0
     */
    public TestServletExternalContextImpl(String arg0) {
        super(arg0);
    }

    public void testGetRequestServletPath() {
        MockHttpServletRequest request = 
            new MockHttpServletRequestImpl(getServletContext(), "/hoge");
        ExternalContext context = 
            new ServletExternalContextImpl(getServletContext(), request, getResponse());
        assertEquals("/hoge", context.getRequestServletPath());
        
        ServletRequest request2 = new MockServletRequestImpl(getServletContext(), "/hoge");
        ExternalContext context2 = 
            new ServletExternalContextImpl(getServletContext(), request2, (ServletResponse)getResponse());
        assertNull(context2.getRequestServletPath());
        
    }

    public void testGetRequestPathInfo() {
        MockHttpServletRequest request = 
            new MockHttpServletRequestImpl(getServletContext(), "/");
        request.setPathInfo("hoge");
        ExternalContext context = 
            new ServletExternalContextImpl(getServletContext(), request, getResponse());
        assertEquals("hoge", context.getRequestPathInfo());

        ServletRequest request2 = new MockServletRequestImpl(getServletContext(), "/hoge");
        ExternalContext context2 = 
            new ServletExternalContextImpl(getServletContext(), request2, (ServletResponse)getResponse());
        assertNull(context2.getRequestPathInfo());
    }

}
