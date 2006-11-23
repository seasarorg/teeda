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

/**
 * @author shot
 */
public class LengthValidator implements Validator, StateHolder {

    public static final String VALIDATOR_ID = "javax.faces.Length";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";

    private int maximum = -1;

    private int minimum = -1;

    private boolean transientValue = false;

    public LengthValidator() {
        super();
    }

    public LengthValidator(int maximum) {
        super();
        this.maximum = maximum;
    }

    public LengthValidator(int maximum, int minimum) {
        super();
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LengthValidator)) {
            return false;
        }

        LengthValidator v = (LengthValidator) obj;
        return maximum == v.maximum && minimum == v.minimum;
    }

    public int hashCode() {
        return maximum * minimum * 17;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);

        if (value == null) {
            return;
        }

        int length = getConvertedValueLength(value);
        if (minimum > -1 && length < minimum) {
            Object[] args = { new Integer(minimum),
                    UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getMinimumMessageId(), args), getMinimumMessageId(), args);
        }

        if (maximum > -1 && length > maximum) {
            Object[] args = { new Integer(maximum),
                    UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    getMaximumMessageId(), args), getMaximumMessageId(), args);
        }
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = new Integer(maximum);
        values[1] = new Integer(minimum);
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        maximum = ((Integer) values[0]).intValue();
        minimum = ((Integer) values[1]).intValue();
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
