package org.seasar.teeda.core.application;

import org.seasar.extension.unit.S2TestCase;


public class ApplicationFactoryImplTest extends S2TestCase {

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
    public ApplicationFactoryImplTest(String arg0) {
        super(arg0);
    }

    public void testApplicationFactoryImpl(){
        super.include("applicationTest.dicon");
        ApplicationFactoryImpl factory = new ApplicationFactoryImpl();
        assertNotNull(factory.getApplication());
    }
}
