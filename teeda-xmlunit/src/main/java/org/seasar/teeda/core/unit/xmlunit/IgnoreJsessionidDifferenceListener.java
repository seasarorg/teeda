/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import org.w3c.dom.Node;

/**
 * @author manhole
 * @author YOKOTA Takehiko
 */
public class IgnoreJsessionidDifferenceListener implements DifferenceListener {

    public int differenceFound(Difference difference) {
        if (DifferenceConstants.ATTR_VALUE.getId() == difference.getId()) {
            String nodeName = difference.getControlNodeDetail().getNode()
                .getNodeName();
            if ("href".equalsIgnoreCase(nodeName)
                || "src".equalsIgnoreCase(nodeName)
                || "action".equalsIgnoreCase(nodeName)) {
                String controlNodeValue = difference.getControlNodeDetail()
                    .getValue();
                String testNodeValue = difference.getTestNodeDetail()
                    .getValue();
                if (removeJsessionid(controlNodeValue).equals(
                    removeJsessionid(testNodeValue))) {
                    System.out.println("recover: jsessionid");
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }
            }
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    private String removeJsessionid(final String s) {
        int leftPos = s.indexOf(";jsessionid=");
        if (-1 == leftPos) {
            return s;
        }
        String removed = s.substring(0, leftPos);
        int rightPos = s.indexOf("?", leftPos);
        if (rightPos == -1) {
            rightPos = s.indexOf("&", leftPos);
        }
        if (-1 < rightPos) {
            removed = removed + s.substring(rightPos);
        }
        return removed;
    }

    public void skippedComparison(Node control, Node test) {
    }

}
