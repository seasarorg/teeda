package org.seasar.teeda.core.context.servlet;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestServletRequestMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletRequestMap.class);
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
     * Constructor for TestServletRequestMap.
     * @param arg0
     */
    public TestServletRequestMap(String arg0) {
        super(arg0);
    }

    public void testServletRequestMap(){
        MockHttpServletRequest request = getRequest();
        request.setAttribute("hoge", "foo");
        ServletRequestMap map = new ServletRequestMap(request);
        assertEquals("foo", map.getAttribute("hoge"));
        assertEquals("hoge", map.getAttributeNames().nextElement());
        
        map.setAttribute("bar", "baz");
        assertEquals("baz", request.getAttribute("bar"));
        assertEquals("baz", map.getAttribute("bar"));
        
        map.removeAttribute("baz");
        assertNull(map.getAttribute("baz"));
    }
}
