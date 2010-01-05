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
package org.seasar.teeda.core.taglib.core;

import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class ValidateLongRangeTagTest extends TeedaTestCase {

    public void testCreateValidator() throws Exception {
        // # Arrange #
        String validatorId = LongRangeValidator.VALIDATOR_ID;
        String validatorClassName = "javax.faces.validator.LongRangeValidator";
        MockApplication app = new MockApplicationImpl();
        app.addValidator(validatorId, validatorClassName);
        setApplication(app);
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #
        Validator validator = tag.createValidator();

        // # Assert #
        assertNotNull(validator);
        assertTrue(validator instanceof LongRangeValidator);
    }

    public void testCreateValidator_NotAddValidator() throws Exception {
        // # Arrange #
        ValidateLongRangeTag tag = new ValidateLongRangeTag();
        try {
            // # Act #
            tag.createValidator();
            fail();
        } catch (NullPointerException e) {
            // # Assert #
            success();
        }
    }

    public void testCreateValidator_noSetProperty() throws Exception {
        // # Arrange #
        addValidator();
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #
        tag.createValidator();

        // # Assert #
        assertFalse(tag.isMinimumSet());
        assertFalse(tag.isMaximumSet());
    }

    public void testSetMinimum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #
        tag.setMinimum("2");
        LongRangeValidator validator = (LongRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(2L == validator.getMinimum());
    }

    public void testSetMinimum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Long(3L));
        getApplication().setValueBinding(vb);
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #
        tag.setMinimum("#{hoge.minimum}");
        LongRangeValidator validator = (LongRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(3L == validator.getMinimum());
    }

    public void testSetMaximum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #        
        tag.setMaximum("888888888888");
        LongRangeValidator validator = (LongRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(888888888888L == validator.getMaximum());
    }

    public void testSetMaximum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Long(9999999999L));
        getApplication().setValueBinding(vb);
        ValidateLongRangeTag tag = new ValidateLongRangeTag();

        // # Act #        
        tag.setMaximum("#{hoge.maximum}");
        LongRangeValidator validator = (LongRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(9999999999L == validator.getMaximum());
    }

    private void addValidator() throws Exception {
        MockApplication app = new MockApplicationImpl();
        app.addValidator("javax.faces.LongRange",
                "javax.faces.validator.LongRangeValidator");
        setApplication(app);
    }

}
