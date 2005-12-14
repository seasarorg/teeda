package org.seasar.teeda.core.exception;

import junit.framework.TestCase;


public class TestExtendFacesException extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestExtendFacesException.class);
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
     * Constructor for TestExtendFacesException.
     * @param arg0
     */
    public TestExtendFacesException(String arg0) {
        super(arg0);
    }

    public void testGetMessage(){
        ExtendFacesException e = new ExtendFacesException();
        assertNotNull(e.getMessageCode());
        assertNotNull(e.getMessage());
        assertNotNull(e.getSimpleMessage());
        System.out.println(e);
    }
}
