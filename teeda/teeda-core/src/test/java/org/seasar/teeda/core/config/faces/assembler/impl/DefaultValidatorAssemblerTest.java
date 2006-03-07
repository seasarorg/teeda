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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.validator.Validator;

import org.seasar.teeda.core.config.faces.element.ValidatorElement;
import org.seasar.teeda.core.config.faces.element.impl.ValidatorElementImpl;
import org.seasar.teeda.core.mock.MockValidator;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultValidatorAssemblerTest extends TeedaTestCase {

    public DefaultValidatorAssemblerTest(String name) {
        super(name);
    }

    public void testAssemble() throws Exception {
        // # Arrange #
        Map map = new HashMap();
        ValidatorElement element = new ValidatorElementImpl();
        element.setValidatorId("mock");
        element.setValidatorClass("org.seasar.teeda.core.mock.MockValidator");
        map.put(element.getValidatorId(), element);
        DefaultValidatorAssembler assembler = new DefaultValidatorAssembler(map);

        // # Act #
        assembler.assemble();

        // # Assert #
        Validator v = getApplication().createValidator("mock");
        assertNotNull(v);
        assertTrue(v instanceof MockValidator);
    }
}
