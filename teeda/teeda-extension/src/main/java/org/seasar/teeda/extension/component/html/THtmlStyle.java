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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class THtmlStyle extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlStyle";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlStyle";

    public static final String DEFAULT_RENDERER_TYPE = COMPONENT_TYPE;

    private String type;

    private String dir;

    private String lang;

    private String media;

    private String title;

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

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getMedia() {
        if (media != null) {
            return media;
        }
        ValueBinding vb = getValueBinding("media");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = super.saveState(context);
        values[1] = type;
        values[2] = dir;
        values[3] = lang;
        values[4] = media;
        values[5] = title;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        type = (String) values[1];
        dir = (String) values[2];
        lang = (String) values[3];
        media = (String) values[4];
        title = (String) values[5];
    }
}
