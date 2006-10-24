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
package org.seasar.teeda.extension.render.html;

import junit.framework.TestCase;

import org.seasar.teeda.extension.component.helper.TreeNode;
import org.seasar.teeda.extension.component.helper.TreeNodeImpl;
import org.seasar.teeda.extension.component.html.THtmlTree;

/**
 * @author shot
 * 
 */
public class THtmlTreeNavigationImageLocatorTest extends TestCase {

    public void testDecide_leaf() throws Exception {
        THtmlTreeNavigationImageLocator i1 = new THtmlTreeNavigationImageLocator();
        MockHtmlTree tree = new MockHtmlTree();
        TreeNode node = new TreeNodeImpl("0", "aaa", true);
        tree.setNode(node);
        tree.setShowLines(false);
        tree.setLastChild(false);
        i1.setUpImageSources(tree);
        assertEquals("spacer.gif", i1.getNavSrc());
        assertNull(i1.getAltSrc());
    }

    public void testDecide_childrenAndNodeIsNotExpanded() throws Exception {
        THtmlTreeNavigationImageLocator i1 = new THtmlTreeNavigationImageLocator();
        MockHtmlTree tree = new MockHtmlTree();
        TreeNode node = new TreeNodeImpl("0", "aaa", false);
        node.addChild(new TreeNodeImpl("0:1", "bbb", true));
        tree.setNode(node);
        tree.setShowLines(false);
        tree.setLastChild(false);
        tree.setNodeExpanded(false);
        i1.setUpImageSources(tree);
        assertEquals("nav-plus.gif", i1.getNavSrc());
        assertEquals("nav-minus.gif", i1.getAltSrc());
    }

    public void testDecide_childrenAndNodeExpanded() throws Exception {
        THtmlTreeNavigationImageLocator i1 = new THtmlTreeNavigationImageLocator();
        MockHtmlTree tree = new MockHtmlTree();
        TreeNode node = new TreeNodeImpl("0", "aaa", false);
        node.addChild(new TreeNodeImpl("0:1", "bbb", true));
        tree.setNode(node);
        tree.setShowLines(false);
        tree.setLastChild(false);
        tree.setNodeExpanded(true);
        i1.setUpImageSources(tree);
        assertEquals("nav-minus.gif", i1.getNavSrc());
        assertEquals("nav-plus.gif", i1.getAltSrc());
    }

    public void testDecide_childrenAndNodeExpandedAndIsLastChild() throws Exception {
        THtmlTreeNavigationImageLocator i1 = new THtmlTreeNavigationImageLocator();
        MockHtmlTree tree = new MockHtmlTree();
        TreeNode node = new TreeNodeImpl("0", "aaa", false);
        node.addChild(new TreeNodeImpl("0:1", "bbb", true));
        tree.setNode(node);
        tree.setShowLines(false);
        tree.setLastChild(true);
        tree.setNodeExpanded(true);
        i1.setUpImageSources(tree);
        assertEquals("nav-minus.gif", i1.getNavSrc());
        assertEquals("nav-plus.gif", i1.getAltSrc());
    }

    public void testDecide_childrenAndNodeExpandedAndIsLastChildAndLastLine() throws Exception {
        THtmlTreeNavigationImageLocator i1 = new THtmlTreeNavigationImageLocator();
        MockHtmlTree tree = new MockHtmlTree();
        TreeNode node = new TreeNodeImpl("0", "aaa", false);
        node.addChild(new TreeNodeImpl("0:1", "bbb", true));
        tree.setNode(node);
        tree.setShowLines(true);
        tree.setLastChild(true);
        tree.setNodeExpanded(true);
        i1.setUpImageSources(tree);
        assertEquals("nav-minus-line-last.gif", i1.getNavSrc());
        assertEquals("nav-plus-line-last.gif", i1.getAltSrc());
    }

    public static class MockHtmlTree extends THtmlTree {

        private boolean showLines = false;
        
        private TreeNode node;

        private boolean lastChild = false;

        private boolean nodeExpanded;
        
        public boolean isNodeExpanded() {
            return nodeExpanded;
        }

        public void setNodeExpanded(boolean nodeExpanded) {
            this.nodeExpanded = nodeExpanded;
        }

        public void setLastChild(boolean lastChild) {
            this.lastChild = lastChild;
        }

        public boolean isLastChild(String nodeId) {
            return lastChild;
        }
        
        public boolean isShowLines() {
            return showLines;
        }

        public void setShowLines(boolean showLines) {
            this.showLines = showLines;
        }

        public void setNode(TreeNode node) {
            this.node = node;
        }

        public TreeNode getNode() {
            return node;
        }

    }
}
