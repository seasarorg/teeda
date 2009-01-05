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
package javax.faces.convert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ConvertUtil;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.NumberConversionUtil;

/**
 * @author shot
 */
public class NumberConverter implements Converter, StateHolder {

    public static final String CONVERTER_ID = "javax.faces.Number";

    public static final String CONVERSION_OBJECT_ID = NumberConverter.class
            .getName()
            + ".CONVERSION";

    public static final String CONVERSION_STRING_ID = NumberConverter.class
            .getName()
            + ".CONVERSION_STRING";

    private Locale locale = null;

    private String pattern = null;

    private String type = null;

    private String currencyCode = null;

    private String currencySymbol = null;

    private boolean isIntegerOnly = false;

    private boolean isTransient = false;

    private boolean isGroupingUsed = false;

    private int maxFractionDigits = 0;

    private int maxIntegerDigits = 0;

    private int minFractionDigits = 0;

    private int minIntegerDigits = 0;

    private static final String TYPE_CURRENCY = "currency";

    private static final String TYPE_NUMBER = "number";

    private static final String TYPE_PERCENT = "percent";

    private boolean isSetMaxFractionDigits = false;

    private boolean isSetMaxIntegerDigits = false;

    private boolean isSetMinFractionDigits = false;

    private boolean isSetMinIntegerDigits = false;

    public NumberConverter() {
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.length() < 1) {
            return null;
        }
        Locale locale = getLocale(context);
        value = NumberConversionUtil.removeDelimeter(value, locale);

