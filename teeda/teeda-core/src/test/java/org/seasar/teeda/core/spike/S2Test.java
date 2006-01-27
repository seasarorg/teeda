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
package org.seasar.teeda.core.spike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ArgDefImpl;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;

/**
 * @author shot
 */
public class S2Test extends S2FrameworkTestCase {

    /**
     * Constructor for S2Test.
     * 
     * @param name
     */
    public S2Test(String name) {
        super(name);
    }

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
        list.add("aaa");
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
        ArgDef aDef1 = new ArgDefImpl("a");
        ArgDef aDef2 = new ArgDefImpl("A");
        mDef.addArgDef(aDef1);
        mDef.addArgDef(aDef2);
        cDef.addInitMethodDef(mDef);
        
        getContainer().register(cDef);

        Map m = (Map) getContainer().getComponent("a");
        assertEquals("A", m.get("a"));
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

    public static class B {
        public String getName() {
            return "bbb";
        }
    }
}
/*
 * 

    <!-- case 1 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
    </managed-bean>
    
    <!-- case 2 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <managed-property>
            <property-name></property-name>
            <value></value>
        </managed-property>
    </managed-bean>
    
    <!-- case 3 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <list-entries>
            <value></value>
            <value></value>
        </list-entries>
    </managed-bean>
    
    <!-- case 4 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <map-entries>
            <map-entry>
                <key></key>
                <value></value>
            </map-entry>
        </map-entries>
    </managed-bean>
    
    <!-- case 5 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <managed-property>
            <property-name></property-name>
            <property-class></property-class>
            <value></value>
        </managed-property>
    </managed-bean>

    <!-- case 6 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <managed-property>
            <property-name></property-name>
            <property-class></property-class>
            <list-entries>
                <value-class></value-class>
                <value></value>
            </list-entries>
        </managed-property>
    </managed-bean>
    
    <!-- case 7 -->
    <managed-bean>
        <managed-bean-name></managed-bean-name>
        <managed-bean-class></managed-bean-class>
        <managed-bean-scope></managed-bean-scope>
        <managed-property>
            <property-name></property-name>
            <property-class></property-class>
            <map-entries>
                <key-class></key-class>
                <value-class></value-class>
                <map-entry>
                    <key></key>
                    <value></value>
                </map-entry>
            </map-entries>
        </managed-property>
    </managed-bean>





 *
 *
 *
 *
 */