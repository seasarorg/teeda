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
        if (DifferenceConstants.TEXT_VALUE.getId() == difference.getId()) {
            String controlNodeValue = difference.getControlNodeDetail()
                    .getValue();
            String testNodeValue = difference.getTestNodeDetail().getValue();
            if (controlNodeValue.trim().equals(testNodeValue.trim())) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }
        } else if (DifferenceConstants.HAS_CHILD_NODES.getId() == difference
                .getId()) {
            NodeDetail cNode = difference.getControlNodeDetail();
            NodeDetail tNode = difference.getTestNodeDetail();
            NodeList cChildNodes = cNode.getNode().getChildNodes();
            NodeList tChildNodes = tNode.getNode().getChildNodes();

            NodeList containChildrenNode;
            if (cChildNodes.getLength() == 0) {
                containChildrenNode = tChildNodes;
            } else {
                containChildrenNode = cChildNodes;
            }
            for (int i = 0; i < containChildrenNode.getLength(); i++) {
                Node item = containChildrenNode.item(i);
                if (Node.TEXT_NODE != item.getNodeType()) {
                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
                }
                if (!"".equals(item.getNodeValue().trim())) {
                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
                }
            }
            return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    public void skippedComparison(Node node1, Node node2) {
        System.out.println("XmlUnitLearningTest.testIgnoreSpace()");
    }

}
