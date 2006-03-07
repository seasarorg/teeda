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
package org.seasar.teeda.core.managedbean.impl;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ManagedBeanFactoryImplTest extends TeedaTestCase {

    private C c;

    public ManagedBeanFactoryImplTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSetManagedBean() {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("a", A.class, Scope.REQUEST);
        A a = (A) factory.getManagedBean("a");
        assertNotNull(a);
        assertEquals("hoge", a.getStr());
    }

    public void testSetManagedBean2() {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("a", A.class, Scope.REQUEST);
        factory.setManagedBean("b", B.class, Scope.REQUEST);
        B b = (B) factory.getManagedBean("b");
        assertEquals("hoge", b.getString());
    }

    public void testSetManagedBean3() {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("c", C.class, Scope.SESSION, "init", "destroy");
        c = (C) factory.getManagedBean("c");
        assertEquals("init:", c.toString());
    }

    protected void tearDownSetManagedBean3() throws Throwable {
        super.tearDownForEachTestMethod();
        assertEquals("init:destroy", c.toString());
    }

    public void testSetManagedBean4() throws Exception {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("a", A.class, Scope.REQUEST);
        factory.setManagedBean("b", A.class, Scope.REQUEST);
        A a = (A) factory.getManagedBean("b");
        assertEquals("hoge", a.getStr());
    }

    public void testManagedBeanScope_request() throws Exception {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("A", A.class, Scope.REQUEST);

        ComponentDef cDef = getContainer().getComponentDef("A");
        assertEquals(InstanceDefFactory.REQUEST, cDef.getInstanceDef());
    }

    public void testManagedBeanScope_session() throws Exception {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("A", A.class, Scope.SESSION);

        ComponentDef cDef = getContainer().getComponentDef("A");
        assertEquals(InstanceDefFactory.SESSION, cDef.getInstanceDef());
    }

    public void testManagedBeanScope_application() throws Exception {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("A", A.class, Scope.APPLICATION);

        ComponentDef cDef = getContainer().getComponentDef("A");
        assertEquals(InstanceDefFactory.APPLICATION, cDef.getInstanceDef());
    }

    public void testManagedBeanScope_none() throws Exception {
        ManagedBeanFactory factory = getManagedBeanFactory();
        factory.setManagedBean("A", A.class, Scope.NONE);

        ComponentDef cDef = getContainer().getComponentDef("A");
        assertEquals(InstanceDefFactory.OUTER, cDef.getInstanceDef());
    }

    public static class A {
        public A() {
        }

        private String str = "hoge";

        public String getStr() {
            return str;
        }
    }

    public static class B {
        private A a_;

        public void setA(A a) {
            a_ = a;
        }

        public String getString() {
            return (a_ != null) ? a_.getStr() : null;
        }
    }

    public static class C {
        private String initName = "";

        private String destroyName = "";

        public C() {
        }

        public void init() {
            initName = "init";
        }

        public void destroy() {
            destroyName = "destroy";
        }

        public String toString() {
            return initName + ":" + destroyName;
        }
    }

}
