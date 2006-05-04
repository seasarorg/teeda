package org.seasar.teeda.core.annotation;

import javax.faces.validator.LengthValidator;
import javax.faces.validator.LongRangeValidator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.validator.ValidatorChain;

public class ConstantValidatorAnnotationHandlerTest extends TeedaTestCase {

    public void testRegisterValidator_autoNaming() throws Exception {
        getContainer().register(LengthValidator.class, "length");
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        ValidatorResource resources = new ValidatorResourceImpl();
        handler.setValidatorResource(resources);
        ComponentDef cDef = new ComponentDefImpl(HogeBean.class);
        handler.registerValidator(cDef);
        assertTrue(resources.getValidator("#{hogeBean.name}") instanceof LengthValidator);
    }

    public void testRegisterValidator_aliasNaming() throws Exception {
        getContainer().register(LengthValidator.class, "length");
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        ValidatorResource resources = new ValidatorResourceImpl();
        handler.setValidatorResource(resources);
        ComponentDef cDef = new ComponentDefImpl(FooBean.class);
        handler.registerValidator(cDef);
        assertTrue(resources.getValidator("#{y.name}") instanceof LengthValidator);
    }

    public void testRegisterValidator_multipleValidators() throws Exception {
        getContainer().register(LengthValidator.class, "length");
        getContainer().register(LongRangeValidator.class, "longRange");
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        ValidatorResource resources = new ValidatorResourceImpl();
        handler.setValidatorResource(resources);
        ComponentDef cDef = new ComponentDefImpl(BarBean.class);
        handler.registerValidator(cDef);
        assertTrue(resources.getValidator("#{barBean.name}") instanceof ValidatorChain);
    }

    public void testRegisterValidator_noSuchProperty() throws Exception {
        getContainer().register(LengthValidator.class, "length");
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        ValidatorResource resources = new ValidatorResourceImpl();
        handler.setValidatorResource(resources);
        ComponentDef cDef = new ComponentDefImpl(BazBean.class);
        try {
            handler.registerValidator(cDef);
            fail();
        }catch(PropertyNotFoundRuntimeException expected){
            success();
        }
        
    }

    public void testGetShortClassName1() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        assertEquals("ConstantValidatorAnnotationHandlerTest", handler
                .getShortClassName(this.getClass()));
    }

    public void testGetShortClassName2() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        assertEquals("AAA", handler.getShortClassName(AAAImpl.class));
    }

    public void testGetAlias1() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(A.class);
        assertEquals("a", handler.getAlias(beanDesc));
    }

    public void testGetAlias2() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(B.class);
        assertNull(handler.getAlias(beanDesc));
    }

    public void testGetExpression() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        assertEquals("#{a.b}", handler.getExpression("a", "b"));
    }

    public void testGetExpressionByAuto() throws Exception {
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        assertEquals("#{b.c}", handler.getExpressionByAuto(B.class, "c"));
    }
    
    public static class AAAImpl {

    }

    public static class A {
        public static final String ALIAS = "a";
    }

    public static class B {
    }

    public static class HogeBean {
        private String name = null;

        public static final String name_VALIDATOR = "{length, minimum=2, maximum=5}";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class FooBean {

        private String name = null;

        public static final String name_VALIDATOR = "{length, minimum=2, maximum=5}";

        public static final String ALIAS = "y";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    
    public static class BarBean {

        private String name = null;

        public static final String name_VALIDATOR = "{length, minimum=2, maximum=5}, {longRange, minimum=2}";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class BazBean {

        private String name = null;

        public static final String name_VALIDATOR = "{length, mmmminimum=2, maximum=5}";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
