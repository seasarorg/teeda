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
 * @author shot
 */
public class CommandButtonTag extends UIComponentTagBase {

    private String immediate;

    private String actionListener;

    private String image;

    public String getComponentType() {
        return HtmlCommandButton.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Button";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.ALT_ATTR, getAlt());
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR,
                getOnchange());
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR,
                getOnselect());
        setComponentProperty(component, JsfConstants.IMMEDIATE_ATTR, immediate);
        setComponentProperty(component, JsfConstants.IMAGE_ATTR, image);
        setActionProperty(component, getAction());
        setActionListenerProperty(component, actionListener);
    }

    public void release() {
        super.release();
        immediate = null;
        actionListener = null;
        image = null;
    }

    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public String getActionListener() {
        return actionListener;
    }

    public String getImage() {
        return image;
    }

    public String getImmediate() {
        return immediate;
    }

}