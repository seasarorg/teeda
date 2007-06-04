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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.CommandButtonTag;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;

/**
 * @author shot
 */
public class TCommandButtonTag extends CommandButtonTag {

    private String disabledJs = null;
    
    public void release() {
        super.release();
        disabledJs = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "disabledJs", disabledJs);
    }

    public String getDisabledJs() {
        return disabledJs;
    }

    public void setDisabledJs(String disabledJs) {
        this.disabledJs = disabledJs;
    }

    public String getComponentType() {
        return THtmlCommandButton.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlCommandButton.DEFAULT_RENDERER_TYPE;
    }

}
