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
package org.seasar.teeda.core.application;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.el.impl.ValueBindingContextImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.exception.IllegalClassTypeException;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValidatorWithProperties;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.NullValidator;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ApplicationImplTest extends TeedaTestCase {

    private ApplicationImpl app_;

    public void testAddComponent() {
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock", mock.getClass().getName());
        UIComponent c = app_.createComponent("mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
        try {
            app_.addComponent("mock2", "java.lang.String");
            fail();
        } catch (IllegalClassTypeException e) {
            assertEquals(UIComponent.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }
    }

    public void testCreateComponent1() {
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock", mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, mock);
        UIComponent c = app_.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testCreateComponent2() {
        app_ = new ApplicationImpl();
        MockUIComponent mock = new MockUIComponent();
        app_.addComponent("mock", mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, "mock");
        UIComponent c = app_.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testAddConverter1() {
        app_ = new ApplicationImpl();
        app_.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app_.createConverter("mock.converter");
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAddConverter2() {
        app_ = new ApplicationImpl();
        app_.addConverter(Hoge.class,
                "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app_.createConverter(Hoge.class);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAddConverter_converterInstanciateEverytime() {
        // # Arrange
        app_ = new ApplicationImpl();
        app_.addConverter("aaa", HogeConverter2.class.getName());
        app_.addConverter(Hoge.class, HogeConverter2.class.getName());

        // # Act
        Converter c = app_.createConverter("aaa");
        
        // # Assert
        assertNotNull(c);
        assertTrue(c instanceof HogeConverter2);
        HogeConverter2 c2 = (HogeConverter2)c;
        assertTrue(c2.getNum() == 1);
        
        // # Act
        c = app_.createConverter(Hoge.class);

        // # Assert
        assertNotNull(c);
        assertTrue(c instanceof HogeConverter2);
        HogeConverter2 c3 = (HogeConverter2)c;
        assertTrue(c3.getNum() == 1);
    }

    public void testCreateConverterForInterface() {
        app_ = new ApplicationImpl();
        app_
                .addConverter(Foo.class,
                        "org.seasar.teeda.core.mock.MockConverter");
        Foo foo = new FooImpl();
        Converter c = app_.createConverter(foo.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testCreateConverterForSuperClass() {
        app_ = new ApplicationImpl();
        app_.addConverter(Hoge.class,
                "org.seasar.teeda.core.mock.MockConverter");
        Hoge hoge = new Hoge2();
        Converter c = app_.createConverter(hoge.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testCreateCoverterForPrimitive() {
        app_ = new ApplicationImpl();
        app_.addConverter(Integer.class,
                "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app_.createConverter(Integer.TYPE);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAddValidator1() {
        app_ = new ApplicationImpl();
        app_.addValidator("teeda.null",
                "org.seasar.teeda.core.mock.NullValidator");
        Validator v = app_.createValidator("teeda.null");
        assertNotNull(v);
        assertTrue(v instanceof NullValidator);
        try {
            app_.addValidator("hoge", "java.lang.String");
        } catch (IllegalClassTypeException e) {
            assertEquals(Validator.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }

    }

    public void testCreateConverter_withProperties1() throws Exception {
        app_ = new ApplicationImpl();
        ConverterConfiguration config = new ConverterConfiguration("pattern",
                "String", "yyyy/MM/dd HH:mm:ss");
        app_.addConverterConfiguration(this.getClass().getName()
                + "$HogeConverter", config);
        app_.addConverter("id", this.getClass().getName() + "$HogeConverter");

        Converter c = app_.createConverter("id");

        assertNotNull(c);
        assertTrue(c instanceof HogeConverter);
        assertEquals("yyyy/MM/dd HH:mm:ss", ((HogeConverter) c).getPattern());
    }

    public void testCreateConverter_withProperties2() throws Exception {
        app_ = new ApplicationImpl();
        ConverterConfiguration config = new ConverterConfiguration("num",
                "int", "3");
        app_.addConverterConfiguration(this.getClass().getName()
                + "$HogeConverter", config);
        app_.addConverter("id", this.getClass().getName() + "$HogeConverter");

        Converter c = app_.createConverter("id");

        assertNotNull(c);
        assertTrue(c instanceof HogeConverter);
        assertEquals(3, ((HogeConverter) c).getNum());
    }

    public void testCreateValueBinding() throws Exception {
        ValueBindingContext ctx = new ValueBindingContextImpl();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ctx.setELParser(parser);
        ctx
                .setValueBindingName("org.seasar.teeda.core.el.impl.ValueBindingImpl");
        app_ = new ApplicationImpl();
        app_.setValueBindingContext(ctx);
        MockVariableResolver resolver = getVariableResolver();
        A a = new A();
        a.setName("AAA");
        resolver.putValue("a", a);
        app_.setVariableResolver(resolver);

        ValueBinding vb = app_.createValueBinding("#{a.name}");
        assertNotNull(vb);

        assertEquals("#{a.name}", vb.getExpressionString());
        Object value = vb.getValue(getFacesContext());
        assertEquals("AAA", value);
    }

    public void testCreateValidator_registerToDIContainer() throws Exception {
        ComponentDef cDef = new ComponentDefImpl(MockValidatorWithProperties.class);
        cDef.setComponentName("mock");
        PropertyDef pDef = new PropertyDefImpl("name");
        pDef.setValue("hoge");
        cDef.addPropertyDef(pDef);
        getContainer().register(cDef);
        
        app_ = new ApplicationImpl();
        Validator v = app_.createValidator("mock");
        assertNotNull(v);
        assertTrue(v instanceof MockValidatorWithProperties);
        MockValidatorWithProperties mock = (MockValidatorWithProperties) v;
        assertEquals("hoge", mock.getName());
    }
    
    public static class HogeConverter implements Converter {

        private String pattern_;

        private int num_;

        public Object getAsObject(FacesContext context, UIComponent component,
                String value) throws ConverterException {
            return null;
        }

        public String getAsString(FacesContext context, UIComponent component,
                Object value) throws ConverterException {
            return null;
        }

        public void setPattern(String pattern) {
            pattern_ = pattern;
        }

        public String getPattern() {
            return pattern_;
        }

        public int getNum() {
            return num_;
        }

        public void setNum(int num) {
            num_ = num;
        }
    }

    private static class Hoge {

    }

    private static class Hoge2 extends Hoge {

    }

    private static interface Foo {

    }

    private static class FooImpl implements Foo {

    }

    public static class A {
        String name_ = "aaa";

        public String getName() {
            return name_;
        }

        public void setName(String name) {
            name_ = name;
        }

    }

    public static class HogeConverter2 implements Converter {

        public int num = 0;
        
        public HogeConverter2() {
            num++;
        }
        
        public Object getAsObject(FacesContext context, UIComponent component,
                String value) throws ConverterException {
            return null;
        }

        public String getAsString(FacesContext context, UIComponent component,
                Object value) throws ConverterException {
            return null;
        }

        public int getNum() {
            return num;
        }
    }
}
