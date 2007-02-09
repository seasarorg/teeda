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
package org.seasar.teeda.core.mock;

import java.util.List;

import org.seasar.teeda.core.config.webapp.element.ContextParamElement;
import org.seasar.teeda.core.config.webapp.element.FilterElement;
import org.seasar.teeda.core.config.webapp.element.ServletElement;
import org.seasar.teeda.core.config.webapp.element.ServletMappingElement;
import org.seasar.teeda.core.config.webapp.element.TaglibElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;

/**
 * @author manhole
 */
public class NullWebappConfig implements WebappConfig {

    public void addContextParamElement(
            final ContextParamElement contextParamElement) {
    }

    public void addFilterElement(final FilterElement filterElement) {
    }

    public void addServletElement(final ServletElement servletElement) {
    }

    public void addServletMappingElement(
            final ServletMappingElement servletMappingElement) {
    }

    public void addTaglibElement(final TaglibElement taglibElement) {
    }

    public ContextParamElement getContextParamElementByParamName(
            final String paramName) {
        return null;
    }

    public List getContextParamElements() {
        return null;
    }

    public FilterElement getFilterElementByFilterName(final String filterName) {
        return null;
    }

    public List getFilterElements() {
        return null;
    }

    public ServletMappingElement getServletMappingElementByServletName(
            final String servletName) {
        return null;
    }

    public ServletElement getServletElementByServletClass(
            final String servletClass) {
        return null;
    }

    public ServletElement getServletElementByServletName(
            final String servletName) {
        return null;
    }

    public List getServletElements() {
        return null;
    }

    public List getServletMappingElements() {
        return null;
    }

    public List getTaglibElements() {
        return null;
    }

    public ServletMappingElement getServletMappingElementByServletClass(
            final String servletClass) {
        return null;
    }

}
