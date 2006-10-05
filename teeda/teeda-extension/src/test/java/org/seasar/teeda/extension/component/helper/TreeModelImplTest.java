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
package org.seasar.teeda.extension.component.helper;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class TreeModelImplTest extends TestCase {

    public void testGetPathInformation() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeModelImpl model = new TreeModelImpl(parent);
        String[] strs = model.getPathInformation("a:b:c:d");
        assertTrue(strs.length == 4);
        assertEquals("a", strs[0]);
        assertEquals("a:b", strs[1]);
        assertEquals("a:b:c", strs[2]);
        assertEquals("a:b:c:d", strs[3]);
    }

    public void testGetNodeById_parentOnly1() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeModelImpl model = new TreeModelImpl(parent);
        assertEquals(parent, model.getNodeById("0"));
    }

    public void testGetNodeById_parentOnly2() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeNodeImpl child = new TreeNodeImpl();
        parent.addChild(child);
        TreeModelImpl model = new TreeModelImpl(parent);
        assertEquals(child, model.getNodeById("1:0"));
    }

    public void testIsLastChild() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeModelImpl model = new TreeModelImpl(parent);
        assertTrue(model.isLastChild("0"));
    }

    public void testIsLastChild2() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeNodeImpl child = new TreeNodeImpl();
        parent.addChild(child);
        TreeModelImpl model = new TreeModelImpl(parent);
        assertFalse(model.isLastChild("0:1"));
    }

}
