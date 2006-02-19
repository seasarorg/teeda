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

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.AssertionUtil;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.ConverterUtil;

/**
 * @author yone
 */
public class ValidateLengthTag extends MaxMinValidatorTag {

    private static final long serialVersionUID = 1L;

    private static final String VALIDATOR_ID = "javax.faces.Length";

    public ValidateLengthTag() {
        super();
    }
    
    protected Validator createValidator() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();
        super.setValidatorId(VALIDATOR_ID);
        
        LengthValidator validator = (LengthValidator)super.createValidator();
        AssertionUtil.assertNotNull("LengthValidator", validator);
        
        if (minimum_ != null) {
            int minimum = 0;
            if (BindingUtil.isValueReference(minimum_)) {
                ValueBinding vb = context.getApplication().createValueBinding(minimum_);
                minimum = ConverterUtil.convertToInt(vb.getValue(context));                  
            } else {
                minimum = ConverterUtil.convertToInt(minimum_);
            }
            validator.setMinimum(minimum);
        }
        if (maximum_ != null) {
            int maximum = 0;
            if (BindingUtil.isValueReference(maximum_)) {
                ValueBinding vb = context.getApplication().createValueBinding(maximum_);
                maximum = ConverterUtil.convertToInt(vb.getValue(context)); 
            } else {
                maximum = ConverterUtil.convertToInt(maximum_);
            }
            validator.setMaximum(maximum);
        }
        return validator;
    }
    
}
