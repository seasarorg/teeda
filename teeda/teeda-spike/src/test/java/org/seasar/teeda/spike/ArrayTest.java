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

import java.io.Serializable;
import java.lang.reflect.Array;

import junit.framework.TestCase;

public class ArrayTest extends TestCase {

    public void test1() throws Exception {
        HogePage h1 = new HogePage("a");
        HogePage h2 = new HogePage("b");
        HogePage h3 = new HogePage("c");
        HogePage[] h = new HogePage[] { h1, h2, h3 };
        System.out.println(h.getClass());
        System.out.println(h.getClass().getComponentType());
        System.out.println(h.length);

        Object o = h;
        int i = 0;
        for (i = 0;; i++) {
            try {
                Array.get(o, i);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                break;
            }
        }
        System.out.println(i);

    }

    public static class HogePage implements Serializable {

        private String name;

        public HogePage(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
