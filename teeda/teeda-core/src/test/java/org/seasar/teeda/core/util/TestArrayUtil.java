package org.seasar.teeda.core.util;

import java.util.List;
import java.util.ArrayList;

import org.seasar.teeda.core.util.ArrayUtil;

import junit.framework.TestCase;


public class TestArrayUtil extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestArrayUtil.class);
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
     * Constructor for TestArrayUtil.
     * @param arg0
     */
    public TestArrayUtil(String arg0) {
        super(arg0);
    }

    public void testIsEmpty(){
        assertTrue(ArrayUtil.isEmpty(null));
        assertTrue(ArrayUtil.isEmpty(new Object[]{}));
        assertFalse(ArrayUtil.isEmpty(new Object[]{""}));
        assertFalse(ArrayUtil.isEmpty(new Object[]{"aaa"}));        
    }
    
}
