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
import javax.faces.component.html.HtmlMessages;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class MessagesTag extends UIComponentTagBase {

    private String globalOnly_;

    private String showDetail_;

    private String showSummary_;

    private String errorClass_;

    private String errorStyle_;

    private String fatalClass_;

    private String fatalStyle_;

    private String infoClass_;

    private String infoStyle_;

    private String layout_;

    private String tooltip_;

    private String warnClass_;

    private String warnStyle_;

    public String getComponentType() {
        return HtmlMessages.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Messages";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.GLOBAL_ONLY_ATTR,
                globalOnly_);
        setComponentProperty(component, JsfConstants.SHOW_DETAIL_ATTR,
                showDetail_);
        setComponentProperty(component, JsfConstants.SHOW_SUMMARY_ATTR,
                showSummary_);
        setComponentProperty(component, JsfConstants.ERROR_CLASS_ATTR,
                errorClass_);
        setComponentProperty(component, JsfConstants.ERROR_STYLE_ATTR,
                errorStyle_);
        setComponentProperty(component, JsfConstants.FATAL_CLASS_ATTR,
                fatalClass_);
        setComponentProperty(component, JsfConstants.FATAL_STYLE_ATTR,
                fatalStyle_);
        setComponentProperty(component, JsfConstants.INFO_CLASS_ATTR,
                infoClass_);
        setComponentProperty(component, JsfConstants.INFO_STYLE_ATTR,
                infoStyle_);
        setComponentProperty(component, JsfConstants.LAYOUT_ATTR, layout_);
        setComponentProperty(component, JsfConstants.TOOLTIP_ATTR, tooltip_);
        setComponentProperty(component, JsfConstants.WARN_CLASS_ATTR,
                warnClass_);
        setComponentProperty(component, JsfConstants.WARN_STYLE_ATTR,
                warnStyle_);
    }

    public void release() {
        super.release();
        globalOnly_ = null;
        showDetail_ = null;
        showSummary_ = null;
        errorClass_ = null;
        errorStyle_ = null;
        fatalClass_ = null;
        fatalStyle_ = null;
        infoClass_ = null;
        infoStyle_ = null;
        layout_ = null;
        tooltip_ = null;
        warnClass_ = null;
        warnStyle_ = null;
    }

    public void setGlobalOnly(String globalOnly) {
        globalOnly_ = globalOnly;
    }

    public void setShowDetail(String showDetail) {
        showDetail_ = showDetail;
    }

    public void setShowSummary(String showSummary) {
        showSummary_ = showSummary;
    }

    public void setErrorClass(String errorClass) {
        errorClass_ = errorClass;
    }

    public void setErrorStyle(String errorStyle) {
        errorStyle_ = errorStyle;
    }

    public void setFatalClass(String fatalClass) {
        fatalClass_ = fatalClass;
    }

    public void setFatalStyle(String fatalStyle) {
        fatalStyle_ = fatalStyle;
    }

    public void setInfoClass(String infoClass) {
        infoClass_ = infoClass;
    }

    public void setInfoStyle(String infoStyle) {
        infoStyle_ = infoStyle;
    }

    public void setLayout(String layout) {
        layout_ = layout;
    }

    public void setTooltip(String tooltip) {
        tooltip_ = tooltip;
    }

    public void setWarnClass(String warnClass) {
        warnClass_ = warnClass;
    }

    public void setWarnStyle(String warnStyle) {
        warnStyle_ = warnStyle;
    }

    String getErrorClass() {
        return errorClass_;
    }

    String getErrorStyle() {
        return errorStyle_;
    }

    String getFatalClass() {
        return fatalClass_;
    }

    String getFatalStyle() {
        return fatalStyle_;
    }

    String getGlobalOnly() {
        return globalOnly_;
    }

    String getInfoClass() {
        return infoClass_;
    }

    String getInfoStyle() {
        return infoStyle_;
    }

    String getLayout() {
        return layout_;
    }

    String getShowDetail() {
        return showDetail_;
    }

    String getShowSummary() {
        return showSummary_;
    }

    String getTooltip() {
        return tooltip_;
    }

    String getWarnClass() {
        return warnClass_;
    }

    String getWarnStyle() {
        return warnStyle_;
    }

}
