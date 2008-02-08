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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.UIComponentTagBase;

/**
 * @author yone
 * @author shot
 */
public class PanelGridTag extends UIComponentTagBase {

    private String columnClasses;

    private String columns;

    private String footerClass;

    private String headerClass;

    private String rowClasses;

    public String getComponentType() {
        return HtmlPanelGrid.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "javax.faces.Grid";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.BORDER_ATTR, getBorder());
        setComponentProperty(component, JsfConstants.BGCOLOR_ATTR, getBgcolor());
        setComponentProperty(component, JsfConstants.CELLPADDING_ATTR,
                getCellpadding());
        setComponentProperty(component, JsfConstants.CELLSPACING_ATTR,
                getCellspacing());
        setComponentProperty(component, JsfConstants.COLUMN_CLASSES_ATTR,
                getColumnClasses());
        setComponentProperty(component, JsfConstants.COLUMNS_ATTR, getColumns());
        setComponentProperty(component, JsfConstants.FOOTER_CLASS_ATTR,
                getFooterClass());
        setComponentProperty(component, JsfConstants.FRAME_ATTR, getFrame());
        setComponentProperty(component, JsfConstants.HEADER_CLASS_ATTR,
                getHeaderClass());
        setComponentProperty(component, JsfConstants.ROW_CLASSES_ATTR,
                getRowClasses());
        setComponentProperty(component, JsfConstants.RULES_ATTR, getRules());
        setComponentProperty(component, JsfConstants.SUMMARY_ATTR, getSummary());
        setComponentProperty(component, JsfConstants.WIDTH_ATTR, getWidth());
    }

    public void release() {
        super.release();
        columnClasses = null;
        columns = null;
        footerClass = null;
        headerClass = null;
        rowClasses = null;
    }

    public void setColumnClasses(String columnClasses) {
        this.columnClasses = columnClasses;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public void setFooterClass(String footerClass) {
        this.footerClass = footerClass;
    }

    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    public void setRowClasses(String rowClasses) {
        this.rowClasses = rowClasses;
    }

    public String getColumnClasses() {
        return columnClasses;
    }

    public String getColumns() {
        return columns;
    }

    public String getFooterClass() {
        return footerClass;
    }

    public String getHeaderClass() {
        return headerClass;
    }

    public String getRowClasses() {
        return rowClasses;
    }

}
