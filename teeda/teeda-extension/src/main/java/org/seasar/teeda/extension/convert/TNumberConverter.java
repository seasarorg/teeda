/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.NumberConverter;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.TargetCommandUtil;

/**
 * @author shot
 */
public class TNumberConverter extends NumberConverter implements
        ConvertTargetSelectable, StateHolder {

    protected String target;

    protected String[] targets;

    protected String objectMessageId;

    protected String stringMessageId;

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        if (!ConverterHelper.isTargetCommand(context, component, targets, this)) {
            return value;
        }
        return super.getAsObject(context, component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        return super.getAsString(context, component, value);
    }

    /**
     * @return Returns the objectMessageId.
     */
    public String getObjectMessageId() {
        return (!StringUtil.isEmpty(objectMessageId)) ? objectMessageId : super
                .getObjectMessageId();
    }

    /**
     * @param objectMessageId The objectMessageId to set.
     */
    public void setObjectMessageId(String objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    /**
     * @return Returns the stringMessageId.
     */
    public String getStringMessageId() {
        return (!StringUtil.isEmpty(stringMessageId)) ? stringMessageId : super
                .getStringMessageId();
    }

    /**
     * @param stringMessageId The stringMessageId to set.
     */
    public void setStringMessageId(String stringMessageId) {
        this.stringMessageId = stringMessageId;
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

    public boolean isTargetCommandConvert(FacesContext context, String[] targets) {
        return TargetCommandUtil.isTargetCommand(context, targets);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = target;
        values[2] = objectMessageId;
        values[3] = stringMessageId;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        target = (String) values[1];
        setTarget(target);
        objectMessageId = (String) values[2];
        stringMessageId = (String) values[3];
    }

}
