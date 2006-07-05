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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.ValidatorUtil;

/**
 * @author shot
 */
public class TLengthValidator extends LengthValidator {

    private String forValue;

    private String[] forValues;

    public TLengthValidator() {
        super();
    }

    public TLengthValidator(int maximum) {
        super(maximum);
    }

    public TLengthValidator(int maximum, int minimum) {
        super(maximum, minimum);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (!ValidatorUtil.isTargetCommand(context, forValues)) {
            return;
        }
        super.validate(context, component, value);
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = forValue;
        return state;
    }

    public void restoreState(FacesContext context, Object obj) {
        Object[] state = (Object[]) obj;
        super.restoreState(context, state[0]);
        forValue = (String) state[1];
        setFor(forValue);
    }

    public String getFor() {
        return forValue;
    }

    public void setFor(String forValue) {
        this.forValue = forValue;
        forValues = StringUtil.split(forValue, ", ");
    }

}
