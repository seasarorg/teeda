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
package org.seasar.teeda.extension.taglib;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.ResourceBundleUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.OutputLabelTag;

/**
 * @author shot
 */
public class TOutputLabelTag extends OutputLabelTag {

    private String key;

    private String propertiesName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (key != null && propertiesName != null) {
            FacesContext context = getFacesContext();
            ViewHandler viewHandler = context.getApplication().getViewHandler();
            Locale locale = viewHandler.calculateLocale(context);
            ResourceBundle bundle = ResourceBundleUtil.getBundle(
                    propertiesName, locale);
            String value = bundle.getString(key);
            setComponentProperty(component, JsfConstants.VALUE_ATTR, value);
        }
    }

}
