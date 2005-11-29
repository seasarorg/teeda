package org.seasar.teeda.core.el.impl;

import javax.faces.el.ValueBinding;
import javax.faces.mock.MockApplication;
import javax.faces.mock.MockValueBinding;
import javax.faces.mock.MockValueBindingWithTwoArgs;

import junit.framework.TestCase;

import org.seasar.teeda.core.el.ValueBindingContext;


public class TestValueBindingContext extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestValueBindingContext.class);
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
     * Constructor for TestValieBindingContext.
     * @param arg0
     */
    public TestValueBindingContext(String arg0) {
        super(arg0);
    }

    public void testCreateValueBinding1(){
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setValueBindingName("javax.faces.mock.MockValueBindingWithTwoArgs");
        ValueBinding vb = context.createValueBinding(new MockApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockValueBindingWithTwoArgs);
    }
    
    public void testCreateValueBinding2(){
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setValueBindingName("javax.faces.mock.MockValueBinding");
        ValueBinding vb = context.createValueBinding(new MockApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockValueBinding);
    }
    
    public void testCreateValueBinding3(){
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setValueBindingName("javax.faces.mock.MockValueBindingWithTwoArgs");
        ValueBinding vb = context.createValueBinding(new MockApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockValueBindingWithTwoArgs);
        
        context.setValueBindingName("javax.faces.mock.MockValueBinding");
        ValueBinding vbNew = context.createValueBinding(new MockApplication(), "hoge");
        assertTrue(vb.equals(vbNew));
    }   
    
}
