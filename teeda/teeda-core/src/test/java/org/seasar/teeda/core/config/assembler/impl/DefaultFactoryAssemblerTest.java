package org.seasar.teeda.core.config.assembler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;

import junit.framework.TestCase;

import org.seasar.teeda.core.config.assembler.impl.DefaultFactoryAssembler;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.config.element.impl.FactoryElementImpl;
import org.seasar.teeda.core.mock.MockApplicationFactory;


public class DefaultFactoryAssemblerTest extends TestCase {

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
     * Constructor for TestSimpleFactoriesAssembler.
     * @param arg0
     */
    public DefaultFactoryAssemblerTest(String arg0) {
        super(arg0);
    }

    public void testAssemble(){
        FactoryElement factoryElement = new FactoryElementImpl();
        factoryElement.addApplicationFactory("org.seasar.teeda.core.mock.MockApplicationFactory");
        List list = new ArrayList();
        list.add(factoryElement);
        DefaultFactoryAssembler assembler = new DefaultFactoryAssembler(list);
        assembler.assemble();
        
        ApplicationFactory appFactory = 
            (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        
        assertNotNull(appFactory);
        assertTrue(appFactory instanceof MockApplicationFactory);
        
    }
}

