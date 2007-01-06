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
package org.seasar.teeda.core.taglib.core;

import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
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
public class ConvertDateTimeTag extends ConverterTag {

    private static final long serialVersionUID = 1L;

    private String dateStyle = JsfConstants.DEFAULT_CONVERTDATETIME_DATE_STYLE;

    private String locale;

    private String pattern;

    private String timeStyle = JsfConstants.DEFAULT_CONVERTDATETIME_TIME_STYLE;

    private String timeZone;

    private String type = JsfConstants.DEFAULT_CONVERTDATETIME_TYPE;

    public ConvertDateTimeTag() {
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setTimeStyle(String timeStyle) {
        this.timeStyle = timeStyle;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateStyle() {
        return dateStyle;
    }

    public String getLocale() {
        return locale;
    }

    public String getPattern() {
        return pattern;
    }

    public String getTimeStyle() {
        return timeStyle;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getType() {
        return type;
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
                this.dateStyle);
        if (dateStyle == null) {
            dateStyle = this.dateStyle;
        }
        converter.setDateStyle(dateStyle);
    }

    protected void setConverterLocale(FacesContext context,
            DateTimeConverter converter) {
        Locale locale = (Locale) ValueBindingUtil
                .getValue(context, getLocale());
        if (locale == null) {
            locale = LocaleUtil.getLocale(getLocale());
        }
        converter.setLocale(locale);
    }

    protected void setConverterPattern(FacesContext context,
            DateTimeConverter converter) {
        String pattern = (String) ValueBindingUtil.getValue(context,
                getPattern());
        if (pattern == null) {
            pattern = getPattern();
        }
        converter.setPattern(pattern);
    }

    protected void setConverterTimeStyle(FacesContext context,
            DateTimeConverter converter) {
        String timeStyle = (String) ValueBindingUtil.getValue(context,
                getTimeStyle());
        if (timeStyle == null) {
            timeStyle = getTimeStyle();
        }
        converter.setTimeStyle(timeStyle);
    }

    protected void setConverterTimeZone(FacesContext context,
            DateTimeConverter converter) {
        TimeZone timeZone = (TimeZone) ValueBindingUtil.getValue(context,
                getTimeZone());
        if (timeZone == null) {
            if (getTimeZone() != null) {
                timeZone = TimeZone.getTimeZone(getTimeZone());
            } else {
                timeZone = TimeZone.getDefault();
            }
        }
        converter.setTimeZone(timeZone);
    }

    protected void setConverterType(FacesContext context,
            DateTimeConverter converter) {
        String type = (String) ValueBindingUtil.getValue(context, getType());
        if (type == null) {
            type = getType();
        }
        converter.setType(type);
    }

    public void release() {
        super.release();
        dateStyle = JsfConstants.DEFAULT_CONVERTDATETIME_DATE_STYLE;
        locale = null;
        pattern = null;
        timeStyle = JsfConstants.DEFAULT_CONVERTDATETIME_TIME_STYLE;
        timeZone = null;
        type = JsfConstants.DEFAULT_CONVERTDATETIME_TYPE;
    }

    private static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
