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

import java.math.BigDecimal;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class TGreaterThanConstantValidator implements Validator, StateHolder {

    private static final String GC_MESSAGE_ID = "org.seasar.teeda.extension.validator.TGreaterThanConstantValidator.GC";

    private boolean transientValue = false;

    private Object target = new Integer(0);

    private String messageId = null;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }
        Object target = getTarget();
        if (target == null) {
            return;
        }
        if (isLessEqual(value, target)) {
            if (!(target instanceof BigDecimal)) {
                target = new BigDecimal(target.toString());
            }
            Object[] args = { target, UIComponentUtil.getLabel(component) };
            String messaId = getMessageId();
            FacesMessage message = FacesMessageUtil.getMessage(context,
                    messaId, args);
            throw new ValidatorException(message, messaId, args);
        }
    }

    private boolean isLessEqual(Object value, Object targetValue) {
        return (!(value instanceof Comparable))
                || ((Comparable) value).compareTo(targetValue) <= 0;
    }

    public String getMessageId() {
        return (messageId != null) ? messageId : GC_MESSAGE_ID;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = messageId;
        values[1] = target;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        messageId = (String) values[0];
        target = values[1];
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
