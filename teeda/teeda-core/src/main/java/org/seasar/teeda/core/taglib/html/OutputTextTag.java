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
import javax.faces.component.html.HtmlOutputText;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;


/**
 * @author yone
 */
public class OutputTextTag extends UIComponentTagBase {

    private String escape_;
    
    public String getComponentType() {
        return HtmlOutputText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Text";
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        setComponentProperty(component, JsfConstants.ESCAPE_ATTR, escape_);        
    }
    
    public void setEscape(String escape) {
        escape_ = escape;
    }
    
    public void release() {
        super.release();
        escape_ = null;
    }
}
