/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.TargetCommandUtil;

/**
 * @author shot
 */
public class TGreaterThanConstantValidator implements Validator, StateHolder {

    private static final String GC_MESSAGE_ID = "org.seasar.teeda.extension.validator.TGreaterThanConstantValidator.GC";

    protected boolean transientValue = false;

    //TODO targetプロパティは、他の拡張Validatorにあわせて、どのボタンが押されたかに使用すべき（すぐには修正できない）。
    protected Object target = new Integer(0);

    //どのボタンが押されたかを指定
    protected String targetCommand = null;

    protected String[] targetCommands;

    protected String messageId = null;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (value == null) {
            return;
        }
        if (target == null) {
            return;
        }
        if (!TargetCommandUtil.isTargetCommand(context, targetCommands)) {
            return;
        }
        Object t = NumberConversionUtil.convertNumber(value.getClass(),
                getTarget());
        if (isLessEqual(value, t)) {
            Object[] args = { t, UIComponentUtil.getLabel(component) };
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
        Object values[] = new Object[3];
        values[0] = messageId;
        values[1] = target;
        values[2] = targetCommand;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        messageId = (String) values[0];
        target = values[1];
        targetCommand = (String) values[2];
        setTargetCommand(targetCommand);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getTargetCommand() {
        return targetCommand;
    }

    public void setTargetCommand(String targetCommand) {
        this.targetCommand = targetCommand;
        if (StringUtil.isEmpty(targetCommand)) {
            return;
        }
        targetCommands = StringUtil.split(targetCommand, ", ");
    }
}
