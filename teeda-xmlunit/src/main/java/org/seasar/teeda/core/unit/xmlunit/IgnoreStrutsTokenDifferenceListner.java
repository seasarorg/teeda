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

import org.apache.struts.taglib.html.Constants;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.NodeDetail;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author YOKOTA Takehiko
 * 
 * FIXME testing
 */
public class IgnoreStrutsTokenDifferenceListner implements DifferenceListener {

    public int differenceFound(Difference difference) {
        NodeDetail control = difference.getControlNodeDetail();
        NodeDetail test = difference.getTestNodeDetail();
        if (isTokenNameAttributeNode(control.getNode())
            && isTokenNameAttributeNode(test.getNode())) {
            return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
        } else {
            return RETURN_ACCEPT_DIFFERENCE;
        }
    }

    boolean isTokenNameAttributeNode(Node node) {
        if (!"value".equals(node.getNodeName())) {
            return false;
        }
        if (node.getNodeType() != Node.ATTRIBUTE_NODE) {
            return false;
        }
        Node ownerElement = ((Attr) node).getOwnerElement();
        if (!"input".equals(ownerElement.getNodeName())) {
            return false;
        }
        NamedNodeMap attrMap = ownerElement.getAttributes();
        Node nameNode = attrMap.getNamedItem("name");
        if (nameNode == null
            || !Constants.TOKEN_KEY.equals(nameNode.getNodeValue())) {
            return false;
        }
        return true;
    }

    public void skippedComparison(Node control, Node test) {
    }

}
