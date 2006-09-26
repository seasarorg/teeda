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
package org.seasar.teeda.extension.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.AbstractValidatorTest;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 * @author shot
 */
public class TNumberLengthValidatorTest extends AbstractValidatorTest {

    // NG:整数部が最大桁数より大きい
    public void testValidate_IntegralGreater() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(3);
        validator.setIntegralMin(1);
        final MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        try {
            validator.validate(getFacesContext(), component,
                    new Integer("1234"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.INTEGRAL_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:整数部が最小桁数より小さい
    public void testValidate_IntegralLesser() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(10);
        validator.setIntegralMin(5);
        final MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        try {
            validator.validate(getFacesContext(), component,
                    new Integer("1234"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.INTEGRAL_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:小数部が最大桁数より大きい
    public void testValidate_FractionGreater() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setFractionMax(4);
        validator.setFractionMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        try {
            validator.validate(getFacesContext(), component, new BigDecimal(
                    "123.54321"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.FRACTION_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:小数部が最小桁数より小さい
    public void testValidate_FractionLesser() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setFractionMax(4);
        validator.setFractionMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        try {
            validator.validate(getFacesContext(), component, new BigDecimal(
                    "123.54321"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.FRACTION_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:整数部は範囲内だが小数部が最小桁数より小さい
    public void testValidate_IntegralOkAndFractionLesser() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(7);
        validator.setIntegralMin(5);
        validator.setFractionMax(4);
        validator.setFractionMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        try {
            validator.validate(getFacesContext(), component, new BigDecimal(
                    "12345.1"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.FRACTION_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:小数部は範囲内だが整数部が最小桁数より小さい
    public void testValidate_IntegralLesserAndFractionOk() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(7);
        validator.setIntegralMin(5);
        validator.setFractionMax(4);
        validator.setFractionMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        try {
            validator.validate(getFacesContext(), component, new BigDecimal(
                    "1234.12"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(TNumberLengthValidator.INTEGRAL_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // NG:整数部も小数部も最小桁数より小さい
    public void testValidate_BothIntegralAndFractionLesser() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(7);
        validator.setIntegralMin(5);
        validator.setFractionMax(4);
        validator.setFractionMin(3);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        component.getAttributes().put("label", "fooLabel");
        try {
            validator.validate(getFacesContext(), component, new BigDecimal(
                    "123.4"));
            fail();
        } catch (final ValidatorException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            System.out.println(facesMessage.getSummary());
            System.out.println(facesMessage.getDetail());
            assertEquals(true, StringUtil.contains(facesMessage.getDetail(), "fooLabel"));
            assertEquals(TNumberLengthValidator.BOTH_MESSAGE_ID, e
                    .getMessageId());
            ExceptionAssert.assertMessageExist(e);
        }
    }

    // OK:整数部だけ入力されていて範囲内(最小値 < x < 最大値)
    public void testValidate_IntegralInRange() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(5);
        validator.setIntegralMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component, new BigDecimal("123"));
    }

    // OK:整数部だけ入力されていて範囲内(最小値 == x)
    public void testValidate_IntegralMin() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(5);
        validator.setIntegralMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component, new BigDecimal("12"));
    }

    // OK:整数部だけ入力されていて範囲内(x == 最大値)
    public void testValidate_IntegralMax() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(5);
        validator.setIntegralMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component,
                new BigDecimal("12345"));
    }

    // OK:小数部だけ入力されていて範囲内(最小値 < x < 最大値)
    public void testValidate_FractionInRange() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setFractionMax(5);
        validator.setFractionMin(3);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component, new BigDecimal(
                "0.1234"));
    }

    // OK:小数部だけ入力されていて範囲内(最小値 == x)
    public void testValidate_FractionMin() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setFractionMax(5);
        validator.setFractionMin(3);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component,
                new BigDecimal("0.123"));
    }

    // OK:小数部だけ入力されていて範囲内(x == 最大値)
    public void testValidate_FractionMax() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setFractionMax(5);
        validator.setFractionMin(3);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component, new BigDecimal(
                "0.12345"));
    }

    // OK:整数部も小数部も範囲内 testValidate_BothIntegralAndFractionLesser
    public void testValidate_BothIntegralAndFractionInRange() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(10);
        validator.setFractionMax(4);
        validator.setFractionMin(2);
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        validator.validate(getFacesContext(), component, new BigDecimal(
                "1234567.123"));
    }

    // OK:入力値がnull
    public void testValidate_NullValue() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        validator.setIntegralMax(3);
        validator.setIntegralMin(1);
        final MockUIComponent component = new MockUIComponent();
        component.setId("aaa");

        validator.validate(getFacesContext(), component, null);
    }

    public void testGetDigits_Integer() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        final FacesContext context = getFacesContext();
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Integer(123));
            assertEquals(3, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Integer(1234567890));
            assertEquals(10, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Integer(0));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Integer(1));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Integer(-1124));
            assertEquals(4, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
    }

    public void testGetDigits_Long() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        final FacesContext context = getFacesContext();
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Long(123));
            assertEquals(3, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Long(1234567890123456789L));
            assertEquals(19, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Long(0));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Long(1));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new Long(-1234));
            assertEquals(4, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
    }

    public void testGetDigits_BigDecimal_Integral() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        final FacesContext context = getFacesContext();
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("123"));
            assertEquals(3, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("1234567890123456789"));
            assertEquals(19, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("123456789012345678901234567890"));
            assertEquals(30, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("0"));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("1"));
            assertEquals(1, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("-123456"));
            assertEquals(6, digits.getIntegral());
            assertEquals(0, digits.getFraction());
        }
    }

    public void testGetDigits_BigDecimal_Fraction() throws Exception {
        final TNumberLengthValidator validator = new TNumberLengthValidator();
        final FacesContext context = getFacesContext();
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("0.1"));
            assertEquals(1, digits.getIntegral());
            assertEquals(1, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("0.1234567890123456789"));
            assertEquals(1, digits.getIntegral());
            assertEquals(19, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context,
                    new BigDecimal("10.123456789012345678901234567890"));
            assertEquals(2, digits.getIntegral());
            assertEquals(30, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("0.0"));
            assertEquals(1, digits.getIntegral());
            assertEquals(1, digits.getFraction());
        }
        {
            final TNumberLengthValidator.Digits digits = validator.getDigits(
                    context, new BigDecimal("-1.123456"));
            assertEquals(1, digits.getIntegral());
            assertEquals(6, digits.getFraction());
        }
    }

    protected Validator createValidator() {
        return new TNumberLengthValidator();
    }

}
