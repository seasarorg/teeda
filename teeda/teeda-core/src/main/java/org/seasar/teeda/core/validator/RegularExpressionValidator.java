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
package org.seasar.teeda.core.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.AssertionUtil;
import javax.faces.internal.FacesMessageUtils;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.util.PatternUtil;
import org.seasar.teeda.core.util.UIComponentUtil;

/**
 * @author shot
 */
public class RegularExpressionValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = RegularExpressionValidator.class
            .getName();

    public static final String REGULAR_EXPRRESSION_MESSAGE_ID = VALIDATOR_ID
            + ".INVALID";

    private boolean transientValue_ = false;

    private String pattern_;

    public RegularExpressionValidator() {
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }
        String strValue = value.toString();
        if (!PatternUtil.matches(getPattern(), strValue)) {
            Object[] args = new Object[] { getPattern(), strValue,
                    UIComponentUtil.getLabel(component) };
            FacesMessage message = FacesMessageUtils.getMessage(context,
                    REGULAR_EXPRRESSION_MESSAGE_ID, args);
            throw new ValidatorException(message);
        }
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return pattern_;
    }

    public void restoreState(FacesContext context, Object state) {
        pattern_ = (String) state;
    }

    public String getPattern() {
        return pattern_;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

}
