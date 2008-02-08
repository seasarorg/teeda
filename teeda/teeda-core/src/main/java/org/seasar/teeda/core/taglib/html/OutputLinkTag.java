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
import javax.faces.component.html.HtmlOutputLink;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class OutputLinkTag extends UIComponentTagBase {

    public String getComponentType() {
        return HtmlOutputLink.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Link";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.CHARSET_ATTR, getCharset());
        setComponentProperty(component, JsfConstants.COORDS_ATTR, getCoords());
        setComponentProperty(component, JsfConstants.HREFLANG_ATTR,
                getHreflang());
        setComponentProperty(component, JsfConstants.REL_ATTR, getRel());
        setComponentProperty(component, JsfConstants.REV_ATTR, getRev());
        setComponentProperty(component, JsfConstants.SHAPE_ATTR, getShape());
        setComponentProperty(component, JsfConstants.TARGET_ATTR, getTarget());
        setComponentProperty(component, JsfConstants.TYPE_ATTR, getType());
    }

}
