/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.webapp.element;

import java.util.List;

/**
 * @author manhole
 * @author shot
 */
public interface WebappConfig {

    List getContextParamElements();

    ContextParamElement getContextParamElementByParamName(String paramName);

    void addContextParamElement(ContextParamElement contextParamElement);

    List getServletElements();

    ServletElement getServletElementByServletName(String servletName);

    ServletElement getServletElementByServletClass(String servletClass);

    void addServletElement(ServletElement servletElement);

    List getFilterElements();

    FilterElement getFilterElementByFilterName(String filterName);

    void addFilterElement(FilterElement filterElement);

    List getTaglibElements();

    void addTaglibElement(TaglibElement taglibElement);

    List getServletMappingElements();

    ServletMappingElement getServletMappingElementByServletName(
            String servletName);

    void addServletMappingElement(ServletMappingElement servletMappingElement);

    ServletMappingElement getServletMappingElementByServletClass(
            String servletClass);

}
