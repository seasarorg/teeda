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

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class HtmlCommandLink extends UICommand {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandLink";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Link";

    private String accesskey_ = null;

    private String charset_ = null;

    private String coords_ = null;

    private String dir_ = null;

    private String hreflang_ = null;

    private String lang_ = null;

    private String onblur_ = null;

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

    private String rel_ = null;

    private String rev_ = null;

    private String shape_ = null;

    private String style_ = null;

    private String styleClass_ = null;

    private String tabindex_ = null;

    private String target_ = null;

    private String title_ = null;

    private String type_ = null;

    public HtmlCommandLink() {
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

    public void setCharset(String charset) {
        charset_ = charset;
    }

    public String getCharset() {
        if (charset_ != null) {
            return charset_;
        }
        ValueBinding vb = getValueBinding("charset");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCoords(String coords) {
        coords_ = coords;
    }

    public String getCoords() {
        if (coords_ != null) {
            return coords_;
        }
        ValueBinding vb = getValueBinding("coords");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

    public void setHreflang(String hreflang) {
        hreflang_ = hreflang;
    }

    public String getHreflang() {
        if (hreflang_ != null) {
            return hreflang_;
        }
        ValueBinding vb = getValueBinding("hreflang");
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

    public void setRel(String rel) {
        rel_ = rel;
    }

    public String getRel() {
        if (rel_ != null) {
            return rel_;
        }
        ValueBinding vb = getValueBinding("rel");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRev(String rev) {
        rev_ = rev;
    }

    public String getRev() {
        if (rev_ != null) {
            return rev_;
        }
        ValueBinding vb = getValueBinding("rev");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setShape(String shape) {
        shape_ = shape;
    }

    public String getShape() {
        if (shape_ != null) {
            return shape_;
        }
        ValueBinding vb = getValueBinding("shape");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

    public void setTarget(String target) {
        target_ = target;
    }

    public String getTarget() {
        if (target_ != null) {
            return target_;
        }
        ValueBinding vb = getValueBinding("target");
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

    public void setType(String type) {
        type_ = type;
    }

    public String getType() {
        if (type_ != null) {
            return type_;
        }
        ValueBinding vb = getValueBinding("type");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[28];
        values[0] = super.saveState(context);
        values[1] = accesskey_;
        values[2] = charset_;
        values[3] = coords_;
        values[4] = dir_;
        values[5] = hreflang_;
        values[6] = lang_;
        values[7] = onblur_;
        values[8] = onclick_;
        values[9] = ondblclick_;
        values[10] = onfocus_;
        values[11] = onkeydown_;
        values[12] = onkeypress_;
        values[13] = onkeyup_;
        values[14] = onmousedown_;
        values[15] = onmousemove_;
        values[16] = onmouseout_;
        values[17] = onmouseover_;
        values[18] = onmouseup_;
        values[19] = rel_;
        values[20] = rev_;
        values[21] = shape_;
        values[22] = style_;
        values[23] = styleClass_;
        values[24] = tabindex_;
        values[25] = target_;
        values[26] = title_;
        values[27] = type_;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey_ = (String) values[1];
        charset_ = (String) values[2];
        coords_ = (String) values[3];
        dir_ = (String) values[4];
        hreflang_ = (String) values[5];
        lang_ = (String) values[6];
        onblur_ = (String) values[7];
        onclick_ = (String) values[8];
        ondblclick_ = (String) values[9];
        onfocus_ = (String) values[10];
        onkeydown_ = (String) values[11];
        onkeypress_ = (String) values[12];
        onkeyup_ = (String) values[13];
        onmousedown_ = (String) values[14];
        onmousemove_ = (String) values[15];
        onmouseout_ = (String) values[16];
        onmouseover_ = (String) values[17];
        onmouseup_ = (String) values[18];
        rel_ = (String) values[19];
        rev_ = (String) values[20];
        shape_ = (String) values[21];
        style_ = (String) values[22];
        styleClass_ = (String) values[23];
        tabindex_ = (String) values[24];
        target_ = (String) values[25];
        title_ = (String) values[26];
        type_ = (String) values[27];
    }
}
