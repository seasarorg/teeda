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
package org.seasar.teeda.extension.taglib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.component.html.THtmlElement;

/**
 * @author higa
 *  
 */
public class TElementTag extends UIComponentTag {

    private String tagName;

    private Map attributes = new HashMap();

    public TElementTag() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void addAttribute(String name, String value) {
        if (JsfConstants.ID_ATTR.equalsIgnoreCase(name)) {
            setId(value);
        } else {
            attributes.put(name, value);
        }
    }

    public String getComponentType() {
        return THtmlElement.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlElement.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        THtmlElement elem = (THtmlElement) component;
        elem.setTagName(tagName);
        elem.setIdSet(getId() != null);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(THtmlElement.class);
        for (Iterator i = attributes.keySet().iterator(); i.hasNext();) {
            String name = (String) i.next();
            String strValue = (String) attributes.get(name);
            if (BindingUtil.isValueReference(strValue)) {
                elem.setValueBindingAttribute(name, strValue);
            } else if (beanDesc.hasPropertyDesc(name)) {
                PropertyDesc pd = beanDesc.getPropertyDesc(name);
                elem.getAttributes().put(name, pd.convertIfNeed(strValue));
            } else {
                elem.getAttributes().put(name, strValue);
            }
        }
    }

    public void release() {
        super.release();
        tagName = null;
        attributes.clear();
    }
}