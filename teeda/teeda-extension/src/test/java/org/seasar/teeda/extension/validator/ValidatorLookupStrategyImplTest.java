/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.validator;

import javax.faces.component.UIInput;
import javax.faces.internal.NormalValidatorBuilderImpl;
import javax.faces.internal.ValidatorResource;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ValidatorLookupStrategyImplTest extends TeedaTestCase {

    public void testDynamic_gotValidator() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("foo");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });
        ValidatorLookupStrategyImpl strategy = new ValidatorLookupStrategyImpl();
        strategy.setDynamicValidatorInvoker(invokerImpl);

        //Act
        Validator v = strategy.findValidator(getFacesContext(), input, "123");

        //Assert
        assertNotNull(v);
        assertTrue(v instanceof TLengthValidator);
        TLengthValidator lengthValidator = (TLengthValidator) v;
        assertTrue(lengthValidator.getMaximum() == 10);
        assertTrue(lengthValidator.getMinimum() == 0);
    }

    public void testDynamicAndStatic_dynamicPrior() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("foo");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });
        register(TLengthValidator.class, "length");
        ValidatorResource.addValidator("#{hoge_hogePage.foo}", "length");
        ValidatorLookupStrategyImpl strategy = new ValidatorLookupStrategyImpl();
        strategy.setDynamicValidatorInvoker(invokerImpl);

        //Act
        Validator v = strategy.findValidator(getFacesContext(), input, "123");

        //Assert
        assertNotNull(v);
        assertTrue(v instanceof TLengthValidator);
        TLengthValidator lengthValidator = (TLengthValidator) v;
        assertTrue(lengthValidator.getMaximum() == 10);
        assertTrue(lengthValidator.getMinimum() == 0);
    }

    public void tearDownDynamicAndStatic_dynamicPrior() throws Exception {
        ValidatorResource.removeAll();
    }

    public void testStatic_gotValidator() throws Exception {
        ValidatorLookupStrategyImpl strategy = new ValidatorLookupStrategyImpl();
        UIInput input = new UIInput();
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });
        register(TLengthValidator.class, "length");
        NormalValidatorBuilderImpl builder = new NormalValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
        ValidatorResource.addValidator("#{hoge_hogePage.foo}", "length");
        Validator v = strategy.findValidator(getFacesContext(), input, "123");
        assertNotNull(v);
        assertTrue(v instanceof LengthValidator);
    }

    public void tearDownStatic_gotValidator() throws Exception {
        ValidatorResource.removeAll();
    }

    public void testStatic_noValidator() throws Exception {
        ValidatorLookupStrategyImpl strategy = new ValidatorLookupStrategyImpl();
        UIInput input = new UIInput();
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{should_not.find_validator}";
            }
        });
        register(TLengthValidator.class, "length");
        ValidatorResource.addValidator("#{hoge_hogePage.foo}", "length");
        Validator v = strategy.findValidator(getFacesContext(), input, "123");
        assertNull(v);
    }

    public void tearDownStatic_noValidator() throws Exception {
        ValidatorResource.removeAll();
    }

    public static class HogePage {

        public Validator getFooValidator() {
            TLengthValidator validator = new TLengthValidator();
            validator.setMaximum(10);
            validator.setMinimum(0);
            return validator;
        }
    }

}
