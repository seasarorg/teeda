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
package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class TRegularExpressionValidatorTest extends TeedaTestCase {

    public void testValidate_validationError() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        TRegularExpressionValidator validator = new TRegularExpressionValidator();
        validator.setPattern("^[1-9][a-z]");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "aa");
            fail();
        } catch (ValidatorException e) {
            assertNotNull(e.getFacesMessage());
        }
    }
    
    public void testValidate_validationOk() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        TRegularExpressionValidator validator = new TRegularExpressionValidator();
        validator.setPattern("^[1-9][a-z]");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "1a");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

}
