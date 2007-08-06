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
package org.seasar.teeda.extension.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class PagePersistenceUtilTest extends TestCase {

    public void test1() throws Exception {
        assertTrue(PagePersistenceUtil.isPersistenceType(int.class));
        assertFalse(PagePersistenceUtil
                .isPersistenceType(NotSerializableClass.class));
        String[] s = new String[4];
        assertTrue(PagePersistenceUtil.isPersistenceType(s.getClass()));

        String[][] s2 = new String[2][2];
        assertTrue(PagePersistenceUtil.isPersistenceType(s2.getClass()));

        int[] i = new int[2];
        assertTrue(PagePersistenceUtil.isPersistenceType(i.getClass()));

        int[][] i2 = new int[2][2];
        assertTrue(PagePersistenceUtil.isPersistenceType(i2.getClass()));

        assertTrue(PagePersistenceUtil
                .isPersistenceType(SerializableClass.class));

        SerializableClass[] sc = new SerializableClass[2];
        assertTrue(PagePersistenceUtil.isPersistenceType(sc.getClass()));

        SerializableClass[][] sc2 = new SerializableClass[2][2];
        assertTrue(PagePersistenceUtil.isPersistenceType(sc2.getClass()));

        assertTrue(PagePersistenceUtil.isPersistenceType(HashMap.class));

        assertTrue(PagePersistenceUtil.isPersistenceType(ArrayList.class));

        assertTrue(PagePersistenceUtil.isPersistenceType(TreeMap.class));

        assertFalse(PagePersistenceUtil.isPersistenceType(Map.class));

        assertFalse(PagePersistenceUtil.isPersistenceType(List.class));

        assertFalse(PagePersistenceUtil.isPersistenceType(Collection.class));

    }

    public static class NotSerializableClass {

    }

    public static class SerializableClass implements Serializable {

        private static final long serialVersionUID = 1L;

    }

}
