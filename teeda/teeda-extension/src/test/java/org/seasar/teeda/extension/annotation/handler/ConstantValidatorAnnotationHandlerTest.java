/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.extension.annotation.handler;

import javax.faces.internal.ValidatorChain;
import javax.faces.internal.ValidatorResource;
import javax.faces.validator.LengthValidator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.validator.TRequiredValidator;

public class ConstantValidatorAnnotationHandlerTest extends TeedaTestCase {

    protected void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testRegisterValidator_single() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class,
                "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        handler.registerValidators("hogeBean");
        LengthValidator validator = (LengthValidator) ValidatorResource
                .getValidator("#{hogeBean.name}");
        assertEquals(2, validator.getMinimum());
        assertEquals(5, validator.getMaximum());
    }

    public void testRegisterValidator_multi() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class,
                "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        cd = new ComponentDefImpl(TRequiredValidator.class,
                "TRequiredValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        handler.registerValidators("hogeBean");
        ValidatorChain chain = (ValidatorChain) ValidatorResource
                .getValidator("#{hogeBean.aaa}");
        assertNotNull(chain);
        assertEquals(2, chain.getValidatorSize());
        LengthValidator validator = (LengthValidator) chain.getValidator(0);
        assertEquals(2, validator.getMinimum());
        assertEquals(5, validator.getMaximum());
        assertEquals(TRequiredValidator.class, chain.getValidator(1).getClass());
    }

    public void testRegisterValidator_underbar() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class,
                "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        handler.registerValidators("hogeBean");
        LengthValidator validator = (LengthValidator) ValidatorResource
                .getValidator("#{hogeBean.bbb_ccc}");
        assertEquals(3, validator.getMinimum());
        assertEquals(8, validator.getMaximum());
    }

    public void testRegisterValidator_inherit() throws Exception {
        ComponentDef cd = new ComponentDefImpl(LengthValidator.class,
                "lengthValidator");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        getContainer().register(HogeHogeBean.class, "hogeHogeBean");
        handler.registerValidators("hogeHogeBean");
        LengthValidator validator = (LengthValidator) ValidatorResource
                .getValidator("#{hogeHogeBean.name}");
        assertEquals(2, validator.getMinimum());
        assertEquals(5, validator.getMaximum());
    }

    public static class HogeBean {
        private String name = null;

        private String aaa;

        private String bbb_ccc;

        public static final String name_lengthValidator = "minimum=2, maximum=5";

        public static final String aaa_lengthValidator = "minimum=2, maximum=5";

        public static final String aaa_TRequiredValidator = null;

        public static final String bbb_ccc_lengthValidator = "minimum=3, maximum=8";

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

        public String getBbb_ccc() {
            return bbb_ccc;
        }

        public void setBbb_ccc(String bbb_ccc) {
            this.bbb_ccc = bbb_ccc;
        }
    }

    public static class HogeHogeBean extends HogeBean {
    }

}
