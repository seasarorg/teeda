package org.seasar.teeda.core.el.impl.commons;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.apache.commons.el.parser.ELParser;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestCommonsExpressionProcessorImpl extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestCommonsExpressionProcessorImpl.class);
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
     * Constructor for TestCommonsExpressionProcessorImpl.
     * @param arg0
     */
    public TestCommonsExpressionProcessorImpl(String arg0) {
        super(arg0);
    }

    public void testProcessExpression(){
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a.b}"));
        try{
            Object obj = parser.ExpressionString();
            processor.processExpression(obj, Object.class);
        }catch(Exception ignore){
        }
        try{
            processor.processExpression(new Object(), Object.class);
            fail();
        }catch(IllegalStateException e){
            success();
        }
    }
    
    public void testEvaluate(){
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a}"));
        Object expression = null;
        try{
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        }catch(Exception ignore){
        }
        MockSimpleVariableResolver resolver = new MockSimpleVariableResolver();
        A a = new A();
        resolver.putValue("a", a);
        MockApplication app = getApplication();
        app.setVariableResolver(resolver);
        Object o = processor.evaluate(getFacesContext(), expression);
        assertSame(a, o);
    }

    public void testEvaluate2(){
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("b${a}"));
        Object expression = null;
        try{
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        }catch(Exception ignore){
        }
        MockSimpleVariableResolver resolver = new MockSimpleVariableResolver();
        A a = new A();
        resolver.putValue("a", "A");
        MockApplication app = getApplication();
        app.setVariableResolver(resolver);
        Object o = processor.evaluate(getFacesContext(), expression);
        assertEquals("bA", o);
    }

    public void testToIndex(){
        notDoneYet();
    }
    
    public void testResolveBase(){
        notDoneYet();
    }
    
    public void testGetCoercedObject(){
        notDoneYet();
    }
    
    public static class MockSimpleVariableResolver extends VariableResolver{
        private Map values_ = new HashMap();
        public MockSimpleVariableResolver(){
        }
        public void putValue(String key, Object value){
            values_.put(key, value);
        }
        public Object resolveVariable(FacesContext context, String name) throws EvaluationException {
            return values_.get(name);
        }
    }
    
    public static class A{
        private String name_ = "name";
        public A(){
        }
        public String getName(){
            return name_;
        }
    }
}
