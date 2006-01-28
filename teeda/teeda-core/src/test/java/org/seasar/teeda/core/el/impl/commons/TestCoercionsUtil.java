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
package org.seasar.teeda.core.el.impl.commons;

import java.util.ArrayList;

import junit.framework.TestCase;


public class TestCoercionsUtil extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestCoercionsUtil.class);
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
     * Constructor for TestCoercionsUtil.
     * @param arg0
     */
    public TestCoercionsUtil(String arg0) {
        super(arg0);
    }

    public void testCoerceToInteger(){
        assertEquals(new Integer(3), CoercionsUtil.coerceToInteger("3"));
        assertEquals(new Integer(1), CoercionsUtil.coerceToInteger(new Boolean(true)));
        assertEquals(new Integer(0), CoercionsUtil.coerceToInteger(new Boolean(false)));
        assertEquals(new Integer(1), CoercionsUtil.coerceToInteger(new Double(1.1)));
        assertNull(CoercionsUtil.coerceToInteger(new ArrayList()));
    }
    
    public void testCoerceToPrimitiveBoolean(){
        assertFalse(CoercionsUtil.coerceToPrimitiveBoolean(null));
        assertFalse(CoercionsUtil.coerceToPrimitiveBoolean(""));
        assertFalse(CoercionsUtil.coerceToPrimitiveBoolean("false"));
        assertFalse(CoercionsUtil.coerceToPrimitiveBoolean(Boolean.FALSE));
        assertTrue(CoercionsUtil.coerceToPrimitiveBoolean("true"));
        assertTrue(CoercionsUtil.coerceToPrimitiveBoolean(Boolean.TRUE));
    }
    
    public void testCoerce(){
        B b = new B();
        b.setStr("b");
        Object obj = CoercionsUtil.coerce(b, A.class);
        assertNotNull(obj);
        assertTrue(obj instanceof B);
        assertEquals("b", ((A)obj).getString());
        assertEquals("aaa", CoercionsUtil.coerce("aaa",String.class));
        assertEquals(new Integer(1), CoercionsUtil.coerce("1",Integer.class));
        assertEquals(new Character('a'), CoercionsUtil.coerce("a",Character.class));
        assertNull(CoercionsUtil.coerce(null,A.class));
    }
    
    public static class A{
        private String str_;
        public void setStr(String str){
            str_ = str;
        }
        public String getString(){
            return str_;
        }
    }
    
    public static class B extends A{
    }
    
}
