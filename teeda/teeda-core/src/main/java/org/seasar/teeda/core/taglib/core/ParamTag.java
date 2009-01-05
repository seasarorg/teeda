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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author shot
 * @author yone
 */
public class ParamTag extends UIComponentTagBase {

    private static final long serialVersionUID = 1L;

    private static final String COMPONENT_TYPE = UIParameter.COMPONENT_TYPE;

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (component instanceof UIParameter) {
            UIParameter parameter = (UIParameter) component;
            setComponentProperty(parameter, JsfConstants.NAME_ATTR, getName());
        }
    }
}
