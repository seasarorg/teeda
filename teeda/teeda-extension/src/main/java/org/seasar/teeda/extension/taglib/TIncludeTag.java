/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.component.TInclude;

/**
 * @author higa
 */
public class TIncludeTag extends UIComponentTagBase {

    private String src;

    /**
     * @return Returns the src.
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src The src to set.
     */
    public void setSrc(String src) {
        this.src = src;
    }

    public String getComponentType() {
        return TInclude.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return TInclude.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(final UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.SRC_ATTR, getSrc());
    }

    public void release() {
        super.release();
        src = null;
    }
}