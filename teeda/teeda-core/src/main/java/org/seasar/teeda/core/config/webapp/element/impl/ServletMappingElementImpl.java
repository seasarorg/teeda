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
package org.seasar.teeda.core.config.webapp.element.impl;

import java.io.Serializable;

import org.seasar.teeda.core.config.webapp.element.ServletMappingElement;

/**
 * @author shot
 */
public class ServletMappingElementImpl implements ServletMappingElement, Serializable {

    private static final long serialVersionUID = 1L;

    private String servletName_;

    private String urlPattern_;

    public ServletMappingElementImpl() {
    }

    public String getServletName() {
        return servletName_;
    }

    public void setServletName(String servletName) {
        servletName_ = servletName;
    }

    public String getUrlPattern() {
        return urlPattern_;
    }

    public void setUrlPattern(String urlPattern) {
        urlPattern_ = urlPattern;
    }

}
