package org.seasar.teeda.core.context.servlet;

import javax.servlet.http.HttpSession;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;


public class TestHttpSessionMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestHttpSessionMap.class);
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
     * Constructor for TestHttpSessionMap.
     * @param arg0
     */
    public TestHttpSessionMap(String arg0) {
        super(arg0);
    }

    public void testHttpSessionMap(){
        MockHttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("aaa", "bbb");
        HttpSessionMap map = new HttpSessionMap(request);
        assertEquals("bbb", map.getAttribute("aaa"));
        assertEquals("aaa", map.getAttributeNames().nextElement());
        
        map.removeAttribute("aaa");
        assertNull(map.getAttribute("aaa"));
        
        map.setAttribute("bbb","ccc");
        assertEquals("ccc", map.getAttribute("bbb"));
        
        session.setAttribute("zzz", "ZZZ");
        assertEquals("ZZZ", map.getAttribute("zzz"));
        
        MockHttpServletRequest request2 = 
            new MockHttpServletRequestImpl(getServletContext(), "/");
        HttpSessionMap map2 = new HttpSessionMap(request2);
        assertNull(map2.getAttribute("aaa"));
        map2.setAttribute("aaa", "AAA");
        assertEquals("AAA", map2.getAttribute("aaa"));
    }
}
