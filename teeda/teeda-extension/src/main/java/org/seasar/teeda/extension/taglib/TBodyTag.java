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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagPropertyUtil;
import org.seasar.teeda.extension.component.UIBody;

/**
 * @author shot
 */
public class TBodyTag extends UIComponentTag {

    private String styleClass;

    private String lang;

    private String dir;

    private String title;

    private String style;

    private String bgcolor;

    private String onload;

    private String onunload;

    private String onclick;

    private String ondblclick;

    private String onmousedown;

    private String onmouseup;

    private String onmouseover;

    private String onmousemove;

    private String onmouseout;

    private String onkeypress;

    private String onkeydown;

    private String onkeyup;

    public TBodyTag() {
    }

    public String getComponentType() {
        return UIBody.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return UIBody.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        styleClass = null;
        lang = null;
        dir = null;
        title = null;
        style = null;
        bgcolor = null;
        onload = null;
        onunload = null;
        onclick = null;
        ondblclick = null;
        onmousedown = null;
        onmouseup = null;
        onmouseover = null;
        onmousemove = null;
        onmouseout = null;
        onkeypress = null;
        onkeydown = null;
        onkeyup = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.STYLE_CLASS_ATTR, styleClass);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.LANG_ATTR, lang);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.DIR_ATTR, dir);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.TITLE_ATTR, title);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.STYLE_ATTR, style);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.BGCOLOR_ATTR, bgcolor);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONLOAD_ATTR, onload);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONUNLOAD_ATTR, onunload);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONCLICK_ATTR, onclick);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONDBLCLICK_ATTR, ondblclick);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONMOUSEDOWN_ATTR, onmousedown);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONMOUSEUP_ATTR, onmouseup);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONMOUSEOVER_ATTR, onmouseover);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONMOUSEMOVE_ATTR, onmousemove);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONMOUSEOUT_ATTR, onmouseout);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONKEYPRESS_ATTR, onkeypress);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONKEYDOWN_ATTR, onkeydown);
        UIComponentTagPropertyUtil.setComponentProperty(component,
                JsfConstants.ONKEYUP_ATTR, onkeyup);
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public String getDir() {
        return dir;
    }

    public String getLang() {
        return lang;
    }

    public String getOnclick() {
        return onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public String getOnload() {
        return onload;
    }

    public String getOnmousedown() {
        return onmousedown;
    }

    public String getOnmousemove() {
        return onmousemove;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public String getOnmouseup() {
        return onmouseup;
    }

    public String getOnunload() {
        return onunload;
    }

    public String getStyle() {
        return style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getTitle() {
        return title;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public void setOnload(String onload) {
        this.onload = onload;
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

    public void setOnunload(String onunload) {
        this.onunload = onunload;
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

}
