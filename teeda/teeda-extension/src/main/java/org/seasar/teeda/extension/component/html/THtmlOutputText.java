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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class THtmlOutputText extends HtmlOutputText {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlOutputText";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlOutputText";

    private String key;

    private String defaultKey;

    private String propertiesName;

    private String defaultPropertiesName;

    public THtmlOutputText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getDefaultKey() {
        if (defaultKey != null) {
            return defaultKey;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                ExtensionConstants.DEFAULT_KEY_ATTR);
    }

    public String getDefaultPropertiesName() {
        if (defaultPropertiesName != null) {
            return defaultPropertiesName;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                ExtensionConstants.DEFAULT_PROPERTIES_NAME_ATTR);
    }

    public String getKey() {
        if (key != null) {
            return key;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                ExtensionConstants.KEY_ATTR);
    }

    public String getPropertiesName() {
        if (propertiesName != null) {
            return propertiesName;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                ExtensionConstants.PROPERTIES_NAME_ATTR);
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
