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
package org.seasar.teeda.core.util;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class PropertyDescUtilTest extends TestCase {

    public void testGetProperty() throws Exception {
        assertEquals(String.class, PropertyDescUtil.getProperty(new A().getClass(),
                "name"));
        assertEquals(int.class, PropertyDescUtil.getProperty(new A().getClass(),
                "num"));
    }

    public void testSetValue() throws Exception {
        A a = new A();
        PropertyDescUtil.setValue(a, "name", "hoge");
        PropertyDescUtil.setValue(a, "num", "5");
        assertEquals("hoge", a.getName());
        assertEquals(5, a.getNum());
    }

    public static class A {
        private String name = "a";

        private int num = 0;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

    }
}
