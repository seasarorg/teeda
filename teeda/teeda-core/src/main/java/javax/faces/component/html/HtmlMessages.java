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
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlMessages extends UIMessages {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlMessages";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Messages";

    private static final String DEFAULT_LAYOUT = "list";

    private static final boolean DEFAULT_TOOLTIP = false;

    private String errorClass = null;

    private String errorStyle = null;

    private String fatalClass = null;

    private String fatalStyle = null;

    private String infoClass = null;

    private String infoStyle = null;

    private String layout = null;

    private String style = null;

    private String styleClass = null;

    private String title = null;

    private Boolean tooltip = null;

    private String warnClass = null;

    private String warnStyle = null;

    public HtmlMessages() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getErrorClass() {
        if (errorClass != null) {
            return errorClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ERROR_CLASS_ATTR);
    }

    public void setErrorStyle(String errorStyle) {
        this.errorStyle = errorStyle;
    }

    public String getErrorStyle() {
        if (errorStyle != null) {
            return errorStyle;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ERROR_STYLE_ATTR);
    }

    public void setFatalClass(String fatalClass) {
        this.fatalClass = fatalClass;
    }

    public String getFatalClass() {
        if (fatalClass != null) {
            return fatalClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.FATAL_CLASS_ATTR);
    }

    public void setFatalStyle(String fatalStyle) {
        this.fatalStyle = fatalStyle;
    }

    public String getFatalStyle() {
        if (fatalStyle != null) {
            return fatalStyle;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.FATAL_STYLE_ATTR);
    }

    public void setInfoClass(String infoClass) {
        this.infoClass = infoClass;
    }

    public String getInfoClass() {
        if (infoClass != null) {
            return infoClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.INFO_CLASS_ATTR);
    }

    public void setInfoStyle(String infoStyle) {
        this.infoStyle = infoStyle;
    }

    public String getInfoStyle() {
        if (infoStyle != null) {
            return infoStyle;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.INFO_STYLE_ATTR);
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayout() {
        if (layout != null) {
            return layout;
        }
        final String str = ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LAYOUT_ATTR);
        return (str != null) ? str : DEFAULT_LAYOUT;
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

    public void setTooltip(boolean tooltip) {
        this.tooltip = Boolean.valueOf(tooltip);
    }

    public boolean isTooltip() {
        if (tooltip != null) {
            return tooltip.booleanValue();
        }
        ValueBinding vb = getValueBinding("tooltip");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_TOOLTIP;
    }

    public void setWarnClass(String warnClass) {
        this.warnClass = warnClass;
    }

    public String getWarnClass() {
        if (warnClass != null) {
            return warnClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.WARN_CLASS_ATTR);
    }

    public void setWarnStyle(String warnStyle) {
        this.warnStyle = warnStyle;
    }

    public String getWarnStyle() {
        if (warnStyle != null) {
            return warnStyle;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.WARN_STYLE_ATTR);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[14];
        values[0] = super.saveState(context);
        values[1] = errorClass;
        values[2] = errorStyle;
        values[3] = fatalClass;
        values[4] = fatalStyle;
        values[5] = infoClass;
        values[6] = infoStyle;
        values[7] = layout;
        values[8] = style;
        values[9] = styleClass;
        values[10] = title;
        values[11] = tooltip;
        values[12] = warnClass;
        values[13] = warnStyle;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        errorClass = (String) values[1];
        errorStyle = (String) values[2];
        fatalClass = (String) values[3];
        fatalStyle = (String) values[4];
        infoClass = (String) values[5];
        infoStyle = (String) values[6];
        layout = (String) values[7];
        style = (String) values[8];
        styleClass = (String) values[9];
        title = (String) values[10];
        tooltip = (Boolean) values[11];
        warnClass = (String) values[12];
        warnStyle = (String) values[13];
    }
}
