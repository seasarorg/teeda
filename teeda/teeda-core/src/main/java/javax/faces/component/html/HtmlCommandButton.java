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

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 * @author manhole
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class HtmlCommandButton extends UICommand {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandButton";

    public static final String DEFAULT_RENDERERTYPE = "javax.faces.Button";

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
        setRendererType(DEFAULT_RENDERERTYPE);
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskey() {
        if (accesskey != null) {
            return accesskey;
        }
        Object value = getValueBindingValue("accesskey");
        return (value != null) ? (String) value : null;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        if (alt != null) {
            return alt;
        }
        Object value = getValueBindingValue("alt");
        return (value != null) ? (String) value : null;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        Object value = getValueBindingValue("dir");
        return (value != null) ? (String) value : null;
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
        Object value = getValueBindingValue("image");
        return (value != null) ? (String) value : null;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        Object value = getValueBindingValue("lang");
        return (value != null) ? (String) value : null;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnblur() {
        if (onblur != null) {
            return onblur;
        }
        Object value = getValueBindingValue("onblur");
        return (value != null) ? (String) value : null;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getOnchange() {
        if (onchange != null) {
            return onchange;
        }
        Object value = getValueBindingValue("onchange");
        return (value != null) ? (String) value : null;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        Object value = getValueBindingValue("onclick");
        return (value != null) ? (String) value : null;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        Object value = getValueBindingValue("ondblclick");
        return (value != null) ? (String) value : null;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnfocus() {
        if (onfocus != null) {
            return onfocus;
        }
        Object value = getValueBindingValue("onfocus");
        return (value != null) ? (String) value : null;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        Object value = getValueBindingValue("onkeydown");
        return (value != null) ? (String) value : null;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        Object value = getValueBindingValue("onkeypress");
        return (value != null) ? (String) value : null;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        Object value = getValueBindingValue("onkeyup");
        return (value != null) ? (String) value : null;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        Object value = getValueBindingValue("onmousedown");
        return (value != null) ? (String) value : null;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        Object value = getValueBindingValue("onmousemove");
        return (value != null) ? (String) value : null;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        Object value = getValueBindingValue("onmouseout");
        return (value != null) ? (String) value : null;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        Object value = getValueBindingValue("onmouseover");
        return (value != null) ? (String) value : null;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        Object value = getValueBindingValue("onmouseup");
        return (value != null) ? (String) value : null;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public String getOnselect() {
        if (onselect != null) {
            return onselect;
        }
        Object value = getValueBindingValue("onselect");
        return (value != null) ? (String) value : null;
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
        Object value = getValueBindingValue("style");
        return (value != null) ? (String) value : null;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        Object value = getValueBindingValue("styleClass");
        return (value != null) ? (String) value : null;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabindex() {
        if (tabindex != null) {
            return tabindex;
        }
        Object value = getValueBindingValue("tabindex");
        return (value != null) ? (String) value : null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        Object value = getValueBindingValue("title");
        return (value != null) ? (String) value : null;
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
