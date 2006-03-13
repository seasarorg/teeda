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
package org.seasar.teeda.core.taglib.core;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.webapp.ConverterTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.ValueBindingUtil;

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
        if (currencyCode_ == null) {
            return;
        }
        String currencyCode = (String) ValueBindingUtil.getValue(context,
                currencyCode_);
        if (currencyCode == null) {
            currencyCode = currencyCode_;
        }
        converter.setCurrencyCode(currencyCode);
    }

    protected void setConverterCurrencySymbol(FacesContext context,
            NumberConverter converter) {
        if (currencySymbol_ == null) {
            return;
        }
        String currencySymbol = (String) ValueBindingUtil.getValue(context,
                currencySymbol_);
        if (currencySymbol == null) {
            currencySymbol = currencySymbol_;
        }
        converter.setCurrencySymbol(currencySymbol);
    }

    protected void setConverterGroupingUsed(FacesContext context,
            NumberConverter converter) {
        if (groupingUsed_ == null) {
            return;
        }
        Boolean b = (Boolean) ValueBindingUtil.getValue(context, groupingUsed_);
        if (b == null) {
            b = new Boolean(groupingUsed_);
        }
        converter.setGroupingUsed(b.booleanValue());
    }

    protected void setConverterIntegerOnly(FacesContext context,
            NumberConverter converter) {
        if (integerOnly_ == null) {
            return;
        }
        Boolean b = (Boolean) ValueBindingUtil.getValue(context, integerOnly_);
        if (b == null) {
            b = new Boolean(integerOnly_);
        }
        converter.setIntegerOnly(b.booleanValue());
    }

    protected void setConverterLocale(FacesContext context,
            NumberConverter converter) {
        Locale locale = (Locale) ValueBindingUtil.getValue(context, locale_);
        if (locale == null) {
            locale = LocaleUtil.getLocale(locale_);
        }
        converter.setLocale(locale);
    }

    protected void setConverterMaxFractionDigits(FacesContext context,
            NumberConverter converter) {
        if (maxFractionDigits_ == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                maxFractionDigits_);
        if (i == null) {
            i = new Integer(maxFractionDigits_);
        }
        converter.setMaxFractionDigits(i.intValue());
    }

    protected void setConverterMaxIntegerDigits(FacesContext context,
            NumberConverter converter) {
        if (maxIntegerDigits_ == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                maxIntegerDigits_);
        if (i == null) {
            i = new Integer(maxIntegerDigits_);
        }
        converter.setMaxIntegerDigits(i.intValue());
    }

    protected void setConverterMinFractionDigits(FacesContext context,
            NumberConverter converter) {
        if (minFractionDigits_ == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                minFractionDigits_);
        if (i == null) {
            i = new Integer(minFractionDigits_);
        }
        converter.setMinFractionDigits(i.intValue());
    }

    protected void setConverterMinIntegerDigits(FacesContext context,
            NumberConverter converter) {
        if (minIntegerDigits_ == null) {
            return;
        }
        Integer i = (Integer) ValueBindingUtil.getValue(context,
                minIntegerDigits_);
        if (i == null) {
            i = new Integer(minIntegerDigits_);
        }
        converter.setMinIntegerDigits(i.intValue());
    }

    protected void setConverterPattern(FacesContext context,
            NumberConverter converter) {
        String pattern = (String) ValueBindingUtil.getValue(context, pattern_);
        if (pattern == null) {
            pattern = pattern_;
        }
        converter.setPattern(pattern);
    }

    protected void setConverterType(FacesContext context,
            NumberConverter converter) {
        String type = (String) ValueBindingUtil.getValue(context, type_);
        if (type == null) {
            type = type_;
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
        currencyCode_ = currencyCode;
    }

    public void setCurrencySymbol(String currencySymbol) {
        currencySymbol_ = currencySymbol;
    }

    public void setGroupingUsed(String groupingUsed) {
        groupingUsed_ = groupingUsed;
    }

    public void setIntegerOnly(String integerOnly) {
        integerOnly_ = integerOnly;
    }

    public void setLocale(String locale) {
        locale_ = locale;
    }

    public void setMaxFractionDigits(String maxFractionDigits) {
        maxFractionDigits_ = maxFractionDigits;
    }

    public void setMaxIntegerDigits(String maxIntegerDigits) {
        maxIntegerDigits_ = maxIntegerDigits;
    }

    public void setMinFractionDigits(String minFractionDigits) {
        minFractionDigits_ = minFractionDigits;
    }

    public void setMinIntegerDigits(String minIntegerDigits) {
        minIntegerDigits_ = minIntegerDigits;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

    public void setType(String type) {
        type_ = type;
    }

    String getCurrencyCode() {
        return currencyCode_;
    }

    String getCurrencySymbol() {
        return currencySymbol_;
    }

    String getGroupingUsed() {
        return groupingUsed_;
    }

    String getIntegerOnly() {
        return integerOnly_;
    }

    String getLocale() {
        return locale_;
    }

    String getMaxFractionDigits() {
        return maxFractionDigits_;
    }

    String getMaxIntegerDigits() {
        return maxIntegerDigits_;
    }

    String getMinFractionDigits() {
        return minFractionDigits_;
    }

    String getMinIntegerDigits() {
        return minIntegerDigits_;
    }

    String getPattern() {
        return pattern_;
    }

    String getType() {
        return type_;
    }

}