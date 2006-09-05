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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.NodeDetail;
import org.w3c.dom.Node;

/**
 * @author manhole
 */
public class RegexpDifferenceListener implements DifferenceListener {

    public int differenceFound(Difference difference) {
        if (DifferenceConstants.TEXT_VALUE.getId() == difference.getId()
                || DifferenceConstants.ATTR_VALUE.getId() == difference.getId()) {
            // 14, 3
            NodeDetail cNodeDetail = difference.getControlNodeDetail();
            NodeDetail tNodeDetail = difference.getTestNodeDetail();
            String cNodeDetailValue = cNodeDetail.getValue();
            String tNodeDetailValue = tNodeDetail.getValue();

            try {
                Pattern pattern = Pattern.compile(cNodeDetailValue);
                Matcher matcher = pattern.matcher(tNodeDetailValue);
                if (matcher.matches()) {
                    System.out
                            .println("recover:TEXT_VALUE || ATTR_VALUE (Regexp)");
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }
            } catch (PatternSyntaxException e) {
            }
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    public void skippedComparison(Node node1, Node node2) {
    }

}
