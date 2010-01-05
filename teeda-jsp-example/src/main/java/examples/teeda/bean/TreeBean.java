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
package examples.teeda.bean;

import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.TreeNodeImpl;

/**
 * @author shot
 */
public class TreeBean {

    private TreeNode tree;

    public TreeBean() {
        tree = new TreeNodeImpl("folder-B", "ROOT", false);

        TreeNodeImpl personNode = new TreeNodeImpl("folder-A", "AAA", false);
        TreeNodeImpl document = new TreeNodeImpl("document", "BBB", true);
        personNode.getChildren().add(document);
        tree.getChildren().add(personNode);
    }

    public TreeNode getTree() {
        return tree;
    }

    public void setTree(TreeNode tree) {
        this.tree = tree;
    }
}
