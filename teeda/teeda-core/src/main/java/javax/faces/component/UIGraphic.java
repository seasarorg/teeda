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
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class UIGraphic extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Graphic";

    public static final String COMPONENT_TYPE = "javax.faces.Graphic";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Image";

    private static final String VALUE_BINDING_NAME = "value";

    private static final String URL_BINDING_NAME = "url";

    private Object value_ = null;

    private boolean valueSet_ = false;

    public UIGraphic() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getUrl() {
        return (String) getValue();
    }

    public void setUrl(String url) {
        setValue(url);
    }

    public Object getValue() {
        if (valueSet_) {
            return value_;
        }
        return ComponentUtils_.getValueBindingValue(this, VALUE_BINDING_NAME);
    }

    public void setValue(Object value) {
        value_ = value;
        valueSet_ = true;
    }

    public ValueBinding getValueBinding(String name) {
        return super.getValueBinding(convertAlias(name));
    }

    public void setValueBinding(String name, ValueBinding vb) {
        super.setValueBinding(convertAlias(name), vb);
    }

    private String convertAlias(String name) {
        if (URL_BINDING_NAME.equals(name)) {
            return VALUE_BINDING_NAME;
        }
        return name;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        value_ = values[1];
        valueSet_ = ((Boolean) values[2]).booleanValue();
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = value_;
        values[2] = (valueSet_) ? Boolean.TRUE : Boolean.FALSE;
        return values;
    }

}
