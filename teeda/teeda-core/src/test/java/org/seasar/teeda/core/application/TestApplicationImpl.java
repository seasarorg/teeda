package org.seasar.teeda.core.application;

import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.exception.IllegalClassTypeException;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullValidator;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestApplicationImpl extends TeedaTestCase {

    private ApplicationImpl app_;
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestApplicationImpl.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        app_ = new ApplicationImpl();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestApplicationImpl.
     * @param arg0
     */
    public TestApplicationImpl(String arg0) {
        super(arg0);
    }

    public void testAddComponent(){
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock" , mock.getClass().getName());
        UIComponent c = app_.createComponent("mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
        try{
            app_.addComponent("mock2", "java.lang.String");
            fail();
        }catch(IllegalClassTypeException e){
            assertEquals(UIComponent.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }
    }
    
    public void testCreateComponent1(){
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock" , mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, mock);
        UIComponent c = app_.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testCreateComponent2(){
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock" , mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, "mock");
        UIComponent c = app_.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testAddConverter1(){
        app_ = new ApplicationImpl();
        app_.addConverter("mock.converter", "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app_.createConverter("mock.converter");
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }
    
    public void testAddConverter2(){
        app_ = new ApplicationImpl();
        app_.addConverter(Hoge.class, "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app_.createConverter(Hoge.class);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }
    
    public void testCreateConverterForInterface(){
        app_ = new ApplicationImpl();
        app_.addConverter(Foo.class, "org.seasar.teeda.core.mock.MockConverter");
        Foo foo = new FooImpl();
        Converter c = app_.createConverter(foo.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }
    
    public void testCreateConverterForSuperClass(){
        app_ = new ApplicationImpl();
        app_.addConverter(Hoge.class, "org.seasar.teeda.core.mock.MockConverter");
        Hoge hoge = new Hoge2();
        Converter c = app_.createConverter(hoge.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }
    
    public void testCreateCoverterForPrimitive(){
        app_ = new ApplicationImpl();
        app_.addConverter(Integer.class, "org.seasar.teeda.core.mock.MockConverter");
        int i = 0;
        Converter c = app_.createConverter(Integer.TYPE);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }
    
    public void testAddValidator(){
        app_ = new ApplicationImpl();
        app_.addValidator("teeda.null", "org.seasar.teeda.core.mock.NullValidator");
        Validator v = app_.createValidator("teeda.null");
        assertNotNull(v);
        assertTrue(v instanceof NullValidator);
        try{
            app_.addValidator("hoge", "java.lang.String");
        }catch(IllegalClassTypeException e){
            assertEquals(Validator.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }
        
    }
    
    private static class Hoge{
        
    }
    
    private static class Hoge2 extends Hoge{
        
    }

    private static interface Foo{
        
    }
    
    private static class FooImpl implements Foo{
        
    }
}
