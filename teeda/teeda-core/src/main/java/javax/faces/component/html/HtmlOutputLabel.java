/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlOutputLabel extends UIOutput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputLabel";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Label";

    private String accesskey = null;

    private String dir = null;

    private String forValue = null;

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

    private String style = null;

    private String styleClass = null;

    private String tabindex = null;

    private String title = null;

    public HtmlOutputLabel() {
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

    public void setFor(String forValue) {
        this.forValue = forValue;
    }

    public String getFor() {
        if (forValue != null) {
            return forValue;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.FOR_ATTR);
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

    public Object saveState(FacesContext context) {
        Object values[] = new Object[21];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = dir;
        values[3] = forValue;
        values[4] = lang;
        values[5] = onblur;
        values[6] = onclick;
        values[7] = ondblclick;
        values[8] = onfocus;
        values[9] = onkeydown;
        values[10] = onkeypress;
        values[11] = onkeyup;
        values[12] = onmousedown;
        values[13] = onmousemove;
        values[14] = onmouseout;
        values[15] = onmouseover;
        values[16] = onmouseup;
        values[17] = style;
        values[18] = styleClass;
        values[19] = tabindex;
        values[20] = title;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        dir = (String) values[2];
        forValue = (String) values[3];
        lang = (String) values[4];
        onblur = (String) values[5];
        onclick = (String) values[6];
        ondblclick = (String) values[7];
        onfocus = (String) values[8];
        onkeydown = (String) values[9];
        onkeypress = (String) values[10];
        onkeyup = (String) values[11];
        onmousedown = (String) values[12];
        onmousemove = (String) values[13];
        onmouseout = (String) values[14];
        onmouseover = (String) values[15];
        onmouseup = (String) values[16];
        style = (String) values[17];
        styleClass = (String) values[18];
        tabindex = (String) values[19];
        title = (String) values[20];
    }
}
