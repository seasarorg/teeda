/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.webapp.ConverterTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 * @author yone
 */
public class ConvertNumberTag extends ConverterTag {

    private static final long serialVersionUID = 1L;

    private String currencyCode_ = null;

    private String currencySymbol_ = null;

    private String groupingUsed_ = JsfConstants.DEFAULT_CONVERTNUMBER_GROUPING_USED;

    private String integerOnly_ = JsfConstants.DEFAULT_CONVERTNUMBER_INTEGER_ONLY;

    private String locale_ = null;

    private String maxFractionDigits_ = null;

    private String maxIntegerDigits_ = null;

    private String minFractionDigits_ = null;

    private String minIntegerDigits_ = null;

    private String pattern_ = null;

    private String type_ = JsfConstants.DEFAULT_CONVERTNUMBER_TYPE;

    public ConvertNumberTag() {
    }

    public void setPageContext(PageContext context) {
        super.setPageContext(context);
        setConverterId(NumberConverter.CONVERTER_ID);
    }

    protected Converter createConverter() throws JspException {
        NumberConverter converter = (NumberConverter) super.createConverter();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        setConverterCurrencyCode(facesContext, converter);
        setConverterCurrencySymbol(facesContext, converter);
        setConverterGroupingUsed(facesContext, converter);
        setConverterIntegerOnly(facesContext, converter);
        setConverterLocale(facesContext, converter);
        setConverterMaxFractionDigits(facesContext, converter);
        setConverterMaxIntegerDigits(facesContext, converter);
        setConverterMinFractionDigits(facesContext, converter);
        setConverterMinIntegerDigits(facesContext, converter);
        setConverterPattern(facesContext, converter);
        setConverterType(facesContext, converter);

        return converter;
    }

    protected void setConverterCurrencyCode(FacesContext context,
            NumberConverter converter) {
        String currencyCode = getCurrencyCode();
        if (currencyCode == null) {
            return;
        }
        String cCode = (String) ValueBindingUtil
                .getValue(context, currencyCode);
        if (cCode == null) {
            cCode = currencyCode;
        }
        converter.setCurrencyCode(cCode);
    }

    protected void setConverterCurrencySymbol(FacesContext context,
            NumberConverter converter) {
        String currencySymbol = getCurrencySymbol();
        if (currencySymbol == null) {
            return;
        }
        String symbol = (String) ValueBindingUtil.getValue(context,
                currencySymbol);
        if (symbol == null) {
            symbol = currencySymbol;
        }
        converter.setCurrencySymbol(symbol);
    }

    protected void setConverterGroupingUsed(FacesContext context,
            NumberConverter converter) {
        final String groupingUsed = getGroupingUsed();
        if (groupingUsed == null) {
            return;
        }
        Boolean b = (Boolean) ValueBindingUtil.getValue(context, groupingUsed);
        if (b == null) {
            b = new Boolean(groupingUsed);
        }
        converter.setGroupingUsed(b.booleanValue());
    }

    protected void setConverterIntegerOnly(FacesContext context,
            NumberConverter converter) {
        final String integerOnly = getIntegerOnly();
        if (integerOnly == null) {
            return;
        }
        Boolean b = (Boolean) ValueBindingUtil.getValue(context, integerOnly);
        if (b == null) {
            b = new Boolean(integerOnly);
        }
        converter.setIntegerOnly(b.booleanValue());
    }

    protected void setConverterLocale(FacesContext context,
            NumberConverter converter) {
        Locale locale = (Locale) ValueBindingUtil
                .getValue(context, getLocale());
        if (locale == null) {
            locale = LocaleUtil.getLocale(getLocale());
        }
        converter.setLocale(locale);
    }

    protected void setConverterMaxFractionDigits(FacesContext context,
            NumberConverter converter) {
        final String maxFractionDigits = getMaxFractionDigits();
        if (maxFractionDigits == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                maxFractionDigits);
        if (i == null) {
            i = new Integer(maxFractionDigits);
        }
        converter.setMaxFractionDigits(i.intValue());
    }

