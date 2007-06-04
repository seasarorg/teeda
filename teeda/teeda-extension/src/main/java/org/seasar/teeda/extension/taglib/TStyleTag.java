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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlStyle;

/**
 * @author shot
 */
public class TStyleTag extends UIComponentTagBase {

    public TStyleTag() {
    }

    private String type;

    private String dir;

    private String lang;

    private String media;

    private String title;

    public String getComponentType() {
        return THtmlStyle.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlStyle.DEFAULT_RENDERER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.TYPE_ATTR, type);
        setComponentProperty(component, JsfConstants.DIR_ATTR, dir);
        setComponentProperty(component, JsfConstants.LANG_ATTR, lang);
        setComponentProperty(component, ExtensionConstants.MEDIA_ATTR, media);
        setComponentProperty(component, JsfConstants.TITLE_ATTR, title);
    }

    public void release() {
        super.release();
        type = null;
        dir = null;
        lang = null;
        media = null;
        title = null;
    }

    public String getDir() {
        return dir;
    }

    public String getLang() {
        return lang;
    }

    public String getMedia() {
        return media;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
    }

}
