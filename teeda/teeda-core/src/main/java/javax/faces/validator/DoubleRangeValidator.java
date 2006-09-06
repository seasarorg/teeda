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

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.DoubleConversionUtil;

/**
 * @author shot
 */
public class DoubleRangeValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.DoubleRange";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";

    public static final String TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";

    private double maximum = Double.MAX_VALUE;

    private double minimum = Double.MIN_VALUE;

    private boolean transientValue = false;

    public DoubleRangeValidator() {
    }

    public DoubleRangeValidator(double maximum) {
        this.maximum = maximum;
    }

    public DoubleRangeValidator(double maximum, double minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleRangeValidator)) {
            return false;
        }

        DoubleRangeValidator v = (DoubleRangeValidator) obj;
        return maximum == v.maximum && minimum == v.minimum;
    }

    public int hashCode() {
        return (int) maximum * (int) minimum * 17;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] obj = (Object[]) state;
        maximum = ((Double) obj[0]).doubleValue();
        minimum = ((Double) obj[1]).doubleValue();
    }

    public Object saveState(FacesContext context) {
        Object[] obj = new Object[2];
        obj[0] = new Double(maximum);
        obj[1] = new Double(minimum);
        return obj;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);

        if (value == null) {
            return;
        }

        double doubleValue;
        try {
            doubleValue = DoubleConversionUtil.toDouble(value).doubleValue();

            if (maximum != Double.MAX_VALUE && minimum != Double.MIN_VALUE) {
                if (doubleValue < minimum || doubleValue > maximum) {
                    Object[] args = { new Double(minimum), new Double(maximum),
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtil.getMessage(
                            context, getNotInRangeMessageId(), args),
                            getNotInRangeMessageId(), args);
                }

            } else if (minimum != Double.MIN_VALUE) {
                if (doubleValue < minimum) {
                    Object[] args = { new Double(minimum),
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtil.getMessage(
                            context, getMinimumMessageId(), args),
                            getMinimumMessageId(), args);
                }

            } else if (maximum != Double.MAX_VALUE) {
                if (doubleValue > maximum) {
                    Object[] args = { new Double(maximum),
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtil.getMessage(
                            context, getMaximumMessageId(), args),
                            getMaximumMessageId(), args);
                }
            }
        } catch (NumberFormatException e) {
            Object[] args = { UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getTypeMessageId(), args), getTypeMessageId(), args);
        }

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
}