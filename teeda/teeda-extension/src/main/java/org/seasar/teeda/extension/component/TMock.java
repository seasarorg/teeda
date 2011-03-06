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
package org.seasar.teeda.extension.component;

import javax.faces.component.UIComponentBase;

/**
 * @author higa
 */
public class TMock extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Mock";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Mock";

    public static final String DEFAULT_RENDERER_TYPE = null;

    public TMock() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getRendererType() {
        return DEFAULT_RENDERER_TYPE;
    }
}