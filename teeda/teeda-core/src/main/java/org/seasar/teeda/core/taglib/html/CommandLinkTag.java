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
import javax.faces.component.html.HtmlCommandLink;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;


/**
 * @author yone
 */
public class CommandLinkTag extends UIComponentTagBase {
    
    private String accesskey_;
    
    private String charset_;
    
    private String coords_;
    
    private String hreflang_;
    
    private String rel_;
    
    private String rev_;
    
    private String shape_;
    
    private String tabindex_;
    
    private String type_;
    
    private String target_;
    
    private String onblur_;
    
    private String onfocus_;
    
    private String action_;
    
    private String immediate_;
    
    private String actionListener_;

    public String getComponentType() {
        return HtmlCommandLink.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Link";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        setActionProperty(component, action_);
        setActionListenerProperty(component, actionListener_);
        setComponentProperty(component, JsfConstants.IMMEDIATE_ATTR, immediate_);
        setComponentProperty(component, JsfConstants.ACCESSKEY_ATTR, accesskey_);
        setComponentProperty(component, JsfConstants.CHARSET_ATTR, charset_);
        setComponentProperty(component, JsfConstants.COORDS_ATTR, coords_);
        setComponentProperty(component, JsfConstants.HREFLANG_ATTR, hreflang_);
        setComponentProperty(component, JsfConstants.ONBLUR_ATTR, onblur_);
        setComponentProperty(component, JsfConstants.ONFOCUS_ATTR, onfocus_);
        setComponentProperty(component, JsfConstants.REL_ATTR, rel_);
        setComponentProperty(component, JsfConstants.REV_ATTR, rev_);
        setComponentProperty(component, JsfConstants.SHAPE_ATTR, shape_);
        setComponentProperty(component, JsfConstants.TABINDEX_ATTR, tabindex_);
        setComponentProperty(component, JsfConstants.TARGET_ATTR, target_);
        setComponentProperty(component, JsfConstants.TYPE_ATTR, type_);
    }
    
    public void release() {
        super.release();
        
        action_ = null;
        immediate_ = null;
        actionListener_ = null;
        accesskey_ = null;
        charset_ = null;
        coords_ = null;
        hreflang_ = null;
        rel_ = null;
        rev_ = null;
        shape_ = null;
        tabindex_ = null;
        type_ = null;
        target_ = null;
        onblur_ = null;
        onfocus_ = null;
    }

    public void setAccesskey(String accesskey) {
        accesskey_ = accesskey;
    }

    public void setAction(String action) {
        action_ = action;
    }

    public void setActionListener(String actionListener) {
        actionListener_ = actionListener;
    }

    public void setCharset(String charset) {
        charset_ = charset;
    }

    public void setCoords(String coords) {
        coords_ = coords;
    }

    public void setHreflang(String hreflang) {
        hreflang_ = hreflang;
    }

    public void setImmediate(String immediate) {
        immediate_ = immediate;
    }

    public void setOnblur(String onblur) {
        onblur_ = onblur;
    }

    public void setOnfocus(String onfocus) {
        onfocus_ = onfocus;
    }

    public void setRel(String rel) {
        rel_ = rel;
    }

    public void setRev(String rev) {
        rev_ = rev;
    }

    public void setShape(String shape) {
        shape_ = shape;
    }

    public void setTabindex(String tabindex) {
        tabindex_ = tabindex;
    }

    public void setTarget(String target) {
        target_ = target;
    }

    public void setType(String type) {
        type_ = type;
    }

}
