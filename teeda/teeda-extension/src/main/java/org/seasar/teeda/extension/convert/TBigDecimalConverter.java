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
package org.seasar.teeda.extension.convert;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.BigDecimalConverter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.ConvertUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.BigDecimalFormatUtil;
import org.seasar.teeda.extension.util.ConverterUtil;

/**
 * @author shot
 * @author contributed by SMG Co., Ltd.(http://www.smg.co.jp/)
 * @author yone
 */
public class TBigDecimalConverter extends BigDecimalConverter implements
        ConvertTargetSelectable {

    public static final int SCALE_NONE = -1;

    public static final int ROUNDINGMODE_NONE = -1;

    private String pattern;

    private Integer scale;

    private Integer roundingMode;

    private String target;

    private String[] targets;

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

            BigDecimal decimalValue = (BigDecimal) value;
            if (this.scale != null && scale.intValue() != SCALE_NONE) {
                if (this.roundingMode != null
                        && scale.intValue() != ROUNDINGMODE_NONE) {
                    decimalValue = decimalValue.setScale(this.scale.intValue(),
                            this.roundingMode.intValue());
                } else {
                    decimalValue = decimalValue.setScale(this.scale.intValue());
                }
            }

            String pattern = getPattern();

            return BigDecimalFormatUtil.format(decimalValue, pattern);
        } catch (Exception e) {
            if (!isTargetCommandConvert(context, targets)) {
                return null;
            }
            throw ConvertUtil.wrappedByConverterException(e);
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
        return ConverterUtil.isTargetCommand(context, targets);
    }
}
