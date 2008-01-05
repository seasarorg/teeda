/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;
import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class TGreaterValidatorTest extends AbstractValidatorTest {

    public void testValidate_invalid() throws Exception {
        TGreaterValidator validator = new TGreaterValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer(234));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        getApplication().addConverter(Integer.class,
                IntegerConverter.class.getName());

        Integer value = new Integer("123");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected);
            System.out.println(expected.getMessage());
            success();
        }
    }

    public void testValidate_noOpIfNotTarget() throws Exception {
        TGreaterValidator validator = new TGreaterValidator();
        validator.setTarget("hoge");
        FacesContext context = getFacesContext();
        context.getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "foo");
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer(234));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        getApplication().addConverter(Integer.class,
                IntegerConverter.class.getName());

        Integer value = new Integer("123");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            //nothing happen
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    public void testValidate_valdateIfTarget() throws Exception {
        TGreaterValidator validator = new TGreaterValidator();
        validator.setTarget("hoge");
        FacesContext context = getFacesContext();
        context.getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "hoge");
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer(234));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        getApplication().addConverter(Integer.class,
                IntegerConverter.class.getName());

        Integer value = new Integer("123");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            fail();
        } catch (ValidatorException expected) {
            success();
        }
    }

    public void testValidate_equalValueIsInvalid() throws Exception {
        TGreaterValidator validator = new TGreaterValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer(234));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        getApplication().addConverter(Integer.class,
                IntegerConverter.class.getName());

        Integer value = new Integer("234");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected);
            System.out.println(expected.getMessage());
            success();
        }
    }

    public void testValidate_valid() throws Exception {
        TGreaterValidator validator = new TGreaterValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer(123));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        getApplication().addConverter(Integer.class,
                IntegerConverter.class.getName());

        Integer value = new Integer("234");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    protected Validator createValidator() {
        return new TGreaterValidator();
    }

}
