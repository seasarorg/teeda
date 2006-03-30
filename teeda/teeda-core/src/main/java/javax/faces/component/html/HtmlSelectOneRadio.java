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

import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

public class HtmlSelectOneRadio extends UISelectOne {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlSelectOneRadio";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Radio";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private static final boolean DEFAULT_DISABLED = false;

    private static final boolean DEFAULT_READONLY = false;

    private String accesskey_ = null;

    private Integer border_ = null;

    private String dir_ = null;

    private Boolean disabled_ = null;

    private String disabledClass_ = null;

    private String enabledClass_ = null;

    private String lang_ = null;

    private String layout_ = null;

    private String onblur_ = null;

    private String onchange_ = null;

    private String onclick_ = null;

    private String ondblclick_ = null;

    private String onfocus_ = null;

    private String onkeydown_ = null;

    private String onkeypress_ = null;

    private String onkeyup_ = null;

    private String onmousedown_ = null;

    private String onmousemove_ = null;

    private String onmouseout_ = null;

    private String onmouseover_ = null;

    private String onmouseup_ = null;

    private String onselect_ = null;

    private Boolean readonly_ = null;

    private String style_ = null;

    private String styleClass_ = null;

    private String tabindex_ = null;

    private String title_ = null;

    private String label_ = null;

    public HtmlSelectOneRadio() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(String accesskey) {
        accesskey_ = accesskey;
    }

    public String getAccesskey() {
        if (accesskey_ != null) {
            return accesskey_;
        }
        ValueBinding vb = getValueBinding("accesskey");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBorder(int border) {
        border_ = new Integer(border);
    }

    public int getBorder() {
        if (border_ != null) {
            return border_.intValue();
        }
        ValueBinding vb = getValueBinding("border");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_BORDER;
    }

    public void setDir(String dir) {
        dir_ = dir;
    }

    public String getDir() {
        if (dir_ != null) {
            return dir_;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDisabled(boolean disabled) {
        disabled_ = Boolean.valueOf(disabled);
    }

    public boolean isDisabled() {
        if (disabled_ != null) {
            return disabled_.booleanValue();
        }
        ValueBinding vb = getValueBinding("disabled");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_DISABLED;
    }

    public void setDisabledClass(String disabledClass) {
        disabledClass_ = disabledClass;
    }

    public String getDisabledClass() {
        if (disabledClass_ != null) {
            return disabledClass_;
        }
        ValueBinding vb = getValueBinding("disabledClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setEnabledClass(String enabledClass) {
        enabledClass_ = enabledClass;
    }

    public String getEnabledClass() {
        if (enabledClass_ != null) {
            return enabledClass_;
        }
        ValueBinding vb = getValueBinding("enabledClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLang(String lang) {
        lang_ = lang;
    }

    public String getLang() {
        if (lang_ != null) {
            return lang_;
        }
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLayout(String layout) {
        layout_ = layout;
    }

    public String getLayout() {
        if (layout_ != null) {
            return layout_;
        }
        ValueBinding vb = getValueBinding("layout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnblur(String onblur) {
        onblur_ = onblur;
    }

    public String getOnblur() {
        if (onblur_ != null) {
            return onblur_;
        }
        ValueBinding vb = getValueBinding("onblur");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnchange(String onchange) {
        onchange_ = onchange;
    }

    public String getOnchange() {
        if (onchange_ != null) {
            return onchange_;
        }
        ValueBinding vb = getValueBinding("onchange");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick) {
        onclick_ = onclick;
    }

    public String getOnclick() {
        if (onclick_ != null) {
            return onclick_;
        }
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick) {
        ondblclick_ = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick_ != null) {
            return ondblclick_;
        }
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnfocus(String onfocus) {
        onfocus_ = onfocus;
    }

    public String getOnfocus() {
        if (onfocus_ != null) {
            return onfocus_;
        }
        ValueBinding vb = getValueBinding("onfocus");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown) {
        onkeydown_ = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown_ != null) {
            return onkeydown_;
        }
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress) {
        onkeypress_ = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress_ != null) {
            return onkeypress_;
        }
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup) {
        onkeyup_ = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup_ != null) {
            return onkeyup_;
        }
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown) {
        onmousedown_ = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown_ != null) {
            return onmousedown_;
        }
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove) {
        onmousemove_ = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove_ != null) {
            return onmousemove_;
        }
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout) {
        onmouseout_ = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout_ != null) {
            return onmouseout_;
        }
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover) {
        onmouseover_ = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover_ != null) {
            return onmouseover_;
        }
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup) {
        onmouseup_ = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup_ != null) {
            return onmouseup_;
        }
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnselect(String onselect) {
        onselect_ = onselect;
    }

    public String getOnselect() {
        if (onselect_ != null) {
            return onselect_;
        }
        ValueBinding vb = getValueBinding("onselect");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setReadonly(boolean readonly) {
        readonly_ = Boolean.valueOf(readonly);
    }

    public boolean isReadonly() {
        if (readonly_ != null) {
            return readonly_.booleanValue();
        }
        ValueBinding vb = getValueBinding("readonly");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_READONLY;
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

    public void setTabindex(String tabindex) {
        tabindex_ = tabindex;
    }

    public String getTabindex() {
        if (tabindex_ != null) {
            return tabindex_;
        }
        ValueBinding vb = getValueBinding("tabindex");
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

    public void setLabel(String label) {
        label_ = label;
    }

    public String getLabel() {
        if (label_ != null) {
            return label_;
        }
        ValueBinding vb = getValueBinding(JsfConstants.LABEL_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : getId();
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[29];
        values[0] = super.saveState(context);
        values[1] = accesskey_;
        values[2] = border_;
        values[3] = dir_;
        values[4] = disabled_;
        values[5] = disabledClass_;
        values[6] = enabledClass_;
        values[7] = lang_;
        values[8] = layout_;
        values[9] = onblur_;
        values[10] = onchange_;
        values[11] = onclick_;
        values[12] = ondblclick_;
        values[13] = onfocus_;
        values[14] = onkeydown_;
        values[15] = onkeypress_;
        values[16] = onkeyup_;
        values[17] = onmousedown_;
        values[18] = onmousemove_;
        values[19] = onmouseout_;
        values[20] = onmouseover_;
        values[21] = onmouseup_;
        values[22] = onselect_;
        values[23] = readonly_;
        values[24] = style_;
        values[25] = styleClass_;
        values[26] = tabindex_;
        values[27] = title_;
        values[28] = label_;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey_ = (String) values[1];
        border_ = (Integer) values[2];
        dir_ = (String) values[3];
        disabled_ = (Boolean) values[4];
        disabledClass_ = (String) values[5];
        enabledClass_ = (String) values[6];
        lang_ = (String) values[7];
        layout_ = (String) values[8];
        onblur_ = (String) values[9];
        onchange_ = (String) values[10];
        onclick_ = (String) values[11];
        ondblclick_ = (String) values[12];
        onfocus_ = (String) values[13];
        onkeydown_ = (String) values[14];
        onkeypress_ = (String) values[15];
        onkeyup_ = (String) values[16];
        onmousedown_ = (String) values[17];
        onmousemove_ = (String) values[18];
        onmouseout_ = (String) values[19];
        onmouseover_ = (String) values[20];
        onmouseup_ = (String) values[21];
        onselect_ = (String) values[22];
        readonly_ = (Boolean) values[23];
        style_ = (String) values[24];
        styleClass_ = (String) values[25];
        tabindex_ = (String) values[26];
        title_ = (String) values[27];
        label_ = (String) values[28];
    }

}
