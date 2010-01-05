/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.application;

import java.io.Serializable;

/**
 * @author higa
 * @author shot
 */
public class TreeStructure implements Serializable {

    private static final long serialVersionUID = 0L;

    private String componentClassName_;

    private String componentId_;

    private TreeStructure[] children_;

    private Object[] facets_;

    public TreeStructure(String componentClassName, String componentId) {
        componentClassName_ = componentClassName;
        componentId_ = componentId;
    }

    public String getComponentClassName() {
        return componentClassName_;
    }

    public String getComponentId() {
        return componentId_;
    }

    public TreeStructure[] getChildren() {
        return (children_ != null) ? children_ : new TreeStructure[0];
    }

    public void setChildren(TreeStructure[] children) {
        children_ = children;
    }

    public Object[] getFacets() {
        return (facets_ != null) ? facets_ : new Object[0];
    }

    public void setFacets(Object[] facets) {
        facets_ = facets;
    }
}