/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.exception.ExtendValidatorException;

public class TRequiredValidatorTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            System.out.println(expected.getFacesMessage().getDetail());
        }
    }

    public void testValidate_for() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        validator.setTarget("aaa, bbb");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMIT_VALUE, "aaa");
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            System.out.println(expected.getFacesMessage().getDetail());
        }
    }

    public void testSaveAndRestore() throws Exception {
        TRequiredValidator validator = new TRequiredValidator();
        validator.setTarget("aaa");
        validator.setMessageId("hoge");
        Object state = validator.saveState(getFacesContext());
        validator = new TRequiredValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("aaa", validator.getTarget());
        assertEquals("hoge", validator.getMessageId());
    }

    public void testValidate_withMessageId() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        validator.setMessageId("hoge");
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ExtendValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            assertEquals("hoge", expected.getMesssageIds()[0]);
        }
    }

    public void testValidate_facesContextIsNull() throws Exception {
        Validator validator = createValidator();
        try {
            validator.validate(null, getFacesContext().getViewRoot(), "hoge");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testValidate_componentIsNull() throws Exception {
        Validator validator = createValidator();
        try {
            validator.validate(getFacesContext(), null, "hoge");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    protected Validator createValidator() {
        return new TRequiredValidator();
    }
}
