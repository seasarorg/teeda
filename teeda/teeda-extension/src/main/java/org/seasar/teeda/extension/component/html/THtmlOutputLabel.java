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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class THtmlOutputLabel extends HtmlOutputLabel {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Label";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Label";

    private String key;

    private String defaultKey;

    private String propertiesName;

    private String defaultPropertiesName;

    public THtmlOutputLabel() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getDefaultKey() {
        if (defaultKey != null) {
            return defaultKey;
        }
        ValueBinding vb = getValueBinding("defaultKey");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getDefaultPropertiesName() {
        if (defaultPropertiesName != null) {
            return defaultPropertiesName;
        }
        ValueBinding vb = getValueBinding("defaultPropertiesName");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getKey() {
        if (key != null) {
            return key;
        }
        ValueBinding vb = getValueBinding("key");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getPropertiesName() {
        if (propertiesName != null) {
            return propertiesName;
        }
        ValueBinding vb = getValueBinding("propertiesName");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    public void setDefaultPropertiesName(String defaultPropertiesName) {
        this.defaultPropertiesName = defaultPropertiesName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = key;
        values[2] = defaultKey;
        values[3] = propertiesName;
        values[4] = defaultPropertiesName;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        key = (String) values[1];
        defaultKey = (String) values[2];
        propertiesName = (String) values[3];
        defaultPropertiesName = (String) values[4];
    }

}
