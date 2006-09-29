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
package org.seasar.teeda.extension.convert;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.BigDecimalConverter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.ConvertUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.util.BigDecimalFormatUtil;

/**
 * @author shot
 */
public class TBigDecimalConverter extends BigDecimalConverter {

    private String pattern;

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
            String pattern = getPattern();
            return BigDecimalFormatUtil.format((BigDecimal) value, pattern);
        } catch (Exception e) {
            throw ConvertUtil.wrappedByConverterException(e);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
