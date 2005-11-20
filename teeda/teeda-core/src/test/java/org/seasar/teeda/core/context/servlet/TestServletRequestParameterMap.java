package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestServletRequestParameterMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletRequestParameterMap.class);
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
     * Constructor for TestServletRequestParameterMap.
     * @param arg0
     */
    public TestServletRequestParameterMap(String arg0) {
        super(arg0);
    }

    public void testServletRequestHeaderMap(){
        MockHttpServletRequest request = getRequest();
        request.setParameter("a", "A");
        ServletRequestParameterMap map = new ServletRequestParameterMap(request);
        assertEquals("A", map.getAttribute("a"));
        Enumeration e = map.getAttributeNames();
        assertNotNull(e);
        assertEquals("a", e.nextElement());
    }
}
