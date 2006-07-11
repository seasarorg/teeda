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
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.Validator;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.PatternUtil;
import org.seasar.teeda.extension.exception.ExtendValidatorException;
import org.seasar.teeda.extension.util.ValidatorUtil;

/**
 * @author shot
 */
public class TRegularExpressionValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = TRegularExpressionValidator.class
            .getName();

    public static final String REGULAR_EXPRRESSION_MESSAGE_ID = VALIDATOR_ID
            + ".INVALID";

    private boolean transientValue = false;

    private String pattern;

    private String forValue;

    private String[] forValues;

    private String messageId;

    public TRegularExpressionValidator() {
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }
        if (!ValidatorUtil.isTargetCommand(context, forValues)) {
            return;
        }
        String strValue = value.toString();
        if (!PatternUtil.matches(getPattern(), strValue)) {
            Object[] args = new Object[] { getPattern(),
                    UIComponentUtil.getLabel(component) };
            String msgId = (messageId != null) ? messageId
                    : REGULAR_EXPRRESSION_MESSAGE_ID;
            FacesMessage message = FacesMessageUtil.getMessage(context, msgId,
                    args);
            throw new ExtendValidatorException(message, new String[] { msgId });
        }
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[3];
        state[0] = pattern;
        state[1] = forValue;
        state[2] = messageId;
        return state;
    }

    public void restoreState(FacesContext context, Object obj) {
        Object[] state = (Object[]) obj;
        pattern = (String) state[0];
        forValue = (String) state[1];
        setFor(forValue);
        messageId = (String) state[2];
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFor() {
        return forValue;
    }

    public void setFor(String forValue) {
        this.forValue = forValue;
        forValues = StringUtil.split(forValue, ", ");
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
