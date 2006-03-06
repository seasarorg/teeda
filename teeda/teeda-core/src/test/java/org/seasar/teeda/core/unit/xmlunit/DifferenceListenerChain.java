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
import java.util.Iterator;
import java.util.List;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

/**
 * @author manhole
 */
public class DifferenceListenerChain implements DifferenceListener {

    private List listeners_ = new ArrayList();

    public void addDifferenceListener(DifferenceListener listener) {
        listeners_.add(listener);
    }

    public int differenceFound(Difference difference) {
        for (Iterator it = listeners_.iterator(); it.hasNext();) {
            DifferenceListener differenceListener = (DifferenceListener) it
                .next();
            int result = differenceListener.differenceFound(difference);
            if (result != DifferenceListener.RETURN_ACCEPT_DIFFERENCE) {
                return result;
            }
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    public void skippedComparison(Node node1, Node node2) {
        for (Iterator it = listeners_.iterator(); it.hasNext();) {
            DifferenceListener differenceListener = (DifferenceListener) it
                .next();
            differenceListener.skippedComparison(node1, node2);
        }
    }

}