        NumberFormat formatter = getNumberFormat(locale);
        formatter.setParseIntegerOnly(isIntegerOnly());
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getObjectMessageId(), args), e);
        }
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        Locale locale = getLocale(context);
        String pattern = getPattern();
        NumberFormat formatter = getNumberFormat(locale);
        if (pattern == null) {
            configureFormatter(formatter);
            if (TYPE_CURRENCY.equals(type)) {
                configureCurrency(formatter);
            }
        }
        try {
            return formatter.format(value);
        } catch (Exception e) {
            Object[] args = ConvertUtil.createExceptionMessageArgs(component,
                    value);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    getStringMessageId(), args), e);
        }
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[15];
        values[0] = getCurrencyCode();
        values[1] = getCurrencySymbol();
        values[2] = isGroupingUsed() ? Boolean.TRUE : Boolean.FALSE;
        values[3] = isIntegerOnly() ? Boolean.TRUE : Boolean.FALSE;
        values[4] = new Integer(getMaxFractionDigits());
        values[5] = isSetMaxFractionDigits ? Boolean.TRUE : Boolean.FALSE;
        values[6] = new Integer(getMaxIntegerDigits());
        values[7] = isSetMaxIntegerDigits ? Boolean.TRUE : Boolean.FALSE;
        values[8] = new Integer(getMinFractionDigits());
        values[9] = isSetMinFractionDigits ? Boolean.TRUE : Boolean.FALSE;
        values[10] = new Integer(getMinIntegerDigits());
        values[11] = isSetMinIntegerDigits ? Boolean.TRUE : Boolean.FALSE;
        values[12] = getLocale();
        values[13] = getPattern();
        values[14] = getType();
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        currencyCode = (String) values[0];
        currencySymbol = (String) values[1];
        isGroupingUsed = ((Boolean) values[2]).booleanValue();
        isIntegerOnly = ((Boolean) values[3]).booleanValue();
        maxFractionDigits = ((Integer) values[4]).intValue();
        isSetMaxFractionDigits = ((Boolean) values[5]).booleanValue();
        maxIntegerDigits = ((Integer) values[6]).intValue();
        isSetMaxIntegerDigits = ((Boolean) values[7]).booleanValue();
        minFractionDigits = ((Integer) values[8]).intValue();
        isSetMinFractionDigits = ((Boolean) values[9]).booleanValue();
        minIntegerDigits = ((Integer) values[10]).intValue();
        isSetMinIntegerDigits = ((Boolean) values[11]).booleanValue();
        locale = (Locale) values[12];
        pattern = (String) values[13];
        type = (String) values[14];
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIntegerOnly() {
        return isIntegerOnly;
    }

    public void setIntegerOnly(boolean isIntegerOnly) {
        this.isIntegerOnly = isIntegerOnly;
    }

    public boolean isGroupingUsed() {
        return isGroupingUsed;
    }

    public void setGroupingUsed(boolean isGroupingUsed) {
        this.isGroupingUsed = isGroupingUsed;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public int getMaxFractionDigits() {
        return maxFractionDigits;
    }

    public void setMaxFractionDigits(int maxFractionDigits) {
        this.maxFractionDigits = maxFractionDigits;
        this.isSetMaxFractionDigits = true;
    }

    public int getMaxIntegerDigits() {
        return maxIntegerDigits;
    }

    public void setMaxIntegerDigits(int maxIntegerDigits) {
        this.maxIntegerDigits = maxIntegerDigits;
        this.isSetMaxIntegerDigits = true;
    }

    public int getMinFractionDigits() {
        return minFractionDigits;
    }

    public void setMinFractionDigits(int minFractionDigits) {
        this.minFractionDigits = minFractionDigits;
        this.isSetMinFractionDigits = true;
    }

    public int getMinIntegerDigits() {
        return minIntegerDigits;
    }

    public void setMinIntegerDigits(int minIntegerDigits) {
        this.minIntegerDigits = minIntegerDigits;
        this.isSetMinIntegerDigits = true;
    }

    private Locale getLocale(FacesContext context) {
        Locale locale = getLocale();
        if (locale != null) {
            return locale;
        }
        return context.getViewRoot().getLocale();
    }

    private NumberFormat getNumberFormat(Locale locale) {
        if (pattern != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
            return new DecimalFormat(pattern, symbols);
        }
        if (type != null) {
            return getNumberFormatForType();
        }
        throw new ConverterException("NumberFormat not found");
    }

    private NumberFormat getNumberFormatForType() {
        if (type.equals(TYPE_CURRENCY)) {
            return NumberFormat.getCurrencyInstance();
        } else if (type.equals(TYPE_NUMBER)) {
            return NumberFormat.getNumberInstance();
        } else if (type.equals(TYPE_PERCENT)) {
            return NumberFormat.getPercentInstance();
        } else {
            throw new ConverterException(new IllegalArgumentException());
        }
    }

    protected void configureFormatter(NumberFormat formatter) {
        formatter.setGroupingUsed(isGroupingUsed());
        setMaximumFractionDigitsToFormatter(formatter);
        setMaximumIntegerDigitsToFormatter(formatter);
        setMinimumFractionDigitsToFormatter(formatter);
        setMinimumIntegerDigitsToFormatter(formatter);
    }

    protected void setMaximumFractionDigitsToFormatter(NumberFormat formatter) {
        if (isSetMaxFractionDigits) {
            formatter.setMaximumFractionDigits(getMaxFractionDigits());
        }
    }

    protected void setMaximumIntegerDigitsToFormatter(NumberFormat formatter) {
        if (isSetMaxIntegerDigits) {
            formatter.setMaximumIntegerDigits(getMaxIntegerDigits());
        }
    }

    protected void setMinimumFractionDigitsToFormatter(NumberFormat formatter) {
        if (isSetMinFractionDigits) {
            formatter.setMinimumFractionDigits(getMinFractionDigits());
        }
    }

    protected void setMinimumIntegerDigitsToFormatter(NumberFormat formatter) {
        if (isSetMinIntegerDigits) {
            formatter.setMinimumIntegerDigits(getMinIntegerDigits());
        }
    }

    protected void configureCurrency(NumberFormat formatter) {
        boolean isCurrencyCodeUse = false;
        String currencyCode = getCurrencyCode();
        String currencySymbol = getCurrencySymbol();
        if ((currencyCode == null) && (currencySymbol == null)) {
            return;
        }
        if (isSetBothCurrencyProperties() && isJava14()) {
            isCurrencyCodeUse = (currencyCode != null);
        } else {
            isCurrencyCodeUse = (currencySymbol == null);
        }
        if (isCurrencyCodeUse) {
            formatter.setCurrency(Currency.getInstance(currencyCode));
        } else {
            DecimalFormat df = (DecimalFormat) formatter;
            DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
            symbols.setCurrencySymbol(currencySymbol);
            df.setDecimalFormatSymbols(symbols);
        }
    }

    protected boolean isSetBothCurrencyProperties() {
        return ((getCurrencyCode() != null) && (getCurrencySymbol() != null));
    }

    protected static boolean isJava14() {
        try {
            Class clazz = Class.forName("java.util.Currency");
            return (clazz != null);
        } catch (Exception ignore) {
            return false;
        }
    }

    protected String getObjectMessageId() {
        return CONVERSION_OBJECT_ID;
    }

    protected String getStringMessageId() {
        return CONVERSION_STRING_ID;
    }

}
