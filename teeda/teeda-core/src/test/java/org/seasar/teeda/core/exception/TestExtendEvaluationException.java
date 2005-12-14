package org.seasar.teeda.core.exception;

import javax.faces.el.EvaluationException;
import org.seasar.teeda.core.mock.MockMethodBinding;
import junit.framework.TestCase;


public class TestExtendEvaluationException extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestExtendEvaluationException.class);
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
    public TestExtendEvaluationException(String arg0) {
        super(arg0);
    }
    
    public void testExtendedMethodNotFoundException(){
        MockMethodBinding mb = new MockMethodBinding("#{aaa}");
        ExtendEvaluationException e = 
            new ExtendEvaluationException(new EvaluationException(), mb);
        assertNotNull(e.getArgs());
        assertNotNull(e.getMessage());
        assertNotNull(e.getMessageCode());
        assertEquals(mb, e.getMethodBinding());
        assertEquals("ETDA0003", e.getMessageCode());
    }

}
