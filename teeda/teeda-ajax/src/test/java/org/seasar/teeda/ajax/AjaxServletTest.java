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
package org.seasar.teeda.ajax;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.SPrintWriter;

/**
 * @author yone
 */
public class AjaxServletTest extends S2FrameworkTestCase {

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        super.include("ajaxTest.dicon");
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestApplicationFactoryImpl.
     * @param arg0
     */
    public AjaxServletTest(String arg0) {
        super(arg0);
    }

    public void testGetComponent_NotFound() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        getRequest().addParameter("component", "Hoge");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(getRequest(), getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testGetComponent_NotPublicNoMeta() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        getRequest().addParameter("component", "ajaxBean2");
        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(getRequest(), getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testAjaxMethod_noMeta() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        getRequest().addParameter("component", "ajaxBean2");
        getRequest().addParameter("action", "ajaxHoge");
        // ## Act ##
        // ## Assert ##
        servlet.doGet(getRequest(), getResponse());
    }

    public void testGetComponent_NotPublicBadMeta() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        getRequest().addParameter("component", "ajaxBean3");
        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(getRequest(), getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testAction_Default() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testAction_BadMethodName() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "hoge");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testSetParameter_ArgString() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxHoge");
        final String ARG1 = "ABCDEFG";
        request.addParameter("arg1", ARG1);
        request.addParameter("arg2", "2");
        MockSPrintWriter writer = new MockSPrintWriter();
        MyMockHttpServletResponseImpl response = new MyMockHttpServletResponseImpl(
                getRequest());
        response.setWriter(writer);

        // ## Act ##
        servlet.doGet(request, response);

        // ## Assert ##
        assertEquals("{arg1:\"ABCDEFG\",arg2:2}", writer.getResult());
        assertEquals(ARG1, ((AjaxBean1) getComponent("ajaxBean1")).getArg1());
    }

    public void testSetParameter_ArgInteger() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxHoge");
        final String ARG2 = "22222";
        request.addParameter("arg2", ARG2);
        MockSPrintWriter writer = new MockSPrintWriter();
        MyMockHttpServletResponseImpl response = new MyMockHttpServletResponseImpl(
                getRequest());
        response.setWriter(writer);

        // ## Act ##
        servlet.doGet(request, response);

        // ## Assert ##
        Integer arg2 = new Integer(ARG2);
        assertEquals("{arg1:null,arg2:22222}", writer.getResult());
        assertEquals(arg2.intValue(), ((AjaxBean1) getComponent("ajaxBean1"))
                .getArg2());
    }

    public void testDefaultResponseCotentType() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        MockHttpServletResponse response = getResponse();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxHoge");

        // ## Act ##
        servlet.doGet(request, response);

        // ## Assert ##
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());
    }

    public void testArgs_isNull() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxFoo");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testArgs_BadArgType() throws Exception {
        // target method is "public Object ajaxFoo(int arg1, String arg2)" 
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxFoo");
        request.addParameter("AjaxParam0", "aaa");
        request.addParameter("AjaxParam1", "6");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testArgs_BadArgc() throws Exception {
        // target method is "public Object ajaxFoo(int arg1, String arg2)" 
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxFoo");
        request.addParameter("AjaxParam0", "4");
        request.addParameter("AjaxParam1", "abc");
        request.addParameter("AjaxParam2", "def");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testArgs_BadArgvOrder() throws Exception {
        // target method is "public Object ajaxFoo(int arg1, String arg2)" 
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxFoo");
        request.addParameter("AjaxParam1", "4");
        request.addParameter("AjaxParam0", "abc");

        // ## Act ##
        // ## Assert ##
        try {
            servlet.doGet(request, getResponse());
            fail();
        } catch (ServletException e) {
            assertMessageExist(e);
        }
    }

    public void testSetParameter_ArgIntegerNull() throws Exception {
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        request.addParameter("component", "ajaxBean4");
        request.addParameter("action", "ajaxTest");
        final String EMPTY_STRING = "";
        request.addParameter("argNum", EMPTY_STRING);
        request.addParameter("arg1", EMPTY_STRING);
        MockSPrintWriter writer = new MockSPrintWriter();
        MyMockHttpServletResponseImpl response = new MyMockHttpServletResponseImpl(
                getRequest());
        response.setWriter(writer);

        // ## Act ##
        servlet.doGet(request, response);

        // ## Assert ##
        assertEquals("{arg1:0,argNum:null}", writer.getResult());
        assertEquals(null, ((AjaxBean4) getComponent("ajaxBean4")).getArgNum());
    }

    public void testArgs_InvokeSuccess() throws Exception {
        // target method is "public Object ajaxFoo(int arg1, String arg2)" 
        // ## Arrange ##
        AjaxServlet servlet = new AjaxServlet();
        servlet.init(getServletConfig());
        MockHttpServletRequest request = getRequest();
        MockSPrintWriter writer = new MockSPrintWriter();
        MyMockHttpServletResponseImpl response = new MyMockHttpServletResponseImpl(
                getRequest());
        response.setWriter(writer);
        request.addParameter("component", "ajaxBean1");
        request.addParameter("action", "ajaxFoo");
        request.addParameter("AjaxParam0", "1");
        request.addParameter("AjaxParam1", "abc");

        // ## Act ##
        servlet.doGet(request, response);

        // ## Assert ##
        assertEquals("{arg1:null,arg2:0}", writer.getResult());
    }

    private static void assertMessageExist(Exception e) {
        String message = e.getMessage();
        System.out.println(message);
        assertNotNull(message);
        assertTrue(message.trim().length() > 0);
    }

    private static class MyMockHttpServletResponseImpl extends
            MockHttpServletResponseImpl {

        private PrintWriter writer = new SPrintWriter();

        public MyMockHttpServletResponseImpl(HttpServletRequest request) {
            super(request);
        }

        public PrintWriter getWriter() {
            return writer;
        }

        public void setWriter(PrintWriter writer) {
            this.writer = writer;
        }

    }

    private static class MockSPrintWriter extends SPrintWriter {

        private String result = null;

        public String getResult() {
            return result;
        }

        public void close() {
            result = out.toString();
            super.close();
        }

    }
}