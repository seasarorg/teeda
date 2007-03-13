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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author higa
 */
public class THtmlScript extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlScript";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlScript";

    public static final String DEFAULT_RENDERER_TYPE = COMPONENT_TYPE;

    private String type = "text/javascript";

    private String language = "JavaScript";

    private String src;

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getType() {
        if (type != null) {
            return type;
        }
        ValueBinding vb = getValueBinding("type");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        if (language != null) {
            return language;
        }
        ValueBinding vb = getValueBinding("language");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSrc() {
        if (src != null) {
            return src;
        }
        ValueBinding vb = getValueBinding("src");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = type;
        values[2] = language;
        values[3] = src;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        type = (String) values[1];
        language = (String) values[2];
        src = (String) values[3];
    }
}
