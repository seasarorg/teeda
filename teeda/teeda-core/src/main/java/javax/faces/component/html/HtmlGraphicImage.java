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

import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class HtmlGraphicImage extends UIGraphic {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlGraphicImage";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Image";

    private static final boolean DEFAULT_ISMAP = false;

    private String alt_ = null;

    private String dir_ = null;

    private String height_ = null;

    private Boolean ismap_ = null;

    private String lang_ = null;

    private String longdesc_ = null;

    private String onclick_ = null;

    private String ondblclick_ = null;

    private String onkeydown_ = null;

    private String onkeypress_ = null;

    private String onkeyup_ = null;

    private String onmousedown_ = null;

    private String onmousemove_ = null;

    private String onmouseout_ = null;

    private String onmouseover_ = null;

    private String onmouseup_ = null;

    private String style_ = null;

    private String styleClass_ = null;

    private String title_ = null;

    private String usemap_ = null;

    private String width_ = null;

    public HtmlGraphicImage() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAlt(String alt) {
        alt_ = alt;
    }

    public String getAlt() {
        if (alt_ != null) {
            return alt_;
        }
        ValueBinding vb = getValueBinding("alt");
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

    public void setHeight(String height) {
        height_ = height;
    }

    public String getHeight() {
        if (height_ != null) {
            return height_;
        }
        ValueBinding vb = getValueBinding("height");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setIsmap(boolean ismap) {
        ismap_ = Boolean.valueOf(ismap);
    }

    public boolean isIsmap() {
        if (ismap_ != null) {
            return ismap_.booleanValue();
        }
        ValueBinding vb = getValueBinding("ismap");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_ISMAP;
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

    public void setLongdesc(String longdesc) {
        longdesc_ = longdesc;
    }

    public String getLongdesc() {
        if (longdesc_ != null) {
            return longdesc_;
        }
        ValueBinding vb = getValueBinding("longdesc");
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

    public void setUsemap(String usemap) {
        usemap_ = usemap;
    }

    public String getUsemap() {
        if (usemap_ != null) {
            return usemap_;
        }
        ValueBinding vb = getValueBinding("usemap");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWidth(String width) {
        width_ = width;
    }

    public String getWidth() {
        if (width_ != null) {
            return width_;
        }
        ValueBinding vb = getValueBinding("width");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[22];
        values[0] = super.saveState(context);
        values[1] = alt_;
        values[2] = dir_;
        values[3] = height_;
        values[4] = ismap_;
        values[5] = lang_;
        values[6] = longdesc_;
        values[7] = onclick_;
        values[8] = ondblclick_;
        values[9] = onkeydown_;
        values[10] = onkeypress_;
        values[11] = onkeyup_;
        values[12] = onmousedown_;
        values[13] = onmousemove_;
        values[14] = onmouseout_;
        values[15] = onmouseover_;
        values[16] = onmouseup_;
        values[17] = style_;
        values[18] = styleClass_;
        values[19] = title_;
        values[20] = usemap_;
        values[21] = width_;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        alt_ = (String) values[1];
        dir_ = (String) values[2];
        height_ = (String) values[3];
        ismap_ = (Boolean) values[4];
        lang_ = (String) values[5];
        longdesc_ = (String) values[6];
        onclick_ = (String) values[7];
        ondblclick_ = (String) values[8];
        onkeydown_ = (String) values[9];
        onkeypress_ = (String) values[10];
        onkeyup_ = (String) values[11];
        onmousedown_ = (String) values[12];
        onmousemove_ = (String) values[13];
        onmouseout_ = (String) values[14];
        onmouseover_ = (String) values[15];
        onmouseup_ = (String) values[16];
        style_ = (String) values[17];
        styleClass_ = (String) values[18];
        title_ = (String) values[19];
        usemap_ = (String) values[20];
        width_ = (String) values[21];
    }

}
