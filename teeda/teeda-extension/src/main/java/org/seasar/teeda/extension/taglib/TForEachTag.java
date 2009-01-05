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
package org.seasar.teeda.extension.taglib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.component.TForEach;

/**
 * @author higa
 *  
 */
public class TForEachTag extends UIComponentTag {

    private String tagName;

    private String pageName;

    private String itemsName;

    private String omittag;

    private Map attributes = new HashMap();

    public TForEachTag() {
    }

    public String getComponentType() {
        return TForEach.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "org.seasar.teeda.extension.ForEach";
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getOmittag() {
        return omittag;
    }

    public void setOmittag(String omittag) {
        this.omittag = omittag;
    }

    public void addAttribute(String name, String value) {
        if (JsfConstants.ID_ATTR.equalsIgnoreCase(name)) {
            setId(value);
        } else {
            attributes.put(name, value);
        }
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "tagName", tagName);
        setComponentProperty(component, "pageName", pageName);
        setComponentProperty(component, "itemsName", itemsName);
        setComponentProperty(component, "omittag", omittag);

        TForEach forEach = (TForEach) component;
        for (Iterator i = attributes.keySet().iterator(); i.hasNext();) {
            String name = (String) i.next();
            String strValue = (String) attributes.get(name);
            if (BindingUtil.isValueReference(strValue)) {
                forEach.setValueBindingAttribute(name, strValue);
            } else {
                forEach.getAttributes().put(name, strValue);
            }
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        tagName = null;
        pageName = null;
        itemsName = null;
        omittag = null;
        attributes = null;
    }
}