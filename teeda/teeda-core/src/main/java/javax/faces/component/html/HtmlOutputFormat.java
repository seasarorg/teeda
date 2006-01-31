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
package javax.faces.component.html;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class HtmlOutputFormat extends UIOutput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputFormat";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Format";

    private static final boolean DEFAULT_ESCAPE = true;

    private Boolean escape_ = null;

    private String style_ = null;

    private String styleClass_ = null;

    private String title_ = null;

    public HtmlOutputFormat() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setEscape(boolean escape) {
        escape_ = Boolean.valueOf(escape);
    }

    public boolean isEscape() {
        if (escape_ != null) {
            return escape_.booleanValue();
        }
        ValueBinding vb = getValueBinding("escape");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_ESCAPE;
    }

    public void setStyle(String style) {
        style_ = style;
    }

    public String getStyle() {
        if (style_ != null) {
            return style_;
        }
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        styleClass_ = styleClass;
    }

    public String getStyleClass() {
        if (styleClass_ != null) {
            return styleClass_;
        }
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title) {
        title_ = title;
    }

    public String getTitle() {
        if (title_ != null) {
            return title_;
        }
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = escape_;
        values[2] = style_;
        values[3] = styleClass_;
        values[4] = title_;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        escape_ = (Boolean) values[1];
        style_ = (String) values[2];
        styleClass_ = (String) values[3];
        title_ = (String) values[4];
    }
}
