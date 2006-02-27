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
import javax.faces.component.html.HtmlInputSecret;

import org.seasar.teeda.core.JsfConstants;


/**
 * @author yone
 */
public class InputSecretTag extends InputTagBase {

    private String redisplay_;
    
    public String getComponentType() {
        return HtmlInputSecret.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Secret";
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.ALT_ATTR, alt_);
        setComponentProperty(component, JsfConstants.REDISPLAY_ATTR, redisplay_);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange_);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect_);
        setComponentProperty(component, JsfConstants.SIZE_ATTR, size_);
    }
    
    public void release() {
        super.release();
        redisplay_ = null;
    }

    public void setRedisplay(String redisplay) {
        redisplay_ = redisplay;
    }
    
}
