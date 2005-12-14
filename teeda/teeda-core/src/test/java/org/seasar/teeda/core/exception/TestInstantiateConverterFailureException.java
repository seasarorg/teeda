package org.seasar.teeda.core.exception;

import junit.framework.TestCase;


public class TestInstantiateConverterFailureException extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner
                .run(TestInstantiateConverterFailureException.class);
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
     * Constructor for TestInstantiateConverterFailureException.
     * @param arg0
     */
    public TestInstantiateConverterFailureException(String arg0) {
        super(arg0);
    }
    
    public void testInstantiateConverterFailureException(){
        Object[] args = {"hoge", "foo"};
        InstantiateConverterFailureException e = 
            new InstantiateConverterFailureException(args, new Exception());
        assertEquals("ETDA0005", e.getMessageCode());
        assertEquals("foo", e.getArgs()[1]);
    }

}
