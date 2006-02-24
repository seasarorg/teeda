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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public abstract class HtmlComponentTagBase extends UIComponentTagBase {

    private String dir;

    private String lang;

    private String style;

    private String styleClass;

    private String title;

    private String onclick;

    private String ondblclick;

    private String onkeydown;

    private String onkeypress;

    private String onkeyup;

    private String onmousedown;

    private String onmousemove;

    private String onmouseout;

    private String onmouseover;

    private String onmouseup;

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.DIR_ATTR, dir);
        setComponentProperty(component, JsfConstants.LANG_ATTR, lang);
        setComponentProperty(component, JsfConstants.STYLE_ATTR, style);
        setComponentProperty(component, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
        setComponentProperty(component, JsfConstants.TITLE_ATTR, title);
        setComponentProperty(component, JsfConstants.ONCLICK_ATTR, onclick);
        setComponentProperty(component, JsfConstants.ONDBLCLICK_ATTR,
                ondblclick);
        setComponentProperty(component, JsfConstants.ONKEYDOWN_ATTR, onkeydown);
        setComponentProperty(component, JsfConstants.ONKEYPRESS_ATTR,
                onkeypress);
        setComponentProperty(component, JsfConstants.ONKEYUP_ATTR, onkeyup);
        setComponentProperty(component, JsfConstants.ONMOUSEDOWN_ATTR,
                onmousedown);
        setComponentProperty(component, JsfConstants.ONMOUSEMOVE_ATTR,
                onmousemove);
        setComponentProperty(component, JsfConstants.ONMOUSEOUT_ATTR,
                onmouseout);
        setComponentProperty(component, JsfConstants.ONMOUSEOVER_ATTR,
                onmouseover);
        setComponentProperty(component, JsfConstants.ONMOUSEUP_ATTR, onmouseup);
    }
    
    public void release() {
        super.release();
        dir = null;
        lang = null;
        style = null;
        styleClass = null;
        title = null;
        onclick = null;
        ondblclick = null;
        onkeydown = null;
        onkeypress = null;
        onkeyup = null;
        onmousedown = null;
        onmousemove = null;
        onmouseout = null;
        onmouseover = null;
        onmouseup = null;
    }

}
