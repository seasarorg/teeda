/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

/**
 * @author higa
 * @author shot
 */
public abstract class TInputTagBase extends TComponentTagBase {

    private String accesskey;

    private String alt;

    private String datafld;

    private String dataformatas;

    private String datasrc;

    private String disabled;

    private String escape;

    private String label;

    private String name;

    private String onblur;

    private String onchange;

    private String onfocus;

    private String onselect;

    private String readonly;

    private String required;

    private String size;

    private String tabindex;

    private String validator;

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setDatafld(String datafld) {
        this.datafld = datafld;
    }

    public void setDataformatas(String dataformatas) {
        this.dataformatas = dataformatas;
    }

    public void setDatasrc(String datasrc) {
        this.datasrc = datasrc;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setEscape(String escape) {
        this.escape = escape;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public void release() {
        super.release();
        accesskey = null;
        alt = null;
        datafld = null;
        dataformatas = null;
        datasrc = null;
        disabled = null;
        escape = null;
        label = null;
        name = null;
        readonly = null;
        required = null;
        size = null;
        tabindex = null;
        validator = null;
        onblur = null;
        onchange = null;
        onfocus = null;
        onselect = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.ACCESSKEY_ATTR, accesskey);
        setComponentProperty(component, JsfConstants.ALT_ATTR, alt);
        setComponentProperty(component, JsfConstants.DATAFLD_ATTR, datafld);
        setComponentProperty(component, JsfConstants.DATAFORMATAS_ATTR,
                dataformatas);
        setComponentProperty(component, JsfConstants.DATASRC_ATTR, datasrc);
        setComponentProperty(component, JsfConstants.DISABLED_ATTR, disabled);
        setComponentProperty(component, JsfConstants.ESCAPE_ATTR, escape);
        setComponentProperty(component, JsfConstants.LABEL_ATTR, label);
        setComponentProperty(component, JsfConstants.NAME_ATTR, name);
        setComponentProperty(component, JsfConstants.READONLY_ATTR, readonly);
        setComponentProperty(component, JsfConstants.REQUIRED_ATTR, required);
        setComponentProperty(component, JsfConstants.SIZE_ATTR, size);
        setComponentProperty(component, JsfConstants.TABINDEX_ATTR, tabindex);
        setValidatorProperty(component, validator);
        setComponentProperty(component, JsfConstants.ONBLUR_ATTR, onblur);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange);
        setComponentProperty(component, JsfConstants.ONFOCUS_ATTR, onfocus);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect);
    }
}