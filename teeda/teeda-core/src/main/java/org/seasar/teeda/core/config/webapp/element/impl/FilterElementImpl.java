/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.webapp.element.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.config.webapp.element.FilterElement;
import org.seasar.teeda.core.config.webapp.element.InitParamElement;

/**
 * @author manhole
 */
public class FilterElementImpl implements FilterElement, Serializable {

    private static final long serialVersionUID = 1L;

    private String filterName_;

    private String filterClass_;

    private Map initParams_ = new HashMap();

    public String getFilterClass() {
        return filterClass_;
    }

    public void setFilterClass(String filterClass) {
        filterClass_ = filterClass;
    }

    public String getFilterName() {
        return filterName_;
    }

    public void setFilterName(String filterName) {
        filterName_ = filterName;
    }

    public InitParamElement getInitParamElementByParamName(String paramName) {
        return (InitParamElement) initParams_.get(paramName);
    }

    public void addInitParamElement(InitParamElement initParam) {
        initParams_.put(initParam.getParamName(), initParam);
    }

}
