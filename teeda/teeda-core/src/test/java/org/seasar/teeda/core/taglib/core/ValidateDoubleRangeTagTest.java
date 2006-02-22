/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

}
