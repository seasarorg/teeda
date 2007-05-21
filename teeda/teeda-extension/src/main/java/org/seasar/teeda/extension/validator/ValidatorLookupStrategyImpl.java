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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.DynamicValidatorInvoker;
import javax.faces.internal.ValidatorLookupStrategy;
import javax.faces.internal.ValidatorResource;
import javax.faces.validator.Validator;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class ValidatorLookupStrategyImpl implements ValidatorLookupStrategy {

    private DynamicValidatorInvoker invoker = DynamicValidatorInvoker.NULL_INVOKER;

    public Validator findValidator(FacesContext context, UIComponent component,
            Object value) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        Validator validator = invoker.invoke(context, component, value);
        if (validator != null) {
            return validator;
        } else {
            return getStaticValidator(context, component, value);
        }
    }

    protected Validator getStaticValidator(FacesContext context,
            UIComponent component, Object value) {
        ValueBinding vb = component.getValueBinding("value");
        if (vb != null) {
            String expression = vb.getExpressionString();
            return ValidatorResource.getValidator(expression);
        }
        return null;
    }

    public void setDynamicValidatorInvoker(DynamicValidatorInvoker invoker) {
        this.invoker = invoker;
    }

    public DynamicValidatorInvoker getDynamicValidatorInvoker() {
        return invoker;
    }

}
