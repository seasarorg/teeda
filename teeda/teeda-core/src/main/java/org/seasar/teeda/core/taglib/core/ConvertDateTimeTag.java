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
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.webapp.ConverterTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.ValueBindingUtil;

/**
 * @author shot
 */
public class ConvertDateTimeTag extends ConverterTag {

    // TODO testing
    private static final long serialVersionUID = 1L;

    private String dateStyle_ = JsfConstants.DEFAULT_CONVERTDATETIME_DATE_STYLE;

    private String locale_;

    private String pattern_;

    private String timeStyle_ = JsfConstants.DEFAULT_CONVERTDATETIME_TIME_STYLE;

    private String timeZone_;

    private String type_ = JsfConstants.DEFAULT_CONVERTDATETIME_TYPE;

    public ConvertDateTimeTag() {
    }

    public void setPageContext(PageContext context) {
        super.setPageContext(context);
        setConverterId(DateTimeConverter.CONVERTER_ID);
    }

    protected Converter createConverter() throws JspException {
        DateTimeConverter converter = (DateTimeConverter) super
                .createConverter();
        FacesContext context = getFacesContext();
        setConverterDateStyle(context, converter);
        setConverterLocale(context, converter);
        setConverterPattern(context, converter);
        setConverterTimeStyle(context, converter);
        setConverterTimeZone(context, converter);
        setConverterType(context, converter);
        return converter;
    }

    protected void setConverterDateStyle(FacesContext context,
            DateTimeConverter converter) {
        String dateStyle = (String) ValueBindingUtil.getValue(context,
                dateStyle_);
        if (dateStyle == null) {
            dateStyle = dateStyle_;
        }
        converter.setDateStyle(dateStyle);
    }

    protected void setConverterLocale(FacesContext context,
            DateTimeConverter converter) {
        Locale locale = (Locale) ValueBindingUtil.getValue(context, locale_);
        if (locale == null) {
            locale = LocaleUtil.getLocale(locale_);
        }
        converter.setLocale(locale);
    }

    protected void setConverterPattern(FacesContext context,
            DateTimeConverter converter) {
        String pattern = (String) ValueBindingUtil.getValue(context, pattern_);
        if (pattern == null) {
            pattern = pattern_;
        }
        converter.setPattern(pattern);
    }

    protected void setConverterTimeStyle(FacesContext context,
            DateTimeConverter converter) {
        String timeStyle = (String) ValueBindingUtil.getValue(context,
                timeStyle_);
        if (timeStyle == null) {
            timeStyle = timeStyle_;
        }
        converter.setTimeStyle(timeStyle);
    }

    protected void setConverterTimeZone(FacesContext context,
            DateTimeConverter converter) {
        TimeZone timeZone = (TimeZone) ValueBindingUtil.getValue(context,
                timeZone_);
        if (timeZone == null) {
            if (timeZone_ != null) {
                timeZone = TimeZone.getTimeZone(timeZone_);
            } else {
                timeZone = TimeZone.getDefault();
            }
        }
        converter.setTimeZone(timeZone);
    }

    protected void setConverterType(FacesContext context,
            DateTimeConverter converter) {
        String type = (String) ValueBindingUtil.getValue(context, type_);
        if (type == null) {
            type = type_;
        }
        converter.setType(type);
    }

    public void setDateStyle(String dateStyle) {
        dateStyle_ = dateStyle;
    }

    public void setLocale(String locale) {
        locale_ = locale;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

    public void setTimeStyle(String timeStyle) {
        timeStyle_ = timeStyle;
    }

    public void setTimeZone(String timeZone) {
        timeZone_ = timeZone;
    }

    public void setType(String type) {
        type_ = type;
    }

    private static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
