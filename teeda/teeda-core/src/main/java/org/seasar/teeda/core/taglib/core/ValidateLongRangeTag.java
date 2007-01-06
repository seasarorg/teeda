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

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.ConverterUtil;

/**
 * @author yone
 * @author shot
 */
public class ValidateLongRangeTag extends MaxMinValidatorTag {

    private static final long serialVersionUID = 1L;

    private static final String VALIDATOR_ID = LongRangeValidator.VALIDATOR_ID;

    protected long minimum = 0;

    protected long maximum = 0;

    public ValidateLongRangeTag() {
        super();
    }

    protected Validator createValidator() throws JspException {
        super.setValidatorId(VALIDATOR_ID);
        LongRangeValidator validator = null;
        validator = (LongRangeValidator) super.createValidator();
        AssertionUtil.assertNotNull("LongRangeValidator", validator);

        evaluateExpressions();
        if (isMinimumSet()) {
            validator.setMinimum(minimum);
        }
        if (isMaximumSet()) {
            validator.setMaximum(maximum);
        }
        return validator;
    }

    private void evaluateExpressions() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();
        final String min = getMinimum();
        if (min != null) {
            if (BindingUtil.isValueReference(min)) {
                ValueBinding vb = ValueBindingUtil.createValueBinding(context,
                        min);
                minimum = ConverterUtil.convertToLong(vb.getValue(context));
            } else {
                minimum = ConverterUtil.convertToLong(min);
            }
        }
        final String max = getMaximum();
        if (max != null) {
            if (BindingUtil.isValueReference(max)) {
                ValueBinding vb = context.getApplication().createValueBinding(
                        max);
                maximum = ConverterUtil.convertToLong(vb.getValue(context));
            } else {
                maximum = ConverterUtil.convertToLong(max);
            }
        }
    }

}