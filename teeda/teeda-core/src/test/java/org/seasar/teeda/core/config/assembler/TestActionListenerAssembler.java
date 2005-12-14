package org.seasar.teeda.core.config.assembler;

import javax.faces.event.ActionListener;

import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockSingleConstructorActionListener;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestActionListenerAssembler extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestActionListenerAssembler.class);
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
     * Constructor for TestActionListenerAssembler.
     * @param arg0
     */
    public TestActionListenerAssembler(String arg0) {
        super(arg0);
    }

    public void testSimpleAssembleActionListener(){
        MockApplication application = getApplication();
        String listenerName = "org.seasar.teeda.core.mock.MockActionListener";
        ActionListenerAssembler assembler = 
            new ActionListenerAssembler(listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockActionListener);
    }
    
    public void testMarshalAssembleActionListener(){
        MockApplication application = getApplication();
        String listenerName = "org.seasar.teeda.core.mock.MockSingleConstructorActionListener";
        ActionListenerAssembler assembler = 
            new ActionListenerAssembler(listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockSingleConstructorActionListener);
        
    }
}
