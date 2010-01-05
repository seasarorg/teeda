/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 * @author manhole
 */
public class HtmlCommandButton extends UICommand {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandButton";

    public static final String DEFAULT_RENDERER_TYPE = "javax.faces.Button";

    private static final boolean DEFAULT_DISABLED = false;

    private static final boolean DEFAULT_READONLY = false;

    private static final String SUBMIT = "submit";

    private static final String RESET = "reset";

    private static final String DEFAULT_TYPE = SUBMIT;

    private String accesskey = null;

    private String alt = null;

    private String dir = null;

    private Boolean disabled = null;

    private String image = null;

    private String lang = null;

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

    private String type = null;

    public HtmlCommandButton() {
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

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        if (alt != null) {
            return alt;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ALT_ATTR);
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
        Boolean value = (Boolean) getValueBindingValue("disabled");
        return (value != null) ? value.booleanValue() : DEFAULT_DISABLED;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        if (image != null) {
            return image;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.IMAGE_ATTR);
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
        Boolean value = (Boolean) getValueBindingValue("readonly");
        return (value != null) ? value.booleanValue() : DEFAULT_READONLY;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        String type = getType0();
        if (RESET.equalsIgnoreCase(type)) {
            return RESET;
        }
        return DEFAULT_TYPE;
    }

    private String getType0() {
        if (type != null) {
            return type;
        }
        Object value = getValueBindingValue("type");
        return (value != null) ? (String) value : DEFAULT_TYPE;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[27];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = alt;
        values[3] = dir;
        values[4] = disabled;
        values[5] = image;
        values[6] = lang;
        values[7] = onblur;
        values[8] = onchange;
        values[9] = onclick;
        values[10] = ondblclick;
        values[11] = onfocus;
        values[12] = onkeydown;
        values[13] = onkeypress;
        values[14] = onkeyup;
        values[15] = onmousedown;
        values[16] = onmousemove;
        values[17] = onmouseout;
        values[18] = onmouseover;
        values[19] = onmouseup;
        values[20] = onselect;
        values[21] = readonly;
        values[22] = style;
        values[23] = styleClass;
        values[24] = tabindex;
        values[25] = title;
        values[26] = type;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        alt = (String) values[2];
        dir = (String) values[3];
        disabled = (Boolean) values[4];
        image = (String) values[5];
        lang = (String) values[6];
        onblur = (String) values[7];
        onchange = (String) values[8];
        onclick = (String) values[9];
        ondblclick = (String) values[10];
        onfocus = (String) values[11];
        onkeydown = (String) values[12];
        onkeypress = (String) values[13];
        onkeyup = (String) values[14];
        onmousedown = (String) values[15];
        onmousemove = (String) values[16];
        onmouseout = (String) values[17];
        onmouseover = (String) values[18];
        onmouseup = (String) values[19];
        onselect = (String) values[20];
        readonly = (Boolean) values[21];
        style = (String) values[22];
        styleClass = (String) values[23];
        tabindex = (String) values[24];
        title = (String) values[25];
        type = (String) values[26];
    }

    private Object getValueBindingValue(String bindingName) {
        ValueBinding vb = getValueBinding(bindingName);
        return (vb != null) ? vb.getValue(getFacesContext()) : null;
    }

}
