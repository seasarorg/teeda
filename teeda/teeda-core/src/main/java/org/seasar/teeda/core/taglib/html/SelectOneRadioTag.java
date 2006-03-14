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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectOneRadio;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author yone
 */
public class SelectOneRadioTag extends InputTagBase {

    private String layout_;

    public String getComponentType() {
        return HtmlSelectOneRadio.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Radio";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.BORDER_ATTR, border_);
        setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
                disabledClass_);
        setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR,
                enabledClass_);
        setComponentProperty(component, JsfConstants.LAYOUT_ATTR, layout_);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange_);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect_);
    }

    public void release() {
        super.release();
        layout_ = null;
    }

    public void setLayout(String layout) {
        layout_ = layout;
    }

    String getLayout() {
        return layout_;
    }

}
