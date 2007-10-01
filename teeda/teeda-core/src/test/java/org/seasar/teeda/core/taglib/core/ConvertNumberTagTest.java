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

import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullPageContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class ConvertNumberTagTest extends TeedaTestCase {

    public void testSetPageContext() throws Exception {
        // # Arrange #
        String converterId = NumberConverter.CONVERTER_ID;
        String converterClassName = "javax.faces.convert.NumberConverter";
        MockApplication app = new MockApplicationImpl();
        app.addConverter(converterId, converterClassName);
        setApplication(app);
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setPageContext(new NullPageContext());

        // # Act #
        Converter c = tag.createConverter();

        // # Assert #
        assertNotNull(c);
        assertTrue(c instanceof NumberConverter);
    }

    public void testSetConverterCurrencyCode_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setCurrencyCode("JPY");
        tag.setConverterCurrencyCode(getFacesContext(), c);

        // # Act & Assert #
        assertEquals("JPY", c.getCurrencyCode());
    }

    public void testSetConverterCurrencyCode_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "JPY");
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setCurrencyCode("#{hoge.japan}");
        tag.setConverterCurrencyCode(getFacesContext(), c);

        // # Act & Assert #
        assertEquals("JPY", c.getCurrencyCode());
    }

    public void testSetConverterCurrencySymbol_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "$");
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setCurrencySymbol("#{hoge.doller}");
        tag.setConverterCurrencySymbol(getFacesContext(), c);

        // # Act & Assert #
        assertEquals("$", c.getCurrencySymbol());
    }

    public void testSetConverterGroupingUsed_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setConverterGroupingUsed(getFacesContext(), c);

        // # Act & Assert #
        assertTrue(new Boolean(JsfConstants.DEFAULT_CONVERTNUMBER_GROUPING_USED)
                .booleanValue() == c.isGroupingUsed());
    }

    public void testSetConverterGroupingUsed_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Boolean(false));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setGroupingUsed("#{hoge.false}");
        tag.setConverterGroupingUsed(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(false, c.isGroupingUsed());
    }

    public void testSetConverterIntegerOnly_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setConverterGroupingUsed(getFacesContext(), c);

        // # Act & Assert #
        assertTrue(new Boolean(JsfConstants.DEFAULT_CONVERTNUMBER_INTEGER_ONLY)
                .booleanValue() == c.isIntegerOnly());
    }

    public void testSetConverterIntegerOnly_bindingtValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Boolean(true));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setIntegerOnly("#{hoge.true}");
        tag.setConverterIntegerOnly(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(true, c.isIntegerOnly());
    }

    public void testSetConverterLocale_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setLocale(Locale.CANADA.toString());
        tag.setConverterLocale(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(Locale.CANADA, c.getLocale());
    }

    public void testSetConverterLocale_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Locale.ITALIAN);
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setLocale("#{locale.getmany}");
        tag.setConverterLocale(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(Locale.ITALIAN, c.getLocale());
    }

    public void testSetConverterMaxFractionDigits_constantValue()
            throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMaxFractionDigits("3");
        tag.setConverterMaxFractionDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(3, c.getMaxFractionDigits());
    }

    public void testSetConverterMaxFractionDigits_bindingValue()
            throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(4));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMaxFractionDigits("#{hoge.maxfactiondigit}");
        tag.setConverterMaxFractionDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(4, c.getMaxFractionDigits());
    }

    public void testSetConverterMaxIntegerDigits_constantValue()
            throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMaxIntegerDigits("5");
        tag.setConverterMaxIntegerDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(5, c.getMaxIntegerDigits());
    }

    public void testSetConverterMaxIntegerDigits_bindingValue()
            throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(6));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMaxIntegerDigits("#{hoge.maxintegerdigits}");
        tag.setConverterMaxIntegerDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(6, c.getMaxIntegerDigits());
    }

    public void testConverterMinFractionDigits_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMinFractionDigits("5");
        tag.setConverterMinFractionDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(5, c.getMinFractionDigits());
    }

    public void testSetConverterMinFractionDigits_bindingValue()
            throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(6));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMinFractionDigits("#{hoge.minfractiondigits}");
        tag.setConverterMinFractionDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(6, c.getMinFractionDigits());
    }

    public void testSetConverterMinIntegerDigits_constantValue()
            throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMinIntegerDigits("1");
        tag.setConverterMinIntegerDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(1, c.getMinIntegerDigits());
    }

    public void testSetConverterMinIntegerDigits_bindingValue()
            throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(2));
        getApplication().setValueBinding(vb);
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setMinIntegerDigits("#{hoge.minintegerdigits}");
        tag.setConverterMinIntegerDigits(getFacesContext(), c);

        // # Act & Assert #
        assertEquals(2, c.getMinIntegerDigits());
    }

    public void testSetConverterType_constantValue() throws Exception {
        // # Arrange #
        NumberConverter c = new NumberConverter();
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setConverterGroupingUsed(getFacesContext(), c);

        // # Act & Assert #
        assertEquals("number", JsfConstants.DEFAULT_CONVERTNUMBER_TYPE);
    }

    public void testCreateConverter() throws Exception {
        // # Arrange #
        String converterId = NumberConverter.CONVERTER_ID;
        String converterClassName = "javax.faces.convert.NumberConverter";
        MockApplication app = new MockApplicationImpl();
        app.addConverter(converterId, converterClassName);
        setApplication(app);
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setPageContext(new NullPageContext());
        tag.setCurrencyCode("JPY");
        tag.setCurrencySymbol("$");
        tag.setGroupingUsed("false");
        tag.setIntegerOnly("true");
        tag.setMaxFractionDigits("2");
        tag.setMaxIntegerDigits("4");
        tag.setMinFractionDigits("1");
        tag.setMinIntegerDigits("5");
        tag.setPattern("###,###.###");
        tag.setType("percent");

        // # Act
        Converter c = tag.createConverter();

        // # Assert #
        assertNotNull(c);
        assertTrue(c instanceof NumberConverter);
        NumberConverter nc = (NumberConverter) c;
        assertEquals("JPY", nc.getCurrencyCode());
        assertEquals("$", nc.getCurrencySymbol());
        assertEquals(false, nc.isGroupingUsed());
        assertEquals(true, nc.isIntegerOnly());
        assertEquals(2, nc.getMaxFractionDigits());
        assertEquals(4, nc.getMaxIntegerDigits());
        assertEquals(1, nc.getMinFractionDigits());
        assertEquals(5, nc.getMinIntegerDigits());
        assertEquals("###,###.###", nc.getPattern());
        assertEquals("percent", nc.getType());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        ConvertNumberTag tag = new ConvertNumberTag();
        tag.setCurrencyCode("JPY");
        tag.setCurrencySymbol("$");
        tag.setGroupingUsed("false");
        tag.setIntegerOnly("true");
        tag.setMaxFractionDigits("2");
        tag.setMaxIntegerDigits("4");
        tag.setMinFractionDigits("1");
        tag.setMinIntegerDigits("5");
        tag.setPattern("###,###.###");
        tag.setType("percent");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getCurrencyCode());
        assertEquals(null, tag.getCurrencySymbol());
        assertEquals(JsfConstants.DEFAULT_CONVERTNUMBER_GROUPING_USED, tag
                .getGroupingUsed());
        assertEquals(JsfConstants.DEFAULT_CONVERTNUMBER_INTEGER_ONLY, tag
                .getIntegerOnly());
        assertEquals(null, tag.getLocale());
        assertEquals(null, tag.getMaxFractionDigits());
        assertEquals(null, tag.getMaxIntegerDigits());
        assertEquals(null, tag.getMinFractionDigits());
        assertEquals(null, tag.getMinIntegerDigits());
        assertEquals(null, tag.getPattern());
        assertEquals(JsfConstants.DEFAULT_CONVERTNUMBER_TYPE, tag.getType());
    }

}
