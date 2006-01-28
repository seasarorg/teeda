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

import java.util.Map;

import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

import junit.framework.TestCase;


public class TestServletInitParameter extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletInitParameter.class);
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
     * Constructor for TestServletInitParameter.
     * @param arg0
     */
    public TestServletInitParameter(String arg0) {
        super(arg0);
    }

    public void testServletInitParameter(){
        MockServletContext context = new MockServletContextImpl(null);
        context.setInitParameter("a", "A");
        Map map = new ServletInitParameterMap(context);
        assertEquals("A", map.get("a"));
        try{
            map.put("b", "B");
            fail();
        }catch(Exception e){
            assertTrue(true);
        }
        try{
            map.remove("b");
            fail();
        }catch(Exception e){
            assertTrue(true);
        }
        try{
            map.clear();
            fail();
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(1, map.size());
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsValue("A"));
        context.setInitParameter("b", "B");
        assertEquals("B", map.get("b"));
        
    }
    
}
