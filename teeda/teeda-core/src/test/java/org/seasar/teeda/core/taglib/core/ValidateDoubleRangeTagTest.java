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
package org.seasar.teeda.core.taglib.core;

import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class ValidateDoubleRangeTagTest extends TeedaTestCase {

    public void testCreateValidator() throws Exception {
        // # Arrange #
        String validatorId = DoubleRangeValidator.VALIDATOR_ID;
        String validatorClassName = "javax.faces.validator.DoubleRangeValidator";
        MockApplication app = new MockApplicationImpl();
        app.addValidator(validatorId, validatorClassName);
        setApplication(app);
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #
        Validator validator = tag.createValidator();

        // # Assert #
        assertNotNull(validator);
        assertTrue(validator instanceof DoubleRangeValidator);
    }

    public void testCreateValidator_NotAddValidator() throws Exception {
        // # Arrange #
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();
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
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #
        tag.createValidator();

        // # Assert #
        assertFalse(tag.isMinimumSet());
        assertFalse(tag.isMaximumSet());
    }

    public void testSetMinimum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #
        tag.setMinimum("11.01");
        DoubleRangeValidator validator = (DoubleRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(11.01d == validator.getMinimum());
    }

    public void testSetMinimum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Double(321.01d));
        getApplication().setValueBinding(vb);
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #
        tag.setMinimum("#{hoge.minimum}");
        DoubleRangeValidator validator = (DoubleRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(321.01d == validator.getMinimum());
    }

    public void testSetMaximum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #        
        tag.setMaximum("543.21");
        DoubleRangeValidator validator = (DoubleRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(543.21d == validator.getMaximum());
    }

    public void testSetMaximum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Double(999.99d));
        getApplication().setValueBinding(vb);
        ValidateDoubleRangeTag tag = new ValidateDoubleRangeTag();

        // # Act #        
        tag.setMaximum("#{hoge.maximum}");
        DoubleRangeValidator validator = (DoubleRangeValidator) tag
                .createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(999.99d == validator.getMaximum());
    }

    private void addValidator() throws Exception {
        MockApplication app = new MockApplicationImpl();
        app.addValidator("javax.faces.DoubleRange",
                "javax.faces.validator.DoubleRangeValidator");
        setApplication(app);
    }

}
