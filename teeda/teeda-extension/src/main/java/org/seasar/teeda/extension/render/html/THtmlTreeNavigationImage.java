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
package org.seasar.teeda.extension.render.html;

import org.seasar.teeda.extension.component.helper.TreeNode;
import org.seasar.teeda.extension.component.html.THtmlTree;

/**
 * @author shot
 * 
 */
class THtmlTreeNavigationImage {

    private static final int NOTHING = 0;

    private static final int CHILDREN = 1;

    private static final int EXPANDED = 2;

    private static final int LINES = 4;

    private static final int LAST = 8;

    private String navSrc;

    private String altSrc;

    private boolean renderLineBackground = false;

    public void decide(THtmlTree tree) {
        final TreeNode node = tree.getNode();
        final String nodeId = tree.getNodeId();
        final boolean showLines = tree.isShowLines();
        int bitMask = NOTHING;
        if(!node.isLeaf()) {
            bitMask += CHILDREN;
        }
        if (bitMask == CHILDREN && tree.isNodeExpanded()) {
            bitMask += EXPANDED;
        }
        if(tree.isLastChild(nodeId)) {
            bitMask += LAST;
        }
        if(showLines) {
            bitMask += LINES;
        }
        switch (bitMask) {
        case (NOTHING):
        case (LAST):
            navSrc = "spacer.gif";
            break;
        case (LINES):
            navSrc = "line-middle.gif";
            break;
        case (LINES + LAST):
            navSrc = "line-last.gif";
            break;
        case (CHILDREN):
        case (CHILDREN + LAST):
            navSrc = "nav-plus.gif";
            altSrc = "nav-minus.gif";
            break;
        case (CHILDREN + LINES):
            navSrc = "nav-plus-line-middle.gif";
            altSrc = "nav-minus-line-middle.gif";
            break;
        case (CHILDREN + LINES + LAST):
            navSrc = "nav-plus-line-last.gif";
            altSrc = "nav-minus-line-last.gif";
            break;
        case (CHILDREN + EXPANDED):
        case (CHILDREN + EXPANDED + LAST):
            navSrc = "nav-minus.gif";
            altSrc = "nav-plus.gif";
            break;
        case (CHILDREN + EXPANDED + LINES):
            navSrc = "nav-minus-line-middle.gif";
            altSrc = "nav-plus-line-middle.gif";
            break;
        case (CHILDREN + EXPANDED + LINES + LAST):
            navSrc = "nav-minus-line-last.gif";
            altSrc = "nav-plus-line-last.gif";
            break;
        case (EXPANDED + LINES):
        case (EXPANDED + LINES + LAST):
        case (EXPANDED):
        case (EXPANDED + LAST):
            throw new IllegalStateException(
                    "Encountered a node ["
                            + nodeId
                            + "] + with an illogical state.  "
                            + "Node is expanded but it is also considered a leaf (a leaf cannot be considered expanded.");
        default:
            // catch all for any other combinations
            throw new IllegalArgumentException("Invalid bit mask of " + bitMask);
        }
        renderLineBackground = (bitMask & LINES) != 0 && (bitMask & LAST) == 0;
    }

    public boolean shouldRenderLineBackground() {
        return renderLineBackground;
    }

    public String getAltSrc() {
        return altSrc;
    }

    public String getNavSrc() {
        return navSrc;
    }

}
