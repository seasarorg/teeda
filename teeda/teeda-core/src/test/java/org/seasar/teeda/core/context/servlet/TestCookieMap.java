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
