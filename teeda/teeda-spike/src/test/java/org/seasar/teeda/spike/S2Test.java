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
package org.seasar.teeda.spike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.assembler.BindingTypeDefFactory;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ArgDefImpl;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;

/**
 * @author shot
 */
public class S2Test extends S2FrameworkTestCase {

    public void test1() {
        ComponentDef cDef = new ComponentDefImpl(A.class, "a");
        cDef.setInstanceDef(InstanceDefFactory.SINGLETON);
        getContainer().register(cDef);

        A a = (A) getContainer().getComponent("a");
        assertEquals("aaa", a.getName());
    }

    public void test2() {
        ComponentDef cDef = new ComponentDefImpl(A.class, "a");
        getContainer().register(cDef);

        ComponentDef cDef2 = new ComponentDefImpl(B.class, "b");
        getContainer().register(cDef2);

        A a = (A) getContainer().getComponent("a");
        assertEquals("bbb", a.getBName());
    }

    public void test3() {
        List list = new ArrayList();
        ComponentDef cDef = new ComponentDefImpl(list.getClass(), "a");
        cDef.setInstanceDef(InstanceDefFactory.SINGLETON);

        InitMethodDef mDef = new InitMethodDefImpl("add");
        ArgDef aDef = new ArgDefImpl("aaa");
        mDef.addArgDef(aDef);
        cDef.addInitMethodDef(mDef);

        getContainer().register(cDef);

        List l = (List) getContainer().getComponent("a");
        assertEquals("aaa", l.get(0));
    }

    public void test4() {
        Map map = new HashMap();
        ComponentDef cDef = new ComponentDefImpl(map.getClass(), "a");
        cDef.setInstanceDef(InstanceDefFactory.SINGLETON);

        InitMethodDef mDef = new InitMethodDefImpl("put");
        ArgDef aDef1 = new ArgDefImpl("key");
        ArgDef aDef2 = new ArgDefImpl("value");
        mDef.addArgDef(aDef1);
        mDef.addArgDef(aDef2);
        cDef.addInitMethodDef(mDef);

        getContainer().register(cDef);

        Map m = (Map) getContainer().getComponent("a");
        assertEquals("value", m.get("key"));
    }

    public void test5() {
        C c = new C();
        ComponentDef cDef = new ComponentDefImpl(c.getClass(), "c");
        cDef.setInstanceDef(InstanceDefFactory.SINGLETON);

        InitMethodDef mDef = new InitMethodDefImpl("setStr");
        ArgDef aDef = new ArgDefImpl("aaa");
        mDef.addArgDef(aDef);
        cDef.addInitMethodDef(mDef);

        getContainer().register(cDef);

        C c_ = (C) getContainer().getComponent("c");
        assertEquals("aaa", c_.getStr());

    }

    public void test6() throws Exception {
        getContainer().register(A1.class);
        getContainer().register(A2.class);
        getContainer().register(A.class);
        getContainer().register(B.class);
        
        getContainer().getComponent(B.class);
    }

    public static class A {
        private B b_;

        public void setB(B b) {
            b_ = b;
        }

        public String getBName() {
            return b_.getName();
        }

        public String getName() {
            return "aaa";
        }
    }

    public static class A1 extends A {
    }

    public static class A2 extends A {
        
    }
    
    public static class B {
        public String getName() {
            return "bbb";
        }
    }

    public static class C {
        private String str_;

        public void setStr(String str) {
            str_ = str;
        }

        protected String getStr() {
            return str_;
        }
    }
}
