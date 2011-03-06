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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author manhole
 * @author shot
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class THtmlGridTh extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGridTh";

    private String styleClass;

    private String title;

    private String style;

    private String rowspan;

    public String getFamily() {
        return THtmlGrid.COMPONENT_FAMILY;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

    public Object saveState(FacesContext context) {
        Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = styleClass;
        values[2] = title;
        values[3] = style;
        values[4] = rowspan;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        styleClass = (String) values[1];
        title = (String) values[2];
        style = (String) values[3];
        rowspan = (String) values[4];
    }

    public String getRowspan() {
        if (rowspan != null) {
            return rowspan;
        }
        ValueBinding vb = getValueBinding("rowspan");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }

}
