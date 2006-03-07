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
import javax.faces.component.html.HtmlDataTable;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 */
public class DataTableTag extends UIComponentTagBase {

    private String first_;

    private String var_;

    private String columnClasses_;

    private String footerClass_;

    private String headerClass_;

    private String rowClasses_;

    public String getComponentType() {
        return HtmlDataTable.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Table";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setComponentProperty(component, JsfConstants.FIRST_ATTR, first_);
        setComponentProperty(component, JsfConstants.ROWS_ATTR, rows_);
        setComponentProperty(component, JsfConstants.VAR_ATTR, var_);
        setComponentProperty(component, JsfConstants.BGCOLOR_ATTR, bgcolor_);
        setComponentProperty(component, JsfConstants.BORDER_ATTR, border_);
        setComponentProperty(component, JsfConstants.CELLPADDING_ATTR,
                cellpadding_);
        setComponentProperty(component, JsfConstants.CELLSPACING_ATTR,
                cellspacing_);
        setComponentProperty(component, JsfConstants.COLUMN_CLASSES_ATTR,
                columnClasses_);
        setComponentProperty(component, JsfConstants.FOOTER_CLASS_ATTR,
                footerClass_);
        setComponentProperty(component, JsfConstants.FRAME_ATTR, frame_);
        setComponentProperty(component, JsfConstants.HEADER_CLASS_ATTR,
                headerClass_);
        setComponentProperty(component, JsfConstants.ROW_CLASSES_ATTR,
                rowClasses_);
        setComponentProperty(component, JsfConstants.RULES_ATTR, rules_);
        setComponentProperty(component, JsfConstants.SUMMARY_ATTR, summary_);
        setComponentProperty(component, JsfConstants.WIDTH_ATTR, width_);
    }

    public void release() {
        super.release();
        first_ = null;
        var_ = null;
        columnClasses_ = null;
        footerClass_ = null;
        headerClass_ = null;
        rowClasses_ = null;
    }

    public void setFirst(String first) {
        first_ = first;
    }

    public void setVar(String var) {
        var_ = var;
    }

    public void setColumnClasses(String columnClasses) {
        columnClasses_ = columnClasses;
    }

    public void setFooterClass(String footerClass) {
        footerClass_ = footerClass;
    }

    public void setHeaderClass(String headerClass) {
        headerClass_ = headerClass;
    }

    public void setRowClasses(String rowClasses) {
        rowClasses_ = rowClasses;
    }

}
