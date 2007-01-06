/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class SelectItemTag extends UIComponentTagBase {

    private static final String COMPONENT_TYPE = UISelectItem.COMPONENT_TYPE;

    protected String itemDescription;

    protected String itemDisabled;

    protected String itemLabel;

    protected String itemValue;

    public SelectItemTag() {
        super();
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemDisabled(String itemDisabled) {
        this.itemDisabled = itemDisabled;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemDisabled() {
        return itemDisabled;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.ITEM_DESCRIPTION_ATTR,
                itemDescription);
        setComponentProperty(component, JsfConstants.ITEM_DISABLED_ATTR,
                itemDisabled);
        setComponentProperty(component, JsfConstants.ITEM_LABEL_ATTR, itemLabel);
        setComponentProperty(component, JsfConstants.ITEM_VALUE_ATTR, itemValue);
    }

    public void release() {
        super.release();
        itemDescription = null;
        itemDisabled = null;
        itemLabel = null;
        itemValue = null;
    }

}
