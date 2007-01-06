/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.util.ValidatorUtil;

/**
 * @author higa
 * @author shot
 */
public abstract class AbstractCompareValidator implements Validator,
        StateHolder, ValidationTargetSelectable {

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";

    private String target = null;

    private String[] targets;

    private String targetId = null;

    private boolean transientValue = false;

    private String messageId = null;

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
        Object values[] = new Object[3];
        values[0] = targetId;
        values[1] = messageId;
        values[2] = target;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        targetId = (String) values[0];
        messageId = (String) values[1];
        target = (String) values[2];
        setTarget(target);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }
        if (!isTargetCommandValidation(context, targets)) {
            return;
        }
        UIComponent targetComponent = getTargetComponent(component);
        Object targetValue = ValueHolderUtil.getValue(targetComponent);
        doValidate(context, component, value, targetComponent, targetValue);
    }

    
    public boolean isTargetCommandValidation(FacesContext context, String[] targets) {
        return ValidatorUtil.isTargetCommand(context, targets);
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