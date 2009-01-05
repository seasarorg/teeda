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
import org.seasar.teeda.extension.component.TCondition;

/**
 * @author shot
 */
public class TConditionTag extends UIComponentTag {

    private String tagName;

    private String refresh;

    private String invisible;

    private String omittag;

    private Map attributes = new HashMap();

    public TConditionTag() {
    }

    public String getComponentType() {
        return TCondition.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "org.seasar.teeda.extension.Condition";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "tagName", tagName);
        setComponentProperty(component, "refresh", refresh);
        setComponentProperty(component, "invisible", invisible);
        setComponentProperty(component, "omittag", omittag);

        TCondition condition = (TCondition) component;
        for (Iterator i = attributes.keySet().iterator(); i.hasNext();) {
            String name = (String) i.next();
            String strValue = (String) attributes.get(name);
            if (BindingUtil.isValueReference(strValue)) {
                condition.setValueBindingAttribute(name, strValue);
            } else {
                condition.getAttributes().put(name, strValue);
            }
        }
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getInvisible() {
        return invisible;
    }

    public void setInvisible(String invisible) {
        this.invisible = invisible;
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

    public void release() {
        super.release();
        refresh = null;
        invisible = null;
        omittag = null;
        attributes = null;
    }

}