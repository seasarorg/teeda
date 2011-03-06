/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.el.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.TeedaVariableResolver;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.NullELParser;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.ExceptionAssert;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 * @author manhole
 */
public class ValueBindingImplTest extends TeedaTestCase {

    public void testGetValueSimple1() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(o == a);
    }

    public void testGetValueSimple2() {
        A a = new A();
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("aaa", o);
        getApplication().getPropertyResolver().setValue(a, "name", "bbb");
        o = vb.getValue(getFacesContext());
        assertEquals("bbb", o);
    }

    public void testGetValueSimple3() {
        A a = new A();
        B b = new B();
        b.setName("hoge");
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("a", a);
        MockPropertyResolver propertyResolver = getPropertyResolver();
        propertyResolver.setValue(a, "b", b);
        getApplication().setPropertyResolver(propertyResolver);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.b.name}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("hoge", o);
    }

    public void testGetValueSimple4() {
        Hoge hoge = new Hoge();
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("hoge", hoge);
        MockPropertyResolver propertyResolver = getPropertyResolver();
        getApplication().setPropertyResolver(propertyResolver);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{hoge.a.name}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("aaa", o);
    }

    public void testGetValueSimple5() {
        C c = new C();
        c.setHoge(true);
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("c_", c);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{c_.hoge}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());
    }

    public void testGetValueSimple6() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", new Integer(1));
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a == 1}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals(Boolean.TRUE, o);
    }

    public void testGetValueSimple7() {
        List list = new ArrayList();
        D d = new D(list);
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("d", d);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{d.blank}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());

    }

    public void testGetValueSimple8() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", new Boolean(true));
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals(Boolean.TRUE, o);
    }

    public void testGetValueSimple9() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", new Boolean(true));
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{a == false}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals(Boolean.FALSE, o);
    }

    public void testGetMapValue1() {
        Map map = new HashMap();
        map.put("hoge", "foo");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("hogemap", map);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap}",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertSame(map, o);
        Map m = (Map) o;
        assertEquals("foo", m.get("hoge"));
    }

    public void testGetMapValue2() {
        Map map = new HashMap();
        map.put("hoge", "foo");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("hogemap", map);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{hogemap.hoge}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("foo", o);
    }

    public void testGetMapValue3() {
        Map map = new HashMap();
        map.put("a", new A());
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("hogemap", map);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{hogemap[\"a\"]['name'] }", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("aaa", o);
    }

    public void testGetListValue1() {
        List list = new ArrayList();
        list.add("aaa");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("list", list);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{list[0] }",
                createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("aaa", o);

        list.add(0, "bbb");
        o = vb.getValue(getFacesContext());
        assertEquals("bbb", o);

        vb.setValue(getFacesContext(), "ccc");
        o = vb.getValue(getFacesContext());
        assertEquals("ccc", o);
    }

    public void testGetListValue2() {
        List list = new ArrayList();
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("list", list);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{empty list}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());
    }

    public void testGetListValue3() {
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{empty list}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());
    }

    public void testGetListValue4() {
        List list = new ArrayList();
        list.add("a");
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("list", list);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{!empty list}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());
    }

    public void testGetListValue5() {
        List list = new ArrayList();
        list.add("a");
        MockVariableResolver variableResolver = getVariableResolver();
        variableResolver.putValue("list", list);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{not empty list}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertTrue(((Boolean) o).booleanValue());
    }

    public void testGetExpressionalValue1() {
        A a = new A();
        B b = new B();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        resolver.putValue("b", b);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{true ? a : b}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertSame(a, o);
    }

    public void testGetMixedValue1() {
        A a = new A();
        B b = new B();
        a.setName("hoge");
        b.setName("bar");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        resolver.putValue("b", b);
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{a.name} and #{b.name}", createELParser());
        Object o = vb.getValue(getFacesContext());
        assertEquals("hoge and bar", o);
    }

    public void testIsReadOnly1() {
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{'baz'}",
                createELParser());
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("'baz'", "'baz'");
        assertTrue(vb.isReadOnly(getFacesContext()));
    }

    public void testIsReadOnly2() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}",
                createELParser());
        assertFalse(vb.isReadOnly(getFacesContext()));
    }

    public void testIsReadOnly3() {
        Foo foo = new Foo();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("foo", foo);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{foo.num}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));
    }

    public void testIsReadOnlyImplicit() {
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{cookie}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{applicationScope}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{facesContext}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{header}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{headerValues}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{initParam}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{param}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{paramValues}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{requestScope}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));

        vb = new ValueBindingImpl(getApplication(), "#{sessionScope}",
                createELParser());
        assertTrue(vb.isReadOnly(getFacesContext()));
    }

    public void testGetType1() {
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{'hoge'}",
                createELParser());
        assertSame(String.class, vb.getType(getFacesContext()));
    }

    public void testGetType2() {
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{true}",
                createELParser());
        assertSame(Boolean.class, vb.getType(getFacesContext()));
    }

    public void testGetType3() {
        getApplication().setVariableResolver(new TeedaVariableResolver());
        ValueBinding vb = new ValueBindingImpl(getApplication(),
                "#{requestScope}", createELParser());
        assertTrue(Map.class.isAssignableFrom(vb.getType(getFacesContext())));
    }

    public void testGetType4() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("aaa", new A());
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{aaa}",
                createELParser());
        assertEquals(A.class, vb.getType(getFacesContext()));
    }

    public void testGetExpressionString() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}",
                createELParser());
        String str = vb.getExpressionString();
        assertEquals("#{a}", str);
    }

    public void testSetType1() {
        A a = new A();
        a.setName("hoge");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}",
                createELParser());
        A anotherA = (A) vb.getValue(getFacesContext());
        assertEquals("hoge", anotherA.getName());

        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.registerManagedBean("a", A.class, Scope.REQUEST);

        anotherA.setName("foo");
        vb.setValue(getFacesContext(), anotherA);
        anotherA = (A) vb.getValue(getFacesContext());
        assertEquals("foo", anotherA.getName());
    }

    public void testSetType2() {
        A a = new A();
        a.setName("aaa");
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}",
                createELParser());
        String s = (String) vb.getValue(getFacesContext());
        assertEquals("aaa", s);

        vb.setValue(getFacesContext(), "bbb");
        assertEquals("bbb", (String) vb.getValue(getFacesContext()));
    }

    public void testSetType3() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("num", new Integer(123));
        ValueBinding vb = new ValueBindingImpl(getApplication(), "#{num}",
                createELParser());
        assertEquals(new Integer(123), vb.getValue(getFacesContext()));

        resolver.putValue("num", new Integer(345));
        vb.setValue(getFacesContext(), new Integer(345));

        Integer num = new Integer(345);
        assertEquals(num, vb.getValue(getFacesContext()));
    }

    public void testSetValue_FacesContextIsNull() throws Exception {
        // ## Arrange ##
        ValueBindingImpl vb = new ValueBindingImpl(getApplication(), "#{a}",
                createELParser());

        // ## Act ##
        // ## Assert ##
        try {
            vb.setValue(null, "val");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testSetValue_DateType() throws Exception {
        // ## Arrange ##
        Conv c = new Conv();
        Date now = new Date();
        c.setDate(now);
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("c", c);
        ValueBindingImpl vb = new ValueBindingImpl(getApplication(),
                "#{c.date}", createELParser());
        FacesContext context = getFacesContext();

        // ## Act ##
        // ## Assert ##
        assertEquals(now, vb.getValue(context));

        // comment out => fail(old ValuBindingImpl)
        //PropertyEditorManager.registerEditor(java.util.Date.class, DateEditorForTest.class);

        Locale defaultLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.JAPANESE);
            vb.setValue(context, "20061231");
        } finally {
            Locale.setDefault(defaultLocale);
        }
        assertEquals(new SimpleDateFormat("yyyyMMdd").parse("20061231"), vb
                .getValue(context));
    }

    public void testSaveAndRestoreState() throws Exception {
        // ## Arrange ##
        ELParser parser = new NullELParser() {
            public Object parse(String expression) {
                return expression + expression;
            }
        };
        getContainer().register(parser);
        FacesContext context = getFacesContext();
        ValueBindingImpl vb1 = new ValueBindingImpl(context.getApplication(),
                "ab", parser);

        // ## Act ##
        Object state = vb1.saveState(context);

        ValueBindingImpl vb2 = new ValueBindingImpl();
        vb2.restoreState(context, state);

        // ## Assert ##
        assertEquals("ab", vb2.getExpressionString());
        assertEquals("abab", vb2.getExpression());
    }

    public void testNotSaveELParser() throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final NullELParser parser = new NullELParser();
        assertEquals(true, parser instanceof Serializable);
        getContainer().register(parser);
        ValueBindingImpl vb1 = new ValueBindingImpl(context.getApplication(),
                "fooo", parser);

        // ## Act ##
        // ## Assert ##
        final Object saved = vb1.saveState(context);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(saved);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        final Object restored = ois.readObject();

        ValueBindingImpl vb2 = new ValueBindingImpl();
        vb2.restoreState(context, restored);

        assertEquals("fooo", vb2.getExpressionString());
    }

    public static class Conv {
        private Date date_;

        public void setDate(Date date) {
            this.date_ = date;
        }

        public Date getDate() {
            return this.date_;
        }
    }

    public static class A {
        private String name = "aaa";

        private B b_;

        public A() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setB(B b) {
            b_ = b;
        }

        public B getB() {
            return b_;
        }
    }

    public static class B {
        private String name_;

        public B() {

        }

        public void setName(String name) {
            name_ = name;
        }

        public String getName() {
            return name_;
        }
    }

    protected ELParser createELParser() {
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        return parser;
    }

    public static class C {
        private boolean hoge_ = false;

        public void setHoge(boolean hoge) {
            hoge_ = hoge;
        }

        public boolean isHoge() {
            return hoge_;
        }
    }

    public static class D {
        List list_;

        public D(List list) {
            list_ = list;
        }

        public List getList() {
            return list_;
        }

        public boolean isBlank() {
            return list_.isEmpty();
        }
    }

    public static class Hoge {
        public Hoge() {
        }

        public A getA() {
            return new A();
        }
    }

    public static class Foo {
        public int getNum() {
            return 0;
        }
    }

}
