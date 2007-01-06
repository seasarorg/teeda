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
package org.seasar.teeda.extension.util;

import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.html.THtmlTree;

/**
 * @author shot
 *
 */
public class TreeNavigationImageLocatorImpl implements
        TreeNavigationImageLocator {

    private static final String LINE_TRUNK_GIF = "line-trunk.gif";

    public static final String imageRoot_BINDING = "bindingType=may";

    private String imageRoot = "";

    private static final String NAV_MINUS_LINE_LAST_GIF = "nav-line-last-expand.gif";

    private static final String NAV_PLUS_LINE_LAST_GIF = "nav-line-last-collapse.gif";

    private static final String NAV_MINUS_LINE_MIDDLE_GIF = "nav-line-middle-expand.gif";

    private static final String NAV_PLUS_LINE_MIDDLE_GIF = "nav-line-middle-collapse.gif";

    private static final String NAV_MINUS_GIF = "nav-expand.gif";

    private static final String NAV_PLUS_GIF = "nav-collapse.gif";

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

    private boolean clickable = false;

    public TreeNavigationImageLocatorImpl() {
    }

    public void setUpImageLocation(THtmlTree tree) {
        final TreeNode node = tree.getNode();
        final String nodeId = tree.getNodeId();
        final boolean showLines = tree.isShowLines();
        int bitMask = NOTHING;
        if (!node.isLeaf()) {
            bitMask += CHILDREN;
        }
        if (bitMask == CHILDREN && tree.isNodeExpanded()) {
            bitMask += EXPANDED;
        }
        if (tree.isLastChild(nodeId)) {
            bitMask += LAST;
        }
        if (showLines) {
            bitMask += LINES;
        }
        switch (bitMask) {
        case (NOTHING):
        case (LAST):
            navSrc = SPACER_GIF;
            setClickable(false);
            break;
        case (LINES):
            navSrc = LINE_MIDDLE_GIF;
            setClickable(false);
            break;
        case (LINES + LAST):
            navSrc = LINE_LAST_GIF;
            setClickable(false);
            break;
        case (CHILDREN):
        case (CHILDREN + LAST):
            navSrc = NAV_PLUS_GIF;
            altSrc = NAV_MINUS_GIF;
            setClickable(true);
            break;
        case (CHILDREN + LINES):
            navSrc = NAV_PLUS_LINE_MIDDLE_GIF;
            altSrc = NAV_MINUS_LINE_MIDDLE_GIF;
            setClickable(true);
            break;
        case (CHILDREN + LINES + LAST):
            navSrc = NAV_PLUS_LINE_LAST_GIF;
            altSrc = NAV_MINUS_LINE_LAST_GIF;
            setClickable(true);
            break;
        case (CHILDREN + EXPANDED):
        case (CHILDREN + EXPANDED + LAST):
            navSrc = NAV_MINUS_GIF;
            altSrc = NAV_PLUS_GIF;
            setClickable(true);
            break;
        case (CHILDREN + EXPANDED + LINES):
            navSrc = NAV_MINUS_LINE_MIDDLE_GIF;
            altSrc = NAV_PLUS_LINE_MIDDLE_GIF;
            setClickable(true);
            break;
        case (CHILDREN + EXPANDED + LINES + LAST):
            navSrc = NAV_MINUS_LINE_LAST_GIF;
            altSrc = NAV_PLUS_LINE_LAST_GIF;
            setClickable(true);
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
        if (navSrc != null) {
            navSrc = imageRoot + navSrc;
        }
        if (altSrc != null) {
            altSrc = imageRoot + altSrc;
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

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    public String getLineBackgroundSrc(boolean shouldShowLineBackground) {
        final String img = (shouldShowLineBackground) ? LINE_TRUNK_GIF
                : SPACER_GIF;
        return imageRoot + img;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
