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
import javax.faces.internal.AssertionUtil;
import javax.faces.internal.ConvertUtils;

/**
 * @author shot
 */
public class NumberConverter implements Converter, StateHolder {

    public static final String CONVERTER_ID = "javax.faces.Number";

    private Locale locale_ = null;

    private String pattern_ = null;

    private String type_ = null;

    private String currencyCode_ = null;

    private String currencySymbol_ = null;

    private boolean isIntegerOnly_ = false;

    private boolean isTransient_ = false;

    private boolean isGroupingUsed_ = false;

    private int maxFractionDigits_ = 0;

    private int maxIntegerDigits_ = 0;

    private int minFractionDigits_ = 0;

    private int minIntegerDigits_ = 0;

    private static final String TYPE_CURRENCY = "currency";

    private static final String TYPE_NUMBER = "number";

    private static final String TYPE_PERCENT = "currency";

    private boolean isSetMaxFractionDigits_ = false;

    private boolean isSetMaxIntegerDigits_ = false;

    private boolean isSetMinFractionDigits_ = false;

    private boolean isSetMinIntegerDigits_ = false;

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
        NumberFormat parser = getNumberFormat(locale);
        parser.setParseIntegerOnly(isIntegerOnly());
        try {
            return parser.parse(value);
        } catch (ParseException e) {
            Object[] args = ConvertUtils.createExceptionMessageArgs(component,
                    value);
            throw ConvertUtils.wrappedByConverterException(this, context, args,
                    e);
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
            if (TYPE_CURRENCY.equals(type_)) {
                configureCurrency(formatter);
            }
        }
        try {
            return formatter.format(value);
        } catch (Exception e) {
            throw ConvertUtils.wrappedByConverterException(e);
        }
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[15];
        values[0] = getCurrencyCode();
        values[1] = getCurrencySymbol();
        values[2] = isGroupingUsed() ? Boolean.TRUE : Boolean.FALSE;
        values[3] = isIntegerOnly() ? Boolean.TRUE : Boolean.FALSE;
        values[4] = new Integer(getMaxFractionDigits());
        values[5] = isSetMaxFractionDigits_ ? Boolean.TRUE : Boolean.FALSE;
        values[6] = new Integer(getMaxIntegerDigits());
        values[7] = isSetMaxIntegerDigits_ ? Boolean.TRUE : Boolean.FALSE;
        values[8] = new Integer(getMinFractionDigits());
        values[9] = isSetMinFractionDigits_ ? Boolean.TRUE : Boolean.FALSE;
        values[10] = new Integer(getMinIntegerDigits());
        values[11] = isSetMinIntegerDigits_ ? Boolean.TRUE : Boolean.FALSE;
        values[12] = getLocale();
        values[13] = getPattern();
        values[14] = getType();
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        currencyCode_ = (String) values[0];
        currencySymbol_ = (String) values[1];
        isGroupingUsed_ = ((Boolean) values[2]).booleanValue();
        isIntegerOnly_ = ((Boolean) values[3]).booleanValue();
        maxFractionDigits_ = ((Integer) values[4]).intValue();
        isSetMaxFractionDigits_ = ((Boolean) values[5]).booleanValue();
        maxIntegerDigits_ = ((Integer) values[6]).intValue();
        isSetMaxIntegerDigits_ = ((Boolean) values[7]).booleanValue();
        minFractionDigits_ = ((Integer) values[8]).intValue();
        isSetMinFractionDigits_ = ((Boolean) values[9]).booleanValue();
        minIntegerDigits_ = ((Integer) values[10]).intValue();
        isSetMinIntegerDigits_ = ((Boolean) values[11]).booleanValue();
        locale_ = (Locale) values[12];
        pattern_ = (String) values[13];
        type_ = (String) values[14];
    }

    public boolean isTransient() {
        return isTransient_;
    }

    public void setTransient(boolean isTransient) {
        isTransient_ = isTransient;
    }

    public Locale getLocale() {
        return locale_;
    }

    public void setLocale(Locale locale) {
        locale_ = locale;
    }

    public String getPattern() {
        return pattern_;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

    public String getType() {
        return type_;
    }

    public void setType(String type) {
        type_ = type;
    }

    public boolean isIntegerOnly() {
        return isIntegerOnly_;
    }

    public void setIntegerOnly(boolean isIntegerOnly) {
        isIntegerOnly_ = isIntegerOnly;
    }

    public boolean isGroupingUsed() {
        return isGroupingUsed_;
    }

    public void setGroupingUsed(boolean isGroupingUsed) {
        isGroupingUsed_ = isGroupingUsed;
    }

    public String getCurrencyCode() {
        return currencyCode_;
    }

    public void setCurrencyCode(String currencyCode) {
        currencyCode_ = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol_;
    }

    public void setCurrencySymbol(String currencySymbol) {
        currencySymbol_ = currencySymbol;
    }

    public int getMaxFractionDigits() {
        return maxFractionDigits_;
    }

    public void setMaxFractionDigits(int maxFractionDigits) {
        maxFractionDigits_ = maxFractionDigits;
        isSetMaxFractionDigits_ = true;
    }

    public int getMaxIntegerDigits() {
        return maxIntegerDigits_;
    }

    public void setMaxIntegerDigits(int maxIntegerDigits) {
        maxIntegerDigits_ = maxIntegerDigits;
        isSetMaxIntegerDigits_ = true;
    }

    public int getMinFractionDigits() {
        return minFractionDigits_;
    }

    public void setMinFractionDigits(int minFractionDigits) {
        minFractionDigits_ = minFractionDigits;
        isSetMinFractionDigits_ = true;
    }

    public int getMinIntegerDigits() {
        return minIntegerDigits_;
    }

    public void setMinIntegerDigits(int minIntegerDigits) {
        minIntegerDigits_ = minIntegerDigits;
        isSetMinIntegerDigits_ = true;
    }

    private Locale getLocale(FacesContext context) {
        Locale locale = getLocale();
        if (locale != null) {
            return locale;
        }
        return context.getViewRoot().getLocale();
    }

    private NumberFormat getNumberFormat(Locale locale) {
        if (pattern_ != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
            return new DecimalFormat(pattern_, symbols);
        }
        if (type_ != null) {
            return getNumberFormatForType();
        }
        throw new ConverterException("NumberFormat not found");
    }

    private NumberFormat getNumberFormatForType() {
        if (type_.equals(TYPE_CURRENCY)) {
            return NumberFormat.getCurrencyInstance();
        } else if (type_.equals(TYPE_NUMBER)) {
            return NumberFormat.getNumberInstance();
        } else if (type_.equals(TYPE_PERCENT)) {
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
        if (isSetMaxFractionDigits_) {
            formatter.setMaximumFractionDigits(getMaxFractionDigits());
        }
    }

    protected void setMaximumIntegerDigitsToFormatter(NumberFormat formatter) {
        if (isSetMaxIntegerDigits_) {
            formatter.setMaximumIntegerDigits(getMaxIntegerDigits());
        }
    }

    protected void setMinimumFractionDigitsToFormatter(NumberFormat formatter) {
        if (isSetMinFractionDigits_) {
            formatter.setMinimumFractionDigits(getMinFractionDigits());
        }
    }

    protected void setMinimumIntegerDigitsToFormatter(NumberFormat formatter) {
        if (isSetMinIntegerDigits_) {
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
}
