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
package org.seasar.teeda.extension.util;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.internal.ValidatorResource;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.validator.TLengthValidator;
import org.seasar.teeda.extension.validator.TRequiredValidator;

/**
 * @author shot
 *
 */
public class NullLabelStrategyImplTest extends TeedaTestCase {

    public void testIsRequired_forced() throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        helper.setForceNullLabel(Boolean.TRUE);
        assertTrue(helper.isNullLabelRequired(getFacesContext(), "aaa"));
    }

    public void testIsRequired_defaultNullLabelExist() throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        assertTrue(helper.isNullLabelRequired(getFacesContext(), "aaa"));
    }

    public void testIsRequired_noNullLabelWhenTRequiredValidatorExists()
            throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        register(TRequiredValidator.class, "required");
        ValidatorResource.addValidator("aaa", "required");
        assertFalse(helper.isNullLabelRequired(getFacesContext(), "aaa"));
    }

    public void testIsRequired_noNullLabelWhenTRequiredValidatorExists2()
            throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        register(TRequiredValidator.class, "required");
        register(TLengthValidator.class, "length");
        ValidatorResource.addValidator("aaa", "required");
        ValidatorResource.addValidator("aaa", "length");
        assertFalse(helper.isNullLabelRequired(getFacesContext(), "aaa"));
    }

    public void testIsRequired_noMatch() throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        MockApplication application = getApplication();
        application.setValueBinding(new MockValueBinding() {

            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return Integer.class;
            }

        });
        register(TLengthValidator.class, "length");
        ValidatorResource.addValidator("aaa", "length");
        assertTrue(helper.isNullLabelRequired(getFacesContext(), "aaa"));
    }

}
