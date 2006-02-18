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

import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullPageContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ConvertDateTimeTagTest extends TeedaTestCase {

    private MockApplication orgApp_;

    public void setupSetPageContext() {
        orgApp_ = getApplication();
    }

    public void testSetPageContext() throws Exception {
        // # Arrange #
        String converterId = DateTimeConverter.CONVERTER_ID;
        String converterClassName = "javax.faces.convert.DateTimeConverter";
        MockApplication app = new MockApplicationImpl();
        app.addConverter(converterId, converterClassName);
        setApplication(app);
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setPageContext(new NullPageContext());
        
        // # Act #
        Converter c = tag.createConverter();
        
        // # Assert #
        assertNotNull(c);
        assertTrue(c instanceof DateTimeConverter);
    }

    public void tearDownSetPageContext() {
        setApplication(orgApp_);
    }

    public void testSetConverterDateStyle_constantValue() throws Exception {
        // # Arrange #
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setConverterDateStyle(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals(JsfConstants.DEFAULT_CONVERTDATETIME_DATE_STYLE, c.getDateStyle());
    }

    public void testSetConverterDateStyle_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "medium");
        getApplication().setValueBinding(vb);
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setDateStyle("#{hoge.medium}");
        tag.setConverterDateStyle(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals("medium", c.getDateStyle());
    }

    public void testSetConverterLocale_constantValue() throws Exception {
        // # Arrange #
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setLocale(Locale.FRENCH.toString());
        tag.setConverterLocale(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals(Locale.FRENCH, c.getLocale());
    }

    public void testSetConverterLocale_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Locale.GERMANY);
        getApplication().setValueBinding(vb);
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setLocale("#{locale.getmany}");
        tag.setConverterLocale(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals(Locale.GERMANY, c.getLocale());
    }

    public void testSetConverterTimeZone_constantValue() throws Exception {
        // # Arrange #
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setTimeZone("America/Los_Angeles");
        tag.setConverterTimeZone(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals(TimeZone.getTimeZone("America/Los_Angeles"), c.getTimeZone());
    }

    public void testSetConverterTimeZone_bindingValue() throws Exception {
        // # Arrange #
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), TimeZone.getTimeZone("Brazil/West"));
        getApplication().setValueBinding(vb);
        DateTimeConverter c = new DateTimeConverter();
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setTimeZone("#{timeZone.a}");
        tag.setConverterTimeZone(getFacesContext(), c);
        
        // # Act & Assert #
        assertEquals(TimeZone.getTimeZone("Brazil/West"), c.getTimeZone());
    }

    public void setupCreateConverter() {
        orgApp_ = getApplication();
    }
    
    public void testCreateConverter() throws Exception {
        // # Arrange #
        String converterId = DateTimeConverter.CONVERTER_ID;
        String converterClassName = "javax.faces.convert.DateTimeConverter";
        MockApplication app = new MockApplicationImpl();
        app.addConverter(converterId, converterClassName);
        setApplication(app);
        ConvertDateTimeTag tag = new ConvertDateTimeTag();
        tag.setPageContext(new NullPageContext());
        tag.setDateStyle("medium");
        tag.setLocale(Locale.JAPANESE.toString());
        tag.setPattern("pattern");
        tag.setTimeStyle("style");
        tag.setTimeZone("America/Los_Angeles");
        tag.setType("type");
        
        // # Act #
        Converter c = tag.createConverter();
        
        // # Assert #
        assertNotNull(c);
        assertTrue(c instanceof DateTimeConverter);
        DateTimeConverter dc = (DateTimeConverter)c;
        assertEquals("medium", dc.getDateStyle());
        assertEquals(Locale.JAPANESE, dc.getLocale());
        assertEquals("pattern", dc.getPattern());
        assertEquals("style", dc.getTimeStyle());
        assertEquals(TimeZone.getTimeZone("America/Los_Angeles"), dc.getTimeZone());
        assertEquals("type", dc.getType());
    }

    public void tearDownCreateConverter() {
        setApplication(orgApp_);
    }

}
