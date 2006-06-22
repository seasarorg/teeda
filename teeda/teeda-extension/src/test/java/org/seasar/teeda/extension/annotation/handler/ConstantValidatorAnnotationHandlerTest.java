package org.seasar.teeda.extension.annotation.handler;

import javax.faces.internal.ValidatorChain;
import javax.faces.internal.ValidatorResource;
import javax.faces.validator.LengthValidator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ConstantValidatorAnnotationHandlerTest extends TeedaTestCase {

    public void testRegisterValidator_single() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class, "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        handler.registerValidator("hogeBean", HogeBean.class);
        LengthValidator validator = (LengthValidator) ValidatorResource.getValidator("#{hogeBean.name}");
        assertEquals(2, validator.getMinimum());
        assertEquals(5, validator.getMaximum());
    }
    
    public void testRegisterValidator_multi() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class, "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        handler.registerValidator("hogeBean", HogeBean.class);
        ValidatorChain chain = (ValidatorChain) ValidatorResource.getValidator("#{hogeBean.aaa}");
        assertNotNull(chain);
        assertEquals(2, chain.getValidatorSize());
        LengthValidator validator = (LengthValidator) chain.getValidator(0);
        assertEquals(2, validator.getMinimum());
        assertEquals(5, validator.getMaximum());
        validator = (LengthValidator) chain.getValidator(1);
        assertEquals(1, validator.getMinimum());
        assertEquals(4, validator.getMaximum());
    }
    
    public static class HogeBean {
        private String name = null;
        private String aaa;

        public static final String name_VALIDATOR = "#{'validator':'lengthValidator', 'minimum':2, 'maximum':5}";
        
        public static final String aaa_VALIDATOR = "{#{'validator':'lengthValidator', 'minimum':2, 'maximum':5},#{'validator':'lengthValidator', 'minimum':1, 'maximum':4}}";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }
    }
}