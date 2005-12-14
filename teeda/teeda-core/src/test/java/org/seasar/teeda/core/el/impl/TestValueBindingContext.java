package org.seasar.teeda.core.el.impl;

import javax.faces.el.ValueBinding;

import junit.framework.TestCase;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.mock.MockMultipleArgsValueBinding;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestValueBindingContext extends TeedaTestCase {

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
        context.setELParser(new MockELParser());
        context.setValueBindingName("org.seasar.teeda.core.mock.MockMultipleArgsValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockMultipleArgsValueBinding);
    }
    
    public void testCreateValueBinding2(){
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setValueBindingName("org.seasar.teeda.core.mock.MockValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockValueBinding);
    }
    
    public void testCreateValueBinding3(){
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setELParser(new MockELParser());
        context.setValueBindingName("org.seasar.teeda.core.mock.MockMultipleArgsValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockMultipleArgsValueBinding);
        
        context.setValueBindingName("org.seasar.teeda.core.mock.MockValueBinding");
        ValueBinding vbNew = context.createValueBinding(getApplication(), "hoge");
        assertTrue(vb.equals(vbNew));
    }   
    
    private static class MockELParser implements ELParser{

        public Object parse(String expression) {
            throw new UnsupportedOperationException();
        }

        public void setExpressionProcessor(ExpressionProcessor processor) {
            throw new UnsupportedOperationException();
        }

        public ExpressionProcessor getExpressionProcessor() {
            throw new UnsupportedOperationException();
        }
        
    }
}
