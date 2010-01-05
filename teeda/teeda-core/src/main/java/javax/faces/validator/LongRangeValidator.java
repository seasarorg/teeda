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
package javax.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.internal.UIInputUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.LongConversionUtil;

/**
 * @author shot
 */
public class LongRangeValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.LongRange";

    public static final String TYPE_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.TYPE";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";

    private long maximum = Long.MAX_VALUE;

    private long minimum = Long.MIN_VALUE;

    private boolean transientValue = false;

    public LongRangeValidator() {
    }

    public LongRangeValidator(long maximum) {
        this.maximum = maximum;
    }

    public LongRangeValidator(long maximum, long minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (UIInputUtil.isEmpty(value)) {
            return;
        }
        long longValue = 0;
        try {
            longValue = LongConversionUtil.toLong(value).longValue();

            if (maximum < Long.MAX_VALUE && minimum > Long.MIN_VALUE) {
                if (longValue < minimum || longValue > maximum) {
                    Object[] args = { new Long(minimum), new Long(maximum),
                            UIComponentUtil.getLabel(component) };
                    FacesMessage message = FacesMessageUtil.getMessage(context,
                            getNotInRangeMessageId(), convertArgs(args));
                    throw new ValidatorException(message,
                            getNotInRangeMessageId(), args);
                }
            } else if (minimum > Long.MIN_VALUE) {
                if (longValue < minimum) {
                    Object[] args = { new Long(minimum),
                            UIComponentUtil.getLabel(component) };
                    FacesMessage message = FacesMessageUtil.getMessage(context,
                            getMinimumMessageId(), convertArgs(args));
                    throw new ValidatorException(message,
                            getMinimumMessageId(), args);
                }
            } else if (maximum < Long.MAX_VALUE) {
                if (longValue > maximum) {
                    Object[] args = { new Long(maximum),
                            UIComponentUtil.getLabel(component) };
                    FacesMessage message = FacesMessageUtil.getMessage(context,
                            getMaximumMessageId(), convertArgs(args));
                    throw new ValidatorException(message,
                            getMaximumMessageId(), args);
                }
            }
        } catch (NumberFormatException e) {
            Object[] args = new Object[] { UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getTypeMessageId(), args), getTypeMessageId(), args);
        }
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = new Long(maximum);
        values[1] = new Long(minimum);
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        maximum = ((Long) values[0]).longValue();
        minimum = ((Long) values[1]).longValue();
    }

    public long getMaximum() {
        return maximum;
    }

    public long getMinimum() {
        return minimum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LongRangeValidator)) {
            return false;
        }

        LongRangeValidator v = (LongRangeValidator) obj;
        return maximum == v.maximum && minimum == v.minimum;
    }

    public int hashCode() {
        return (int) maximum * (int) minimum * 17;
    }

    protected String getMaximumMessageId() {
        return MAXIMUM_MESSAGE_ID;
    }

    protected String getMinimumMessageId() {
        return MINIMUM_MESSAGE_ID;
    }

    protected String getNotInRangeMessageId() {
        return NOT_IN_RANGE_MESSAGE_ID;
    }

    protected String getTypeMessageId() {
        return TYPE_MESSAGE_ID;
    }

    protected Object[] convertArgs(Object[] args) {
        return args;
    }
}
