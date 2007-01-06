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
package org.seasar.teeda.spike.rhino2;

import org.seasar.teeda.spike.rhino2.RhinoTestCase2;

/**
 * 
 * @author yone
 *
 */
public class RhinoTest extends RhinoTestCase2 {

    // test calc function return type int
    public void testCalc() throws Exception {
        // # Arrange #
        Object[] args = { new Integer(10), new Integer(5) };

        // # Act #
        int calc = execJsFuncResultInt("calc", args);

        // # Assert #
        assertEquals(15, calc);
    }

    // test catString function return type string
    public void testCatString() throws Exception {
        // # Arrange #
        Object[] args = { "kumu", "teeda" };

        // # Act #
        String cat = execJsFuncResultString("catString", args);

        // # Assert #
        assertEquals("Hello kumu teeda", cat);
    }

    // test isNull function return type boolean
    public void testIsNull_Null() throws Exception {
        // # Arrange #
        Object[] args = { null };

        // # Act #
        boolean isNull = execJsFuncResultBoolean("isNull", args);

        // # Assert #
        assertTrue(isNull);
    }

    // test isNull function return type boolean
    public void testIsNull_NotNull() throws Exception {
        // # Arrange #
        Object[] args = { "not null" };

        // # Act #
        boolean isNull = execJsFuncResultBoolean("isNull", args);

        // # Assert #
        assertFalse(isNull);
    }

    // test function return type object
    public void testSplitTest() throws Exception {
        // # Arrange #
        Object[] args = { "seasar,s2jsf,s2dao,teeda" };

        // # Act #
        String[] strArray = execJsFuncResultStringArray("splitTest", args);

        for (int i = 0; i < strArray.length; i++) {
            System.out.println("strArray[" + i + "]=" + strArray[i]);
        }

        // # Assert #
        assertEquals("seasar", strArray[0]);
        assertEquals("s2jsf", strArray[1]);
        assertEquals("s2dao", strArray[2]);
        assertEquals("teeda", strArray[3]);
    }

    // test getObject
    public void testObjectName() throws Exception {
        // # Act #
        Object obj = getJsObject("mockName");

        // # Assert #
        assertNotNull(obj);

        assertEquals("AjaxComponent", obj.toString());
    }

    // test undefined object
    public void testGetObject_UnDefined() throws Exception {
        // # Act #
        Object obj = getJsObject("xxxxxxxxx");

        // # Assert #
        assertNull(obj);
    }

}