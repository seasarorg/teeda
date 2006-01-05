/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.webapp.element.ContextParamElement;
import org.seasar.teeda.core.config.webapp.element.FilterElement;
import org.seasar.teeda.core.config.webapp.element.ServletElement;
import org.seasar.teeda.core.config.webapp.element.TaglibElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;

/**
 * @author manhole
 */
public class WebappConfigImpl implements WebappConfig, Serializable {

    private static final long serialVersionUID = 1L;

    private Map contextParams_ = new HashMap();
    private Map filters_ = new HashMap();
    private Map servlets_ = new HashMap();
    private List taglibs_ = new ArrayList();

    public List getContextParams() {
        return new ArrayList(contextParams_.values());
    }

    public ContextParamElement getContextParamElementByParamName(String name) {
        return (ContextParamElement) contextParams_.get(name);
    }

    public void addContextParamElement(ContextParamElement contextParam) {
        contextParams_.put(contextParam.getParamName(), contextParam);
    }

    public List getServletElements() {
        return new ArrayList(servlets_.values());
    }

    public ServletElement getServletElementByServletName(String servletName) {
        return (ServletElement) servlets_.get(servletName);
    }

    public void addServletElement(ServletElement servlet) {
        servlets_.put(servlet.getServletName(), servlet);
    }

    public List getFilterElements() {
        return new ArrayList(filters_.values());
    }

    public FilterElement getFilterElementByFilterName(String filterName) {
        return (FilterElement) filters_.get(filterName);
    }

    public void addFilterElement(FilterElement filterElement) {
        filters_.put(filterElement.getFilterName(), filterElement);
    }

    public List getTaglibElements() {
        return Collections.unmodifiableList(taglibs_);
    }

    public void addTaglibElement(TaglibElement taglibElement) {
        taglibs_.add(taglibElement);
    }

}
