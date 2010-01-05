/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author manhole
 */
public class THtmlRadioItem extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Radio";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlRadioItem";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlRadioItem";

    private String name;

    private Object value;

    private Boolean disabled;

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        Object value = ComponentUtil_.getValueBindingValue(this,
                JsfConstants.NAME_ATTR);
        return (value != null) ? (String) value : null;

    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        return ComponentUtil_.getValueBindingValue(this, "value");
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isDisabled() {
        if (disabled != null) {
            return disabled.booleanValue();
        }
        final Boolean v = (Boolean) ComponentUtil_.getValueBindingValue(this,
                "disabled");
        return v != null ? v.booleanValue() : false;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = Boolean.valueOf(disabled);
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = name;
        values[2] = value;
        values[3] = disabled;
        return values;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        name = (String) values[1];
        value = values[2];
        disabled = (Boolean) values[3];
    }

}
