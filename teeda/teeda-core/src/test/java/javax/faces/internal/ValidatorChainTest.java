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
package javax.faces.internal;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.unit.TestUtil;

/**
 * @author shot
 * @author manhole
 */
public class ValidatorChainTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false, false };
        Validator v1 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[0] = true;
            }
        };
        Validator v2 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[1] = true;
            }
        };
        ValidatorChain chain = new ValidatorChain();
        chain.add(v1);
        chain.add(v2);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");

        // ## Act ##
        chain.validate(getFacesContext(), component, new Integer(2));

        // ## Assert ##
        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }

    public void testSaveAndRestoreState() throws Exception {
        final ValidatorChain chain1 = new ValidatorChain();
        {
            final LengthValidator lengthValidator = new LengthValidator();
            lengthValidator.setMaximum(33);
            chain1.add(lengthValidator);

            final DoubleRangeValidator doubleRangeValidator = new DoubleRangeValidator();
            doubleRangeValidator.setMinimum(10);
            doubleRangeValidator.setMaximum(21);
            chain1.add(doubleRangeValidator);
        }
        final FacesContext context = getFacesContext();
        final Object saved = chain1.saveState(context);
        final Object decoded = TestUtil.serializeAndDeserialize(saved);

        final ValidatorChain chain2 = new ValidatorChain();
        chain2.restoreState(context, decoded);

        assertEquals(2, chain2.getValidatorSize());
        final LengthValidator lengthValidator = (LengthValidator) chain2
                .getValidator(0);
        assertEquals(33, lengthValidator.getMaximum());

        final DoubleRangeValidator doubleRangeValidator = (DoubleRangeValidator) chain2
                .getValidator(1);
        assertEquals(10.0, doubleRangeValidator.getMinimum(), 0);
        assertEquals(21.0, doubleRangeValidator.getMaximum(), 0);
    }

}
