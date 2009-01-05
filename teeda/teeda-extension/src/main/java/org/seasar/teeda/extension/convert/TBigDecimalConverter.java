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
package org.seasar.teeda.extension.convert;

import java.math.BigDecimal;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.BigDecimalConverter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.ConvertUtil;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.BigDecimalFormatUtil;
import org.seasar.teeda.extension.util.TargetCommandUtil;

/**
 * @author shot
 * @author contributed by SMG Co., Ltd.(http://www.smg.co.jp/)
 * @author yone
 */
public class TBigDecimalConverter extends BigDecimalConverter implements
        ConvertTargetSelectable, StateHolder {

    public static final int SCALE_NONE = -1;

    public static final int ROUNDINGMODE_NONE = -1;

    protected String pattern;

    protected Integer scale;

    protected Integer roundingMode;

    protected String target;

    protected String[] targets;

    protected boolean transientValue = false;

    protected String objectMessageId;

    protected String stringMessageId;

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (!ConverterHelper.isTargetCommand(context, component, targets, this)) {
            return value;
        }
        return super.getAsObject(context, component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (value == null) {
            return "";
        }
        try {
            if (value instanceof String) {
                return (String) value;
            }
            if (!isTargetCommandConvert(context, targets)) {
                return null;
            }
            BigDecimal decimalValue = (BigDecimal) value;
            if (this.scale != null && scale.intValue() != SCALE_NONE) {
                if (this.roundingMode != null &&
                        scale.intValue() != ROUNDINGMODE_NONE) {
                    decimalValue = decimalValue.setScale(this.scale.intValue(),
                            this.roundingMode.intValue());
                } else {
                    decimalValue = decimalValue.setScale(this.scale.intValue());
                }
            }

            String pattern = getPattern();
            return BigDecimalFormatUtil.format(decimalValue, pattern);
        } catch (Exception e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    (String) value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getStringMessageId(), args));
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(Integer roundingMode) {
        this.roundingMode = roundingMode;
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

    public String getObjectMessageId() {
        return (!StringUtil.isEmpty(objectMessageId)) ? objectMessageId : super
                .getObjectMessageId();
    }

    public void setObjectMessageId(String objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    public String getStringMessageId() {
        return (!StringUtil.isEmpty(stringMessageId)) ? stringMessageId : super
                .getStringMessageId();
    }

    public void setStringMessageId(String stringMessageId) {
        this.stringMessageId = stringMessageId;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        pattern = (String) values[0];
        scale = (Integer) values[1];
        roundingMode = (Integer) values[2];
        target = (String) values[3];
        setTarget(target);
        objectMessageId = (String) values[4];
        stringMessageId = (String) values[5];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = pattern;
        values[1] = scale;
        values[2] = roundingMode;
        values[3] = target;
        values[4] = objectMessageId;
        values[5] = stringMessageId;
        return values;
    }

}
