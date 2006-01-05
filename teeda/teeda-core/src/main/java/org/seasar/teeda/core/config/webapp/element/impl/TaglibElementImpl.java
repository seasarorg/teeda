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

import org.seasar.teeda.core.config.webapp.element.TaglibElement;

/**
 * @author manhole
 */
public class TaglibElementImpl implements TaglibElement, Serializable {

    private static final long serialVersionUID = 1L;
    private String taglibUri_;
    private String taglibLocation_;

    public String getTaglibLocation() {
        return taglibLocation_;
    }

    public void setTaglibLocation(String taglibLocation) {
        taglibLocation_ = taglibLocation;
    }

    public String getTaglibUri() {
        return taglibUri_;
    }

    public void setTaglibUri(String taglibUri) {
        taglibUri_ = taglibUri;
    }

}
