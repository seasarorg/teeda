/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class UITitle extends UIOutput {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Title";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Title";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Title";

    private String lang;

    private String dir;

    private String key;

    private String defaultKey;

    private String propertiesName;

    private String defaultPropertiesName;

    private String templateValue;

    public UITitle() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getDir() {
        return dir;
    }

    public String getLang() {
        return lang;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
        Object values[] = new Object[8];
        values[0] = super.saveState(context);
        values[1] = dir;
        values[2] = lang;
        values[3] = key;
        values[4] = defaultKey;
        values[5] = propertiesName;
        values[6] = defaultPropertiesName;
        values[7] = templateValue;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        dir = (String) values[1];
        lang = (String) values[2];
        key = (String) values[3];
        defaultKey = (String) values[4];
        propertiesName = (String) values[5];
        defaultPropertiesName = (String) values[6];
        templateValue = (String) values[7];
    }

    public String getTemplateValue() {
        if (templateValue != null) {
            return templateValue;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                ExtensionConstants.TEMPLATEVALUE_ATTR);
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

}
