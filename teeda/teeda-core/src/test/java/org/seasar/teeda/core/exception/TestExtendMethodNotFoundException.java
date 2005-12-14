package org.seasar.teeda.core.exception;

import javax.faces.el.EvaluationException;
import javax.faces.el.MethodNotFoundException;


import org.seasar.teeda.core.exception.ExtendMethodNotFoundExceptin;
import org.seasar.teeda.core.mock.MockMethodBinding;

import junit.framework.TestCase;


public class TestExtendMethodNotFoundException extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestExtendMethodNotFoundException.class);
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
     * Constructor for TestExtendedMethodNotFoundException.
     * @param arg0
     */
    public TestExtendMethodNotFoundException(String arg0) {
        super(arg0);
    }
    
    public void testExtendedMethodNotFoundException(){
        MockMethodBinding mb = new MockMethodBinding("#{aaa}");
        ExtendMethodNotFoundExceptin e = 
            new ExtendMethodNotFoundExceptin(new MethodNotFoundException(), mb);
            assertNotNull(e.getArgs());
            assertNotNull(e.getMessage());
            assertNotNull(e.getMessageCode());
            assertEquals(mb, e.getMethodBinding());
            assertEquals("ETDA0002", e.getMessageCode());
    }

}
