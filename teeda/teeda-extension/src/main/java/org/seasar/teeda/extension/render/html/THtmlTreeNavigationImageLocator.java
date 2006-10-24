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
public class THtmlTreeNavigationImageLocator {

    private static final String NAV_MINUS_LINE_LAST_GIF = "nav-minus-line-last.gif";

    private static final String NAV_PLUS_LINE_LAST_GIF = "nav-plus-line-last.gif";

    private static final String NAV_MINUS_LINE_MIDDLE_GIF = "nav-minus-line-middle.gif";

    private static final String NAV_PLUS_LINE_MIDDLE_GIF = "nav-plus-line-middle.gif";

    private static final String NAV_MINUS_GIF = "nav-minus.gif";

    private static final String NAV_PLUS_GIF = "nav-plus.gif";

    private static final String LINE_LAST_GIF = "line-last.gif";

    private static final String LINE_MIDDLE_GIF = "line-middle.gif";

    private static final String SPACER_GIF = "spacer.gif";

    private static final int NOTHING = 0;

    private static final int CHILDREN = 1;

    private static final int EXPANDED = 2;

    private static final int LINES = 4;

    private static final int LAST = 8;

    private String navSrc;

    private String altSrc;

    private boolean renderLineBackground = false;

    public void setUpImageSources(THtmlTree tree) {
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
            navSrc = SPACER_GIF;
            break;
        case (LINES):
            navSrc = LINE_MIDDLE_GIF;
            break;
        case (LINES + LAST):
            navSrc = LINE_LAST_GIF;
            break;
        case (CHILDREN):
        case (CHILDREN + LAST):
            navSrc = NAV_PLUS_GIF;
            altSrc = NAV_MINUS_GIF;
            break;
        case (CHILDREN + LINES):
            navSrc = NAV_PLUS_LINE_MIDDLE_GIF;
            altSrc = NAV_MINUS_LINE_MIDDLE_GIF;
            break;
        case (CHILDREN + LINES + LAST):
            navSrc = NAV_PLUS_LINE_LAST_GIF;
            altSrc = NAV_MINUS_LINE_LAST_GIF;
            break;
        case (CHILDREN + EXPANDED):
        case (CHILDREN + EXPANDED + LAST):
            navSrc = NAV_MINUS_GIF;
            altSrc = NAV_PLUS_GIF;
            break;
        case (CHILDREN + EXPANDED + LINES):
            navSrc = NAV_MINUS_LINE_MIDDLE_GIF;
            altSrc = NAV_PLUS_LINE_MIDDLE_GIF;
            break;
        case (CHILDREN + EXPANDED + LINES + LAST):
            navSrc = NAV_MINUS_LINE_LAST_GIF;
            altSrc = NAV_PLUS_LINE_LAST_GIF;
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
            throw new IllegalArgumentException("Invalid argument");
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
