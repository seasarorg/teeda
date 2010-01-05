/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.IntegerConverter;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.teeda.core.application.impl.DefaultComponentLookupStrategy;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingFactory;
import org.seasar.teeda.core.el.impl.ValueBindingFactoryImpl;
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
import org.seasar.teeda.core.unit.ExceptionAssert;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.DIContainerUtil;

public class ApplicationImplTest extends TeedaTestCase {

    public void testAddComponent() {
        ApplicationImpl app = createApplication();
        MockUIComponent mock = new MockUIComponent();
        app.addComponent("mock", mock.getClass().getName());
        UIComponent c = app.createComponent("mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
        try {
            app.addComponent("mock2", "java.lang.String");
            fail();
        } catch (IllegalClassTypeException e) {
            assertEquals(UIComponent.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }
    }

    public void testCreateComponent1() {
        ApplicationImpl app = createApplication();
        MockUIComponent mock = new MockUIComponent();
        app.addComponent("mock", mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, mock);
        UIComponent c = app.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testCreateComponent2() {
        ApplicationImpl app = createApplication();
        MockUIComponent mock = new MockUIComponent();
        app.addComponent("mock", mock.getClass().getName());
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, "mock");
        UIComponent c = app.createComponent(vb, context, "mock");
        assertNotNull(c);
        assertTrue(c instanceof MockUIComponent);
    }

    public void testCreateConverter_ConverterIdIsNull() throws Exception {
        // ## Arrange ##
        ApplicationImpl app = createApplication();
        String converterId = null;

        // ## Act & Assert ##
        try {
            app.createConverter(converterId);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testCreateConverter_ConverterForClassIsNull() throws Exception {
        // ## Arrange ##
        ApplicationImpl app = createApplication();
        Class converterForClass = null;

        // ## Act & Assert ##
        try {
            app.createConverter(converterForClass);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testAddConverter1() {
        ApplicationImpl app = createApplication();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app.createConverter("mock.converter");
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAddConverter2() {
        ApplicationImpl app = createApplication();
        app
                .addConverter(Hoge.class,
                        "org.seasar.teeda.core.mock.MockConverter");
        Converter c = app.createConverter(Hoge.class);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAddConverter_converterInstanciateEverytime() {
        // # Arrange
        ApplicationImpl app = createApplication();
        app.addConverter("aaa", HogeConverter2.class.getName());
        app.addConverter(Hoge.class, HogeConverter2.class.getName());

        // # Act
        Converter c = app.createConverter("aaa");

        // # Assert
        assertNotNull(c);
        assertTrue(c instanceof HogeConverter2);
        HogeConverter2 c2 = (HogeConverter2) c;
        assertTrue(c2.getNum() == 1);

        // # Act
        c = app.createConverter(Hoge.class);

        // # Assert
        assertNotNull(c);
        assertTrue(c instanceof HogeConverter2);
        HogeConverter2 c3 = (HogeConverter2) c;
        assertTrue(c3.getNum() == 1);
    }

    public void testCreateConverterForInterface() {
        ApplicationImpl app = createApplication();
        app.addConverter(Foo.class, "org.seasar.teeda.core.mock.MockConverter");
        Foo foo = new FooImpl();
        Converter c = app.createConverter(foo.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testCreateConverterForSuperClass() {
        ApplicationImpl app = createApplication();
        app
                .addConverter(Hoge.class,
                        "org.seasar.teeda.core.mock.MockConverter");
        Hoge hoge = new Hoge2();
        Converter c = app.createConverter(hoge.getClass());
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testCreateCoverterForPrimitive() {
        ApplicationImpl app = createApplication();
        Converter c = app.createConverter(Integer.TYPE);
        assertNotNull(c);
        assertTrue(c instanceof IntegerConverter);
    }

    public void testAddValidator1() {
        ApplicationImpl app = createApplication();
        app.addValidator("teeda.null",
                "org.seasar.teeda.core.mock.NullValidator");
        Validator v = app.createValidator("teeda.null");
        assertNotNull(v);
        assertTrue(v instanceof NullValidator);
        try {
            app.addValidator("hoge", "java.lang.String");
        } catch (IllegalClassTypeException e) {
            assertEquals(Validator.class.getName(), e.getArgs()[0]);
            assertTrue(true);
        }

    }

    public void testCreateConverter_withProperties1() throws Exception {
        ApplicationImpl app = createApplication();
        ConverterConfiguration config = new ConverterConfiguration("pattern",
                "String", "yyyy/MM/dd HH:mm:ss");
        app.addConverterConfiguration("someConverterId", config);
        app.addConverter("someConverterId", HogeConverter.class.getName());

        Converter c = app.createConverter("someConverterId");

        assertNotNull(c);
        assertTrue(c instanceof HogeConverter);
        assertEquals("yyyy/MM/dd HH:mm:ss", ((HogeConverter) c).getPattern());
    }

    public void testCreateConverter_withProperties2() throws Exception {
        ApplicationImpl app = createApplication();
        ConverterConfiguration config = new ConverterConfiguration("num",
                "int", "3");
        app.addConverterConfiguration("someConverterId", config);
        app.addConverter("someConverterId", HogeConverter.class.getName());

        Converter c = app.createConverter("someConverterId");

        assertNotNull(c);
        assertTrue(c instanceof HogeConverter);
        assertEquals(3, ((HogeConverter) c).getNum());
    }

    //    public void testCreateConverter_notCreatedFromDI() throws Exception {
    //        // ## Arrange ##
    //        ComponentDef componentDef = new ComponentDefImpl(
    //                DateTimeConverter.class);
    //        componentDef.setComponentName("java.util.Date");
    //        getContainer().register(componentDef);
    //        ApplicationImpl app = createApplication();
    //        // ## Act & Assert ##
    //        assertNull(app.createConverter(Date.class));
    //    }

    public void testGetWellKnownConverter() throws Exception {
        assertEquals(IntegerConverter.class, ApplicationImpl
                .getWellKnownConverter(int.class).getClass());
    }

    public void testCreateValueBinding() throws Exception {
        ValueBindingFactory ctx = new ValueBindingFactoryImpl();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ctx.setELParser(parser);
        ApplicationImpl app = createApplication();
        app.setValueBindingFactory(ctx);
        MockVariableResolver resolver = getVariableResolver();
        A a = new A();
        a.setName("AAA");
        resolver.putValue("a", a);
        app.setVariableResolver(resolver);

        ValueBinding vb = app.createValueBinding("#{a.name}");
        assertNotNull(vb);

        assertEquals("#{a.name}", vb.getExpressionString());
        Object value = vb.getValue(getFacesContext());
        assertEquals("AAA", value);
    }

    public void testCreateValidator_registerToDIContainer() throws Exception {
        ComponentDef cDef = new ComponentDefImpl(
                MockValidatorWithProperties.class);
        cDef.setComponentName("mock");
        PropertyDef pDef = new PropertyDefImpl("name");
        pDef.setValue("hoge");
        cDef.addPropertyDef(pDef);
        getContainer().register(cDef);
        ApplicationImpl app = createApplication();
        Validator v = app.createValidator("mock");
        assertNotNull(v);
        assertTrue(v instanceof MockValidatorWithProperties);
        MockValidatorWithProperties mock = (MockValidatorWithProperties) v;
        assertEquals("hoge", mock.getName());
    }

    public void testCreateConverter_duplicateConverter1() throws Exception {
        ApplicationImpl app = createApplication();
        app.addConverter(getClass(), A1Converter.class.getName());
        app.addConverter(getClass(), A2Converter.class.getName());

        Converter c = app.createConverter(getClass());
        assertNotNull(c);
        assertTrue(c instanceof A1Converter);
    }

    public void testCreateConverter_duplicateConverter2() throws Exception {
        ComponentDef cDef = new ComponentDefImpl(A1Converter.class, Date.class
                .getName());
        getContainer().register(cDef);
        ApplicationImpl app = createApplication();
        app.addConverter(getClass(), A2Converter.class.getName());

        Converter c = app.createConverter(getClass());
        assertNotNull(c);
        assertTrue(c instanceof A1Converter);

        DIContainerUtil.getComponent(A1Converter.class);
    }

    public void testCreateComponent_orderTest() throws Exception {
        ComponentDef cDef = new ComponentDefImpl(MockUIComponent.class, "mock");
        PropertyDef pDef = new PropertyDefImpl("id");
        pDef.setValue("aaa");
        cDef.addPropertyDef(pDef);
        getContainer().register(cDef);
        ApplicationImpl app = createApplication();
        app.addComponent("mock", MockUIComponent.class.getName());

        UIComponent c = app.createComponent("mock");
        assertNotNull(c);
        MockUIComponent mock = (MockUIComponent) c;
        assertEquals("aaa", mock.getId());
    }

    public ApplicationImpl createApplication() {
        ApplicationImpl app = new ApplicationImpl();
        app.setComponentLookupStrategy(new DefaultComponentLookupStrategy());
        return app;
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

    public static class A1Converter implements Converter {

        public Object getAsObject(FacesContext context, UIComponent component,
                String value) throws ConverterException {
            return null;
        }

        public String getAsString(FacesContext context, UIComponent component,
                Object value) throws ConverterException {
            return null;
        }

    }

    public static class A2Converter extends A1Converter {
    }
}
