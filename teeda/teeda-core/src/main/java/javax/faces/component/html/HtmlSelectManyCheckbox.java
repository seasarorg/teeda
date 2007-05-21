/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.UIValueUtil;

/**
 * @author shot
 */
public class HtmlSelectManyCheckbox extends UISelectMany {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlSelectManyCheckbox";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Checkbox";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private static final boolean DEFAULT_DISABLED = false;

    private static final boolean DEFAULT_READONLY = false;

    private String accesskey = null;

    private Integer border = null;

    private String dir = null;

    private Boolean disabled = null;

    private String disabledClass = null;

    private String enabledClass = null;

    private String lang = null;

    private String layout = null;

    private String onblur = null;

    private String onchange = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onfocus = null;

    private String onkeydown = null;

    private String onkeypress = null;

    private String onkeyup = null;

    private String onmousedown = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onmouseover = null;

    private String onmouseup = null;

    private String onselect = null;

    private Boolean readonly = null;

    private String style = null;

    private String styleClass = null;

    private String tabindex = null;

    private String title = null;

    private String label = null;

    public HtmlSelectManyCheckbox() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskey() {
        if (accesskey != null) {
            return accesskey;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ACCESSKEY_ATTR);
    }

    public void setBorder(int border) {
        this.border = new Integer(border);
    }

    public int getBorder() {
        if (border != null) {
            return border.intValue();
        }
        ValueBinding vb = getValueBinding("border");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_BORDER;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.DIR_ATTR);
    }

    public void setDisabled(boolean disabled) {
        this.disabled = Boolean.valueOf(disabled);
    }

    public boolean isDisabled() {
        if (disabled != null) {
            return disabled.booleanValue();
        }
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_DISABLED;
    }

    public void setDisabledClass(String disabledClass) {
        this.disabledClass = disabledClass;
    }

    public String getDisabledClass() {
        if (disabledClass != null) {
            return disabledClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.DISABLED_CLASS_ATTR);
    }

    public void setEnabledClass(String enabledClass) {
        this.enabledClass = enabledClass;
    }

    public String getEnabledClass() {
        if (enabledClass != null) {
            return enabledClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ENABLED_CLASS_ATTR);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LANG_ATTR);
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayout() {
        if (layout != null) {
            return layout;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LAYOUT_ATTR);
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnblur() {
        if (onblur != null) {
            return onblur;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONBLUR_ATTR);
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getOnchange() {
        if (onchange != null) {
            return onchange;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONCHANGE_ATTR);
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONCLICK_ATTR);
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONDBLCLICK_ATTR);
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnfocus() {
        if (onfocus != null) {
            return onfocus;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONFOCUS_ATTR);
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYDOWN_ATTR);
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYPRESS_ATTR);
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYUP_ATTR);
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEDOWN_ATTR);
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEMOVE_ATTR);
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOUT_ATTR);
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOVER_ATTR);
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEUP_ATTR);
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public String getOnselect() {
        if (onselect != null) {
            return onselect;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONSELECT_ATTR);
    }

    public void setReadonly(boolean readonly) {
        this.readonly = Boolean.valueOf(readonly);
    }

    public boolean isReadonly() {
        if (readonly != null) {
            return readonly.booleanValue();
        }
        ValueBinding vb = getValueBinding("readonly");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_READONLY;
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

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabindex() {
        if (tabindex != null) {
            return tabindex;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TABINDEX_ATTR);
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        if (label != null) {
            return label;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LABEL_ATTR);
    }

    public void validate(FacesContext context) {
        Object submittedValue = getSubmittedValue();
        if ("".equals(submittedValue)) {
            submittedValue = new String[0];
        }
        Object convertedValue = RendererUtil.getConvertedValue(context, this,
                submittedValue);
        if (!isValid()) {
            return;
        }
        boolean empty = UIValueUtil.isManyEmpty(convertedValue);
        if (isRequired() && empty) {
            context.addMessage(getClientId(context), FacesMessageUtil
                    .getMessage(context, REQUIRED_MESSAGE_ID,
                            new Object[] { getLabel() }));
            setValid(false);
            return;
        }
        if (!empty) {
            UIComponentUtil.callValidators(context, this, convertedValue);
        }
        if (PostbackUtil.isPostback(context.getExternalContext()
                .getRequestMap())) {
            validateForExtension(context, convertedValue);
        }
        if (!isValid()) {
            return;
        }
        if (isDisabled()) {
            return;
        }
        Object previousValue = getValue();
        setValue(convertedValue);
        setSubmittedValue(null);
        if (compareValues(previousValue, convertedValue)) {
            queueEvent(new ValueChangeEvent(this, previousValue, convertedValue));
        }
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[29];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = border;
        values[3] = dir;
        values[4] = disabled;
        values[5] = disabledClass;
        values[6] = enabledClass;
        values[7] = lang;
        values[8] = layout;
        values[9] = onblur;
        values[10] = onchange;
        values[11] = onclick;
        values[12] = ondblclick;
        values[13] = onfocus;
        values[14] = onkeydown;
        values[15] = onkeypress;
        values[16] = onkeyup;
        values[17] = onmousedown;
        values[18] = onmousemove;
        values[19] = onmouseout;
        values[20] = onmouseover;
        values[21] = onmouseup;
        values[22] = onselect;
        values[23] = readonly;
        values[24] = style;
        values[25] = styleClass;
        values[26] = tabindex;
        values[27] = title;
        values[28] = label;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        border = (Integer) values[2];
        dir = (String) values[3];
        disabled = (Boolean) values[4];
        disabledClass = (String) values[5];
        enabledClass = (String) values[6];
        lang = (String) values[7];
        layout = (String) values[8];
        onblur = (String) values[9];
        onchange = (String) values[10];
        onclick = (String) values[11];
        ondblclick = (String) values[12];
        onfocus = (String) values[13];
        onkeydown = (String) values[14];
        onkeypress = (String) values[15];
        onkeyup = (String) values[16];
        onmousedown = (String) values[17];
        onmousemove = (String) values[18];
        onmouseout = (String) values[19];
        onmouseover = (String) values[20];
        onmouseup = (String) values[21];
        onselect = (String) values[22];
        readonly = (Boolean) values[23];
        style = (String) values[24];
        styleClass = (String) values[25];
        tabindex = (String) values[26];
        title = (String) values[27];
        label = (String) values[28];
    }
}
