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

import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.extension.exception.ExtendValidatorException;

/**
 * @author shot
 *
 */
public class TLongRangeValidatorTest extends AbstractValidatorTest {

    public void testValidate_normal() throws Exception {
        TLongRangeValidator validator = new TLongRangeValidator();
        validator.setTarget("aaa");
        validator.setMaximum(3);
        validator.setMinimum(1);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        try {
            validator.validate(getFacesContext(), component, "4");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getMessage());
        }
    }

    public void testValidate_targetCommandPointed() throws Exception {
        TLongRangeValidator validator = new TLongRangeValidator();
        validator.setTarget("aaa");
        validator.setMaximum(3);
        validator.setMinimum(1);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        try {
            validator.validate(getFacesContext(), component, "4");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getMessage());
        }
    }

    public void testValidate_targetCommandNotPointed() throws Exception {
        TLongRangeValidator validator = new TLongRangeValidator();
        validator.setTarget("aaa");
        validator.setMaximum(3);
        validator.setMinimum(1);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        try {
            validator.validate(getFacesContext(), component, "4");
            success();
        } catch (ValidatorException expected) {
            fail();
        }
    }

    public void testValidate_withMessageId() throws Exception {
        TLongRangeValidator validator = new TLongRangeValidator();
        validator.setTarget("aaa");
        validator.setMaximum(3);
        validator.setMinimum(1);
        validator.setMaximumMessageId("a");
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        try {
            validator.validate(getFacesContext(), component, "4");
            fail();
        } catch (ExtendValidatorException expected) {
            assertNotNull(expected.getMessage());
            assertEquals("a", expected.getMesssageIds()[0]);
        }
    }

    public void testSaveAndRestore() throws Exception {
        TLongRangeValidator validator = new TLongRangeValidator();
        validator.setTarget("aaa");
        validator.setMaximumMessageId("a");
        validator.setMinimumMessageId("b");
        validator.setNotInRangeMessageId("c");
        validator.setTypeMessageId("d");
        validator.setConvert(false);
        Object state = validator.saveState(getFacesContext());
        validator = new TLongRangeValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("aaa", validator.getTarget());
        assertEquals("a", validator.getMaximumMessageId());
        assertEquals("b", validator.getMinimumMessageId());
        assertEquals("c", validator.getNotInRangeMessageId());
        assertEquals("d", validator.getTypeMessageId());
        assertFalse(validator.isConvert());
    }

    protected Validator createValidator() {
        return new TLongRangeValidator();
    }

}
