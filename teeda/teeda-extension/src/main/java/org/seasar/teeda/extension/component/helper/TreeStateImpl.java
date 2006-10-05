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

import java.util.HashSet;
import java.util.Set;

public class TreeStateImpl implements TreeState {

    private static final long serialVersionUID = 1L;

    private Set expandedNodes = new HashSet();

    private boolean transientValue = false;

    private String selected;

    public boolean isNodeExpanded(String nodeId) {
        return expandedNodes.contains(nodeId);
    }

    public void toggleExpanded(String nodeId) {
        if (expandedNodes.contains(nodeId)) {
            expandedNodes.remove(nodeId);
        } else {
            expandedNodes.add(nodeId);
        }
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean trans) {
        transientValue = trans;
    }

    public void expandPath(String[] nodePath) {
        for (int i = 0; i < nodePath.length; i++) {
            String nodeId = nodePath[i];
            expandedNodes.add(nodeId);
        }
    }

    public void collapsePath(String[] nodePath) {
        for (int i = 0; i < nodePath.length; i++) {
            String nodeId = nodePath[i];
            expandedNodes.remove(nodeId);
        }
    }

    public void setSelected(String nodeId) {
        selected = nodeId;
    }

    public boolean isSelected(String nodeId) {
        return nodeId.equals(selected);
    }
}
