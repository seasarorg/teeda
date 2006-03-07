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
import javax.faces.component.html.HtmlCommandButton;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class CommandButtonTag extends UIComponentTagBase {

    private String immediate_;

    private String actionListener_;

    private String image_;

    public String getComponentType() {
        return HtmlCommandButton.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Button";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.ALT_ATTR, alt_);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange_);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect_);

        setComponentProperty(component, JsfConstants.IMMEDIATE_ATTR, immediate_);
        setComponentProperty(component, JsfConstants.IMAGE_ATTR, image_);
        setActionProperty(component, action_);
        setActionListenerProperty(component, actionListener_);
    }

    public void release() {
        super.release();

        action_ = null;
        immediate_ = null;
        actionListener_ = null;
        image_ = null;
    }

    public void setActionListener(String actionListener) {
        actionListener_ = actionListener;
    }

    public void setImage(String image) {
        image_ = image;
    }

    public void setImmediate(String immediate) {
        immediate_ = immediate;
    }

}