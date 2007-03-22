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

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class HtmlOutputLink extends UIOutput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputLink";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Link";

    private String accesskey = null;

    private String charset = null;

    private String coords = null;

    private String dir = null;

    private String hreflang = null;

    private String lang = null;

    private String onblur = null;

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

    private String rel = null;

    private String rev = null;

    private String shape = null;

    private String style = null;

    private String styleClass = null;

    private String tabindex = null;

    private String target = null;

    private String title = null;

    private String type = null;

    public HtmlOutputLink() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskey() {
        if (accesskey != null) {
            return accesskey;
        }
        ValueBinding vb = getValueBinding("accesskey");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        if (charset != null) {
            return charset;
        }
        ValueBinding vb = getValueBinding("charset");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getCoords() {
        if (coords != null) {
            return coords;
        }
        ValueBinding vb = getValueBinding("coords");
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

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public String getHreflang() {
        if (hreflang != null) {
            return hreflang;
        }
        ValueBinding vb = getValueBinding("hreflang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnblur() {
        if (onblur != null) {
            return onblur;
        }
        ValueBinding vb = getValueBinding("onblur");
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

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnfocus() {
        if (onfocus != null) {
            return onfocus;
        }
        ValueBinding vb = getValueBinding("onfocus");
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

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        if (rel != null) {
            return rel;
        }
        ValueBinding vb = getValueBinding("rel");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getRev() {
        if (rev != null) {
            return rev;
        }
        ValueBinding vb = getValueBinding("rev");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getShape() {
        if (shape != null) {
            return shape;
        }
        ValueBinding vb = getValueBinding("shape");
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

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTabindex() {
        if (tabindex != null) {
            return tabindex;
        }
        ValueBinding vb = getValueBinding("tabindex");
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        if (type != null) {
            return type;
        }
        ValueBinding vb = getValueBinding("type");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[28];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = charset;
        values[3] = coords;
        values[4] = dir;
        values[5] = hreflang;
        values[6] = lang;
        values[7] = onblur;
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
        values[19] = rel;
        values[20] = rev;
        values[21] = shape;
        values[22] = style;
        values[23] = styleClass;
        values[24] = tabindex;
        values[25] = target;
        values[26] = title;
        values[27] = type;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        charset = (String) values[2];
        coords = (String) values[3];
        dir = (String) values[4];
        hreflang = (String) values[5];
        lang = (String) values[6];
        onblur = (String) values[7];
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
        rel = (String) values[19];
        rev = (String) values[20];
        shape = (String) values[21];
        style = (String) values[22];
        styleClass = (String) values[23];
        tabindex = (String) values[24];
        target = (String) values[25];
        title = (String) values[26];
        type = (String) values[27];
    }
}
