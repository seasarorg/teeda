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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectOneListbox;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author yone
 * @author shot
 */
public class SelectOneListboxTag extends InputTagBase {

    public String getComponentType() {
        return HtmlSelectOneListbox.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Listbox";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
                getDisabledClass());
        setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR,
                getEnabledClass());
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR,
                getOnchange());
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR,
                getOnselect());
        setComponentProperty(component, JsfConstants.SIZE_ATTR, getSize());
    }

}
