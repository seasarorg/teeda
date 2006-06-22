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
import javax.faces.internal.FacesMessageUtils;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class DoubleRangeValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.DoubleRange";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";

    public static final String TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";

    private Double maximum = null;

    private Double minimum = null;

    private boolean transientValue_ = false;

    public DoubleRangeValidator() {
        super();
    }

    public DoubleRangeValidator(double maximum) {
        super();
        this.maximum = new Double(maximum);
    }

    public DoubleRangeValidator(double maximum, double minimum) {
        super();
        this.maximum = new Double(maximum);
        this.minimum = new Double(minimum);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleRangeValidator)) {
            return false;
        }

        DoubleRangeValidator v = (DoubleRangeValidator) obj;

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
        Double max = maximum != null ? maximum : new Double(1);
        Double min = minimum != null ? minimum : new Double(1);
        return max.hashCode() * min.hashCode() * 17;
    }

    public double getMaximum() {
        return (maximum != null) ? maximum.doubleValue() : Double.MAX_VALUE;
    }

    public double getMinimum() {
        return (minimum != null) ? minimum.doubleValue() : Double.MIN_VALUE;
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] obj = (Object[]) state;
        maximum = (Double) obj[0];
        minimum = (Double) obj[1];
    }

    public Object saveState(FacesContext context) {
        Object[] obj = new Object[2];
        obj[0] = maximum;
        obj[1] = minimum;
        return obj;
    }

    public void setMaximum(double maximum) {
        this.maximum = new Double(maximum);
    }

    public void setMinimum(double minimum) {
        this.minimum = new Double(minimum);
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
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
            doubleValue = parseDoubleValue(value);

            if (maximum != null && minimum != null) {

                double minValue = minimum.doubleValue();
                double maxValue = maximum.doubleValue();

                if (doubleValue < minValue || doubleValue > maxValue) {
                    Object[] args = { minimum, maximum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, NOT_IN_RANGE_MESSAGE_ID, args));
                }

            } else if (minimum != null) {

                double minValue = minimum.doubleValue();
                if (doubleValue < minValue) {
                    Object[] args = { minimum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, MINIMUM_MESSAGE_ID, args));
                }

            } else if (maximum != null) {

                double maxValue = maximum.doubleValue();
                if (doubleValue > maxValue) {
                    Object[] args = { maximum,
                            UIComponentUtil.getLabel(component) };
                    throw new ValidatorException(FacesMessageUtils.getMessage(
                            context, MAXIMUM_MESSAGE_ID, args));
                }
            }

        } catch (NumberFormatException e) {
            Object[] args = { UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtils.getMessage(context,
                    TYPE_MESSAGE_ID, args));
        }

    }

    private static double parseDoubleValue(Object obj)
            throws NumberFormatException {

        double value;
        if (obj instanceof Number) {
            value = ((Number) obj).doubleValue();
        } else {
            value = Double.parseDouble(obj.toString());
        }
        return value;
    }

}
