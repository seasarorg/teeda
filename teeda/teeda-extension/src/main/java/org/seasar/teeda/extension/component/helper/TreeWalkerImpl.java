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

import java.util.Stack;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.UITreeData;

/*
 * parent node="0", first child="0:0", second child="0:1"
 */
public class TreeWalkerImpl implements TreeWalker {

    private static final String ROOT_NODE_ID = "0";

    private UITreeData tree;

    private Stack nodeStack = new Stack();

    private Stack idStack = new Stack();

    private boolean checkState = true;

    private boolean startedWalking = false;

    public boolean isCheckState() {
        return checkState;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }

    public boolean next() {
        if (!startedWalking) {
            // the first next() call just needs to
            // set the root node and push it onto the stack
            final String rootNodeId = getRootNodeId();
            idStack.push(rootNodeId);
            tree.setNodeId(rootNodeId);
            nodeStack.push(tree.getNode());
            startedWalking = true;
            return true;
        }
        if (nodeStack.isEmpty()) {
            return false;
        }
        TreeNode prevNode = (TreeNode) nodeStack.peek();
        String prevNodeId = (String) idStack.peek();
        if (prevNode.isLeaf()) {
            nodeStack.pop();
            idStack.pop();
            return next();
        } else {
            TreeNode nextNode = null;
            String nextNodeId = null;
            final String nodeId = tree.getNodeId();
            if (prevNodeId.equals(nodeId)) {
                /**
                 * We know there is at least one child b/c otherwise we would have popped the node after
                 * checking isLeaf.  Basically we need to keep drilling down until we reach the deepest
                 * node that is available for "walking."  Then we'll return to the parent and render its
                 * siblings and walk back up the tree.
                 */
                nextNodeId = prevNodeId + ExtensionConstants.NAME_SEPARATOR
                        + "0";
                // don't render any children if the node is not expanded
                if (checkState) {
                    if (!tree.getDataModel().getTreeState().isNodeExpanded(
                            prevNodeId)) {
                        nodeStack.pop();
                        idStack.pop();
                        return next();
                    }
                }
            } else {
                // get the parent node
                String currentNodeId = nodeId;
                String parentNodeId = StringUtil.substringFromLast(
                        currentNodeId, ExtensionConstants.NAME_SEPARATOR);
                tree.setNodeId(parentNodeId);
                TreeNode parentNode = tree.getNode();
                int siblingCount = Integer.parseInt(currentNodeId
                        .substring(parentNodeId.length() + 1));
                siblingCount++;
                if (siblingCount == parentNode.getChildCount()) {
                    // no more siblings
                    nodeStack.pop();
                    idStack.pop();
                    return next();
                }
                nextNodeId = parentNodeId + ExtensionConstants.NAME_SEPARATOR
                        + siblingCount;
            }
            tree.setNodeId(nextNodeId);
            nextNode = tree.getNode();
            nodeStack.push(nextNode);
            idStack.push(nextNodeId);
            return true;
        }
    }

    public String getRootNodeId() {
        return ROOT_NODE_ID;
    }

    protected void reset() {
        nodeStack.empty();
        idStack.empty();
        startedWalking = false;
    }

    protected void setTree(UITreeData tree) {
        this.tree = tree;
    }

    public void walkBegin(UITreeData treeData) {
        reset();
        setTree(treeData);
    }
}
