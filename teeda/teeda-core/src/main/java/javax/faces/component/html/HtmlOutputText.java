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
package javax.faces.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlOutputText extends UIOutput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputText";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Text";

    private static final boolean DEFAULT_ESCAPE = true;

    private Boolean escape = null;

    private String style = null;

    private String styleClass = null;

    private String title = null;

    public HtmlOutputText() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setEscape(boolean escape) {
        this.escape = Boolean.valueOf(escape);
    }

    public boolean isEscape() {
        if (escape != null) {
            return escape.booleanValue();
        }
        ValueBinding vb = getValueBinding("escape");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_ESCAPE;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_ATTR);
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_CLASS_ATTR);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TITLE_ATTR);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = escape;
        values[2] = style;
        values[3] = styleClass;
        values[4] = title;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        escape = (Boolean) values[1];
        style = (String) values[2];
        styleClass = (String) values[3];
        title = (String) values[4];
    }
}
