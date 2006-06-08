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
import javax.faces.component.html.HtmlMessage;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class MessageTag extends UIComponentTagBase {

    private String forStr;

    private String showDetail;

    private String showSummary;

    private String errorClass;

    private String errorStyle;

    private String fatalClass;

    private String fatalStyle;

    private String infoClass;

    private String infoStyle;

    private String tooltip;

    private String warnClass;

    private String warnStyle;

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getErrorStyle() {
        return errorStyle;
    }

    public void setErrorStyle(String errorStyle) {
        this.errorStyle = errorStyle;
    }

    public String getFatalClass() {
        return fatalClass;
    }

    public void setFatalClass(String fatalClass) {
        this.fatalClass = fatalClass;
    }

    public String getFatalStyle() {
        return fatalStyle;
    }

    public void setFatalStyle(String fatalStyle) {
        this.fatalStyle = fatalStyle;
    }

    public String getFor() {
        return forStr;
    }

    public void setFor(String forStr) {
        this.forStr = forStr;
    }

    public String getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(String infoClass) {
        this.infoClass = infoClass;
    }

    public String getInfoStyle() {
        return infoStyle;
    }

    public void setInfoStyle(String infoStyle) {
        this.infoStyle = infoStyle;
    }

    public String getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(String showDetail) {
        this.showDetail = showDetail;
    }

    public String getShowSummary() {
        return showSummary;
    }

    public void setShowSummary(String showSummary) {
        this.showSummary = showSummary;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getWarnClass() {
        return warnClass;
    }

    public void setWarnClass(String warnClass) {
        this.warnClass = warnClass;
    }

    public String getWarnStyle() {
        return warnStyle;
    }

    public void setWarnStyle(String warnStyle) {
        this.warnStyle = warnStyle;
    }

    public String getComponentType() {
        return HtmlMessage.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Message";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.FOR_ATTR, forStr);
        setComponentProperty(component, JsfConstants.SHOW_DETAIL_ATTR,
                showDetail);
        setComponentProperty(component, JsfConstants.SHOW_SUMMARY_ATTR,
                showSummary);
        setComponentProperty(component, JsfConstants.ERROR_CLASS_ATTR,
                errorClass);
        setComponentProperty(component, JsfConstants.ERROR_STYLE_ATTR,
                errorStyle);
        setComponentProperty(component, JsfConstants.FATAL_CLASS_ATTR,
                fatalClass);
        setComponentProperty(component, JsfConstants.FATAL_STYLE_ATTR,
                fatalStyle);
        setComponentProperty(component, JsfConstants.INFO_CLASS_ATTR, infoClass);
        setComponentProperty(component, JsfConstants.INFO_STYLE_ATTR, infoStyle);
        setComponentProperty(component, JsfConstants.TOOLTIP_ATTR, tooltip);
        setComponentProperty(component, JsfConstants.WARN_CLASS_ATTR, warnClass);
        setComponentProperty(component, JsfConstants.WARN_STYLE_ATTR, warnStyle);
    }

    public void release() {
        super.release();
        forStr = null;
        showDetail = null;
        showSummary = null;
        errorClass = null;
        errorStyle = null;
        fatalClass = null;
        fatalStyle = null;
        infoClass = null;
        infoStyle = null;
        tooltip = null;
        warnClass = null;
        warnStyle = null;
    }

}