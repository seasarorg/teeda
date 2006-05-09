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
package javax.faces.validator;

import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.AssertionUtil;
import javax.faces.internal.FacesMessageUtils;

import org.seasar.teeda.core.util.UIComponentUtil;

/**
 * @author shot
 */
public class LongRangeValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.LongRange";

    public static final String TYPE_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.TYPE";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";

    private Long maximum = null;

    private Long minimum = null;

    private boolean transientValue_ = false;

    public LongRangeValidator() {
        super();
    }

    public LongRangeValidator(long maximum) {
        super();
        this.maximum = new Long(maximum);
    }

    public LongRangeValidator(long maximum, long minimum) {
        super();
        this.maximum = new Long(maximum);
        this.minimum = new Long(minimum);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }

        long longValue;
        try {
            longValue = parseLongValue(value);

            if (maximum != null && minimum != null) {

                double minValue = minimum.longValue();
                double maxValue = maximum.longValue();

                if (longValue < minValue || longValue > maxValue) {
                    Object[] args = { minimum, maximum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, NOT_IN_RANGE_MESSAGE_ID, args));
                }

            } else if (minimum != null) {

                double minValue = minimum.longValue();
                if (longValue < minValue) {
                    Object[] args = { minimum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, MINIMUM_MESSAGE_ID, args));
                }

            } else if (maximum != null) {

                double maxValue = maximum.longValue();
                if (longValue > maxValue) {
                    Object[] args = { maximum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, MAXIMUM_MESSAGE_ID, args));
                }
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(FacesMessageUtils.getMessage(context,
                    TYPE_MESSAGE_ID, new Object[] { UIComponentUtil
                            .getLabel(component) }));
        }

    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = maximum;
        values[1] = minimum;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        maximum = (Long) values[0];
        minimum = (Long) values[1];
    }

    public long getMaximum() {
        return (maximum != null) ? maximum.longValue() : Long.MAX_VALUE;
    }

    public long getMinimum() {
        return (minimum != null) ? minimum.longValue() : Long.MIN_VALUE;
    }

    public void setMaximum(long maximum) {
        this.maximum = new Long(maximum);
    }

    public void setMinimum(long minimum) {
        this.minimum = new Long(minimum);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LongRangeValidator)) {
            return false;
        }

        LongRangeValidator v = (LongRangeValidator) obj;

        if ((maximum != null && v.maximum == null)
                && (maximum == null && v.maximum != null)) {
            return false;
        }

        if ((minimum != null && v.minimum == null)
                && (minimum == null && v.minimum != null)) {
            return false;
        }

        return (this.getMaximum() == v.getMaximum() && this.getMinimum() == v
                .getMinimum());
    }

    public int hashCode() {
        Long max = maximum != null ? maximum : new Long(1);
        Long min = minimum != null ? minimum : new Long(1);
        return max.hashCode() * min.hashCode() * 17;
    }

    private static long parseLongValue(Object obj) throws NumberFormatException {

        long value;
        if (obj instanceof Number) {
            value = ((Number) obj).longValue();
        } else {
            value = Long.parseLong(obj.toString());
        }
        return value;
    }
}
