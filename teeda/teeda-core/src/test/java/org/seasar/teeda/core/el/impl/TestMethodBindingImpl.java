package org.seasar.teeda.core.el.impl;

import javax.faces.el.MethodBinding;

import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestMethodBindingImpl extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestMethodBindingImpl.class);
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
     * Constructor for TestMethodBindingImpl.
     * @param arg0
     */
    public TestMethodBindingImpl(String arg0) {
        super(arg0);
    }

    public void testGetType1(){
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBindingBase vb = new ValueBindingImpl(getApplication(), "#{a.getName}", new CommonsELParser());
        MethodBinding mb = new MethodBindingImpl(vb, new Class[]{}, new CommonsELParser());
        assertSame(String.class, mb.getType(getFacesContext()));
    }
    
    public void testGetType2(){
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBindingBase vb = new ValueBindingImpl(getApplication(), "#{a.getNum}", new CommonsELParser());
        MethodBinding mb = new MethodBindingImpl(vb, new Class[]{}, new CommonsELParser());
        assertSame(int.class, mb.getType(getFacesContext()));
    }
    
    public void testInvoke1(){
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBindingBase vb = new ValueBindingImpl(getApplication(), "#{a.getName}", new CommonsELParser());
        MethodBinding mb = new MethodBindingImpl(vb, new Class[]{}, new CommonsELParser());
        assertEquals(a.getName(), mb.invoke(getFacesContext(), null));
    }
    
    public static class A{
        private String name_ = "aaa";
        private int num_ = 0;
        public A(){
        }
        
        public String getName(){
            return name_;
        }
        
        public int getNum(){
            return num_;
        }
        
        public void setName(String name){
            name_ = name;
        }
        
        public void setNum(int num){
            num_ = num;
        }
    }
}
