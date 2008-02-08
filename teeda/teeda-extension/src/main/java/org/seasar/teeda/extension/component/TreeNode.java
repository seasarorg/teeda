/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import java.io.Serializable;
import java.util.List;

public interface TreeNode extends Serializable {

    boolean isLeaf();

    void setLeaf(boolean leaf);

    void addChild(TreeNode node);

    List getChildren();

    void setChildren(List children);

    String getType();

    void setType(String type);

    Object getDescription();

    void setDescription(Object description);

    void setValue(Object value);

    Object getValue();

    int getChildCount();

    TreeNode getChild(int index);

    void setExpanded(boolean expanded);

    boolean isExpanded();
}
