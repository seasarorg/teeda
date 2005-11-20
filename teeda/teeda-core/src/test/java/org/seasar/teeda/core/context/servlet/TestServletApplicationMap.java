package org.seasar.teeda.core.context.servlet;

import javax.servlet.ServletContext;

import org.seasar.extension.unit.S2TestCase;


public class TestServletApplicationMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletApplicationMap.class);
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
     * Constructor for TestServletApplicationMap.
     * @param arg0
     */
    public TestServletApplicationMap(String arg0) {
        super(arg0);
    }

    public void testServletApplicationMap(){
        ServletContext context = getServletContext();
        context.setAttribute("a", "A");
        ServletApplicationMap map = new ServletApplicationMap(context);
        assertEquals("A", map.get("a"));
        assertEquals(1, map.size());
        
        map.put("a", "B");
        assertEquals("B", map.get("a"));
        
        map.clear();
        assertNull(map.get("a"));
        
        context.setAttribute("b", "B");
        assertEquals("B", map.get("b"));
        
        context.removeAttribute("b");
        assertNull(map.get("b"));
    }
}
