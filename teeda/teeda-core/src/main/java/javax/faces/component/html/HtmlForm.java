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

import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class HtmlForm extends UIForm {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlForm";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Form";

    private static final String DEFAULT_ENCTYPE = "application/x-www-form-urlencoded";

    private String accept = null;

    private String acceptcharset = null;

    private String dir = null;

    private String enctype = null;

    private String lang = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onkeydown = null;

    private String onkeypress = null;

    private String onkeyup = null;

    private String onmousedown = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onmouseover = null;

    private String onmouseup = null;

    private String onreset = null;

    private String onsubmit = null;

    private String style = null;

    private String styleClass = null;

    private String target = null;

    private String title = null;

    public HtmlForm() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAccept() {
        if (accept != null) {
            return accept;
        }
        ValueBinding vb = getValueBinding("accept");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setAcceptcharset(String acceptcharset) {
        this.acceptcharset = acceptcharset;
    }

    public String getAcceptcharset() {
        if (acceptcharset != null) {
            return acceptcharset;
        }
        ValueBinding vb = getValueBinding("acceptcharset");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public String getEnctype() {
        if (enctype != null) {
            return enctype;
        }
        ValueBinding vb = getValueBinding("enctype");
        return vb != null ? (String) vb.getValue(getFacesContext())
                : DEFAULT_ENCTYPE;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnreset(String onreset) {
        this.onreset = onreset;
    }

    public String getOnreset() {
        if (onreset != null) {
            return onreset;
        }
        ValueBinding vb = getValueBinding("onreset");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public String getOnsubmit() {
        if (onsubmit != null) {
            return onsubmit;
        }
        ValueBinding vb = getValueBinding("onsubmit");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        if (target != null) {
            return target;
        }
        ValueBinding vb = getValueBinding("target");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[22];
        values[0] = super.saveState(context);
        values[1] = accept;
        values[2] = acceptcharset;
        values[3] = dir;
        values[4] = enctype;
        values[5] = lang;
        values[6] = onclick;
        values[7] = ondblclick;
        values[8] = onkeydown;
        values[9] = onkeypress;
        values[10] = onkeyup;
        values[11] = onmousedown;
        values[12] = onmousemove;
        values[13] = onmouseout;
        values[14] = onmouseover;
        values[15] = onmouseup;
        values[16] = onreset;
        values[17] = onsubmit;
        values[18] = style;
        values[19] = styleClass;
        values[20] = target;
        values[21] = title;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accept = (String) values[1];
        acceptcharset = (String) values[2];
        dir = (String) values[3];
        enctype = (String) values[4];
        lang = (String) values[5];
        onclick = (String) values[6];
        ondblclick = (String) values[7];
        onkeydown = (String) values[8];
        onkeypress = (String) values[9];
        onkeyup = (String) values[10];
        onmousedown = (String) values[11];
        onmousemove = (String) values[12];
        onmouseout = (String) values[13];
        onmouseover = (String) values[14];
        onmouseup = (String) values[15];
        onreset = (String) values[16];
        onsubmit = (String) values[17];
        style = (String) values[18];
        styleClass = (String) values[19];
        target = (String) values[20];
        title = (String) values[21];
    }

}
