package org.seasar.teeda.core.util;

import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestNavigationHandlerUtil extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestNavigationHandlerUtil.class);
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
     * Constructor for TestNavigationHandlerUtil.
     * @param arg0
     */
    public TestNavigationHandlerUtil(String arg0) {
        super(arg0);
    }

    public void testHandleNavigation(){
        NavigationHandlerUtil.handleNavigation(getFacesContext(), "from", "to");
        MockNavigationHandler handler = 
            (MockNavigationHandler)getApplication().getNavigationHandler();
        assertEquals("from", handler.getFromAction());
        assertEquals("to", handler.getOutCome());
    }
}
