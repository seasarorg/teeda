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
package org.seasar.teeda.core.config.webapp.element;

import java.util.List;

/**
 * @author manhole
 * @author shot
 */
public interface WebappConfig {

    public List getContextParamElements();

    public ContextParamElement getContextParamElementByParamName(
            String paramName);

    public void addContextParamElement(ContextParamElement contextParamElement);

    public List getServletElements();

    public ServletElement getServletElementByServletName(String servletName);

    public void addServletElement(ServletElement servletElement);

    public List getFilterElements();

    public FilterElement getFilterElementByFilterName(String filterName);

    public void addFilterElement(FilterElement filterElement);

    public List getTaglibElements();

    public void addTaglibElement(TaglibElement taglibElement);

    public List getServletMappingElement();

    public ServletMappingElement getServetMappingElementByServletName(
            String servletName);

    public void addServletMappingElement(
            ServletMappingElement servletMappingElement);

}
