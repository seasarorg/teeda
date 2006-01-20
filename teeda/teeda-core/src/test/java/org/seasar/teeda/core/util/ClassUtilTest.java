/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
public class ClassUtilTest extends TestCase {

    /**
     * Constructor for ClassUtilTest.
     * 
     * @param name
     */
    public ClassUtilTest(String name) {
        super(name);
    }

    public void testGetWrapperClassByTypeName() throws Exception {
        assertEquals(Character.TYPE, ClassUtil
                .getWrapperClassByTypeName(Character.TYPE.getName()));
        assertEquals(Byte.TYPE, ClassUtil.getWrapperClassByTypeName(Byte.TYPE
                .getName()));
        assertEquals(Short.TYPE, ClassUtil
                .getWrapperClassByTypeName(Short.TYPE.getName()));
        assertEquals(Integer.TYPE, ClassUtil
                .getWrapperClassByTypeName(Integer.TYPE.getName()));
        assertEquals(Long.TYPE, ClassUtil.getWrapperClassByTypeName(Long.TYPE
                .getName()));
        assertEquals(Integer.TYPE, ClassUtil
                .getWrapperClassByTypeName(Integer.TYPE.getName()));
        assertEquals(Double.TYPE, ClassUtil
                .getWrapperClassByTypeName(Double.TYPE.getName()));
        assertEquals(Float.TYPE, ClassUtil
                .getWrapperClassByTypeName(Float.TYPE.getName()));
        assertEquals(Boolean.TYPE, ClassUtil
                .getWrapperClassByTypeName(Boolean.TYPE.getName()));
        assertNull(ClassUtil.getWrapperClassByTypeName(String.class.getName()));
    }

    public void testGetWrapperClassIfPrimitiveTypeName() throws Exception {
        assertEquals(Character.TYPE, ClassUtil
                .getWrapperClassIfPrimitiveTypeName(Character.TYPE.getName()));
        assertEquals(String.class, ClassUtil
                .getWrapperClassIfPrimitiveTypeName("java.lang.String"));
    }
}
