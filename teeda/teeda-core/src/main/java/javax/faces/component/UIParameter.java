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
package javax.faces.component;

import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class UIParameter extends UIComponentBase {

    public static final String COMPONENT_TYPE = "javax.faces.Parameter";

    public static final String COMPONENT_FAMILY = "javax.faces.Parameter";

    private String name_ = null;

    private Object value_ = null;

    private static final String NAME_BINDING_NAME = "name";

    private static final String VALUE_BINDING_NAME = "value";

    public UIParameter() {
        super();
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getName() {
        if (name_ != null) {
            return name_;
        }
        return (String) ComponentUtils_.getValueBindingValue(this,
                NAME_BINDING_NAME);
    }

    public void setName(String name) {
        name_ = name;
    }

    public Object getValue() {
        if (value_ != null) {
            return value_;
        }
        return ComponentUtils_.getValueBindingValue(this, VALUE_BINDING_NAME);
    }

    public void setValue(Object value) {
        value_ = value;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        name_ = (String) values[1];
        value_ = values[2];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = name_;
        values[2] = value_;
        return values;
    }
}
