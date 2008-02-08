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
import org.seasar.teeda.core.config.webapp.element.ServletMappingElement;
import org.seasar.teeda.core.config.webapp.element.TaglibElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;

/**
 * @author manhole
 * @author shot
 */
public class WebappConfigImpl implements WebappConfig, Serializable {

    private static final long serialVersionUID = 1L;

    private final Map contextParams = new HashMap();

    private final Map filters = new HashMap();

    private final Map servletsByName = new HashMap();

    private final Map servletsByClass = new HashMap();

    private final List taglibs = new ArrayList();

    private final Map servletMappings = new HashMap();

    public List getContextParamElements() {
        return new ArrayList(contextParams.values());
    }

    public ContextParamElement getContextParamElementByParamName(
            final String name) {
        return (ContextParamElement) contextParams.get(name);
    }

    public void addContextParamElement(final ContextParamElement contextParam) {
        contextParams.put(contextParam.getParamName(), contextParam);
    }

    public List getServletElements() {
        return new ArrayList(servletsByName.values());
    }

    public ServletElement getServletElementByServletName(
            final String servletName) {
        return (ServletElement) servletsByName.get(servletName);
    }

    public ServletElement getServletElementByServletClass(
            final String servletClass) {
        return (ServletElement) servletsByClass.get(servletClass);
    }

    public void addServletElement(final ServletElement servlet) {
        servletsByName.put(servlet.getServletName(), servlet);
        servletsByClass.put(servlet.getServletClass(), servlet);
    }

    public List getFilterElements() {
        return new ArrayList(filters.values());
    }

    public FilterElement getFilterElementByFilterName(final String filterName) {
        return (FilterElement) filters.get(filterName);
    }

    public void addFilterElement(final FilterElement filterElement) {
        filters.put(filterElement.getFilterName(), filterElement);
    }

    public List getTaglibElements() {
        return Collections.unmodifiableList(taglibs);
    }

    public void addTaglibElement(final TaglibElement taglibElement) {
        taglibs.add(taglibElement);
    }

    public List getServletMappingElements() {
        return new ArrayList(servletMappings.values());
    }

    public ServletMappingElement getServletMappingElementByServletName(
            final String servletName) {
        return (ServletMappingElement) servletMappings.get(servletName);
    }

    public void addServletMappingElement(
            final ServletMappingElement servletMappingElement) {
        servletMappings.put(servletMappingElement.getServletName(),
                servletMappingElement);
    }

    public ServletMappingElement getServletMappingElementByServletClass(
            final String servletClass) {
        final ServletElement servletElement = getServletElementByServletClass(servletClass);
        if (servletElement == null) {
            return null;
        }
        final ServletMappingElement servletMappingElement = getServletMappingElementByServletName(servletElement
                .getServletName());
        return servletMappingElement;
    }

}
