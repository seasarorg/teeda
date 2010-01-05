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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 * @author shot
 */
public class FormTag extends UIComponentTagBase {

    public String getComponentType() {
        return HtmlForm.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Form";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.ACCEPT_ATTR, getAccept());
        setComponentProperty(component, JsfConstants.ACCEPTCHARSET_ATTR,
                getAcceptcharset());
        setComponentProperty(component, JsfConstants.ENCTYPE_ATTR, getEnctype());
        setComponentProperty(component, JsfConstants.ONRESET_ATTR, getOnreset());
        setComponentProperty(component, JsfConstants.ONSUBMIT_ATTR,
                getOnsubmit());
        setComponentProperty(component, JsfConstants.TARGET_ATTR, getTarget());
    }

}
