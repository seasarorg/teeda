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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestServletRequestParameterValuesMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletRequestParameterValuesMap.class);
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
     * Constructor for TestServletRequestParameterValuesMap.
     * @param arg0
     */
    public TestServletRequestParameterValuesMap(String arg0) {
        super(arg0);
    }

    public void testServletRequestHeaderValuesMap(){
        MockHttpServletRequest request = getRequest();
        request.setParameter("a", new String[]{"A", "B"});
        ServletRequestParameterValuesMap map = new ServletRequestParameterValuesMap(request);
        Object o = map.getAttribute("a");
        assertNotNull(o);
        assertTrue(o instanceof String[]);
        String[] strs = (String[])o;
        assertEquals(2, strs.length);
        
        assertEquals("a", map.getAttributeNames().nextElement());
    }
}
