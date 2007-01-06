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
package org.seasar.teeda.core.config.faces.impl;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.AbstractFacesConfigurator;

/**
 * @author shot
 */
public class CoreFacesConfigurator extends AbstractFacesConfigurator {

    private static final String BASE_FACES_CONFIG = "core-faces-config.xml";

    private String path_ = JsfConstants.CORE_PACKAGE_ROOT.replace('.', '/')
            + "/" + BASE_FACES_CONFIG;

    public CoreFacesConfigurator() {
        super();
    }

    protected String getPath() {
        return path_;
    }

    protected void setPath(String path) {
        path_ = path;
    }

}
