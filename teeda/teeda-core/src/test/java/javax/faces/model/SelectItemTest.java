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
package javax.faces.model;

import junit.framework.TestCase;

import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class SelectItemTest extends TestCase {

    public void testConstructor1_NullValue() throws Exception {
        try {
            new SelectItem(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor2_NullValue() throws Exception {
        try {
            new SelectItem(null, "label");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor2_NullLabel() throws Exception {
        try {
            new SelectItem("value", null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor3_NullValue() throws Exception {
        try {
            new SelectItem(null, "label", "desc");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor3_NullLabel() throws Exception {
        try {
            new SelectItem("value", null, "desc");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor4_NullValue() throws Exception {
        try {
            new SelectItem(null, "label", "desc", true);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor4_NullLabel() throws Exception {
        try {
            new SelectItem("value", null, "desc", true);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testSetValue_Null() throws Exception {
        SelectItem selectItem = new SelectItem();
        try {
            selectItem.setValue(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testSetLabel_Null() throws Exception {
        SelectItem selectItem = new SelectItem();
        try {
            selectItem.setLabel(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

}
