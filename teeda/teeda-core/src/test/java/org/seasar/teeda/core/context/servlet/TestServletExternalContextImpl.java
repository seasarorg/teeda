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
