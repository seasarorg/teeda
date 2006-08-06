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
package org.seasar.teeda.extension.component;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * @author higa
 *  
 */
public class UIText extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Text";

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Text";

    private String value;

    public UIText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = value;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        value = (String) values[1];
    }
}