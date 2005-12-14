package org.seasar.teeda.core.util;

import junit.framework.TestCase;


public class TestTagConvertUtil extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestTagConvertUtil.class);
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
     * Constructor for TestTagConvertUtil.
     * @param arg0
     */
    public TestTagConvertUtil(String arg0) {
        super(arg0);
    }

    public void testConvertToSetter(){
        String[] strs = 
            TagConvertUtil.convertToSetter("converter-for-class");
        assertEquals("setConverterForClass", strs[0]);
        assertEquals("addConverterForClass", strs[1]);
    }
    
    public void testWithoutAnySetter(){        
        String s = "converter";
        String[] strs = 
            TagConvertUtil.convertToSetter(s);
        assertEquals("setConverter", strs[0]);
        assertEquals("addConverter", strs[1]);
    }
}
