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

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.exception.NoValueReferenceRuntimeException;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.component.TUISelectItems;

/**
 * @author higa
 * @author shot
 */
public abstract class TSelectTagBase extends TInputTagBase {

    private String border;

    private String disabledClass;

    private String enabledClass;

    private String items;

    private String itemLabel;

    private String itemValue;

    private String layout;

    public void setBorder(String border) {
        this.border = border;
    }

    public void setDisabledClass(String disabledClass) {
        this.disabledClass = disabledClass;
    }

    public void setEnabledClass(String enabledClass) {
        this.enabledClass = enabledClass;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void release() {
        super.release();
        border = null;
        disabledClass = null;
        enabledClass = null;
        items = null;
        itemLabel = null;
        itemValue = null;
        layout = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.BORDER_ATTR, border);
        setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
                disabledClass);
        setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR,
                enabledClass);
        setComponentProperty(component, JsfConstants.LAYOUT_ATTR, layout);
        if (items != null) {
            if (!isValueReference(items)) {
                throw new NoValueReferenceRuntimeException(items);
            }
            TUISelectItems child = new TUISelectItems();
            BindingUtil.setValueBinding(child, JsfConstants.VALUE_ATTR, items);
            if (itemValue != null) {
                child.setItemValue(itemValue);
            }
            if (itemLabel != null) {
                child.setItemLabel(itemLabel);
            }
            child.setNullLabelRequired(isNullLabelRequired());
            component.getChildren().add(child);
        }
    }

    protected boolean isNullLabelRequired() {
        return false;
    }
}