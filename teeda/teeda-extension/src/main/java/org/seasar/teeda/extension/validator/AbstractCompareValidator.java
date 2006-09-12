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

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author higa
 * @author shot
 */
public abstract class AbstractCompareValidator implements Validator,
        StateHolder {

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";

    private String targetId = null;

    private boolean transientValue = false;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[1];
        values[0] = targetId;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        targetId = (String) values[0];
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        UIComponent targetComponent = getTargetComponent(component);
        Object targetValue = ValueHolderUtil.getValue(targetComponent);
        doValidate(context, component, value, targetComponent, targetValue);
    }

    protected UIComponent getTargetComponent(UIComponent component) {
        if (targetId == null) {
            throw new EmptyRuntimeException("targetId");
        }
        UIComponent targetComponent = component.findComponent(targetId);
        if (targetComponent == null) {
            throw new EmptyRuntimeException(targetId);
        }
        return targetComponent;
    }

    protected abstract void doValidate(FacesContext context,
            UIComponent component, Object value, UIComponent targetComponent,
            Object targetValue) throws ValidatorException;
}