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
package org.seasar.teeda.extension.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.ExtensionConstants;

public class TreeModelImpl implements TreeModel {

    private static final long serialVersionUID = 1L;

    private static final String ROOT_NODE_ID = "0";

    private TreeNode root;

    public TreeModelImpl(TreeNode root) {
        AssertionUtil.assertNotNull("root", root);
        this.root = root;
    }

    public boolean isNodeExpanded(String nodeId) {
        AssertionUtil.assertNotNull("nodeId", nodeId);
        if (isRootNode(nodeId)) {
            return root.isExpanded();
        }
        TreeNode node = getNodeById(nodeId);
        return node.isExpanded();
    }

    public void toggleExpanded(String nodeId) {
        AssertionUtil.assertNotNull("nodeId", nodeId);
        setExpanded(nodeId, true);
    }

    public void collapseExpanded(String nodeId) {
        AssertionUtil.assertNotNull("nodeId", nodeId);
        setExpanded(nodeId, false);
    }

    protected void setExpanded(String nodeId, boolean expanded) {
        TreeNode node = null;
        if (isRootNode(nodeId)) {
            node = root;
        } else {
            node = getNodeById(nodeId);
        }
        node.setExpanded(expanded);
    }

    public String[] getPathInformation(String nodeId) {
        if (nodeId == null) {
            throw new IllegalArgumentException();
        }
        List pathList = new ArrayList();
        pathList.add(nodeId);
        while (nodeId.lastIndexOf(ExtensionConstants.NAME_SEPARATOR) != -1) {
            nodeId = nodeId.substring(0, nodeId
                    .lastIndexOf(ExtensionConstants.NAME_SEPARATOR));
            pathList.add(nodeId);
        }
        Object[] ret = pathList.toArray(new String[pathList.size()]);
        Arrays.sort(ret, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                int len1 = arg0.toString().length();
                int len2 = arg1.toString().length();
                if (len1 < len2) {
                    return -1;
                } else if (len1 == len2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return (String[]) ret;
    }

    public boolean isLastChild(String nodeId) {
        AssertionUtil.assertNotNull("nodeId", nodeId);
        if (isRootNode(nodeId)) {
            return true;
        }
        String parentId = nodeId.substring(0, nodeId
                .lastIndexOf(ExtensionConstants.NAME_SEPARATOR));
        String childString = nodeId.substring(nodeId
                .lastIndexOf(ExtensionConstants.NAME_SEPARATOR) + 1);
        int childId = Integer.parseInt(childString);
        TreeNode parentNode = getNodeById(parentId);
        return ((childId + 1) == parentNode.getChildCount());
    }

    protected boolean isRootNode(String nodeId) {
        return (nodeId.lastIndexOf(ExtensionConstants.NAME_SEPARATOR) == -1);
    }

    public TreeNode getNodeById(String nodeId) {
        if (nodeId == null)
            return null;
        TreeNode node = null;
        StringTokenizer st = new StringTokenizer(nodeId,
                ExtensionConstants.NAME_SEPARATOR);
        while (st.hasMoreTokens()) {
            int nodeIndex = Integer.parseInt(st.nextToken());
            if (node == null) {
                node = root;
            } else {
                node = (TreeNode) node.getChildren().get(nodeIndex);
            }
        }
        return node;
    }

    public TreeWalker getTreeWalker() {
        return new TreeWalkerImpl();
    }
    
    public TreeNode getRootNode() {
        return getNodeById(ROOT_NODE_ID);
    }

}
