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
package org.seasar.teeda.core.config.faces.element.impl;

import org.seasar.teeda.core.config.faces.element.RendererElement;

/**
 * @author shot
 */
public class RendererElementImpl implements RendererElement {

    private String family_;

    private String rendererType_;

    private String rendererClass_;

    public RendererElementImpl() {
    }

    public void setComponentFamily(String family) {
        family_ = family;
    }

    public void setRendererType(String rendererType) {
        rendererType_ = rendererType;
    }

    public void setRendererClass(String rendererClass) {
        rendererClass_ = rendererClass;
    }

    public String getComponentFamily() {
        return family_;
    }

    public String getRendererType() {
        return rendererType_;
    }

    public String getRendererClass() {
        return rendererClass_;
    }

}
