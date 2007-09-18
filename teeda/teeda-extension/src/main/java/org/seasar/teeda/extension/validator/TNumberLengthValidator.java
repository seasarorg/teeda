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
package org.seasar.teeda.extension.validator;

import java.math.BigDecimal;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.internal.UIInputUtil;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.TargetCommandUtil;

/**
 * @author manhole
 */
public class TNumberLengthValidator implements Validator, StateHolder,
        ValidationTargetSelectable {

    /*
     * String型で"ab.cd"と入ってきた場合も、このバリデータでは桁数しか見ない。
     * 数値であって欲しい場合はプロパティの型をBigDecimalにしておく
     *
     */

    static final String INTEGRAL_MESSAGE_ID = "org.seasar.teeda.extension.validator.TNumberLengthValidator.INTEGRAL";

    static final String FRACTION_MESSAGE_ID = "org.seasar.teeda.extension.validator.TNumberLengthValidator.FRACTION";

    static final String BOTH_MESSAGE_ID = "org.seasar.teeda.extension.validator.TNumberLengthValidator.BOTH";

    private static final BigDecimal ZERO = new BigDecimal("0");

    protected int integralMin = 1;

    protected int integralMax = Integer.MAX_VALUE;

    protected int fractionMin = 0;

    protected int fractionMax = Integer.MAX_VALUE;

    protected boolean transientValue = false;

    protected String target;

    protected String[] targets;

    public void validate(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        if (UIInputUtil.isEmpty(value)) {
            return;
        }
        if (!isTargetCommandValidation(context, targets)) {
            return;
        }
        final Digits digits = getDigits(context, value);
        final boolean integralSuccess = validateIntegral(digits);
        final boolean fractionSuccess = validateFraction(digits);

        if (!integralSuccess && !fractionSuccess) {
            final Object[] args = { UIComponentUtil.getLabel(component),
                    new Integer(integralMin), new Integer(integralMax),
                    new Integer(fractionMin), new Integer(fractionMax) };
            final FacesMessage message = FacesMessageUtil.getMessage(context,
                    BOTH_MESSAGE_ID, args);
            throw new ValidatorException(message, BOTH_MESSAGE_ID, args);
        } else if (!integralSuccess) {
            final Object[] args = { UIComponentUtil.getLabel(component),
                    new Integer(integralMin), new Integer(integralMax) };
            final FacesMessage message = FacesMessageUtil.getMessage(context,
                    INTEGRAL_MESSAGE_ID, args);
            throw new ValidatorException(message, INTEGRAL_MESSAGE_ID, args);
        } else if (!fractionSuccess) {
            final Object[] args = { UIComponentUtil.getLabel(component),
                    new Integer(fractionMin), new Integer(fractionMax) };
            final FacesMessage message = FacesMessageUtil.getMessage(context,
                    FRACTION_MESSAGE_ID, args);
            throw new ValidatorException(message, FRACTION_MESSAGE_ID, args);
        }
    }

    private boolean validateFraction(Digits digits) {
        if (fractionMin <= fractionMax) {
            final int fraction = digits.getFraction();
            if (fraction < fractionMin || fractionMax < fraction) {
                return false;
            }
        }
        return true;
    }

    private boolean validateIntegral(Digits digits) {
        if (integralMin <= integralMax) {
            final int integral = digits.getIntegral();
            if (integral < integralMin || integralMax < integral) {
                return false;
            }
        }
        return true;
    }

    protected Digits getDigits(final FacesContext context, final Object value) {
        final Digits digits = new Digits();
        if (value instanceof Integer) {
            final Integer num = (Integer) value;
            final int abs = Math.abs(num.intValue());
            digits.setIntegral(String.valueOf(abs).length());
        } else if (value instanceof Long) {
            final Long num = (Long) value;
            final long abs = Math.abs(num.longValue());
            digits.setIntegral(String.valueOf(abs).length());
        } else if (value instanceof BigDecimal) {
            BigDecimal num = (BigDecimal) value;
            if (num.compareTo(ZERO) < 0) {
                num = num.negate();
            }
            final String s = num.toString();
            final Locale locale = context.getViewRoot().getLocale();
            final String decimalSeparator = NumberConversionUtil
                    .findDecimalSeparator(locale);
            final int pos = s.indexOf(decimalSeparator);
            if (-1 < pos) {
                digits.setIntegral(s.substring(0, pos).length());
                digits.setFraction(s.substring(pos + 1).length());
            } else {
                digits.setIntegral(s.length());
            }
        }
        return digits;
    }

    public int getFractionMax() {
        return fractionMax;
    }

    public void setFractionMax(int fractionMax) {
        this.fractionMax = fractionMax;
    }

    public int getFractionMin() {
        return fractionMin;
    }

    public void setFractionMin(int fractionMin) {
        this.fractionMin = fractionMin;
    }

    public int getIntegralMax() {
        return integralMax;
    }

    public void setIntegralMax(int integralMax) {
        this.integralMax = integralMax;
    }

    public int getIntegralMin() {
        return integralMin;
    }

    public void setIntegralMin(int integralMin) {
        this.integralMin = integralMin;
    }

    protected static class Digits {

        private int integral;

        private int fraction;

        public int getFraction() {
            return fraction;
        }

        public void setFraction(int fraction) {
            this.fraction = fraction;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        integralMin = ((Integer) values[0]).intValue();
        integralMax = ((Integer) values[1]).intValue();
        fractionMin = ((Integer) values[2]).intValue();
        fractionMax = ((Integer) values[3]).intValue();
        target = (String) values[4];
        setTarget(target);
    }

    public Object saveState(FacesContext context) {
        Object[] state = new Object[5];
        state[0] = new Integer(integralMin);
        state[1] = new Integer(integralMax);
        state[2] = new Integer(fractionMin);
        state[3] = new Integer(fractionMax);
        state[4] = target;
        return state;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
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

    public boolean isTargetCommandValidation(FacesContext context,
            String[] targets) {
        return TargetCommandUtil.isTargetCommand(context, targets);
    }

    public String[] getTargets() {
        return targets;
    }

}
