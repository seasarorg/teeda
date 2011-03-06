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
public class TByteLengthValidatorTest extends AbstractValidatorTest {

    public void testConstants() throws Exception {
        assertEquals(
                "org.seasar.teeda.extension.validator.TByteLengthValidator.MAXIMUM",
                TByteLengthValidator.MAXIMUM_MESSAGE_ID);
        assertEquals(
                "org.seasar.teeda.extension.validator.TByteLengthValidator.MINIMUM",
                TByteLengthValidator.MINIMUM_MESSAGE_ID);
        assertEquals("teeda.extension.ByteLength",
                TByteLengthValidator.VALIDATOR_ID);
    }

    public void testValidate_greaterThanMaximum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(1);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "hogehoge");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected);
            success();
        }
    }

    public void testValidate_lowerThanMinimum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(3);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "あ");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected);
            success();
        }
    }

    public void testGetConvertedValueLength() throws Exception {
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setCharSet("NO_SUCH_ENCODE");
        try {
            validator.getConvertedValueLength("aaaa");
        } catch (ValidatorException expected) {
            success();
        }
    }

    public void testValidate_validateTargetPointed() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setTarget("aaa");
        validator.setMinimum(4);
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "111");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected);
        }
    }

    public void testValidate_validateTargetNotPointed() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setTarget("aaa");
        validator.setMinimum(4);
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "111");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testValidate_validateWithMessageId() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMinimum(4);
        validator.setMaximumMessageId("max");
        validator.setMinimumMessageId("min");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "1");
            fail();
        } catch (ExtendValidatorException e) {
            assertEquals("max", e.getMesssageIds()[0]);
            assertEquals("min", e.getMesssageIds()[1]);
        }
    }

    public void testValidate_NoValidate() throws Exception {
        FacesContext context = getFacesContext();
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMaximum(33);
        validator.setMinimum(11);
        validator.validate(context, new MockUIComponent(), "");
    }

    public void testSaveAndRestore() throws Exception {
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setCharSet("Shift_JIS");
        validator.setTarget("aaa");
        validator.setMaximumMessageId("max");
        validator.setMinimumMessageId("min");
        Object state = validator.saveState(getFacesContext());
        validator = new TByteLengthValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("Shift_JIS", validator.getCharSet());
        assertEquals("aaa", validator.getTarget());
        assertEquals("max", validator.getMaximumMessageId());
        assertEquals("min", validator.getMinimumMessageId());
    }

    protected Validator createValidator() {
        return new TByteLengthValidator();
    }

}
