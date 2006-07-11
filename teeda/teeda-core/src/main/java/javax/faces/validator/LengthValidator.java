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
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class LengthValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.Length";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";

    private Integer maximum = null;

    private Integer minimum = null;

    private boolean transientValue = false;

    public LengthValidator() {
        super();
    }

    public LengthValidator(int maximum) {
        super();
        this.maximum = new Integer(maximum);
    }

    public LengthValidator(int maximum, int minimum) {
        super();
        this.maximum = new Integer(maximum);
        this.minimum = new Integer(minimum);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LengthValidator)) {
            return false;
        }

        LengthValidator v = (LengthValidator) obj;
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
        Integer max = maximum != null ? maximum : new Integer(1);
        Integer min = minimum != null ? minimum : new Integer(1);
        return max.hashCode() * min.hashCode() * 17;
    }

    public int getMaximum() {
        return (maximum != null) ? maximum.intValue() : 0;
    }

    public int getMinimum() {
        return (minimum != null) ? minimum.intValue() : 0;
    }

    public void setMaximum(int maximum) {
        this.maximum = new Integer(maximum);
    }

    public void setMinimum(int minimum) {
        this.minimum = new Integer(minimum);
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);

        if (value == null) {
            return;
        }

        int length = getConvertedValueLength(value);
        if (minimum != null && length < minimum.intValue()) {
            Object[] args = { minimum, UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getMinimumMessageId(), args));
        }

        if (maximum != null && length > maximum.intValue()) {
            Object[] args = { maximum, UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getMaximumMessageId(), args));
        }
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = maximum;
        values[1] = minimum;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        maximum = (Integer) values[0];
        minimum = (Integer) values[1];
    }

    protected int getConvertedValueLength(Object value) {
        int length = 0;
        if (value instanceof String) {
            length = ((String) value).length();
        } else {
            length = value.toString().length();
        }
        return length;
    }

    protected String getMinimumMessageId() {
        return MINIMUM_MESSAGE_ID;
    }

    protected String getMaximumMessageId() {
        return MAXIMUM_MESSAGE_ID;
    }
}
