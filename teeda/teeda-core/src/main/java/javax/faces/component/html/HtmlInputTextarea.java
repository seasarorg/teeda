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

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlInputTextarea extends UIInput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlInputTextarea";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Textarea";

    private static final int DEFAULT_COLS = Integer.MIN_VALUE;

    private static final boolean DEFAULT_DISABLED = false;

    private static final boolean DEFAULT_READONLY = false;

    private static final int DEFAULT_ROWS = Integer.MIN_VALUE;

    private String accesskey;

    private Integer cols;

    private String dir;

    private Boolean disabled;

    private String lang;

    private String onblur;

    private String onchange;

    private String onclick;

    private String ondblclick;

    private String onfocus;

    private String onkeydown;

    private String onkeypress;

    private String onkeyup;

    private String onmousedown;

    private String onmousemove;

    private String onmouseout;

    private String onmouseover;

    private String onmouseup;

    private String onselect;

    private Boolean readonly;

    private Integer rows;

    private String style;

    private String styleClass;

    private String tabindex;

    private String title;

    private String label;

    private String wrap;

    public HtmlInputTextarea() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(final String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskey() {
        if (accesskey != null) {
            return accesskey;
        }
        final ValueBinding vb = getValueBinding("accesskey");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCols(final int cols) {
        this.cols = new Integer(cols);
    }

    public int getCols() {
        if (cols != null) {
            return cols.intValue();
        }
        final ValueBinding vb = getValueBinding("cols");
        final Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_COLS;
    }

    public void setDir(final String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        final ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = Boolean.valueOf(disabled);
    }

    public boolean isDisabled() {
        if (disabled != null) {
            return disabled.booleanValue();
        }
        final ValueBinding vb = getValueBinding("disabled");
        final Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_DISABLED;
    }

    public void setLang(final String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        final ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnblur(final String onblur) {
        this.onblur = onblur;
    }

    public String getOnblur() {
        if (onblur != null) {
            return onblur;
        }
        final ValueBinding vb = getValueBinding("onblur");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnchange(final String onchange) {
        this.onchange = onchange;
    }

    public String getOnchange() {
        if (onchange != null) {
            return onchange;
        }
        final ValueBinding vb = getValueBinding("onchange");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(final String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        final ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(final String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        final ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnfocus(final String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnfocus() {
        if (onfocus != null) {
            return onfocus;
        }
        final ValueBinding vb = getValueBinding("onfocus");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(final String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        final ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(final String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        final ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(final String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        final ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(final String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        final ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(final String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        final ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(final String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        final ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(final String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        final ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(final String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        final ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnselect(final String onselect) {
        this.onselect = onselect;
    }

    public String getOnselect() {
        if (onselect != null) {
            return onselect;
        }
        final ValueBinding vb = getValueBinding("onselect");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setReadonly(final boolean readonly) {
        this.readonly = Boolean.valueOf(readonly);
    }

    public boolean isReadonly() {
        if (readonly != null) {
            return readonly.booleanValue();
        }
        final ValueBinding vb = getValueBinding("readonly");
        final Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_READONLY;
    }

    public void setRows(final int rows) {
        this.rows = new Integer(rows);
    }

    public int getRows() {
        if (rows != null) {
            return rows.intValue();
        }
        final ValueBinding vb = getValueBinding("rows");
        final Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_ROWS;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        final ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(final String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        final ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTabindex(final String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabindex() {
        if (tabindex != null) {
            return tabindex;
        }
        final ValueBinding vb = getValueBinding("tabindex");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        final ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getLabel() {
        if (label != null) {
            return label;
        }
        final ValueBinding vb = getValueBinding(JsfConstants.LABEL_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getWrap() {
        if (wrap != null) {
            return wrap;
        }
        final ValueBinding vb = getValueBinding(JsfConstants.WRAP_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWrap(final String wrap) {
        this.wrap = wrap;
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[28];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = cols;
        values[3] = dir;
        values[4] = disabled;
        values[5] = lang;
        values[6] = onblur;
        values[7] = onchange;
        values[8] = onclick;
        values[9] = ondblclick;
        values[10] = onfocus;
        values[11] = onkeydown;
        values[12] = onkeypress;
        values[13] = onkeyup;
        values[14] = onmousedown;
        values[15] = onmousemove;
        values[16] = onmouseout;
        values[17] = onmouseover;
        values[18] = onmouseup;
        values[19] = onselect;
        values[20] = readonly;
        values[21] = rows;
        values[22] = style;
        values[23] = styleClass;
        values[24] = tabindex;
        values[25] = title;
        values[26] = label;
        values[27] = wrap;
        return values;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        cols = (Integer) values[2];
        dir = (String) values[3];
        disabled = (Boolean) values[4];
        lang = (String) values[5];
        onblur = (String) values[6];
        onchange = (String) values[7];
        onclick = (String) values[8];
        ondblclick = (String) values[9];
        onfocus = (String) values[10];
        onkeydown = (String) values[11];
        onkeypress = (String) values[12];
        onkeyup = (String) values[13];
        onmousedown = (String) values[14];
        onmousemove = (String) values[15];
        onmouseout = (String) values[16];
        onmouseover = (String) values[17];
        onmouseup = (String) values[18];
        onselect = (String) values[19];
        readonly = (Boolean) values[20];
        rows = (Integer) values[21];
        style = (String) values[22];
        styleClass = (String) values[23];
        tabindex = (String) values[24];
        title = (String) values[25];
        label = (String) values[26];
        wrap = (String) values[27];
    }

}
