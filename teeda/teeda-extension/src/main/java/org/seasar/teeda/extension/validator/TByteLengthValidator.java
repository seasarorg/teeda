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

import java.io.UnsupportedEncodingException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.exception.ExtendValidatorException;
import org.seasar.teeda.extension.util.ValidatorUtil;

/**
 * @author shot
 */
public class TByteLengthValidator extends LengthValidator implements
        ValidationTargetSelectable {

    public static final String VALIDATOR_ID = "teeda.core.ByteLength";

    public static final String MAXIMUM_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.MINIMUM";

    public static final String ENCODE_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.ENCODE";

    private String charSet = null;

    private String target;

    private String[] targets;

    private String minimumMessageId;

    private String maximumMessageId;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (!isTargetCommandValidation(context, targets)) {
            return;
        }
        try {
            super.validate(context, component, value);
        } catch (ValidatorException e) {
            throw new ExtendValidatorException(e.getFacesMessage(), e,
                    new String[] { maximumMessageId, minimumMessageId });
        }
    }

    protected int getConvertedValueLength(Object value) {
        String str = null;
        if (value instanceof String) {
            str = (String) value;
        } else {
            str = value.toString();
        }
        if (StringUtil.isEmpty(charSet)) {
            return str.getBytes().length;
        }
        try {
            return getBytes(str, charSet).length;
        } catch (UnsupportedEncodingException e) {
            Object[] args = new Object[] { value, charSet };
            throw new ValidatorException(FacesMessageUtil.getMessage(
                    FacesContext.getCurrentInstance(), ENCODE_MESSAGE_ID, args));
        }

    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
        if (StringUtil.isEmpty(target)) {
            return;
        }
        targets = StringUtil.split(target, ", ");
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = charSet;
        values[2] = target;
        values[3] = maximumMessageId;
        values[4] = minimumMessageId;
        return values;
    }

    public void restoreState(FacesContext context, Object obj) {
        Object[] state = (Object[]) obj;
        super.restoreState(context, state[0]);
        charSet = (String) state[1];
        target = (String) state[2];
        setTarget(target);
        maximumMessageId = (String) state[3];
        minimumMessageId = (String) state[4];
    }

    private static byte[] getBytes(String value, String charSet)
            throws UnsupportedEncodingException {
        return value.getBytes(charSet);
    }

    public String getMaximumMessageId() {
        return !StringUtil.isEmpty(maximumMessageId) ? maximumMessageId
                : MAXIMUM_MESSAGE_ID;
    }

    public String getMinimumMessageId() {
        return !StringUtil.isEmpty(minimumMessageId) ? minimumMessageId
                : MINIMUM_MESSAGE_ID;
    }

    public void setMinimumMessageId(String minimumMessageId) {
        this.minimumMessageId = minimumMessageId;
    }

    public void setMaximumMessageId(String maximumMessageId) {
        this.maximumMessageId = maximumMessageId;
    }

    public boolean isTargetCommandValidation(FacesContext context,
            String[] targets) {
        return ValidatorUtil.isTargetCommand(context, targets);
    }
}