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
package org.seasar.teeda.core.el.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.el.MethodBinding;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockVariableResolver;
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
}
