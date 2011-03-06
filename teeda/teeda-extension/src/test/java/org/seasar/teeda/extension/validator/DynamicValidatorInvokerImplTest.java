/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DynamicValidatorInvokerImplTest extends TeedaTestCase {

    public void testInvoke_invoked() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("foo-1");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNotNull(validator);
        assertTrue(validator instanceof TLengthValidator);
        TLengthValidator lengthValidator = (TLengthValidator) validator;
        assertTrue(lengthValidator.getMaximum() == 10);
        assertTrue(lengthValidator.getMinimum() == 0);
    }

    public void testInvoke_noInvokeWhenIdIsNull() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId(null);
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeWhenVBIsNull() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("aaa");
        input.setValueBinding("value", null);

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeWhenExpressionStringIsNotAppropriateSyntax()
            throws Exception {
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
                return "aaaa";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeWhenExpressionStringIsNotAppropriateSyntax2()
            throws Exception {
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
                return "#{aaaa}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeWhenNoValidatorProperty() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("boo");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeWhenValidatorPropertyDoesNotReturnAppropriateReturnType()
            throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return HogePage.class;
            }

        });
        UIInput input = new UIInput();
        input.setId("bar");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeComponentNotFound1() throws Exception {
        //Arrange
        getContainer().register(HogePage.class);
        DynamicValidatorInvokerImpl invokerImpl = new DynamicValidatorInvokerImpl();
        invokerImpl.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return null;
            }

        });
        UIInput input = new UIInput();
        input.setId("foo");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{hoge_hogePage.foo}";
            }
        });

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public void testInvoke_noInvokeComponentNotFound2() throws Exception {
        //Arrange
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

        //Act
        Validator validator = invokerImpl.invoke(getFacesContext(), input,
                "123");

        //Assert
        assertNull(validator);
    }

    public static class HogePage {

        public Validator getFooValidator() {
            TLengthValidator validator = new TLengthValidator();
            validator.setMaximum(10);
            validator.setMinimum(0);
            return validator;
        }

        public Converter getBarValidator() {
            return null;
        }
    }
}
