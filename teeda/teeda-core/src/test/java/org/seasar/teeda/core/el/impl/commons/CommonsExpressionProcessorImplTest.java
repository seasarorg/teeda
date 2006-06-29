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
package org.seasar.teeda.core.el.impl.commons;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.el.ReferenceSyntaxException;

import org.apache.commons.el.parser.ELParser;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class CommonsExpressionProcessorImplTest extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CommonsExpressionProcessorImplTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestCommonsExpressionProcessorImpl.
     * @param arg0
     */
    public CommonsExpressionProcessorImplTest(String arg0) {
        super(arg0);
    }

    public void testProcessExpression() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a.b}"));
        try {
            Object obj = parser.ExpressionString();
            processor.processExpression(obj, Object.class);
        } catch (Exception ignore) {
        }
        try {
            processor.processExpression(new Object(), Object.class);
            fail();
        } catch (IllegalStateException e) {
            success();
        }
    }

    public void testEvaluate() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a}"));
        Object expression = null;
        try {
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        } catch (Exception ignore) {
        }
        MockVariableResolver resolver = getVariableResolver();
        A a = new A();
        resolver.putValue("a", a);
        Object o = processor.evaluate(getFacesContext(), expression);
        assertSame(a, o);
    }

    public void testEvaluate2() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("b${a}"));
        Object expression = null;
        try {
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        } catch (Exception ignore) {
        }
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("a", "A");
        MockApplication app = getApplication();
        app.setVariableResolver(resolver);
        Object o = processor.evaluate(getFacesContext(), expression);
        assertEquals("bA", o);
    }

    public void testToIndex() {
        List previous = new ArrayList();
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        Integer indexValue = processor.toIndex(previous, "1");
        assertEquals(new Integer(1), indexValue);

        indexValue = processor.toIndex(new String[] { "2" }, "2");
        assertEquals(new Integer(2), indexValue);

        try {
            indexValue = processor.toIndex(previous, List.class);
            fail();
        } catch (ReferenceSyntaxException expected) {
            success();
        }

        indexValue = processor.toIndex(new MockUIComponent(), "3");
        assertEquals(new Integer(3), indexValue);
    }

    public void testResolveBase1() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a}"));
        Object expression = null;
        try {
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        } catch (Exception ignore) {
        }
        Object o = processor.resolveBase(getFacesContext(), expression);
        assertNotNull(o);
        assertTrue(o instanceof String);
        assertEquals("a", (String) o);
    }

    public void testResolveBase2() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new ELParser(new StringReader("${a.name}"));
        Object expression = null;
        try {
            expression = parser.ExpressionString();
            processor.processExpression(expression, Object.class);
        } catch (Exception ignore) {
        }
        MockVariableResolver resolver = getVariableResolver();
        A a = new A();
        resolver.putValue("a", a);
        Object o = processor.resolveBase(getFacesContext(), expression);
        assertNotNull(o);
        assertTrue(o instanceof Object[]);
        Object[] objs = (Object[]) o;
        assertSame(a, objs[0]);
        assertEquals("name", objs[1]);
    }

    public void testGetCoercedObject() {
        CommonsExpressionProcessorImpl processor = new CommonsExpressionProcessorImpl();
        ArrayList list = new ArrayList();
        list.add("a");
        Object o = processor.getCoercedObject(list, List.class);
        assertNotNull(o);
        assertTrue(o instanceof List);
        List l = (List) o;
        assertEquals("a", l.get(0));
    }

    public static class A {
        private String name_ = "aaa";

        public A() {
        }

        public String getName() {
            return name_;
        }
    }
}
