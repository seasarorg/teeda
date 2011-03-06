/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TreeNodeImpl implements TreeNode, Comparable, Serializable {

    private static final long serialVersionUID = 1L;

    private List children = new LinkedList();

    private String type;

    private Object description;

    private boolean leaf;

    private Object value;

    private boolean expanded;

    public TreeNodeImpl() {
    }

    public TreeNodeImpl(String type, Object description, boolean leaf) {
        this(type, description, null, leaf);
    }

    public TreeNodeImpl(String type, Object description, Object value,
            boolean leaf) {
        this.type = type;
        this.description = description;
        this.value = value;
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return leaf || !hasChild();
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List getChildren() {
        return children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getDescription() {
        return description;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public int getChildCount() {
        return getChildren().size();
    }

    protected boolean hasChild() {
        return (getChildCount() != 0);
    }

    public int compareTo(Object obj) {
        TreeNode otherNode = (TreeNode) obj;
        if (isLeaf() && !otherNode.isLeaf()) {
            return 1;
        } else if (!isLeaf() && otherNode.isLeaf()) {
            return -1;
        } else {
            final String mine = getDescription().toString();
            final String other = otherNode.getDescription().toString();
            return mine.compareTo(other);
        }
    }

    public void addChild(TreeNode node) {
        children.add(node);
    }

    public TreeNode getChild(int index) {
        return (TreeNode) children.get(index);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void setChildren(List children) {
        this.children = children;
    }

}
