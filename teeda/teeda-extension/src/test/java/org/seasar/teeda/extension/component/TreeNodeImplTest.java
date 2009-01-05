/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import org.seasar.teeda.extension.component.TreeNodeImpl;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class TreeNodeImplTest extends TestCase {

    public void testIsLeaf_singleIsLeaf() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl();
        assertTrue(node.isLeaf());
    }

    public void testIsLeaf_singleIsLeaf2() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl("type", "desc", true);
        assertTrue(node.isLeaf());
    }

    public void testIsLeaf_singleIsLeaf3() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl("type", "desc", false);
        assertTrue(node.isLeaf());
    }

    public void testIsLeaf_multipleLeaf1() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl();
        TreeNodeImpl child = new TreeNodeImpl();
        parent.addChild(child);
        assertFalse(parent.isLeaf());
    }

    public void testIsLeaf_multipleLeaf2() throws Exception {
        TreeNodeImpl parent = new TreeNodeImpl("a", "b", true);
        TreeNodeImpl child = new TreeNodeImpl();
        parent.addChild(child);
        assertTrue(parent.isLeaf());
    }

    public void testCompare1() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl("a", "b", true);
        TreeNodeImpl otherNode = new TreeNodeImpl("b", "c", false);
        otherNode.addChild(new TreeNodeImpl("c", "d", true));
        assertTrue(node.compareTo(otherNode) == 1);
    }

    public void testCompare2() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl("b", "c", false);
        node.addChild(new TreeNodeImpl("c", "d", true));
        TreeNodeImpl node2 = new TreeNodeImpl("a", "b", true);
        assertTrue(node.compareTo(node2) == -1);
    }

    public void testCompare3() throws Exception {
        TreeNodeImpl node = new TreeNodeImpl("b", "c", true);
        node.setDescription("a");
        TreeNodeImpl node2 = new TreeNodeImpl("a", "b", true);
        node.setDescription("b");
        assertTrue(node.compareTo(node2) == 0);
    }

}
