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

public class LengthValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.Length";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";

    private Integer maximum_ = null;

    private Integer minimum_ = null;

    private boolean transientValue_ = false;

    public LengthValidator() {
        super();
    }

    public LengthValidator(int maximum) {
        super();
        maximum_ = new Integer(maximum);
    }

    public LengthValidator(int maximum, int minimum) {
        super();
        maximum_ = new Integer(maximum);
        minimum_ = new Integer(minimum);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LengthValidator)) {
            return false;
        }

        LengthValidator v = (LengthValidator) obj;
        if ((maximum_ != null && v.maximum_ == null)
                && (maximum_ == null && v.maximum_ != null)) {
            return false;
        }

        if ((minimum_ != null && v.minimum_ == null)
                && (minimum_ == null && v.minimum_ != null)) {
            return false;
        }

        return (this.getMaximum() == v.getMaximum() && this.getMinimum() == v
                .getMinimum());

    }

    public int hashCode() {
        Integer max = maximum_ != null ? maximum_ : new Integer(1);
        Integer min = minimum_ != null ? minimum_ : new Integer(1);
        return max.hashCode() * min.hashCode() * 17;
    }

    public int getMaximum() {
        return (maximum_ != null) ? maximum_.intValue() : 0;
    }

    public int getMinimum() {
        return (minimum_ != null) ? minimum_.intValue() : 0;
    }

    public void setMaximum(int maximum) {
        maximum_ = new Integer(maximum);
    }

    public void setMinimum(int minimum) {
        minimum_ = new Integer(minimum);
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);

        if (value == null) {
            return;
        }

        int length = getConvertedValueLength(value);
        if (minimum_ != null && length < minimum_.intValue()) {
            Object[] args = { minimum_, component.getId() };
            throw new ValidatorException(FacesMessageUtils.getMessage(context,
                    MINIMUM_MESSAGE_ID, args));
        }

        if (maximum_ != null && length > maximum_.intValue()) {
            Object[] args = { maximum_, component.getId() };
            throw new ValidatorException(FacesMessageUtils.getMessage(context,
                    MAXIMUM_MESSAGE_ID, args));
        }
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = maximum_;
        values[1] = minimum_;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        maximum_ = (Integer) values[0];
        minimum_ = (Integer) values[1];
    }

    private static int getConvertedValueLength(Object value) {
        int length = 0;
        if (value instanceof String) {
            length = ((String) value).length();
        } else {
            length = value.toString().length();
        }
        return length;
    }
}
