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

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class UITitle extends UIOutput {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Title";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Title";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Title";

    private String lang;

    private String dir;

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

    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = dir;
        values[2] = lang;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        dir = (String) values[1];
        lang = (String) values[2];
    }

}
