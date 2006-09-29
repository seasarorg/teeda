package org.seasar.teeda.extension.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.seasar.framework.util.NumberConversionUtil;

public class BigDecimalFormatUtil {

    protected BigDecimalFormatUtil() {
    }

    public static String format(BigDecimal value, String pattern) {
        return format(value, pattern, Locale.getDefault());
    }

    public static String format(final BigDecimal value, final String pattern,
            final Locale locale) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        String valueText = value.toString();
        String decimalSeparator = NumberConversionUtil
                .findDecimalSeparator(locale);
        int separatorIndex = valueText.indexOf(decimalSeparator);
        if (isDecimalFormat(decimalFormat)) {
            return decimalFormat.format(value);
        } else if (separatorIndex == -1) {
            return decimalFormat.format(value.toBigInteger());
        } else {
            int decimalCount = 0;
            if (pattern.indexOf(decimalSeparator) != -1) {
                decimalCount = pattern.length()
                        - pattern.indexOf(decimalSeparator) - 1;
            }
            if (decimalCount < (valueText.length()
                    - valueText.indexOf(decimalSeparator) - 1)) {
                return decimalFormat.format(value);
            }
            BigDecimal intNum = new BigDecimal(valueText.substring(0,
                    separatorIndex));
            if (intNum.compareTo(new BigDecimal("0")) == 0) {
                return decimalFormat.format(value);
            }
            String intValue = decimalFormat.format(intNum);
            String str = valueText.substring(valueText
                    .lastIndexOf(decimalSeparator));
            BigDecimal bd = new BigDecimal(str);
            String decimalValue = decimalFormat.format(bd);
            String text = null;
            if (intValue.indexOf(decimalSeparator) == -1) {
                text = intValue;
            } else {
                text = intValue
                        .substring(0, intValue.indexOf(decimalSeparator));
            }
            if (decimalValue.indexOf(decimalSeparator) != -1) {
                text = text
                        + decimalValue.substring(decimalValue
                                .indexOf(decimalSeparator));
            }
            return text;
        }
    }

    private static boolean isDecimalFormat(DecimalFormat decimalFormat) {
        String pattern = decimalFormat.toPattern();
        StringBuffer buf = new StringBuffer(pattern);
        DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        char[] charList = new char[4];
        charList[0] = dfs.getZeroDigit();
        charList[1] = dfs.getDigit();
        charList[2] = dfs.getDecimalSeparator();
        charList[3] = dfs.getGroupingSeparator();
        for (int i = 0; i < charList.length; i++) {
            int charIndex;
            while ((charIndex = buf.toString().indexOf(charList[i])) != -1) {
                buf.deleteCharAt(charIndex);
            }
        }
        return (buf.length() == 0) ? false : true;
    }

}