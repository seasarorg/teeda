/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.config.faces.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.faces.element.impl.ManagedBeanElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.ManagedPropertyElementImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultManagedBeanAssemblerTest extends TeedaTestCase {

    public void testSimplyAssemble() throws Exception {
        ManagedBeanElement mb = new ManagedBeanElementImpl();
        mb.setManagedBeanName("aaa");
        mb.setManagedBeanClass(getClass().getName() + "$" + "A");
        mb.setManagedBeanScope("session");
        Map map = new HashMap();
        map.put(mb.getManagedBeanName(), mb);

        DefaultManagedBeanAssembler assembler = new DefaultManagedBeanAssembler(
                map);

        assembler.assemble();

        ManagedBeanFactory factory = getManagedBeanFactory();
        A a = (A) factory.getManagedBean("aaa");
        assertEquals("default", a.getName());
    }

    // FIXME need to fix.
    public void fixme_testComplexAssemble() throws Exception {
        /*
         * <context-param> <param-name>defaultName </param-name>
         * <param-value>izu </param-value> </context-param>
         * 
         * <managed-bean> <managed-bean-name>developer </managed-bean-name>
         * <managed-bean-class>HogeBean </managed-bean-class>
         * <managed-bean-scope>request </managed-bean-scope> <managed-property>
         * <property-name>name </property-name> <value-ref>initParam.defaultName
         * </value-ref> </managed-property> </managed-bean>
         */
        getServletContext().setInitParameter("hoge", "foo");
        ManagedBeanElement mb = new ManagedBeanElementImpl();
        mb.setManagedBeanName("aaa");
        mb.setManagedBeanClass(getClass().getName() + "$" + "A");
        mb.setManagedBeanScope("session");
        ManagedPropertyElement p = new ManagedPropertyElementImpl();
        p.setPropertyName("name");
        p.setValue("#{initParam.hoge}");
        mb.addManagedPropertyElement(p);
        Map map = new HashMap();
        map.put(mb.getManagedBeanName(), mb);

        DefaultManagedBeanAssembler assembler = new DefaultManagedBeanAssembler(
                map);

        assembler.assemble();

        ManagedBeanFactory factory = getManagedBeanFactory();
        A a = (A) factory.getManagedBean("aaa");
        assertEquals("foo", a.getName());
    }

    public static class A {
        private String name_ = "default";

        public String getName() {
            return name_;
        }

        public void setName(String name) {
            name_ = name;
        }
    }

}