    protected void setConverterMaxIntegerDigits(FacesContext context,
            NumberConverter converter) {
        final String maxIntegerDigits = getMaxIntegerDigits();
        if (maxIntegerDigits == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                maxIntegerDigits);
        if (i == null) {
            i = new Integer(maxIntegerDigits);
        }
        converter.setMaxIntegerDigits(i.intValue());
    }

    protected void setConverterMinFractionDigits(FacesContext context,
            NumberConverter converter) {
        final String minFractionDigits = getMinFractionDigits();
        if (minFractionDigits == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                minFractionDigits);
        if (i == null) {
            i = new Integer(minFractionDigits);
        }
        converter.setMinFractionDigits(i.intValue());
    }

    protected void setConverterMinIntegerDigits(FacesContext context,
            NumberConverter converter) {
        final String minIntegerDigits = getMinIntegerDigits();
        if (minIntegerDigits == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                minIntegerDigits);
        if (i == null) {
            i = new Integer(minIntegerDigits);
        }
        converter.setMinIntegerDigits(i.intValue());
    }

    protected void setConverterPattern(FacesContext context,
            NumberConverter converter) {
        String pattern = (String) ValueBindingUtil.getValue(context,
                getPattern());
        if (pattern == null) {
            pattern = getPattern();
        }
        converter.setPattern(pattern);
    }

    protected void setConverterType(FacesContext context,
            NumberConverter converter) {
        String type = (String) ValueBindingUtil.getValue(context, getType());
        if (type == null) {
            type = getType();
        }
        converter.setType(type);
    }

    public void release() {
        super.release();
        currencyCode_ = null;
        currencySymbol_ = null;
        groupingUsed_ = JsfConstants.DEFAULT_CONVERTNUMBER_GROUPING_USED;
        integerOnly_ = JsfConstants.DEFAULT_CONVERTNUMBER_INTEGER_ONLY;
        locale_ = null;
        maxFractionDigits_ = null;
        maxIntegerDigits_ = null;
        minFractionDigits_ = null;
        minIntegerDigits_ = null;
        pattern_ = null;
        type_ = JsfConstants.DEFAULT_CONVERTNUMBER_TYPE;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode_ = currencyCode;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol_ = currencySymbol;
    }

    public void setGroupingUsed(String groupingUsed) {
        this.groupingUsed_ = groupingUsed;
    }

    public void setIntegerOnly(String integerOnly) {
        this.integerOnly_ = integerOnly;
    }

    public void setLocale(String locale) {
        this.locale_ = locale;
    }

    public void setMaxFractionDigits(String maxFractionDigits) {
        this.maxFractionDigits_ = maxFractionDigits;
    }

    public void setMaxIntegerDigits(String maxIntegerDigits) {
        this.maxIntegerDigits_ = maxIntegerDigits;
    }

    public void setMinFractionDigits(String minFractionDigits) {
        this.minFractionDigits_ = minFractionDigits;
    }

    public void setMinIntegerDigits(String minIntegerDigits) {
        this.minIntegerDigits_ = minIntegerDigits;
    }

    public void setPattern(String pattern) {
        this.pattern_ = pattern;
    }

    public void setType(String type) {
        this.type_ = type;
    }

    public String getCurrencyCode() {
        return currencyCode_;
    }

    public String getCurrencySymbol() {
        return currencySymbol_;
    }

    public String getGroupingUsed() {
        return groupingUsed_;
    }

    public String getIntegerOnly() {
        return integerOnly_;
    }

    public String getLocale() {
        return locale_;
    }

    public String getMaxFractionDigits() {
        return maxFractionDigits_;
    }

    public String getMaxIntegerDigits() {
        return maxIntegerDigits_;
    }

    public String getMinFractionDigits() {
        return minFractionDigits_;
    }

    public String getMinIntegerDigits() {
        return minIntegerDigits_;
    }

    public String getPattern() {
        return pattern_;
    }

    public String getType() {
        return type_;
    }

}