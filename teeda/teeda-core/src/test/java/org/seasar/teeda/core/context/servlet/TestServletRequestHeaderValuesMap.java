package org.seasar.teeda.core.context.servlet;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestServletRequestHeaderValuesMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletRequestHeaderValuesMap.class);
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
     * Constructor for TestServletRequestHeaderValuesMap.
     * @param arg0
     */
    public TestServletRequestHeaderValuesMap(String arg0) {
        super(arg0);
    }

    public void testServletRequestHeaderValuesMap(){
        MockHttpServletRequest request = getRequest();
        request.addHeader("a", "A");
        request.addHeader("a", "B");
        ServletRequestHeaderValuesMap map = new ServletRequestHeaderValuesMap(request);
        Object o = map.getAttribute("a");
        assertNotNull(o);
        assertTrue(o instanceof String[]);
        String[] strs = (String[])o;
        assertEquals(2, strs.length);
        
        assertEquals("a", map.getAttributeNames().nextElement());
    }
}
