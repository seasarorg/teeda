/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
public class SelectItemGroupTest extends TestCase {

    public void testConstructor1_NullLabel() throws Exception {
        try {
            new SelectItemGroup(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor1() throws Exception {
        SelectItemGroup selectItemGroup = new SelectItemGroup("foo");
        assertEquals("", selectItemGroup.getValue());
        assertEquals("foo", selectItemGroup.getLabel());
        assertEquals(false, selectItemGroup.isDisabled());
    }

    public void testConstructor2_NullLabel() throws Exception {
        try {
            new SelectItemGroup((String) null, "desc", false,
                    new SelectItem[] {});
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testConstructor2_NullSelectItem() throws Exception {
        try {
            new SelectItemGroup("label", "desc", false, (SelectItem[]) null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testSetSelectItems_Null() throws Exception {
        SelectItemGroup selectItemGroup = new SelectItemGroup();
        try {
            selectItemGroup.setSelectItems(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

}
