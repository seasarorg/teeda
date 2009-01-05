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
package org.seasar.teeda.core.taglib.core;

import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class ValidateLengthTagTest extends TeedaTestCase {

    public void testCreateValidator() throws Exception {
        // # Arrange #
        String validatorId = LengthValidator.VALIDATOR_ID;
        String validatorClassName = "javax.faces.validator.LengthValidator";
        MockApplication app = new MockApplicationImpl();
        app.addValidator(validatorId, validatorClassName);
        setApplication(app);
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #
        Validator validator = tag.createValidator();

        // # Assert #
        assertNotNull(validator);
        assertTrue(validator instanceof LengthValidator);
    }

    public void testCreateValidator_NotAddValidator() throws Exception {
        // # Arrange #
        ValidateLengthTag tag = new ValidateLengthTag();
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
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #
        tag.createValidator();

        // # Assert #
        assertFalse(tag.isMinimumSet());
        assertFalse(tag.isMaximumSet());
    }

    public void testSetMinimum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #
        tag.setMinimum("2");
        LengthValidator validator = (LengthValidator) tag.createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(2 == validator.getMinimum());
    }

    public void testSetMinimum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(1));
        getApplication().setValueBinding(vb);
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #
        tag.setMinimum("#{hoge.minimum}");
        LengthValidator validator = (LengthValidator) tag.createValidator();

        // # Assert #
        assertTrue(tag.isMinimumSet());
        assertTrue(1 == validator.getMinimum());
    }

    public void testSetMaximum_constantValue() throws Exception {
        // # Arrange #
        addValidator();
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #        
        tag.setMaximum("30");
        LengthValidator validator = (LengthValidator) tag.createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(30 == validator.getMaximum());
    }

    public void testSetMaximum_bindingValue() throws Exception {
        // # Arrange #
        addValidator();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(50));
        getApplication().setValueBinding(vb);
        ValidateLengthTag tag = new ValidateLengthTag();

        // # Act #        
        tag.setMaximum("#{hoge.maximum}");
        LengthValidator validator = (LengthValidator) tag.createValidator();

        // # Assert #
        assertTrue(tag.isMaximumSet());
        assertTrue(50 == validator.getMaximum());
    }

    private void addValidator() throws Exception {
        MockApplication app = new MockApplicationImpl();
        app.addValidator("javax.faces.Length",
                "javax.faces.validator.LengthValidator");
        setApplication(app);
    }

}
