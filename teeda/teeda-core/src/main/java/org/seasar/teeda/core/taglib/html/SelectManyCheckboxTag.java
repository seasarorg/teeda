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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectManyCheckbox;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author yone
 * @author shot
 */
public class SelectManyCheckboxTag extends InputTagBase {

    private String layout;

    public String getComponentType() {
        return HtmlSelectManyCheckbox.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Checkbox";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.BORDER_ATTR, getBorder());
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR,
                getOnchange());
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR,
                getOnselect());
        setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
                getDisabledClass());
        setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR,
                getEnabledClass());
        setComponentProperty(component, JsfConstants.LAYOUT_ATTR, getLayout());
    }

    public void release() {
        super.release();
        layout = null;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayout() {
        return layout;
    }

}
