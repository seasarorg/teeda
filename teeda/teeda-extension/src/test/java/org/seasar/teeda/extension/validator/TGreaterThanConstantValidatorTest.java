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

import java.math.BigDecimal;

import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class TGreaterThanConstantValidatorTest extends AbstractValidatorTest {

    public void test1() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        MockUIComponent component = new MockUIComponent();
        try {
            validator.validate(getFacesContext(), component, new Integer(1));
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    public void test2() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        validator.setTarget(new Integer(1));
        MockUIComponent component = new MockUIComponent();
        try {
            validator.validate(getFacesContext(), component, new Integer(1));
            fail();
        } catch (ValidatorException expected) {
            success();
        }
    }

    public void test3() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        validator.setTarget(new Integer(0));
        MockUIComponent component = new MockUIComponent();
        component.getAttributes().put(JsfConstants.LABEL_ATTR, "value");
        try {
            validator.validate(getFacesContext(), component, new Integer(-1));
            fail();
        } catch (ValidatorException expected) {
            System.out.println(expected.getFacesMessage().getDetail());
            success();
        }
    }

    public void test4() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        BigDecimal bd = new BigDecimal("111111111.11111");
        validator.setTarget(bd);
        MockUIComponent component = new MockUIComponent();
        component.getAttributes().put(JsfConstants.LABEL_ATTR, "value");
        try {
            validator.validate(getFacesContext(), component, bd);
            fail();
        } catch (ValidatorException expected) {
            System.out.println(expected.getFacesMessage().getDetail());
            success();
        }
    }

    public void test5() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        BigDecimal bd = new BigDecimal("1111");
        validator.setTarget(bd);
        MockUIComponent component = new MockUIComponent();
        component.getAttributes().put(JsfConstants.LABEL_ATTR, "value");
        try {
            validator.validate(getFacesContext(), component, new Long(1111));
            fail();
        } catch (ValidatorException expected) {
            System.out.println(expected.getFacesMessage().getDetail());
            success();
        }
    }

    public void test6() throws Exception {
        TGreaterThanConstantValidator validator = new TGreaterThanConstantValidator();
        validator.setTarget(new Integer(1000000));
        MockUIComponent component = new MockUIComponent();
        component.getAttributes().put(JsfConstants.LABEL_ATTR, "value");
        try {
            validator.validate(getFacesContext(), component, new Long(1111));
            fail();
        } catch (ValidatorException expected) {
            System.out.println(expected.getFacesMessage().getDetail());
            success();
        }
    }

    protected Validator createValidator() {
        return new TGreaterThanConstantValidator();
    }

}
