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
package org.seasar.teeda.core.unit.xmlunit;

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.NodeDetail;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author manhole
 */
public class TextTrimmingDifferenceListener implements DifferenceListener {

    public int differenceFound(Difference difference) {
        NodeDetail cNodeDetail = difference.getControlNodeDetail();
        NodeDetail tNodeDetail = difference.getTestNodeDetail();
        Node cNode = cNodeDetail.getNode();
        Node tNode = tNodeDetail.getNode();
        if (DifferenceConstants.TEXT_VALUE.getId() == difference.getId()) {
            // 14
            String cNodeDetailValue = cNodeDetail.getValue();
            String tNodeDetailValue = tNodeDetail.getValue();
            if (cNodeDetailValue.trim().equals(tNodeDetailValue.trim())) {
                System.out.println("recover:TEXT_VALUE");
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
        } else if (DifferenceConstants.NODE_TYPE.getId() == difference.getId()) {
            // 17
            String cNodeValue = cNode.getNodeValue();
            String tNodeValue = tNode.getNodeValue();
            if (isBlank(cNodeValue) && isBlank(tNodeValue)) {
                System.out.println("recover:NODE_TYPE");
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
        } else if (DifferenceConstants.HAS_CHILD_NODES.getId() == difference
            .getId()) {
            // 18
            NodeList cChildNodes = cNode.getChildNodes();
            NodeList tChildNodes = tNode.getChildNodes();

            // System.out.println("cNodeDetail=" + cNodeDetail);
            // System.out.println("tNodeDetail=" + tNodeDetail);
            // System.out.println("cNodeDetail getXpathLocation="
            // + cNodeDetail.getXpathLocation());
            // System.out.println("tNodeDetail getXpathLocation="
            // + tNodeDetail.getXpathLocation());
            // System.out.println("cNode=" + cNode);
            // System.out.println("tNode=" + tNode);
            // System.out.println("cChildNodes=" + cChildNodes);
            // System.out.println("tChildNodes=" + tChildNodes);
            NodeList containChildrenNode;
            if (cChildNodes.getLength() == 0) {
                containChildrenNode = tChildNodes;
            } else {
                containChildrenNode = cChildNodes;
            }
            List nodes = minusEmptyTextNode(containChildrenNode);
            if (nodes.isEmpty()) {
                System.out.println("recover:HAS_CHILD_NODES");
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
            if (Node.TEXT_NODE == cNode.getNodeType()
                || Node.TEXT_NODE == tNode.getNodeType()) {
                System.out
                    .println("recover:HAS_CHILD_NODES (one is TEXT_NODE)");
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
        } else if (DifferenceConstants.CHILD_NODELIST_LENGTH.getId() == difference
            .getId()) {
            // 19
            NodeList cChildNodes = cNode.getChildNodes();
            NodeList tChildNodes = tNode.getChildNodes();
            List cNodeList = minusEmptyTextNode(cChildNodes);
            List tNodeList = minusEmptyTextNode(tChildNodes);
            if (cNodeList.size() == tNodeList.size()) {
                boolean equal = true;
                for (int i = 0; i < cNodeList.size(); i++) {
                    Node node1 = (Node) cNodeList.get(i);
                    Node node2 = (Node) tNodeList.get(i);
                    if (node1.getNodeType() != node2.getNodeType()) {
                        equal = false;
                    }
                    if (!node1.getNodeName().equals(node2.getNodeName())) {
                        equal = false;
                    }
                    String node1Value = node1.getNodeValue();
                    String node2Value = node2.getNodeValue();
                    if (!(equals(node1Value, node2Value))) {
                        equal = false;
                    }
                }
                if (equal) {
                    System.out.println("recover:CHILD_NODELIST_LENGTH");
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            }
        } else if (DifferenceConstants.CHILD_NODELIST_SEQUENCE.getId() == difference
            .getId()) {
            // 20

            // System.out.println(cNodeDetail.getValue());
            // System.out.println(tNodeDetail.getValue());
            // System.out.println(cNode.getNodeName());
            // System.out.println(tNode.getNodeName());
            //
            // System.out.println(cNode.getChildNodes());
            // System.out.println(tNode.getChildNodes());

            System.out.println("recover:CHILD_NODELIST_SEQUENCE");
            return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    private List minusEmptyTextNode(NodeList containChildrenNode) {
        List l = new ArrayList();
        for (int i = 0; i < containChildrenNode.getLength(); i++) {
            Node node = containChildrenNode.item(i);
            String nodeValue = node.getNodeValue();
            // TEXT_NODE=3
            if (Node.TEXT_NODE != node.getNodeType()) {
                l.add(node);
            } else if (nodeValue != null && !"".equals(nodeValue.trim())) {
                l.add(node);
            }
        }
        return l;
    }

    public void skippedComparison(Node control, Node test) {
    }

    boolean equals(String target1, String target2) {
        return (target1 == null) ? (target2 == null) : target1.equals(target2);
    }

    boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
