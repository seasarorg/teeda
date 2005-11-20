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

    public void testGetAuthType() {
        //TODO getAuthType() を実装します。
    }

    public void testGetContext() {
        //TODO getContext() を実装します。
    }

    public void testGetInitParameter() {
        //TODO getInitParameter() を実装します。
    }

    public void testGetRemoteUser() {
        //TODO getRemoteUser() を実装します。
    }

    public void testGetRequest() {
        //TODO getRequest() を実装します。
    }

    public void testGetRequestContextPath() {
        //TODO getRequestContextPath() を実装します。
    }

    public void testGetRequestCookieMap() {
        //TODO getRequestCookieMap() を実装します。
    }

    public void testGetRequestHeaderMap() {
        //TODO getRequestHeaderMap() を実装します。
    }

    public void testGetRequestHeaderValuesMap() {
        //TODO getRequestHeaderValuesMap() を実装します。
    }

    public void testGetRequestLocale() {
        //TODO getRequestLocale() を実装します。
    }

    public void testGetRequestLocales() {
        //TODO getRequestLocales() を実装します。
    }

    public void testGetRequestMap() {
        //TODO getRequestMap() を実装します。
    }

    public void testGetRequestParameterMap() {
        //TODO getRequestParameterMap() を実装します。
    }

    public void testGetRequestParameterNames() {
        //TODO getRequestParameterNames() を実装します。
    }

    public void testGetRequestParameterValuesMap() {
        //TODO getRequestParameterValuesMap() を実装します。
    }

    public void testGetResourcePaths() {
        //TODO getResourcePaths() を実装します。
    }

    public void testGetResponse() {
        //TODO getResponse() を実装します。
    }

    public void testGetSession() {
        //TODO getSession() を実装します。
    }

    public void testGetSessionMap() {
        //TODO getSessionMap() を実装します。
    }

    public void testIsUserInRole() {
        //TODO isUserInRole() を実装します。
    }

    /*
     * void log のテスト中のクラス(String)
     */
    public void testLogString() {
        //TODO log() を実装します。
    }

    /*
     * void log のテスト中のクラス(String, Throwable)
     */
    public void testLogStringThrowable() {
        //TODO log() を実装します。
    }

    public void testRedirect() {
        //TODO redirect() を実装します。
    }

    public void testServletExternalContextImpl() {
        //TODO ServletExternalContextImpl() を実装します。
    }

    /*
     * URL getResource のテスト中のクラス(String)
     */
    public void testGetResourceString() {
        //TODO getResource() を実装します。
    }

    /*
     * InputStream getResourceAsStream のテスト中のクラス(String)
     */
    public void testGetResourceAsStreamString() {
        //TODO getResourceAsStream() を実装します。
    }

    /*
     * Principal getUserPrincipal のテスト中のクラス()
     */
    public void testGetUserPrincipal() {
        //TODO getUserPrincipal() を実装します。
    }

    /*
     * java.net.URL getResource のテスト中のクラス(String)
     */
    public void testGetResourceString1() {
        //TODO getResource() を実装します。
    }

    /*
     * java.io.InputStream getResourceAsStream のテスト中のクラス(String)
     */
    public void testGetResourceAsStreamString1() {
        //TODO getResourceAsStream() を実装します。
    }

    /*
     * java.security.Principal getUserPrincipal のテスト中のクラス()
     */
    public void testGetUserPrincipal1() {
        //TODO getUserPrincipal() を実装します。
    }

}
