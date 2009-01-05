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
package org.seasar.teeda.extension.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ValidatorChain;
import javax.faces.validator.Validator;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class MergeableValidatorLookupStrategyImpl extends
        ValidatorLookupStrategyImpl {

    public Validator findValidator(FacesContext context, UIComponent component,
            Object value) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        Validator validator = getDynamicValidatorInvoker().invoke(context,
                component, value);
        Validator staticValidator = getStaticValidator(context, component,
                value);
        return createChain(validator, staticValidator);
    }

    protected ValidatorChain createChain(Validator dynamicValidator,
            Validator staticValidator) {
        ValidatorChain chain = new ValidatorChain();
        if (staticValidator != null) {
            chain.add(staticValidator);
        }
        if (dynamicValidator != null) {
            chain.add(dynamicValidator);
        }
        return chain;
    }
}
