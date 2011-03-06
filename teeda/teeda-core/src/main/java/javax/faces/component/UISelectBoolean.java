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

import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class UISelectBoolean extends UIInput {

    public static final String COMPONENT_TYPE = "javax.faces.SelectBoolean";

    public static final String COMPONENT_FAMILY = "javax.faces.SelectBoolean";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Checkbox";

    public UISelectBoolean() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public boolean isSelected() {
        Boolean value = (Boolean) getValue();
        if (value != null) {
            return value.booleanValue();
        } else {
            return false;
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setValue(Boolean.TRUE);
        } else {
            setValue(Boolean.FALSE);
        }
    }

    public ValueBinding getValueBinding(String name) {
        return super.getValueBinding(convertAlias(name));
    }

    public void setValueBinding(String name, ValueBinding vb) {
        super.setValueBinding(convertAlias(name), vb);
    }

    private String convertAlias(String name) {
        if ("selected".equals(name)) {
            return "value";
        }
        return name;
    }

}
