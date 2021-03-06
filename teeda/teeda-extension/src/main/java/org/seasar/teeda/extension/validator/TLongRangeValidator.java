/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.exception.ExtendValidatorException;
import org.seasar.teeda.extension.util.TargetCommandUtil;

/**
 * @author shot
 */
public class TLongRangeValidator extends LongRangeValidator implements
        ValidationTargetSelectable {

    protected String target;

    protected String[] targets;

    protected String maximumMessageId;

    protected String minimumMessageId;

    protected String notInRangeMessageId;

    protected String typeMessageId;

    protected boolean convert = true;

    public TLongRangeValidator() {
        super();
    }

    public TLongRangeValidator(long maximum) {
        super(maximum);
    }

    public TLongRangeValidator(long maximum, long minimum) {
        super(maximum, minimum);
    }

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
                    new String[] { maximumMessageId, minimumMessageId,
                            notInRangeMessageId, typeMessageId });
        }
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[7];
        state[0] = super.saveState(context);
        state[1] = target;
        state[2] = maximumMessageId;
        state[3] = minimumMessageId;
        state[4] = notInRangeMessageId;
        state[5] = typeMessageId;
        state[6] = new Boolean(convert);
        return state;
    }

    public void restoreState(FacesContext context, Object obj) {
        Object[] state = (Object[]) obj;
        super.restoreState(context, state[0]);
        target = (String) state[1];
        setTarget(target);
        maximumMessageId = (String) state[2];
        minimumMessageId = (String) state[3];
        notInRangeMessageId = (String) state[4];
        typeMessageId = (String) state[5];
        convert = ((Boolean) state[6]).booleanValue();
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

    public String getMaximumMessageId() {
        return !StringUtil.isEmpty(maximumMessageId) ? maximumMessageId
                : MAXIMUM_MESSAGE_ID;
    }

    public String getMinimumMessageId() {
        return !StringUtil.isEmpty(minimumMessageId) ? minimumMessageId
                : MINIMUM_MESSAGE_ID;
    }

    public String getNotInRangeMessageId() {
        return !StringUtil.isEmpty(notInRangeMessageId) ? notInRangeMessageId
                : NOT_IN_RANGE_MESSAGE_ID;
    }

    public String getTypeMessageId() {
        return !StringUtil.isEmpty(typeMessageId) ? typeMessageId
                : TYPE_MESSAGE_ID;
    }

    public void setMaximumMessageId(String maximumMessageId) {
        this.maximumMessageId = maximumMessageId;
    }

    public void setMinimumMessageId(String minimumMessageId) {
        this.minimumMessageId = minimumMessageId;
    }

    public void setNotInRangeMessageId(String notInRangeMessageId) {
        this.notInRangeMessageId = notInRangeMessageId;
    }

    public void setTypeMessageId(String typeMessageId) {
        this.typeMessageId = typeMessageId;
    }

    public boolean isConvert() {
        return convert;
    }

    public void setConvert(boolean convert) {
        this.convert = convert;
    }

    protected Object[] convertArgs(Object[] args) {
        return (isConvert()) ? convert(args) : args;
    }

    protected Object[] convert(Object[] args) {
        Object[] ret = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Long) {
                long l = ((Long) args[i]).longValue();
                ret[i] = String.valueOf(l);
            } else {
                ret[i] = args[i];
            }
        }
        return ret;
    }

    public boolean isTargetCommandValidation(FacesContext context,
            String[] targets) {
        return TargetCommandUtil.isTargetCommand(context, targets);
    }

    public String[] getTargets() {
        return targets;
    }

}
