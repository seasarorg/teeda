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
package org.seasar.teeda.core.el.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

import junitx.framework.ObjectAssert;

import org.seasar.framework.exception.SRuntimeException;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.unit.ExceptionAssert;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class MethodBindingImplTest extends TeedaTestCase {

    public void testGetType1() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{a.getName}", parser);
        MethodBinding mb = new MethodBindingImpl(vb, new Class[] {}, parser);
        assertSame(String.class, mb.getType(getFacesContext()));
    }

    public void testGetType2() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{a.getNum}", parser);
        MethodBinding mb = new MethodBindingImpl(vb, new Class[] {}, parser);
        assertSame(int.class, mb.getType(getFacesContext()));
    }

    public void testGetType3() {
        Map map = new HashMap();
        map.put("a", new A());
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("m", map);
        resolver.putValue("a", new A());
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{m[\"a\"].toString}", parser);
        MethodBinding mb = new MethodBindingImpl(vb, new Class[] {}, parser);
        assertSame(String.class, mb.getType(getFacesContext()));
    }

    public void testInvoke1() {
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{a.getName}", parser);
        MethodBinding mb = new MethodBindingImpl(vb, new Class[] {}, parser);
        assertEquals(a.getName(), mb.invoke(getFacesContext(), null));
    }

    public void testInvokeThrowsEvaluationException() throws Exception {
        // ## Arrange ##
        C c = new C();
        c.setThrowable(new SRuntimeException("someMessageId"));
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("c", c);
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{c.execute}", parser);
        MethodBindingImpl mb = new MethodBindingImpl(vb, new Class[] {}, parser);

        // ## Act ##
        // ## Assert ##
        try {
            mb.invoke(getFacesContext(), null);
            fail();
        } catch (EvaluationException e) {
            ExceptionAssert.assertMessageExist(e);
            Throwable cause = e.getCause();
            ObjectAssert.assertInstanceOf(SRuntimeException.class, cause);
            SRuntimeException runtimeException = (SRuntimeException) cause;
            assertEquals("someMessageId", runtimeException.getMessageCode());
        }
    }

    public void testInvokeThrowsMethodNotFoundException() throws Exception {
        // ## Arrange ##
        A a = new A();
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", a);
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{a.wrongMethodName}", parser);
        MethodBinding mb = new MethodBindingImpl(vb, new Class[] {}, parser);

        // ## Act ##
        // ## Assert ##
        try {
            mb.invoke(getFacesContext(), null);
            fail();
        } catch (MethodNotFoundException e) {
            ExceptionAssert.assertMessageExist(e);
        }
    }

    public void testSaveAndRestoreState() throws Exception {
        //      ## Arrange ##
        A a = new A();
        a.setName("foooooo");
        getVariableResolver().putValue("aaa", a);

        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingBase vb = new ValueBindingImpl(getApplication(),
                "#{aaa.getName}", parser);
        MethodBindingImpl mb1 = new MethodBindingImpl(vb, new Class[] {},
                parser);
        final FacesContext context = getFacesContext();
        assertEquals("foooooo", mb1.invoke(context, null));

        getContainer().register(parser);

        // ## Act ##
        final Object state = mb1.saveState(context);
        MethodBindingImpl mb2 = new MethodBindingImpl();
        mb2.restoreState(context, state);

        // ## Assert ##
        assertEquals("foooooo", mb2.invoke(context, null));
    }

    public void testNotSaveELParser() throws Exception {
        // ## Arrange ##
        MockVariableResolver resolver = getVariableResolver();
        A a = new A();
        a.setNum(4);
        resolver.putValue("a", a);
        ELParser parser = new CommonsELParser();
        getContainer().register(parser);
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBindingImpl vb = new ValueBindingImpl(getApplication(),
                "#{a.getNum}", parser);
        MethodBindingImpl mb1 = new MethodBindingImpl(vb, new Class[] {},
                parser);
        FacesContext context = getFacesContext();

        assertEquals(true, parser instanceof Serializable);

        // ## Act ##
        // ## Assert ##
        final Object saved = mb1.saveState(context);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(saved);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        final Object restored = ois.readObject();

        MethodBindingImpl mb2 = new MethodBindingImpl();
        mb2.restoreState(context, restored);

        assertEquals("#{a.getNum}", mb2.getExpressionString());

        Object o = mb2.invoke(context, new Object[] {});
        assertEquals(new Integer(4), o);
    }

    public static class A {
        private String name_ = "aaa";

        private int num_ = 0;

        private B b_;

        public A() {
        }

        public String getName() {
            return name_;
        }

        public int getNum() {
            return num_;
        }

        public void setName(String name) {
            name_ = name;
        }

        public void setNum(int num) {
            num_ = num;
        }

        public B getB() {
            return b_;
        }

        public void setB(B b) {
            b_ = b;
        }
    }

    public static class B {
        private String name_ = "bbb";

        public B() {
        }

        public String getName() {
            return name_;
        }

        public void setName(String name) {
            name_ = name;
        }
    }

    public static class C {
        private RuntimeException re_;

        public void execute() {
            throw re_;
        }

        public void setThrowable(RuntimeException re) {
            re_ = re;
        }
    }

}
