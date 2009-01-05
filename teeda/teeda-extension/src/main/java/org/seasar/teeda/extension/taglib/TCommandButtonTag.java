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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.CommandButtonTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;

/**
 * @author shot
 */
public class TCommandButtonTag extends CommandButtonTag {

    private String renderJs = null;

    private String time = null;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void release() {
        super.release();
        renderJs = null;
        time = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, ExtensionConstants.RENDERJS_ATTR,
                renderJs);
        setComponentProperty(component, ExtensionConstants.TIME_ATTR, time);
    }

    public String getRenderJs() {
        return renderJs;
    }

    public void setRenderJs(String disabledJs) {
        this.renderJs = disabledJs;
    }

    public String getComponentType() {
        return THtmlCommandButton.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlCommandButton.DEFAULT_RENDERER_TYPE;
    }

}
