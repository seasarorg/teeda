package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import javax.servlet.http.Cookie;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestCookieMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestCookieMap.class);
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
     * Constructor for TestCookieMap.
     * @param arg0
     */
    public TestCookieMap(String arg0) {
        super(arg0);
    }

    public void testCookieMap(){
        MockHttpServletRequest request = getRequest();
        Cookie cookie = new Cookie("a", "A");
        request.addCookie(cookie);
        CookieMap map = new CookieMap(request);
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsValue("A"));
        assertFalse(map.isEmpty());
        assertEquals(cookie, map.getAttribute("a"));
        assertEquals(1, map.size());
        for(Enumeration e = map.getAttributeNames();e.hasMoreElements();){
            assertEquals(cookie.getName(), e.nextElement());
        }
        
    }
}
