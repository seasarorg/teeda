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
package org.seasar.teeda.extension.validator;

import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author yone
 */
public class TEqualValidatorTest extends AbstractValidatorTest {

    public void testValidate_invalid() throws Exception {
        TEqualValidator validator = new TEqualValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue("hoge");
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        String value = "foo";
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

    public void testValidate_equalValueIsOk() throws Exception {
        TEqualValidator validator = new TEqualValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue("hoge");
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        String value = "hoge";
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    public void testValidate_equalValueIsOk2() throws Exception {
        TEqualValidator validator = new TEqualValidator();
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        UIInput child1 = new UIInput();
        child1.setId("aaa");
        child1.setValue(new Integer("111"));
        UIInput child2 = new UIInput();
        child2.setId("bbb");
        root.getChildren().add(child1);
        root.getChildren().add(child2);

        Integer value = new Integer("111");
        validator.setTargetId("aaa");

        try {
            validator.validate(context, child2, value);
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    protected Validator createValidator() {
        return new TGreaterEqualValidator();
    }

}
