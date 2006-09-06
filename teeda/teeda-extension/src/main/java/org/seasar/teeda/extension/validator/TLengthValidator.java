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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.exception.ExtendValidatorException;
import org.seasar.teeda.extension.util.ValidatorUtil;

/**
 * @author shot
 */
public class TLengthValidator extends LengthValidator {

    private String target;

    private String[] targets;

    private String maximumMessageId;

    private String minimumMessageId;

    public TLengthValidator() {
        super();
    }

    public TLengthValidator(int maximum) {
        super(maximum);
    }

    public TLengthValidator(int maximum, int minimum) {
        super(maximum, minimum);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (!ValidatorUtil.isTargetCommand(context, targets)) {
            return;
        }
        try {
            super.validate(context, component, value);
        } catch (ValidatorException e) {
            throw new ExtendValidatorException(e.getFacesMessage(), e,
                    new String[] { maximumMessageId, minimumMessageId });
        }
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[4];
        state[0] = super.saveState(context);
        state[1] = target;
        state[2] = maximumMessageId;
        state[3] = minimumMessageId;
        return state;
    }

    public void restoreState(FacesContext context, Object obj) {
        Object[] state = (Object[]) obj;
        super.restoreState(context, state[0]);
        target = (String) state[1];
        setTarget(target);
        maximumMessageId = (String) state[2];
        minimumMessageId = (String) state[3];
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
        targets = StringUtil.split(target, ", ");
    }

    public String getMaximumMessageId() {
        return !StringUtil.isEmpty(maximumMessageId) ? maximumMessageId
                : MAXIMUM_MESSAGE_ID;
    }

    public String getMinimumMessageId() {
        return !StringUtil.isEmpty(minimumMessageId) ? minimumMessageId
                : MINIMUM_MESSAGE_ID;
    }

    public void setMaximumMessageId(String maximumMessageId) {
        this.maximumMessageId = maximumMessageId;
    }

    public void setMinimumMessageId(String minimumMessageId) {
        this.minimumMessageId = minimumMessageId;
    }
}