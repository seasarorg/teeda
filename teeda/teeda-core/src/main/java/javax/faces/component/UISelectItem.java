/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package javax.faces.component;

import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class UISelectItem extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectItem";

    public static final String COMPONENT_TYPE = "javax.faces.SelectItem";

    private static final String ITEM_DESCRIPTION_BINDING_NAME = "itemDescription";

    private static final String ITEM_DISABLED_BINDING_NAME = "itemDisabled";

    private static final String ITEM_LABEL_BINDING_NAME = "itemLabel";

    private static final String ITEM_VALUE_BINDING_NAME = "itemValue";

    private static final String VALUE_BINDING_NAME = "value";

    private String itemDescription = null;

    private String itemLabel = null;

    private Object itemValue = null;

    private Object value = null;

    private boolean itemDisabled = false;

    private boolean itemDisabledSet = false;

    public UISelectItem() {
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getItemDescription() {
        if (itemDescription != null) {
            return itemDescription;
        }
        return (String) ComponentUtil_.getValueBindingValue(this,
                ITEM_DESCRIPTION_BINDING_NAME);
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isItemDisabled() {
        if (itemDisabledSet) {
            return itemDisabled;
        }
        Object value = ComponentUtil_.getValueBindingValue(this,
                ITEM_DISABLED_BINDING_NAME);
        return (value != null) ? Boolean.TRUE.equals(value) : itemDisabled;
    }

    public void setItemDisabled(boolean itemDisabled) {
        this.itemDisabled = itemDisabled;
        itemDisabledSet = true;
    }

    public String getItemLabel() {
        if (itemLabel != null) {
            return itemLabel;
        }
        return (String) ComponentUtil_.getValueBindingValue(this,
                ITEM_LABEL_BINDING_NAME);
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public Object getItemValue() {
        if (itemValue != null) {
            return itemValue;
        }
        return ComponentUtil_.getValueBindingValue(this,
                ITEM_VALUE_BINDING_NAME);
    }

    public void setItemValue(Object itemValue) {
        this.itemValue = itemValue;
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        return ComponentUtil_.getValueBindingValue(this, VALUE_BINDING_NAME);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        itemDescription = (String) values[1];
        itemDisabled = ((Boolean) values[2]).booleanValue();
        itemDisabledSet = ((Boolean) values[3]).booleanValue();
        itemLabel = (String) values[4];
        itemValue = values[5];
        value = values[6];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[7];
        values[0] = super.saveState(context);
        values[1] = itemDescription;
        values[2] = ComponentUtil_.convertToBoolean(itemDisabled);
        values[3] = ComponentUtil_.convertToBoolean(itemDisabledSet);
        values[4] = itemLabel;
        values[5] = itemValue;
        values[6] = value;
        return values;
    }

}
