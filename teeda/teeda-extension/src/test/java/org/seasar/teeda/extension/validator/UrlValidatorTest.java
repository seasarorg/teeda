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

import javax.faces.context.FacesContext;
import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.extension.exception.ExtendValidatorException;

/**
 * @author shot
 */
public class UrlValidatorTest extends AbstractValidatorTest {

    public void testValidate_validationError() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        try {
            validator.validate(getFacesContext(), new MockUIComponent(),
                    "ftp://aaa.com");
            fail();
        } catch (ValidatorException e) {
            assertNotNull(e.getFacesMessage());
        }
    }

    public void testValidate_validationOk() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        try {
            validator.validate(getFacesContext(), new MockUIComponent(),
                    "http://www.yahoo.co.jp");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testValidate_validationOk2() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        try {
            validator.validate(getFacesContext(), new MockUIComponent(),
                    "https://www.yahoo.co.jp");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testValidate_validationOk3() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        try {
            validator.validate(getFacesContext(), new MockUIComponent(),
                    "http://127.0.0.1/a");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testValidate_validateTargetPointed() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        validator.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "a1");
            fail();
        } catch (ValidatorException e) {
            assertNotNull(e.getFacesMessage());
        }
    }

    public void testValidate_validateTargetNotPointed() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        validator.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "a1");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testValidate_validationErrorWithMessageId() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        UrlValidator validator = new UrlValidator();
        validator.setMessageId("hoge");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "aa");
            fail();
        } catch (ExtendValidatorException e) {
            assertNotNull(e.getFacesMessage());
            assertEquals("hoge", e.getMesssageIds()[0]);
        }
    }

    public void testValidate_NoValidate() throws Exception {
        FacesContext context = getFacesContext();
        UrlValidator validator = new UrlValidator();
        validator.setTarget("abc");
        final MockUIComponent component = new MockUIComponent();
        component.setId("abc");
        validator.validate(context, component, "");
    }

    public void testSaveAndRestore() throws Exception {
        UrlValidator validator = new UrlValidator();
        validator.setTarget("aaa");
        validator.setPattern("^[1-9]");
        validator.setMessageId("bbb");
        Object state = validator.saveState(getFacesContext());
        validator = new UrlValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("aaa", validator.getTarget());
        assertEquals("^[1-9]", validator.getPattern());
        assertEquals("bbb", validator.getMessageId());
    }

    protected Validator createValidator() {
        return new UrlValidator();
    }
}
