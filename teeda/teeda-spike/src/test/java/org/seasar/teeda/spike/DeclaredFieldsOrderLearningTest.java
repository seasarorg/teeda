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
package org.seasar.teeda.spike;

import java.lang.reflect.Field;

import junit.framework.TestCase;

public class DeclaredFieldsOrderLearningTest extends TestCase {

    public void test() throws Exception {
        Field[] fields = Hoge.class.getDeclaredFields();
        assertTrue(fields.length == 3);
        assertEquals("aaa", fields[0].getName());
        assertEquals("bbb", fields[1].getName());
        assertEquals("ccc", fields[2].getName());
    }

    public static class Hoge {

        public static final String aaa = "AAA";

        public static final String bbb = "BBB";

        public static final String ccc = "CCC";
    }
}
