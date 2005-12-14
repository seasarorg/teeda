package org.seasar.teeda.core.application;

import org.seasar.extension.unit.S2TestCase;


public class TestApplicationFactoryImpl extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestApplicationFactoryImpl.class);
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
     * Constructor for TestApplicationFactoryImpl.
     * @param arg0
     */
    public TestApplicationFactoryImpl(String arg0) {
        super(arg0);
    }

    public void testApplicationFactoryImpl(){
        super.include("applicationTest.dicon");
        ApplicationFactoryImpl factory = new ApplicationFactoryImpl();
        assertNotNull(factory.getApplication());
    }
}
