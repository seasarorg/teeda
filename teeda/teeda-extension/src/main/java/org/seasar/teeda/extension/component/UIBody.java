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
package org.seasar.teeda.extension.component;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class UIBody extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Body";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Body";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Body";

    private String styleClass;

    private String lang;

    private String dir;

    private String title;

    private String style;

    private String bgcolor;

    private String onload;

    private String onunload;

    private String onclick;

    private String ondblclick;

    private String onmousedown;

    private String onmouseup;

    private String onmouseover;

    private String onmousemove;

    private String onmouseout;

    private String onkeypress;

    private String onkeydown;

    private String onkeyup;

    public UIBody() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getBgcolor() {
        if (bgcolor != null) {
            return bgcolor;
        }
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnload() {
        if (onload != null) {
            return onload;
        }
        ValueBinding vb = getValueBinding("onload");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getOnunload() {
        if (onunload != null) {
            return onunload;
        }
        ValueBinding vb = getValueBinding("onunload");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnload(String onload) {
        this.onload = onload;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setOnunload(String onunload) {
        this.onunload = onunload;
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
        Object values[] = new Object[19];
        values[0] = super.saveState(context);
        values[1] = styleClass;
        values[2] = lang;
        values[3] = dir;
        values[4] = title;
        values[5] = style;
        values[6] = bgcolor;
        values[7] = onload;
        values[8] = onunload;
        values[9] = onclick;
        values[10] = ondblclick;
        values[11] = onmousedown;
        values[12] = onmouseup;
        values[13] = onmouseover;
        values[14] = onmousemove;
        values[15] = onmouseout;
        values[16] = onkeypress;
        values[17] = onkeydown;
        values[18] = onkeyup;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        styleClass = (String) values[1];
        lang = (String) values[2];
        dir = (String) values[3];
        title = (String) values[4];
        style = (String) values[5];
        bgcolor = (String) values[6];
        onload = (String) values[7];
        onunload = (String) values[8];
        onclick = (String) values[9];
        ondblclick = (String) values[10];
        onmousedown = (String) values[11];
        onmouseup = (String) values[12];
        onmouseover = (String) values[13];
        onmousemove = (String) values[14];
        onmouseout = (String) values[15];
        onkeypress = (String) values[16];
        onkeydown = (String) values[17];
        onkeyup = (String) values[18];
    }

}
